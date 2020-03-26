package de.axone.web;

import java.nio.file.Path;
import java.util.Comparator;

public interface PictureBuilderBuilder {

	public static final int DEFAULT_PRESCALE_SIZE = 2000;

	public PictureBuilderNG builder( String identifier, int index );

	public PictureBuilderBuilder watermark( String watermark );

	public PictureBuilderBuilder prescaleSize( int prescaleSize );


	/*
	public static PictureBuilderBuilder instance( Path homedir, Path maindir, Path cachedir, int hashLength, int prescaleSize ) {

		return new PictureBuilderBuilderImpl( homedir, maindir, cachedir, hashLength, prescaleSize );
	}
	*/

	public static PictureBuilderBuilder instance( Path homedir, Path maindir, Path cachedir, int hashLength ) {

		return new PictureBuilderBuilderImpl( homedir, maindir, cachedir, hashLength, DEFAULT_PRESCALE_SIZE );
	}

	public static PictureBuilderBuilder instance( Path homedir ) {

		return new PictureBuilderBuilderImpl( homedir, null, null, 1, DEFAULT_PRESCALE_SIZE );
	}


	public static class SameNameFirst implements Comparator<Path> {

		private final String identifier;

		public SameNameFirst( String identifier ) {

			this.identifier = identifier;
		}

		@Override
		public int compare( Path o1, Path o2 ) {

			Path f1 = o1.getFileName(),
			     f2 = o2.getFileName();

			if( identifier.equals( base( f1 ) ) ) return -1;
			if( identifier.equals( base( f2 ) ) ) return 1;

			return f1.compareTo( f2 );
		}

		private static final String base( Path path ) {

			Path file = path.getFileName();
			String filename = file.toString();
			int i = filename.lastIndexOf( '.' );

			if( i > 0 )
					return filename.substring( 0, i );
			else
					return filename;
		}
	}

}
