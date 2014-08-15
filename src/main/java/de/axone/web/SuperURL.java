package de.axone.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import de.axone.exception.Assert;
import de.axone.tools.Str;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_Copy;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_Request;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_String;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_URI;
import de.axone.web.encoding.AttributeEncoder;
import de.axone.web.encoding.HtmlEncoder;


/**
 * Extended and more usefull version of Javas URI class whereas
 * it uses URI wherever possible to do de/encoding stuff but
 * privides a wider range of manipulation functions. (Setters!!!)
 * 
 * A URI looks like this: 
 * 
 * scheme://user:pass@host:port/path?query#fragment
 * 
 * scheme: e.g. http
 * user: the user
 * pass: the pass or nothing
 * host: www.subdomain.myhost.com
 * port: 80. mostly nothing
 * path: blah/blo/blu/bläh
 * query: key1=value1&key2=value2;key3=value3
 * fragment: something
 * 
 * More for URLs: RFC 2396
 * 
 * Note that all encoding is done using UTF-8!
 * 
 * see <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
 * @author flo
 */
public final class SuperURL {
	
	private static final String UTF8 = "utf-8";

	public enum Part {
		Scheme, UserInfo, Host, Port, Path, Query, Fragment
	}
	
	public static final Set<Part> ALL_PARTS = Collections.unmodifiableSet( EnumSet.allOf( Part.class ) ),
	                              NO_PARTS = Collections.unmodifiableSet( EnumSet.noneOf( Part.class ) ),
	                              UPTO_PATH = Collections.unmodifiableSet( EnumSet.of( Part.Scheme, Part.UserInfo, Part.Host, Part.Port ) ),
	                              FROM_PATH = Collections.unmodifiableSet( EnumSet.of( Part.Path, Part.Query, Part.Fragment ) )
	                              ;
	
	private String scheme;
	boolean includeScheme = true;
	private UserInfo userInfo;
	boolean includeUserInfo = true;
	private Host host;
	boolean includeHost = true;
	private Integer port;
	boolean includePort = true;
	private Path path;
	boolean includePath = true;
	private Query query;
	boolean includeQuery = true;
	private String fragment;
	boolean includeFragment = true;
	
	public SuperURL(){}
	
	public SuperURL( String scheme, UserInfo userInfo, Host host, Integer port, Path path, Query query, String fragment ){
		
		if( scheme != null ){
			this.scheme = scheme;
			this.includeScheme = true;
		}
		if( userInfo != null ){
			this.userInfo = userInfo;
			this.includeUserInfo = true;
		}
		if( host != null ){
			this.host = host;
			this.includeHost = true;
		}
		if( port != null ){
			this.port = port;
			this.includePort = true;
		}
		if( path != null ){
			this.path = path;
			this.includePath = true;
		}
		if( query != null ){
			this.query = query;
			this.includeQuery = true;
		}
		if( fragment != null ){
			this.fragment = fragment;
			this.includeFragment = true;
		}
	}
	
	/* --- Direct Access --- */
	
