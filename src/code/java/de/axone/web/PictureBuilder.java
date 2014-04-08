package de.axone.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.async.ThreadQueue;

/**
 * Creates a watermarked picture and caches it
 *
 * @author flo
 */
public class PictureBuilder {
	
	public static final Logger log = LoggerFactory.getLogger( PictureBuilder.class );

	private final String mainDir;
	private final File cacheDir;
	private final File main;
	private final String identifier;
	private final int index;
	private final int hashLength;
	
	public PictureBuilder(File cacheDir, String identifier) {

		this( cacheDir, identifier, 0 );
	}

	public PictureBuilder(File cacheDir, String identifier, int index ) {
		this( cacheDir, "main", identifier, index, 1 );
	}
		
	public PictureBuilder(File cacheDir, String mainDir, String identifier, int index, int hashLength ) {

		this.cacheDir = cacheDir;
		this.mainDir = mainDir;
		this.identifier = identifier;
		this.hashLength = hashLength;
		String main = findMain( cacheDir, identifier, index );
		//E.rr( main );
		if( main != null ){
			this.main = new File( cacheDir, main );
		} else {
			this.main = null;
		}
		//E.rr( this.main );
		this.index = index;
	}

	/*
	 * Find the main file
	 *
	 * (the 'main' file is that file which we are working with)
	 */
	private String findMain( File path, String name, int index ) {
		
		//E.rr( path.getAbsolutePath() );
		//E.rr( name );
		//E.rr( index );

		StringBuilder builder = new StringBuilder();
		String hashDir = hashName( name, hashLength );

		//E.rr( mainDir );
		builder.append( '/' );
		if( mainDir != null ) builder.append( mainDir ).append( '/' );
		//E.rr( hashDir );
		if( hashDir != null ) builder.append( hashDir ).append( '/' );
		//E.rr( name );
		builder.append( name );;
		
		//E.rr( builder.toString() );
		
		// Try to find main file without dir operation
		boolean found = false;
		if( index == 0 ) {
			
			StringBuilder probe = new StringBuilder( builder );
			
			probe.append( '/' ).append( name ).append( ".jpg" );
			
			//E.rr( probe );
			
			File f = new File( path, probe.toString() );
			
			//E.rr( f.getAbsolutePath() );
			
			if( f.isFile() && f.canRead() ){
				found = true;
				builder = probe;
			}
		}
		
		//E.rr( found );
		
		if( !found ){
			File mainDir = new File( path, builder.toString() );
			
			//E.rr( mainDir.getAbsolutePath() );
			
			File[] mainFilesA = mainDir.listFiles( JPEG );

			if( mainFilesA != null ) {

				List<File> mainFiles = Arrays.asList( mainFilesA );
	
				Collections.sort( mainFiles, new JpegSorter( name ) );
	
				if( index >= mainFiles.size() ) {
					index = mainFiles.size() - 1;
				}
				
				if( index >= 0 ){
					builder.append( '/' ).append( mainFiles.get( index ).getName() );
					found = true;
				}
			}
		}
		//E.rr( found );

		if( found ){
			return builder.toString();
		} else {
			return null;
		}
	}

	public static int fileCount( File path, String name ){
		//TODO: Das passt so statisch nicht ...
		return fileCount( path, name, 1 );
	}
	public static int fileCount( File path, String name, int hashLength ){

		StringBuilder builder = new StringBuilder();
		String hashDir = hashName( name, hashLength );

		builder.append( "/main/" ).append( hashDir ).append( '/' )
				.append( name );

		File mainDir = new File( path, builder.toString() );

		File[] mainFiles = mainDir.listFiles( JPEG );

		if( mainFiles != null ){

    		return mainFiles.length;
		} else {
			return 0;
		}
	}

	public boolean exists() {
		
		return main != null && main.isFile() && main.canRead();
	}

	public File get( int size, File watermark ) throws IOException {
		
		return get( size, watermark, true, false );
	}

	// Der Lock muss wohl static sein, damit auch von verschiedenen Klassen
	// kein synchroner zugriff möglich ist.
	//private static String lock = "I am a unique Lock";
	private static ThreadQueue threadQueue = new ThreadQueue( 4 );

