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

import de.axone.tools.E;


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
				
				if( line.length() == 0  || line.charAt( 0 ) == '#' ) continue;
				
				String [] parts = line.split( "\\s+", 2 );
				String [] types  = parts[ 0 ].split( "/" );
				
				String [] exts = null;
				if( parts.length > 1 ) exts = parts[1].split( "\\s+" );
				else exts = new String[]{};
				
				MimeTypeImpl mime = new MimeTypeImpl( types[0], types[1], exts );
				
				for( String ext : mime.getExtensions() ){
					E.rr( ext, mime );
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
		
		// binary
		BIN( MG.application, "octect-stream" ),
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
		public String toString(){ return getGroup() + "/" + getType(); }
		
		@Override
		public String getGroup() { return group.name(); }
		@Override
		public String getType() { return type; }
		@Override
		@JsonIgnore
		public String[] getExtensions() { return ext; }
	}
	
	public static class MimeTypeImpl implements MimeType {
		
		private String
			group, type;
		private String [] ext;
		
		public MimeTypeImpl( String group, String type, String ... ext ){
			this.group = group;
			this.type = type;
			this.ext = ext;
		}
		
		@Override
		public String toString(){ return getGroup() + "/" + getType(); }
		
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
	}
	
}