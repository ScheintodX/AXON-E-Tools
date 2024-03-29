package de.axone.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.async.ThreadQueue;
import de.axone.data.Mime;
import de.axone.data.Mime.MimeType;
import de.axone.data.Mime.MimeTypes;
import de.axone.data.tupple.Pair;
import de.axone.gfx.ImageScaler;
import de.axone.thread.ThreadsafeContractor;

class PictureBuilderBuilderImpl implements PictureBuilderBuilder {

	public static final Logger log = LoggerFactory.getLogger( PictureBuilderNG.class );

	private static final String MAIN = "main",
			PLAIN = "plain"
			;

	private final Path homedir,
	cachedir,
	maindir
	;

	private int prescaleSize;

	private final int hashLength;

	private Optional<Path> watermark = Optional.empty();

	private static ThreadQueue threadQueue = new ThreadQueue( 4 );


	/**
	 * @param homedir something like '/home/shop/pictures'
	 * @param maindir absolute or relative directory where uploaded files are stored.
	 * 			In case of <code>null</code> <code>homedir/main</code> is used
	 * @param cachedir absolute or relative directory for created files
	 * 			In case of <code>null</code> <code>homedir</code> is used
	 * @param hashLength length of the directories for storing a hashed version of the identifier
	 */
	PictureBuilderBuilderImpl( Path homedir, Path maindir, Path cachedir, int hashLength, int prescaleSize ) {

		this.homedir = homedir;

		if( maindir == null ) {
			this.maindir = homedir.resolve( MAIN );
		} else if( maindir.isAbsolute() ) {
			this.maindir = maindir;
		} else if( maindir.equals( homedir ) ) {
			this.maindir = maindir;
		} else {
			this.maindir = homedir.resolve( maindir );
		}

		if( cachedir == null ) {
			this.cachedir = homedir;
		} else if( cachedir.isAbsolute() ) {
			this.cachedir = cachedir;
		} else {
			this.cachedir = homedir.resolve( cachedir );
		}

		this.hashLength = hashLength;
		this.prescaleSize = prescaleSize;
	}

	// copy constructor
	private PictureBuilderBuilderImpl( PictureBuilderBuilderImpl other ) {

		this.homedir = other.homedir;
		this.maindir = other.maindir;
		this.cachedir = other.cachedir;
		this.hashLength = other.hashLength;
		this.prescaleSize = other.prescaleSize;
	}

	@Override
	public PictureBuilderBuilder watermark( String watermark ) {

		PictureBuilderBuilderImpl result = new PictureBuilderBuilderImpl( this );

		result.watermark = watermark != null ? Optional.of( Paths.get( watermark ) ) : Optional.empty();

		return result;
	}

	@Override
	public PictureBuilderBuilder prescaleSize( int prescaleSize ) {

		PictureBuilderBuilderImpl result = new PictureBuilderBuilderImpl( this );

		result.prescaleSize = prescaleSize;

		return result;
	}

	@Override
	public PictureBuilderNG builder( String identifier, int index ) {
		return new PictureBuilderNGImpl( identifier, index );
	}

	private class PictureBuilderNGImpl implements PictureBuilderNG {

		private final String identifier;
		private final int index;

		private final String hashName;

		public PictureBuilderNGImpl( String identifier, int index ) {
			this.identifier = identifier;
			this.index = index;

			this.hashName = hashLength > 0 ? hashName( identifier, hashLength ) : null;
		}

		@Override
		public int fileCount() {

			Path dir = mainDir();

			if( ! Files.isDirectory( dir ) ) return 0;

			int result;

			try( Stream<Path> stream = Files.list( dir ) ) {

				result = (int) stream
						.filter( PIXEL )
						.count();

			} catch( IOException e ) {
				throw new Error( "Error reading: " + dir );
			}

			return result;

		}

		@Override
		public boolean exists() {

			Optional<Path> mainFile = mainFile();

			return mainFile.isPresent() && Files.isReadable( mainFile.get() );
		}

		@Override
		public @Nullable FileTime mtime() throws IOException {

			Optional<Path> mainFile = mainFile();

			if( !mainFile.isPresent() ) return null;

			return Files.getLastModifiedTime( mainFile.get() );
		}