	private File get( int size, File watermark, boolean doPrescale, boolean hq )
			throws IOException {

		if( ! exists() ) return null;

		File cache = getCacheFile( size, watermark );
		
		//E.rr( cache.getAbsolutePath() );

		if( !cache.exists() ) {
			
			long start = System.currentTimeMillis();

    		//synchronized( lock ) {
			ThreadQueue.Lock lock = null;
			try {
				// Get lock. Only if called in outer recursion
				if( ! hq ) lock = threadQueue.lock( identifier + "___" + index );

    			// Second try. Other thread could have created it by now
    			// We do this because we don't want the lock to reach out to
    			// the first "getCacheFile" call
    			if( ! getCacheFile( size, watermark ).exists() ){

    				// Look for old version
    				File dir = cache.getParentFile();
    				File [] fileList = dir.listFiles( new OldFilenameFilter() );
    				if( fileList != null ) for( File oldFile : fileList ) {

    					boolean ok = oldFile.delete();
    					if( ! ok ) throw new IOException( "Cannot delete: " + oldFile.getAbsolutePath() );
    				}

    				// Get precached image if this is'nt a request for it
    				File imageFile;
    				if( doPrescale ) {
    					imageFile = get( 1000, null, false, true );
    				} else {
    					imageFile = main;
    				}

    				// Read in
    				BufferedImage inImage;
    				try{
    				inImage = ImageIO.read( imageFile );
    				} catch( IOException e ){
    					throw new IOException( "Cannot read: " + imageFile.getAbsolutePath(), e );
    				}

    				// Make watermark
    				if( watermark != null ) {

    					BufferedImage watermarkImage;
    					try {
        					watermarkImage = ImageIO.read( watermark );
        				} catch( IOException e ){
        					throw new IOException( "Cannot read watermark: " + watermark.getAbsolutePath(), e );
        				}


    					int ww = watermarkImage.getWidth();
    					int wh = watermarkImage.getHeight();

    					int iw = inImage.getWidth();
    					int ih = inImage.getHeight();

    					Graphics2D drawGraphics = inImage.createGraphics();
    					drawGraphics.drawImage( watermarkImage, iw - ww, ih - wh,
    							ww, wh, null );
    				}

    				// Scale
    				Dim d = mkOuterbox( size, inImage.getWidth(), inImage
    						.getHeight() );

    				BufferedImage resultImage = new BufferedImage( d.w, d.h,
    						BufferedImage.TYPE_INT_RGB );

    				Image scaled = inImage.getScaledInstance( d.w, d.h,
    						Image.SCALE_SMOOTH );

    				Graphics2D g2 = resultImage.createGraphics();

    				g2.drawImage( scaled, 0, 0, d.w, d.h, null );

    				// Quality can be higher for smaller images
    				float quality;
    				if( hq ) {
    					quality = 1f;
    				} else {
    					if( size <= 200 ) {
    						quality = .9f;
    					} else if( size <= 400 ) {
    						quality = .7f;
    					} else if( size <= 700 ) {
    						quality = .5f;
    					} else {
    						quality = .4f;
    					}
    				}

    				// ImageIO.write( resultImage, "jpeg", cache );
    				// Code from:
    				// http://www.universalwebservices.net/web-programming-resources/java/adjust-jpeg-image-compression-quality-when-saving-images-in-java
    				// Let you choose compression level
    				Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName( "jpeg" );
    				ImageWriter writer = iter.next();
    				ImageWriteParam iwp = writer.getDefaultWriteParam();
    				iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
    				iwp.setProgressiveMode( ImageWriteParam.MODE_DEFAULT );
    				iwp.setCompressionQuality( quality );
    				FileImageOutputStream output = new FileImageOutputStream( cache );
    				writer.setOutput( output );
    				IIOImage image = new IIOImage( resultImage, null, null );
    				writer.write( null, image, iwp );
    				output.close();
    			}
    		} finally {
    			if( ! hq ) threadQueue.releaseLock( lock );
    			long dur = System.currentTimeMillis() - start;
    			log.info( "Rendered in {} ms", dur );
    		}
		}

		return cache;
	}

	/*
	 * Calculate Size (x/y) using Outer-Box algorithm
	 *
	 * In words: Get the size so that the resulting image whould fit into
	 */
	private Dim mkOuterbox( int size, int originalWidth, int originalHeight ) {

		Dim res = new Dim();

		double max = originalWidth > originalHeight ? originalWidth
				: originalHeight;

		res.w = (int) ( ( originalWidth / max ) * size );
		res.h = (int) ( ( originalHeight / max ) * size );

		return res;
	}