	public String getScheme() {
		return scheme;
	}
	public SuperURL setScheme( String scheme ) {
		this.scheme = scheme;
		return this;
	}
	public SuperURL setIncludeScheme( boolean includeScheme ){
		this.includeScheme = includeScheme;
		return this;
	}
	public boolean isIncludeScheme(){
		return includeScheme;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
	public SuperURL setUserInfo( UserInfo userInfo ) {
		this.userInfo = userInfo;
		return this;
	}
	public SuperURL setIncludeUserInfo( boolean includeUserInfo ){
		this.includeUserInfo = includeUserInfo;
		return this;
	}
	public boolean isIncludeUserInfo(){
		return includeUserInfo;
	}

	public Host getHost() {
		return host;
	}
	public void setHost( Host host ) {
		this.host = host;
	}
	public void setIncludeHost( boolean includeHost ){
		this.includeHost = includeHost;
	}
	public boolean isIncludeHost(){
		return includeHost;
	}

	public Integer getPort() {
		return port;
	}
	public SuperURL setPort( Integer port ) {
		this.port = port;
		return this;
	}
	public SuperURL setIncludePort( boolean includePort ){
		this.includePort = includePort;
		return this;
	}
	public boolean isIncludePort(){
		return includePort;
	}

	public String getFragment() {
		return fragment;
	}
	public SuperURL setFragment( String fragment ) {
		this.fragment = fragment;
		return this;
	}
	public SuperURL setIncludeFragment( boolean includeFragment ){
		this.includeFragment = includeFragment;
		return this;
	}
	public boolean isIncludeFragment(){
		return includeFragment;
	}
	
	// TODO: Da ist das automatische erzeugen des Pfades dazu gekommen.
	// Das können jetzt also beim Aufrufer Checks entfernt werden.
	public Path getPath() {
		if( path == null ) path = new Path();
		return path;
	}
	public SuperURL setPath( Path path ) {
		this.path = path;
		return this;
	}
	public SuperURL setIncludePath( boolean includePath ){
		this.includePath = includePath;
		return this;
	}
	public boolean isIncludePath(){
		return includePath;
	}

	public Query getQuery() {
		if( query == null ) query = new Query();
		return query;
	}
	public SuperURL setQuery( Query query ) {
		this.query = query;
		return this;
	}
	public SuperURL setIncludeQuery( boolean includeQuery ){
		this.includeQuery = includeQuery;
		return this;
	}
	public boolean isIncludeQuery(){
		return includeQuery;
	}
	
	/* --- Helpers --- */
	public SuperURL setQueryParameter( String key, String value ){
		getQuery().setValue( key, value );
		return this;
	}
	
	public String getQueryParameter( String key ){
		
		return getQuery().getValue( key );
	}
	
	public boolean hasHost(){
		return getHost() != null;
	}
	public boolean hasPath(){
		return getPath() != null
			&& getPath().toString().length() > 0;
	}
	
	public SuperURL appendPath( String path ){
		return appendPath( path, true );
	}
	public SuperURL appendPath( String path, boolean decode ){
		
		Path newPath = SuperURL.Path.parse( path, decode );
		getPath().append( newPath );
		getPath().endsWithSlash = newPath.endsWithSlash;
		return this;
	}
	
	/* --- ToString --- */
	
	public StringBuilder toStringBB( StringBuilder result, boolean encode ){
		
		if( includeScheme && scheme != null ){
			result.append( scheme ).append( "://" );
		}
		
		if( includeUserInfo && userInfo != null ){
			userInfo.toStringBB( result, encode );
			result.append( '@' );
		}
		
		if( includeHost && host != null ){
			host.toStringBB( result, encode );
		}
		
		if( includePort && port != null ){
			result.append( ':' ).append( port );
		}
		
		if( includePath && path != null ){
			if( ! path.startsWithSlash && path.length() > 0 ) result.append( '/' );
			path.toStringBB( result, encode );
		}
		
		if( includeQuery && query != null && query.size() > 0 ){
			result.append( '?' );
			query.toStringBB( result, encode );
		}
		
		if( includeFragment && fragment != null ){
			
			String fragmentStr = fragment;
			if( encode ) fragmentStr = encode( fragmentStr );
			result.append( '#' ).append( fragmentStr );
		}
		
		return result;
	}
	public StringBuilder toStringB( boolean encode ){
		return toStringBB( new StringBuilder(), encode );
	}
	public String toStringEncode( boolean encode ){
		return toStringB( encode ).toString();
	}
	@Deprecated
	@Override
	public String toString(){
		return toStringEncode( true );
	}
	
	public String toRedirect(){
		
		return toStringEncode( true );
	}
	
	public String toHtml(){
		
		return HtmlEncoder.ENCODE( toStringEncode( false ) );
	}
	
	public String toAttribute(){
		
		return AttributeEncoder.ENCODE( toStringEncode( true ) );
	}
	
	public String toText() {
		
		return toStringEncode( false );
	}
	
	public String toValue() {
		
		return toStringEncode( true );
	}
	
	public void writeInHolder( Writer writer ) throws IOException{
		writer.write( toStringEncode( true ) );
	}

	public static String encode( String text ) {
		
		if( text == null ) return text;
		
		try {
			return URLEncoder.encode( text, UTF8 );
		} catch( UnsupportedEncodingException e ) {
			throw new IllegalArgumentException( "Cannot urlify " + text );
		}
	}
	
	private static final String decode( String value ){
		
		if( value == null ) return null;
		
		try {
			return URLDecoder.decode( value, UTF8 );
		} catch( UnsupportedEncodingException e ) {
			throw new IllegalArgumentException( "Undecodeable: '" + value + "'" );
		}
	}
	
	public URL toURL() {
		try {
			return new URL( toStringEncode( false ) );
		} catch( MalformedURLException e ){
			throw new IllegalStateException( 
					"Cannot create URL for: " + this.toStringEncode( false ), e );
		}
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( includeScheme ? 1231 : 1237 );
		result = prime * result + ( ( scheme == null ) ? 0 : scheme.hashCode() );
		result = prime * result + ( includeUserInfo ? 1231 : 1237 );
		result = prime * result + ( ( userInfo == null ) ? 0 : userInfo.hashCode() );
		result = prime * result + ( includeHost ? 1231 : 1237 );
		result = prime * result + ( ( host == null ) ? 0 : host.hashCode() );
		result = prime * result + ( includePort ? 1231 : 1237 );
		result = prime * result + ( ( port == null ) ? 0 : port.hashCode() );
		result = prime * result + ( includePath ? 1231 : 1237 );
		result = prime * result + ( ( path == null ) ? 0 : path.hashCode() );
		result = prime * result + ( includeQuery ? 1231 : 1237 );
		result = prime * result + ( ( query == null ) ? 0 : query.hashCode() );
		result = prime * result + ( includeFragment ? 1231 : 1237 );
		result = prime * result + ( ( fragment == null ) ? 0 : fragment.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof SuperURL ) ) return false;
		
		SuperURL other = (SuperURL) obj;
		
		return equals( other, ALL_PARTS );
	}
	
