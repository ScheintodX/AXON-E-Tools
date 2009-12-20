package de.axone.tools;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class UrlParser {

	private ArrayList<String> path;
	
	public UrlParser( File file, int trim ){
		
		init( file.getPath(), trim );
	}
	
	public UrlParser( URL url, int trim ){
		
		init( url.getPath(), trim );
	}
	
	private void init( String path, int trim ){
		
		String[] parts = path.split( "/" );

		LinkedList<String> myPath = new LinkedList<String>();

		int i = -1;
		for( String part : parts ) {

			i++;

			part = part.trim();

			if( ( i == 0 || i == parts.length - 1 ) && part.length() == 0 )
				continue;

			myPath.addLast( part );
			
			i++;
		}
		
		if( trim > 0 ){
			
			for( int t = 0; t < trim; t++ ){
				if( myPath.size() > 0 ) myPath.removeLast();
			}
			
		} else if( trim < 0 ){
			
			for( int t = 0; t < -trim; t++ ){
				if( myPath.size() > 0 ) myPath.removeFirst();
			}
		}

		this.path = new ArrayList<String>( myPath );
	}

	public UrlParser(URL url) {

		this( url, 0 );
	}

	public int length() {

		return path.size();
	}

	public String get( int i ) {

		return path.get( i );
	}
	
	public String path( String separator, boolean includeExtension ){
		return path( separator, 0, includeExtension );
	}
	public String path( String separator, int indent, boolean includeExtension ){
		
		StringBuilder result = new StringBuilder();
		
		for( int i = indent; i < path.size(); i++ ){
			
			String pathItem = path.get( i );
			
			if( i > 0 ) result.append( separator );
			
			if( ( i == path.size() -1 ) && !includeExtension ){
				
				result.append( removeExtension( pathItem ) );
			} else {
    			result.append( pathItem );
			}
		}
		
		return result.toString();
	}
	
	public String fileWithoutExtension(){
		
		if( length() == 0 ){
			return null;
		}
		
		String lastPart = path.get( length()-1 );
		return removeExtension( lastPart );
	}
	
	public String fileType(){
		
		if( length() == 0 )
			return null;
		
		String lastPart = path.get( length()-1 );
		return contentTypeFor( lastPart );
	}
	
	public static String removeExtension( String uri ){
		
		int extPos = uri.lastIndexOf( '.' );
		
		if( extPos > 0  ) {
			
			String withoutExtension = uri.substring( 0, extPos );
			
			return withoutExtension;
		} else {
			return uri;
		}
	}
	
	public static String extension( String uri ) {

		int extPos = uri.lastIndexOf( '.' );
		int slashPos = uri.lastIndexOf( '/' );

		if( extPos > 0 && extPos > slashPos ) {

			String extension = uri.substring( extPos + 1 );

			return extension;
		} else {
			return null;
		}
	}

	public static String contentTypeFor( String uri ) {

		String extension = extension( uri );

		if( extension != null ) {

			/* Images */
			if( extension.equalsIgnoreCase( "jpg" )
					|| extension.equalsIgnoreCase( "jpeg" ) ) {

				return "image/jpeg";
			} else if( extension.equalsIgnoreCase( "png" ) ) {
				return "image/png";
			} else if( extension.equalsIgnoreCase( "gif" ) ) {
				return "image/gif";
				/* css/js */
			} else if( extension.equalsIgnoreCase( "css" ) ) {
				return "text/css";
			} else if( extension.equalsIgnoreCase( "js" ) ) {
				return "text/javascript";
				/* html */
			} else if( extension.equalsIgnoreCase( "htm" )
					|| extension.equalsIgnoreCase( "html" )
					|| extension.equalsIgnoreCase( "xhtml" ) ) {
				return "text/html";
			} else if( extension.equalsIgnoreCase( "txt" ) ) {
				return "text/plain";
			}
		}

		return null;
	}

	public static String encodingFor( String uri ) {

		String contentType = contentTypeFor( uri );
		
		if( contentType == null ) return null;
		
		if( contentType.matches( "text.*" ) ){
				return "utf-8";
		}
		return null;
	}

	
}