	private File getCacheFile( int size, File watermark ) throws IOException {

		File dir = getCacheDir( size, watermark );

		long last = main.lastModified();

		File file = new File( dir, main.getName() + "_" + last + ".jpeg" );

		return file;
	}

	private File getCacheDir( int size, File watermark ) throws IOException {

		String waterDir;
		if( watermark != null ) {

			waterDir = hashWatermark( watermark );
		} else {
			waterDir = "plain";
		}

		// Create chache dir if not exists
		File subWatermark = new File( cacheDir, waterDir );
		String hash = hashName( identifier, hashLength );
		File subName;
		if( hash != null ){
			File subHash = new File( subWatermark, hash );
			subName = new File( subHash, identifier );
		} else {
			subName = new File( subWatermark, identifier );
		}
		File subSize = new File( subName, Integer.toString( size ) );

		// Only enter synchronized if needed
		if( ! subSize.isDirectory() ){
			synchronized( this ){
				createDir( cacheDir );
				createDir( subSize );
			}
		}

		return subSize;
	}

	private static void createDir( File dir ) throws IOException {

		if( !dir.exists() ) {
			boolean ok = dir.mkdirs();
			if( ! ok ){
				// This is some wired kind of thread issue. Should not happen because of the synchronized
				// but does anyways. Perhaps some filesystem problem? This prevents senseless error messages
				if( !dir.exists() ){
					throw new IOException( "Cannot create: " + dir.getAbsolutePath() );
				}
			}
		}

		if( !dir.isDirectory() )
			throw new IOException( "" + dir.getAbsolutePath()
					+ " exists but is no directory" );
	}

	/*
	 * Create hash for watermark currently this is filename but only printable
	 * chars
	 */
	private static String hashWatermark( File watermark ) {

		return watermark.getName().replaceAll( "\\W", "" ).toLowerCase();
	}

	/*
	 * Create hashString for directory hashing
	 */
	private static String hashName( String name, int length ) {
		
		//E.rr( name, length );
		
		if( length == 0 ) return null;

		int i = 0;

		// Remove trailing '0' chars
		while( i < name.length() - 1 && name.charAt( i ) == '0' ) {
			i++;
		}
		;

		if( name.length() > i ) {
			
			int endPos = i+length;
			if( endPos >= name.length() ) endPos = name.length()-1;

			return name.substring( i, endPos ).toLowerCase();
		} else {
			return "NOHASH";
		}
	}

	private static class Dim {
		int w, h;
	}

	private class OldFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept( File dir, String name ) {
			
			return name.startsWith( PictureBuilder.this.main.getName() );
		}
	}

	public static final JpegFilter JPEG = new JpegFilter();

	private static class JpegFilter implements FilenameFilter {

		@Override
		public boolean accept( File dir, String name ) {

			if( name.length() > 4
					&& ".jpg".equalsIgnoreCase( name
							.substring( name.length() - 4 ) )
					|| name.length() > 5
					&& ".jpeg".equalsIgnoreCase( name
							.substring( name.length() - 5 ) ) ) {
				return true;
			}

			return false;
		}
	}

	private static class JpegSorter implements Comparator<File> {

		private String mainFileName;

		public JpegSorter( String mainFileName ){

			this.mainFileName = mainFileName + ".jpg";
		}

		@Override
		public int compare( File o1, File o2 ) {

			if( o1.getName().equalsIgnoreCase( mainFileName ) ){
				return -1;
			}

			if( o2.getName().equalsIgnoreCase( mainFileName ) ){
				return 1;
			}

			return o1.getName().compareTo( o2.getName() );
		}

	}

	@Override
	public String toString(){

		StringBuilder result = new StringBuilder();

		result.append( "PB:" );
		result.append( " identifier: " + identifier );
		if( main != null )
    		result.append( " main: " )
    			.append( main.getAbsolutePath() );
		if( cacheDir != null )
			result.append( " cacheDir: " )
				.append( cacheDir.getAbsolutePath() );
		result.append( " index: " + index );

		return result.toString();
	}
}