	public boolean equals( SuperURL other, Set<Part> parts ) {
		
		if( parts.contains( Part.Scheme ) ){
			if( includeScheme != other.includeScheme ) return false;
			if( scheme == null ) {
				if( other.scheme != null ) return false;
			} else if( !scheme.equals( other.scheme ) ) return false;
		}
				
		if( parts.contains( Part.UserInfo ) ){
			if( includeUserInfo != other.includeUserInfo ) return false;
			if( userInfo == null ) {
				if( other.userInfo != null ) return false;
			} else if( !userInfo.equals( other.userInfo ) ) return false;
		}
		
		if( parts.contains( Part.Host ) ){
			if( includeHost != other.includeHost ) return false;
			if( host == null ) {
				if( other.host != null ) return false;
			} else if( !host.equals( other.host ) ) return false;
		}
			
		if( parts.contains( Part.Port ) ){
			if( includePort != other.includePort ) return false;
			if( port == null ) {
				if( other.port != null ) return false;
			} else if( !port.equals( other.port ) ) return false;
		}
			
		if( parts.contains( Part.Path ) ){
			if( includePath != other.includePath ) return false;
			if( path == null ) {
				if( other.path != null ) return false;
			} else if( !path.equals( other.path ) ) return false;
		}
			
		if( parts.contains( Part.Query ) ){
			if( includeQuery != other.includeQuery ) return false;
			if( query == null ) {
				if( other.query != null ) return false;
			} else if( !query.equals( other.query ) ) return false;
		}
			
		if( parts.contains( Part.Fragment ) ){
			if( includeFragment != other.includeFragment ) return false;
			if( fragment == null ) {
				if( other.fragment != null ) return false;
			} else if( !fragment.equals( other.fragment ) ) return false;
		}
		
		return true;
	}

	public static final class Host implements Iterable<String>{
		
		public List<String> parts;
		
		public Host(){
			parts = new LinkedList<String>();
		}
		
		public static Host parse( String parseMe ){
			return parse( parseMe, true );
		}
		public static Host parse( String parseMe, boolean decode ){
			
			Host result = new Host();
			
			String [] parts = Str.splitFast( parseMe, '.' );
			
			if( decode ){
				for( int i=0; i<parts.length; i++ ){
					parts[ i ] = decode( parts[ i ] );
				}
			}
			
			result.parts = new ArrayList<>( Arrays.asList( parts ) );
			
			return result;
		}
		
		public Host copy() {
			Host result = new Host();
			result.parts.addAll( parts );
			return result;
		}
    	
    	public List<String> getParts(){
    		return parts;
    	}
    	public Host setParts( LinkedList<String> parts ){
    		this.parts = parts;
    		return this;
    	}
    	public int getSize(){
    		return this.parts.size();
    	}
    	public String getTld(){
    		if( parts != null && parts.size() > 0 ) return parts.get( parts.size()-1 );
    		else return null;
    	}
    	public String getHost(){
    		if( parts != null && parts.size() > 0 ) return parts.get( 0 );
    		else return null;
    	}
    	public List<String> getNet(){
    		if( parts != null && parts.size() > 2 ){
        		LinkedList<String> result = new LinkedList<String>();
        		for( int i = 1; i < parts.size()-1; i++ ){
        			result.addLast( parts.get( i ) );
        		}
        		return result;
    		} else {
    			return null;
    		}
    	}
    	