		@Override
		public Optional<Pair<Path,Boolean>> get( int size ) throws IOException {

			return get( size, watermarkFile(), true, false );
		}

		private Path mainDir() {
			return pathTo( maindir, hashName, identifier );
		}

		private Optional<Path> watermarkFile() {

			if( !watermark.isPresent() ) return Optional.empty();

			Path wfile = homedir
					.resolve( watermark.get() )
					;

			if( ! Files.exists( wfile ) ) return Optional.empty();

			return Optional.of( wfile );
		}

		private Optional<Path> cachedFile( int size, Optional<Path> watermark ) throws IOException {

			Path fileCacheDir = createCacheDir( cachedir, identifier, size, hashName, watermark );

			Optional<Path> mainResult = mainFile();

			if( !mainResult.isPresent() ) return Optional.empty();

			Path main = mainResult.get();

			Optional<String> ext = ext( main );

			if( !ext.isPresent() ) return Optional.empty();

			String extX = ext.get().toLowerCase();
			if( "jpg".equals( extX ) ) { extX = "jpeg"; }

			Path mainPath = fileCacheDir.resolve( main.getFileName().toString() + '_' +
					Files.getLastModifiedTime( main ).toMillis() + '.'  + extX );

			return Optional.of( mainPath );
		}

		private Optional<Pair<Path,Boolean>> get( int size, Optional<Path> watermark, boolean doPrescale, boolean hq )
				throws IOException {

			if( ! exists() ) return Optional.empty();

			boolean hasChanged = false;

			Optional<Path> cachedFileResult = cachedFile( size, watermark );
			if( ! cachedFileResult.isPresent() ) return Optional.empty();
			Path cachedFile = cachedFileResult.get();

			/*
			if( ! Files.isRegularFile( cachedFile ) ) {
				E.rr( "MISS: ", cachedFile );
			}
			*/

			if( ! Files.isRegularFile( cachedFile ) ) {

				log.trace( "MISS: {}", cachedFile );

				long start = System.currentTimeMillis();

				Optional<Path> mainFileResult = mainFile();
				if( !mainFileResult.isPresent() ) return Optional.empty();
				Path mainFile = mainFileResult.get();

				//synchronized( lock ) {}
				ThreadQueue.Lock lock = null;
				try {
					// Get lock. Only if called in outer recursion
					if( ! hq ) {
						lock = threadQueue.lock( identifier + "___" + index );
					}

					// Second try. Other thread could have created it by now
					// We do this because we don't want the lock to reach out to
					// the first "getCacheFile" call
					Optional<Path> secondTry = cachedFile( size, watermark );
					if( ! secondTry.isPresent() ) return Optional.empty();

					if( ! Files.isRegularFile( secondTry.get() ) ){

						// Look for old version
						Path cacheDir = cachedFile.getParent();

						List<Path> oldVersions;
						try( Stream<Path> stream = Files.list( cacheDir ) ) {
							oldVersions = stream
									.filter( new OldVersionOf( cachedFile ) )
									.collect( Collectors.toList() )
									;
						}

						for( Path p : oldVersions ) {

							boolean ok = Files.deleteIfExists( p );
							if( ! ok ) throw new IOException( "Cannot delete: " + p );
						}

						// Get precached image if this isn't a request for it
						Optional<Pair<Path,Boolean>> imageFile;
						if( doPrescale ) {
							imageFile = get( prescaleSize, Optional.empty(), false, true );
						} else {
							imageFile = Optional.of( new Pair<>( mainFile, false ) );
						}

						if( !imageFile.isPresent() )
							return Optional.empty();

						ImageScaler.instance().scale( cachedFile, imageFile.get().getLeft(), watermark, size, hq );

						hasChanged = true;
					}

				} finally {
					if( ! hq ) {
						threadQueue.releaseLock( lock );
					}
					long dur = System.currentTimeMillis() - start;
					log.info( "{} Rendered in {} ms", cachedFile, dur );
				}
			}

			return Optional.of( new Pair<>( cachedFile, hasChanged ) );
		}

