package de.axone.web;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.exception.Assert;
import de.axone.tools.Str;


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
	
	public static final Logger log =
			LoggerFactory.getLogger( SuperURL.class );
	
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
	
	public SuperURL( String parse ) throws URISyntaxException{
		
		this( parse, false );
	}
	
	public SuperURL( String parse, boolean noHost ) throws URISyntaxException{
		
		this( new URI( fixForChrome( parse ) ), noHost );
	}
	
	public SuperURL( URI uri ){
		
		this( uri, false );
	}
	
	public SuperURL( URI uri, boolean noHost ) {
		
		initialize( uri, noHost );
	}
	
	public SuperURL( HttpServletRequest request ) {
		
		this( request, false );
	}
	
	public SuperURL( HttpServletRequest request, boolean noHost ) {
		
		StringBuffer urlSB = request.getRequestURL();
		
		try {
			
			URI uri;
			
			if( urlSB != null ){
				
				String urlStr = fixForChrome( urlSB.toString() );
				
				uri = new URI( urlStr );
			} else {
				uri = new URI( "" );
				noHost = true;
			}
			
			initialize( uri, noHost );
			
			Enumeration<?> names = request.getParameterNames();
			while( names.hasMoreElements() ){
				
				if( query == null ){
					query = new Query();
				}
				
				String name = (String)names.nextElement();
				String[] values = request.getParameterValues( name ); // Buggy (warum?)
				
				for( String v : values ){
					query.addValue( name, v );
				}
				
			}
			
		} catch( URISyntaxException e ) {
			
			throw new IllegalArgumentException( "Error processing URL: " + urlSB, e );
		}
		
	}
	
	private void initialize( URI uri, boolean noHost ){
		
		if( ! noHost ){
			
    		scheme = uri.getScheme();
    		
    		if( uri.getUserInfo() != null )
        		userInfo = new UserInfo( uri.getUserInfo() );
    		
    		fragment = uri.getFragment();
    		
    		if( uri.getHost() != null )
        		host = new Host( uri.getHost() );
    		
    		if( uri.getPort() >= 0 )
        		port = uri.getPort();
		}
		
		if( uri.getPath() != null )
    		path = new Path( uri.getPath() );
		
		if( uri.getQuery() != null )
    		query = new Query( uri.getQuery() );
	}
	
	/* --- Direct Access --- */
	
	public String getScheme() {
		return scheme;
	}
	public void setScheme( String scheme ) {
		this.scheme = scheme;
	}
	public void setIncludeScheme( boolean includeScheme ){
		this.includeScheme = includeScheme;
	}
	public boolean isIncludeScheme(){
		return includeScheme;
	}
	

	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo( UserInfo userInfo ) {
		this.userInfo = userInfo;
	}
	public void setIncludeUserInfo( boolean includeUserInfo ){
		this.includeUserInfo = includeUserInfo;
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
	public void setPort( Integer port ) {
		this.port = port;
	}
	public void setIncludePort( boolean includePort ){
		this.includePort = includePort;
	}
	public boolean isIncludePort(){
		return includePort;
	}

	public String getFragment() {
		return fragment;
	}
	public void setFragment( String fragment ) {
		this.fragment = fragment;
	}
	public void setIncludeFragment( boolean includeFragment ){
		this.includeFragment = includeFragment;
	}
	public boolean isIncludeFragment(){
		return includeFragment;
	}
	
	public Path getPath() {
		return path;
	}
	public void setPath( Path path ) {
		this.path = path;
	}
	public void setIncludePath( boolean includePath ){
		this.includePath = includePath;
	}
	public boolean isIncludePath(){
		return includePath;
	}

	public Query getQuery() {
		return query;
	}
	public void setQuery( Query query ) {
		this.query = query;
	}
	public void setIncludeQuery( boolean includeQuery ){
		this.includeQuery = includeQuery;
	}
	public boolean isIncludeQuery(){
		return includeQuery;
	}
	
	/* --- Helpers --- */
	public void setQueryParameter( String key, String value ){
		
		if( getQuery() == null ) setQuery( new SuperURL.Query() );
		
		getQuery().setValue( key, value );
	}
	
	public String getQueryParameter( String key ){
		
		if( getQuery() == null ) return null;
		
		return getQuery().getValue( key );
	}
	
	public boolean hasHost(){
		return getHost() != null;
	}
	public boolean hasPath(){
		return getPath() != null
			&& getPath().toString().length() > 0;
	}
	
	public void appendPath( String path ){
		
		Path newPath = new SuperURL.Path( path );
		getPath().append( newPath );
		getPath().endsWithSlash = newPath.endsWithSlash;
		
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
			// Domains don't get url encoded
			host.toStringBB( result, false );
		}
		
		if( includePort && port != null ){
			result.append( ':' ).append( port );
		}
		
		if( includePath && path != null ){
			path.toStringBB( result, encode );
		}
		
		if( includeQuery && query != null && query.size() > 0 ){
			result.append( '?' );
			query.toStringBB( result, encode );
		}
		
		if( includeFragment && fragment != null ){
			
			String fragmentStr = fragment;
			if( encode ) fragmentStr = urlify( fragmentStr );
			result.append( '#' ).append( fragmentStr );
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
	
	public static class Host {
		
		public LinkedList<String> parts = new LinkedList<String>();
		
		public Host( String parseMe ){
			
			parts.addAll( Arrays.asList( parseMe.split( "\\." ) ) );
		}
		
    	public StringBuilder toStringBB( StringBuilder result, boolean encode ){
    		
    		boolean first = true;
    		for( String part : parts ){
    			
    			if( first ) first = false;
    			else result.append( '.' );
    			
    			result.append( part );
    		}
    		return result;
    	}
    	
    	public LinkedList<String> getParts(){
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
	}
	
	public static class Path implements Iterable<String> {
		
		private LinkedList<String> path = new LinkedList<String>();
		private boolean endsWithSlash;
		private boolean startsWithSlash;
		
		public Path(){}
		public Path( LinkedList<String> path, boolean endsWithSlash ){
			this.path = path;
			this.endsWithSlash = endsWithSlash;
		}
		
		public Path( String parseMe ){
			
			if( parseMe.endsWith( "/" ) ) endsWithSlash = true;
			
			if( parseMe.startsWith( "/" ) ){
				startsWithSlash = true;
				parseMe = parseMe.substring( 1 );
			}
			
			if( parseMe.length() == 0 ) return; // empty path. only domain
			
			String[] parts = parseMe.split( "/" );
			
			path.addAll( Arrays.asList( parts ) );
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
		public void setEndsWithSlash( boolean isDir ){
			endsWithSlash = isDir;
		}
		
		public boolean isStartsWithSlash(){
			return startsWithSlash;
		}
		public void setStartsWithSlash( boolean isDir ){
			startsWithSlash = isDir;
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
			
			for( String part : path ){
				
				if( encode ) part = urlify( part );
				
				result.append( '/' ).append( part );
			}
			if( endsWithSlash ) result.append( '/' );
			
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
		
	}
	
	public static class Query {
		
		private HashMap<String,LinkedList<QueryPart>> forName = new HashMap<String,LinkedList<QueryPart>>();
		private LinkedList<QueryPart> path = new LinkedList<QueryPart>();
		
		public Query(){}
		
		public Query( String parseMe ){
			
			String[] parts = parseMe.split( "&" );
        			
			for( String part : parts ){
        				
				QueryPart qPart = new QueryPart( part );
				path.addLast( qPart );
        				
				if( ! forName.containsKey( qPart.key ) ){
					forName.put( qPart.key, new LinkedList<QueryPart>() );
				}
				forName.get( qPart.key ).addLast( qPart );
			}
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
		
		public Query addValue( String key, String value ){
			
			if( ! forName.containsKey( key ) ){
				forName.put( key, new LinkedList<QueryPart>() );
			}
			QueryPart part = new QueryPart( key, value );
			forName.get( key ).addLast( part );
			path.add( part );
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
		
		public static class QueryPart {
			
			private String key;
			private String value;
			
			public QueryPart( String key, String value ){
				this.key = key;
				this.value = value;
			}
			
			public QueryPart( String parseMe ){
				
				String[] parts = parseMe.split( "=", 2 );
				
				key = parts[ 0 ];
				if( parts.length > 1 ){
    				value = parts[ 1 ];
				}
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
					if( keyStr != null ) keyStr = urlify( keyStr );
					if( valStr != null ) valStr = urlify( valStr );
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

		}
	}
	
	public static class UserInfo {
		
		private String user;
		private String pass;
		
		public UserInfo(){}
		
		public UserInfo( String user, String pass ){
			this.user = user;
			this.pass = pass;
		}
		
		public UserInfo( String parseMe ){
			
			String[] parts = parseMe.split( ":", 2 );
    			
			user = parts[ 0 ];
			if( parts.length > 1 )
				pass = parts[ 1 ];
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
				if( userStr != null ) userStr = urlify( userStr );
				if( passStr != null ) passStr = urlify( passStr );
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
	}

	public static String urlify( String text ) {
		
		try {
			return URLEncoder.encode( text, "utf-8" );
		} catch( UnsupportedEncodingException e ) {
			throw new RuntimeException( "Cannot urlify " + text );
		}
	}
	
	private static String fixForChrome( String old ){
		
		if( ! ( old.contains( "[" ) || old.contains( "]" ) ) ) return old;
		return old.replace( "[", "%5B" ).replace( "]", "%5D" );
	}
	
	public URL toURL() throws MalformedURLException{
		return new URL( toString( false ) );
	}

}
