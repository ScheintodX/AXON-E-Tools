package de.axone.tools;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import de.axone.data.Charsets;

public class UrlParser {

	private boolean startsWithSlash;
	private boolean endsWithSlash;
	private ArrayList<String> path;
	
	public UrlParser( File file, int trim ){
		
		init( file.getPath(), trim );
	}
	
	public UrlParser( URL url, int trim ){
		
		init( url.getPath(), trim );
	}
	
	private void init( String path, int trim ){
		
		path = path.trim();
		int len = path.length();
		
		if( len > 0 && path.charAt( 0 ) == '/' ){
			startsWithSlash = true;
		}
		if( len > 0 && path.charAt( len-1 ) == '/' ){
			endsWithSlash = true;
		}
		
		if( startsWithSlash ){
			path = path.substring( 1 );
			len--;
		}
		if( endsWithSlash && len > 0 ){
			path = path.substring( 0, path.length()-1 );
			//len--;
		}
		
		String[] parts = path.split( "/" );

		LinkedList<String> myPath = new LinkedList<String>();
		
		for( String part : parts ) {

			part = part.trim();
			myPath.addLast( part );
		}
		
		if( myPath.size() == 1 ){
			String part = myPath.get( 0 );
			if( part.length() == 0 ) myPath.remove( 0 );
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
		return contentTypeFor( uri, false );
	}
	public static String contentTypeFor( String uri, boolean modern ) {

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
			} else if( extension.equalsIgnoreCase( "svg" ) ) {
				return "image/svg+xml";
			} else if( extension.equalsIgnoreCase( "ico" ) ) {
				return "image/x-icon";
			} else if( extension.equalsIgnoreCase( "css" ) ) {
				return "text/css";
			} else if( extension.equalsIgnoreCase( "scss" ) ) {
				return "text/css";
			} else if( extension.equalsIgnoreCase( "sass" ) ) {
				return "text/css";
			} else if( extension.equalsIgnoreCase( "js" ) ) {
				return "text/javascript";
				/* html */
			} else if( extension.equalsIgnoreCase( "htm" )
					|| extension.equalsIgnoreCase( "html" ) ){
				return "text/html";
			} else if( extension.equalsIgnoreCase( "xhtml" ) ) {
				if( modern ){
					return "application/xhtml+xml";
				} else {
					return "text/html";
				}
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
				return Charsets.utf8;
		}
		return null;
	}

	public boolean startsWithSlash(){
		return startsWithSlash;
	}
	public boolean endsWithSlash(){
		return endsWithSlash;
	}
	
}