		@Override
		public String toString() {
			return identifier + '/' + index;
		}

		private Optional<Path> mainFile;
		Optional<Path> mainFile() {

			if( mainFile == null ) {
				try {
					mainFile = findFile( maindir, hashName, identifier, index );
				} catch( IOException e ) {
					mainFile = Optional.empty();
					throw new Error( e );
				}
			}
			return mainFile;
		}


		private class OldVersionOf implements Predicate<Path> {

			private final String mainFileName;

			OldVersionOf( Path mainFile ) {

				mainFileName = mainFile.getFileName().toString();
			}

			@Override
			public boolean test( Path path ) {
				return path.getFileName().toString().startsWith( mainFileName );
			}

		}


		@Override
		public Path lookingAt() {
			return mainDir();
		};

	}

	private static ThreadsafeContractor cacheDirLocker8 =
			new ThreadsafeContractor();

	private static Path createCacheDir( Path cachedir, String identifier, int size, String hashName, Optional<Path> watermark ) throws IOException {

		// Pictures without watermark got in PLAIN dir.
		Optional<String> hashedWatermark = hashWatermark( watermark );
		String watername = hashedWatermark.isPresent() ? hashedWatermark.get() : PLAIN;

		// .../cache/shoppng
		Path dir = cachedir.resolve( watername );

		// .../cache/shoppng/1/12345
		Path sub = pathTo( dir, hashName, identifier );

		// .../cache/shoppng/1/12345/1000
		Path sub2 = sub.resolve( Integer.toString( size ) );

		// Create chache dir if not exists
		// Only enter synchronized if needed

		cacheDirLocker8
		.when( () -> { return ! Files.isDirectory( sub2 ); } )
		.then( () -> { Files.createDirectories( sub2 ); } )
		;

		return sub2;
	}


	static Optional<Path> findFile( Path maindir, String hashName, String identifier, int index ) throws IOException {

		Path dir = pathTo( maindir, hashName, identifier );

		if( ! Files.isDirectory( dir ) ) return Optional.empty();

		if( index <= 0 ) {

			Path candidate = dir.resolve( identifier + ".jpg" );

			if( Files.exists( candidate ) )
					return Optional.of( candidate );
		}

		List<Path> files;
		try( Stream<Path> stream = Files.list( dir ) ) {
			files = stream
					.filter( PIXEL )
					.sorted( new SameNameFirst( identifier ) )
					.collect( Collectors.toList() )
					;
		}

		if( files.size() <= index ) return Optional.empty();

		return Optional.of( files.get( index ) );
	}

	private static Path pathTo( Path dir, String hashName, String identifier ) {

		if( hashName != null )
			return dir.resolve( hashName ).resolve( identifier );
		else
			return dir.resolve( identifier );
	}

	static String hashName( String name, int length ) {

		int nameLen = name.length();

		if( length >= nameLen ) return name.toLowerCase();

		// Find trailing '0' chars
		int iNon0;
		for( iNon0 = 0; iNon0 < nameLen-length; iNon0++ ) {

			if( name.charAt( iNon0 ) != '0' ) {
				break;
			}
		}

		return name.substring( iNon0, iNon0+length ).toLowerCase();
	}

	static Optional<String> hashWatermark( Optional<Path> watermark ) {

		if( ! watermark.isPresent() ) return Optional.empty();
		return Optional.of( watermark.get().getFileName().toString().replaceAll( "\\W", "" ).toLowerCase() );
	}

	public static final Predicate<Path> PIXEL = ( path ) -> {

		Optional<String> ext = ext( path );

		if( !ext.isPresent() ) return false;

		MimeType type = Mime.ForExtension( ext.get() );

		return type.isOneOf( MimeTypes.JPG, MimeTypes.PNG );
	};

	private static final Optional<String> ext( Path path ) {

		Path file = path.getFileName();
		String filename = file.toString();
		int i = filename.lastIndexOf( '.' );

		if( i > 0 && i < filename.length() )
				return Optional.of( filename.substring( i+1 ) );

		return Optional.empty();
	}


}
