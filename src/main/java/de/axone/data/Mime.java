package de.axone.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Mime {

	private Map<String,MimeType> extendedTypesForExtension;

	public static final MimeType UNKNOWN = new MimeTypeImpl( "inode", "file" );
	public static final MimeType DIRECTORY = new MimeTypeImpl( "inode", "directory" );

	public MimeType forExtension( String extension ){
		MimeType t;
		if( extendedTypesForExtension != null ){
			t = extendedTypesForExtension.get(  extension  );
			if( t != null ) return t;
		}
		t = MimeTypes.forExtension( extension );
		if( t != null ) return t;

		return UNKNOWN;
	}

	public void load( File mimeFile ) throws IOException{

		extendedTypesForExtension = new HashMap<>();

		try( BufferedReader fin = new BufferedReader( new FileReader( mimeFile ) ) ){

			String line;
			while( ( line = fin.readLine() ) != null ){

				line = line.trim();

				if( line.length() == 0  || line.charAt( 0 ) == '#' ) {
					continue;
				}

				String [] parts = line.split( "\\s+", 2 );
				String [] types  = parts[ 0 ].split( "/" );

				String [] exts = null;
				if( parts.length > 1 ) {
					exts = parts[1].split( "\\s+" );
				} else {
					exts = new String[]{};
				}

				MimeTypeImpl mime = new MimeTypeImpl( types[0], types[1], exts );

				for( String ext : mime.getExtensions() ){
					extendedTypesForExtension.put( ext, mime );
				}
			}
		}

	}

	private enum MG {
		image, application, text;
	}

	public static MimeType ForExtension( String ext ){
		MimeType t = MimeTypes.forExtension( ext );
		if( t != null ) return t;
		return UNKNOWN;
	}
	public static MimeType ForFile( String filename ){
		if( ! filename.contains( "." ) ) return UNKNOWN;
		return ForExtension( filename.substring(
				filename.lastIndexOf( "." )+1, filename.length() ) );
	}
	public static MimeType ForFile( File file ){
		if( file.isDirectory() ) return DIRECTORY;
		return ForFile( file.getName() );
	}
	public static MimeType ForFile( Path path ){
		if( path.toFile().isDirectory() ) return DIRECTORY;
		return ForFile( path.toFile().getName() );
	}

	public enum MimeTypes implements MimeType {

		// image
		JPG( MG.image, "jpeg", "jpg", "jpeg" ),
		PNG( MG.image, "png", "png" ),
		GIF( MG.image, "gif", "gif" ),
		SVG( MG.image, "svg+xml", "svg" ),

		// text
		HTML( MG.text, "html", "htm", "html" ),
		XHTML( MG.text, "xhtml+xml", "xhtm", "xhtml" ),
		PLAIN( MG.text, "plain", "txt" ),

		// code
		JAVASCRIPT( MG.application, "javascript", "js" ),
		JSON( MG.application, "json" ),
		XML( MG.application, "xml" ),

		// binary
		BIN( MG.application, "octect-stream" ),

		// X
		GZIP( MG.application, "x-gzip" )
		;


		// Map for extension resolution of enums
		private static final Map<String,MimeType> basicTypesForExtension;
		static {
			basicTypesForExtension = new TreeMap<>();
			for( MimeType type : MimeTypes.values() ){
				for( String ext : type.getExtensions() ){
					basicTypesForExtension.put( ext, type );
				}
			}
		}

		public static MimeType forExtension( String extension ){
			return basicTypesForExtension.get( extension );
		}

		private final MG group;
		private final String type;
		private final String [] ext;
		MimeTypes( MG group, String type, String ... ext ){
			this.group = group;
			this.type = type;
			this.ext = ext;
		}

		@Override
		public String getGroup() { return group.name(); }
		@Override
		public String getType() { return type; }
		@Override
		@JsonIgnore
		public String[] getExtensions() { return ext; }

		@Override
		public String toString(){ return text(); }
	}

	public static class MimeTypeImpl implements MimeType {

		private final String
			group, type;
		private final String [] ext;

		public MimeTypeImpl( String group, String type, String ... ext ){
			this.group = group;
			this.type = type;
			this.ext = ext;
		}

		@Override
		public String toString(){ return text(); }

		@Override
		public String getGroup() { return group; }
		@Override
		public String getType() { return type; }
		@Override
		@JsonIgnore
		public String[] getExtensions() { return ext; }
	}

	public static interface MimeType {
		public String getGroup();
		public String getType();
		public String [] getExtensions();
		public default String text(){ return getGroup() + "/" + getType(); }


		public default boolean is( Path path ) {
			return is( path.getFileName().toString() );
		}
		public default boolean is( File file ) {
			return extIs( ext( file.getPath() ) );
		}
		public default boolean is( String filename ) {
			return extIs( ext( filename ) );
		}
		public default boolean extIs( String ext ) {

			String[] exts = getExtensions();

			for( int i=0; i<exts.length; i++ ) {

				if( exts[ i ].equals( ext ) ) return true;
			}

			return false;
		}
		public default boolean is( MimeType other ) {
			return getGroup().equals( other.getGroup() ) &&
					getType().equals( other.getType() );
		}
		public default boolean isOneOf( MimeType ... others ) {
			for( MimeType o : others ) {
				if( is( o ) ) return true;
			}
			return false;
		}
	}

	private static final String ext( String filename ) {

		int i = filename.lastIndexOf( '.' );

		if( i > 0 && i < filename.length() -1 )
			return filename.substring( i +1 );
		return "";
	}

}