    	public String getNetAsString(){
    		return Str.join( ".", getNet() );
    	}
    	
    	public StringBuilder toStringBB( StringBuilder result, boolean encode ){
    		
    		boolean first = true;
    		for( String part : parts ){
    			
    			if( encode ) part = encode( part );
    			
    			if( first ) first = false;
    			else result.append( '.' );
    			
    			result.append( part );
    		}
    		return result;
    	}
    	public StringBuilder toStringB( boolean encode ){
    		return toStringBB( new StringBuilder(), encode );
    	}
    	public String toString( boolean encode ){
    		return toStringB( encode ).toString();
    	}
    	@Override
    	public String toString(){
    		return toString( false );
    	}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( parts == null ) ? 0 : parts.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof Host ) ) return false;
			
			Host other = (Host) obj;
			
			if( parts == null ) {
				if( other.parts != null ) return false;
			} else if( !parts.equals( other.parts ) ) return false;
			
			return true;
		}

		public List<String> toList() {
			return new ArrayList<>( parts );
		}

		@Override
		public Iterator<String> iterator() {
			return parts.iterator();
		}
    	
	}
	
	public static final class Path implements Iterable<String> {
		
		private LinkedList<String> path;
		private boolean endsWithSlash;
		private boolean startsWithSlash;
		
		public Path(){
			this( new LinkedList<String>(), false );
		}
		public Path( LinkedList<String> path, boolean endsWithSlash ){
			this.path = path;
			this.endsWithSlash = endsWithSlash;
		}
		
		public static Path parse( String parseMe ){
			return parse( parseMe, true );
		}
		
		public static Path parse( String parseMe, boolean decode ){
			
			Path result = new Path();
			
			if( parseMe == null || parseMe.length() == 0 ){
				return result;
			}
			
			if( "/".equals( parseMe ) ){
				result.setStartsWithSlash( true );
				return result;
			}
			
			String[] parts = Str.splitFast( parseMe, '/' );
			
			ArrayList<String> asList = new ArrayList<>( parts.length );
			
			for( int i = 0; i < parts.length ; i++ ){
				
				String part = parts[ i ];
				
				if( i == 0 ){
					if( part.length() == 0 ){
						result.startsWithSlash = true;
						continue;
					}
				}
				if( i == parts.length -1 ){
					if( part.length() == 0 ){
						result.endsWithSlash = true;
						continue;
					}
				}
				
				if( decode ) part = decode( part );
				
				asList.add( part );
			}
			
			result.path.addAll( asList );
			
			return result;
		}
		
		public Path copy() {
			Path result = new Path();
			result.path.addAll( path );
			result.endsWithSlash = endsWithSlash;
			result.startsWithSlash = startsWithSlash;
			return result;
		}
		
		public int length(){
			return path.size();
		}
		
		public String get( int index ){
			return path.get( index );
		}
		
		public boolean isEndsWithSlash(){
			return endsWithSlash;
		}
		public Path setEndsWithSlash( boolean isEndsWithSlashes ){
			endsWithSlash = isEndsWithSlashes;
			return this;
		}
		
		public boolean isStartsWithSlash(){
			return startsWithSlash;
		}
		public Path setStartsWithSlash( boolean isStartsWithSlashes ){
			startsWithSlash = isStartsWithSlashes;
			return this;
		}
		
		public boolean isAbsolute(){
			return isStartsWithSlash();
		}
		public boolean isRelative(){
			return !isAbsolute();
		}
		
		public String getLast(){
			if( path != null && path.size() > 0 ) return path.getLast();
			return null;
		}
		public Path replaceLast( String part ){
			Assert.notNull( part, "part" );
			Assert.notEmpty( part, "part" );
			if( path != null && path.size() > 0 ) path.set( path.size()-1, part );
			return this;
		}
		public Path addLast( String part ){
			Assert.notNull( part, "part" );
			Assert.notEmpty( part, "part" );
			if( path == null ) path = new LinkedList<String>();
			path.addLast( part );
			return this;
		}
		public Path addAll( Path path ){
			for( String item : path ){
				addLast( item );
			}
			setEndsWithSlash( path.isEndsWithSlash() );
			return this;
		}
		public Path addAll( String ... path ){
			for( String item : path ){
				addLast( item );
			}
			return this;
		}
		public Path removeLast(){
			if( path != null && path.size() > 0 ) path.removeLast();
			return this;
		}
		
		public String getFirst(){
			if( path != null && path.size() > 0 ) return path.getFirst();
			return null;
		}
		public Path replaceFirst( String part ){
			Assert.notNull( part, "part" );
			Assert.notEmpty( part, "part" );
			if( path != null && path.size() > 0 ) path.set( 0, part );
			return this;
		}
		public Path addFirst( String part ){
			Assert.notNull( part, "part" );
			Assert.notEmpty( part, "part" );
			if( path == null ) path = new LinkedList<String>();
			path.addFirst( part );
			return this;
		}
		public Path removeFirst(){
			if( path != null && path.size() > 0 ) path.removeFirst();
			return this;
		}
		public Path removeFirst( int amount ){
			for( ;amount>0; amount-- ){
				removeFirst();
			}
			return this;
		}
		public Path append( Path path ){
			for( String part : path.path ){
				addLast( part );
			}
			endsWithSlash = path.isEndsWithSlash();
			return this;
		}
		
		public String getExtension(){
			String lastPart = getLast();
			if( lastPart != null ){
				int pos = lastPart.lastIndexOf( '.' );
				if( pos >= 0 ){
					return lastPart.substring( pos+1 );
				}
			}
			return null;
		}
		
		public String getLastWithoutExtension(){
			
			String lastPart = getLast();
			if( lastPart != null ){
				int pos = lastPart.lastIndexOf( '.' );
				if( pos >= 0 ){
					return lastPart.substring( 0, pos );
				}
			}
			return null;
		}
		
		public String getMimeType(){
			
			if( endsWithSlash ){
				return "text/directory";
			}
			
			String extension = getExtension();
			if( extension != null ) {
				/* Images */
				if( extension.equalsIgnoreCase( "jpg" )
						|| extension.equalsIgnoreCase( "jpeg" ) ) {
	
					return "image/jpeg";
				} else if( extension.equalsIgnoreCase( "png" ) ) {
					return "image/png";
				} else if( extension.equalsIgnoreCase( "gif" ) ) {
					return "image/gif";
				} else if( extension.equalsIgnoreCase( "ico" ) ) {
					return "image/x-icon";
				} else if( extension.equalsIgnoreCase( "svg" ) ) {
					return "image/svg+xml";
					/* css/js */
				} else if( extension.equalsIgnoreCase( "css" ) ) {
					return "text/css";
				} else if( extension.equalsIgnoreCase( "js" ) ) {
					return "text/javascript";
					/* html */
				} else if( extension.equalsIgnoreCase( "htm" )
						|| extension.equalsIgnoreCase( "html" )
						/* Not! application/xhtml+xml. Not good in any browser. */
						|| extension.equalsIgnoreCase( "xhtml" ) ) {
					return "text/html";
				} else if( extension.equalsIgnoreCase( "txt" ) ) {
					return "text/plain";
				} else {
					
					// Use Java. (Doesn't know xhtml for example)
					String last = getLast();
					return URLConnection.getFileNameMap().getContentTypeFor( last );
				}
			}
			
			return null;
		}
		
		public List<String> toList(){
			
			return new ArrayList<>( path );
		}
		
		public String [] toArray(){
			
			return path.toArray( new String[ path.size() ] );
		}
		
		/**
		 * Get slice of path as new Path
		 * 
		 * @param index to start the slice from. 0 means complete copy. positive integer remove
		 *        from the left. negative from the right;
		 * @return
		 */
		public Path slice( int index ){
			
			LinkedList<String> newPath = new LinkedList<String>( path );
			
			while( index > 0 && newPath.size() > 0 ){
				newPath.removeFirst();
				index--;
			}
			while( index < 0 && newPath.size() > 0 ){
				newPath.removeLast();
				index--;
			}
			
			return new Path( newPath, endsWithSlash );
		}
		public StringBuilder toStringBB( StringBuilder result, boolean encode ){
			
			if( startsWithSlash ) result.append( '/' );
			
			boolean first = true;
			for( String part : path ){
				
				if( encode ) part = encode( part );
				
				if( first ) first = false; 
				else result.append( '/' );
				
				result.append( part );
			}
			
			// Now we need to prevent double slashes if starts && ends and no content
			if( endsWithSlash && ( ! startsWithSlash || path.size() > 0 ) ) result.append( '/' );
			
			return result;
		}
		public StringBuilder toStringB( boolean encode ){
			return toStringBB( new StringBuilder(), encode );
		}
		public String toString( boolean encode ){
			return toStringB( encode ).toString();
		}
		
		@Override
		public String toString(){
			return toString( false );
		}

		@Override
		public Iterator<String> iterator() {
			return path.iterator();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( endsWithSlash ? 1231 : 1237 );
			result = prime * result + ( ( path == null ) ? 0 : path.hashCode() );
			result = prime * result + ( startsWithSlash ? 1231 : 1237 );
			return result;
		}
		
		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof Path ) ) return false;
			
			Path other = (Path) obj;
			
			if( startsWithSlash != other.startsWithSlash ) return false;
			if( endsWithSlash != other.endsWithSlash ) return false;
			
			if( path == null ) {
				if( other.path != null ) return false;
			} else if( !path.equals( other.path ) ) return false;
			
			return true;
		}
		
	}
	
	public static final class Query implements Iterable<Query.QueryPart> {
		
		private HashMap<String,LinkedList<QueryPart>> forName = new HashMap<String,LinkedList<QueryPart>>();
		private LinkedList<QueryPart> path = new LinkedList<QueryPart>();
		
		public Query(){}
		
		public static Query parse( String parseMe ){
			return parse( parseMe, true );
		}
		public static Query parse( String parseMe, boolean decode ){
			
			Query result = new Query();
			
			String[] parts = Str.splitFast( parseMe, '&' );
        			
			for( String part : parts ){
        				
				QueryPart qPart = QueryPart.parse( part, decode );
				
				result.add( qPart );
			}
			
			return result;
		}
		
		public static Query fromPlainMap( Map<String,String> parameters ){
		
			Query result = new Query();
			
			for( Map.Entry<String,String> entry : parameters.entrySet() ){
				result.addValue( entry.getKey(), entry.getValue() );
			}
			return result;
		}
		
		public static Query fromMultiMap( Map<String,String[]> parameters ){
			
			Query result = new Query();
			
			for( Map.Entry<String,String[]> paras : parameters.entrySet() ){
				
				for( String value : paras.getValue() ){
					
					result.addValue( paras.getKey(), value );
				}
			}
			return result;
		}
		
		public static Query fromArray( String ... parameters ){
			
			if( parameters.length % 2 != 0 )
					throw new IllegalArgumentException( "Only even parameter count allowed" );
			
			Query result = new Query();
			
			for( int i=0; i<parameters.length; i+=2 ){
				
				result.addValue( parameters[ i ], parameters[ i+1 ] );
			}
			
			return result;
		}
		
		public Query( QueryPart ... parts ){
			
			addAll( parts );
		}
		
		public Query copy(){
			
			Query result = new Query();
			for( QueryPart part : path ){
				result.add( part.copy() );
			}
			return result;
		}
		
		public LinkedList<QueryPart> getPath(){
			return path;
		}
		public void setPath( LinkedList<QueryPart> path ){
			this.path = path;
		}
		public int size(){
			return path.size();
		}
		public boolean has( String key ){
			return forName.containsKey( key );
		}
		
		public LinkedList<QueryPart> getParts( String key ){
			return forName.get( key );
		}
		public QueryPart getPart( String key ){
			
			if( forName.containsKey( key ) ){
				LinkedList<QueryPart> parts = forName.get( key );
				if( parts.size() > 0 ){
					return parts.getLast();
				}
			}
			return null;
		}
		public String getValue( String key ){
			
			QueryPart part = getPart( key );
			if( part != null ) return part.getValue();
			else return null;
		}
		public List<String> getValues( String key ){
			
			LinkedList<QueryPart> parts = getParts( key );
			if( parts != null ){
    			LinkedList<String> result = new LinkedList<String>();
    			for( QueryPart part : parts ){
    				result.addLast( part.getValue() );
    			}
    			return result;
			} else {
				return null;
			}
		}
		
		public Query addValue( String key, Object value ){
			
			if( ! forName.containsKey( key ) ){
				forName.put( key, new LinkedList<QueryPart>() );
			}
			QueryPart part = new QueryPart( key, value != null ? value.toString() : null );
			forName.get( key ).addLast( part );
			path.add( part );
			return this;
		}
		
		public Query addAll( Map<String,Object> values ){
			
			if( values != null ) for( Map.Entry<String,Object> entry : values.entrySet() ){
				addValue( entry.getKey(), entry.getValue().toString() );
			}
			return this;
		}
		
		public Query addAll( QueryPart ... parts ){
			
			for( QueryPart part : parts ){
				
				add( part );
			}
			return this;
		}
		
		public Query setAll( Map<String,Object> values ){
			
			if( values != null ) for( Map.Entry<String,Object> entry : values.entrySet() ){
				setValue( entry.getKey(), entry.getValue().toString() );
			}
			return this;
		}
		public Query addAll( Query other ){
			for( QueryPart part : other.getPath() ){
				add( part );
			}
			return this;
		}
		public Query add( QueryPart part ){
			addValue( part.getKey(), part.getValue() );
			return this;
		}
		
		public Query setValue( String key, String value ){
			remValue( key );
			addValue( key, value );
			return this;
		}
		public Query remValue( String key ){
			
			if( forName.containsKey( key ) ){
				
				LinkedList<QueryPart> parts = forName.get( key );
				
				for( QueryPart part : parts ){
					path.remove( part );
				}
				forName.remove( key );
			}
			return this;
		}
			
		public StringBuilder toStringBB( StringBuilder result, boolean encode ){
			
			boolean first = true;
			for( QueryPart part : path ){
				
				if( first ) first = false;
				else result.append( '&' );
				
				part.toStringBB( result, encode );
			}
			return result;
		}
		
		public StringBuilder toStringB( boolean encode ){
			return toStringBB( new StringBuilder(), encode );
		}
		public String toString( boolean encode ){
			return toStringB( encode ).toString();
		}
		@Override
		public String toString(){
			return toString( false );
		}
		
		// Compatibility
		
		public Map<String,String[]> toParameterMap() {
			
			Map<String,String[]> result = new HashMap<>( forName.size() );
			
			for( Map.Entry<String,LinkedList<QueryPart>> entry : forName.entrySet() ){
				
				List<QueryPart> valueList = entry.getValue();
				String [] values = new String[ valueList.size() ];
				int i=0;
				for( QueryPart value : valueList ){
					values[ i ] = value.getValue();
				}
				
				result.put( entry.getKey(), values );
			}
			
			return result;
		}
		
		/**
		 * Built to match request.getParameterNames
		 * @return
		 */
		public Enumeration<String> getParameterNames(){
			Vector<String> result = new Vector<>();
			result.addAll( forName.keySet() );
			return result.elements();
		}
		public String [] getParameterValues( String key ){
			
			List<QueryPart> parts = forName.get( key );
			ArrayList<String> result = new ArrayList<>( parts.size() );
			for( QueryPart part : parts ){
				result.add( part.getValue() );
			}
			return result.toArray( new String[ result.size() ] );
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( path == null ) ? 0 : path.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof Query ) ) return false;
			
			Query other = (Query) obj;
			
			if( path == null ) {
				if( other.path != null ) return false;
			} else if( !path.equals( other.path ) ) return false;
			
			return true;
		}

		@Override
		public Iterator<QueryPart> iterator() {
			
			// This one is unmodifyable because modifying only the path breaks things
			return Collections.unmodifiableCollection( path ).iterator();
		}
		
		public static final class QueryPart {
			
			private String key;
			private String value;
			
			public QueryPart( String key, String value ){
				this.key = key;
				this.value = value;
			}
			
			public static QueryPart parse( String parseMe, boolean decode ){
				
				String[] parts = Str.splitFastLimited( parseMe, '=', 2 );
				
				String key = parts[ 0 ];
				String value = null;
				if( parts.length > 1 ){
    				value = parts[ 1 ];
				}
				if( decode ){
					key = decode( key );
					value = decode( value );
				}
				return new QueryPart( key, value );
			}
			
			public QueryPart copy(){
				return new QueryPart( key, value );
			}
			
			public String getKey() {
				return key;
			}
			public void setKey( String key ) {
				this.key = key;
			}

			public String getValue() {
				return value;
			}
			public void setValue( String val ) {
				this.value = val;
			}
			
			public StringBuilder toStringBB( StringBuilder builder, boolean encode ){
				
				String keyStr = key;
				String valStr = value;
				
				if( encode ){
					if( keyStr != null ) keyStr = encode( keyStr );
					if( valStr != null ) valStr = encode( valStr );
				}
				
				if( keyStr != null ){
    				builder.append( keyStr );
				}
				
				if( valStr != null ){
					builder.append( '=' ).append( valStr );
				}
				
				return builder;
			}
			public StringBuilder toStringB( boolean encode ){
				return toStringBB( new StringBuilder(), encode );
			}
			public String toString( boolean encode ){
				return toStringB( encode ).toString();
			}
			@Override
			public String toString(){
				return toString( false );
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ( ( key == null ) ? 0 : key.hashCode() );
				result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
				return result;
			}

			@Override
			public boolean equals( Object obj ) {
				if( this == obj ) return true;
				if( obj == null ) return false;
				if( !( obj instanceof QueryPart ) ) return false;
				
				QueryPart other = (QueryPart) obj;
				
				if( key == null ) {
					if( other.key != null ) return false;
				} else if( !key.equals( other.key ) ) return false;
				
				if( value == null ) {
					if( other.value != null ) return false;
				} else if( !value.equals( other.value ) ) return false;
				
				return true;
			}

		}
	}
	
	public static final class UserInfo {
		
		private String user;
		private String pass;
		
		public UserInfo(){}
		
		public UserInfo( String user, String pass ){
			this.user = user;
			this.pass = pass;
		}
		
		public static UserInfo parse( String parseMe ){
			return parse( parseMe, true );
		}
		public static UserInfo parse( String parseMe, boolean decode ){
			
			String[] parts = Str.splitFastLimited( parseMe, ':', 2 );
    			
			String user = parts[ 0 ];
			String pass = null;
			if( parts.length > 1 )
				pass = parts[ 1 ];
			
			if( decode ){
				user = decode( user );
				pass = decode( pass );
			}
			
			return new UserInfo( user, pass );
		}

		public UserInfo copy() {
			return new UserInfo( user, pass );
		}
		
		public String getUser() {
			return user;
		}
		public UserInfo setUser( String user ) {
			this.user = user;
			return this;
		}

		public String getPass() {
			return pass;
		}
		public UserInfo setPass( String pass ) {
			this.pass = pass;
			return this;
		}
		
		public StringBuilder toStringBB( StringBuilder result, boolean encode ){
			
			String userStr = user;
			String passStr = pass;
			
			if( encode ){
				if( userStr != null ) userStr = encode( userStr );
				if( passStr != null ) passStr = encode( passStr );
			}
			
			if( userStr != null ){
    			result.append( userStr );
			}
			
			if( passStr != null ){
				result.append( ':' ).append( passStr );
			}
			return result;
		}
		public StringBuilder toStringB( boolean encode ){
			return toStringBB( new StringBuilder(), encode );
		}
		public String toString( boolean encode ){
			return toStringB( encode ).toString();
		}
		@Override
		public String toString(){
			return toString( false );
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( pass == null ) ? 0 : pass.hashCode() );
			result = prime * result + ( ( user == null ) ? 0 : user.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof UserInfo ) ) return false;
			
			UserInfo other = (UserInfo) obj;
			
			if( pass == null ) {
				if( other.pass != null ) return false;
			} else if( !pass.equals( other.pass ) ) return false;
			
			if( user == null ) {
				if( other.user != null ) return false;
			} else if( !user.equals( other.user ) ) return false;
			
			return true;
		}
		
	}
	
	
	/* ***************************************************
	 * BUILDER
	 ************************************************** */
	
	private static final SuperURLBuilder_String BUILDER_STRING = SuperURLBuilders.fromString();
	private static final SuperURLBuilder_Request BUILDER_REQUEST = SuperURLBuilders.fromRequest();
	private static final SuperURLBuilder_URI BUILDER_URI = SuperURLBuilders.fromURI();
	private static final SuperURLBuilder_Copy BUILDER_SHALLOW_COPY = SuperURLBuilders.fromSuperURL().usingDeepCopy( false );
	private static final SuperURLBuilder_Copy BUILDER_DEEP_COPY = SuperURLBuilders.fromSuperURL().usingDeepCopy( true );
	
	public static SuperURL fromString( String parseMe ){
		return BUILDER_STRING.build( parseMe );
	}
	
	public static SuperURL fromRequest( HttpServletRequest request ){
		return BUILDER_REQUEST.build( request );
	}
	
	public static SuperURL fromURI( URI uri ){
		return BUILDER_URI.build( uri );
	}
	
	public static SuperURL copyShallow( SuperURL other ){
		return BUILDER_SHALLOW_COPY.build( other );
	}
	
	public static SuperURL copyDeep( SuperURL other ){
		return BUILDER_DEEP_COPY.build( other );
	}
	
}