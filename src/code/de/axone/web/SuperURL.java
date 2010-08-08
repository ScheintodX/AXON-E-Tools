package de.axone.web;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
 * see <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
 * @author flo
 */
public class SuperURL {
	
	private String scheme;
	private UserInfo userInfo;
	private Host host;
	private Integer port;
	private Path path;
	private Query query;
	private String fragment;
	
	public SuperURL(){}
	
	public SuperURL( String parse ) throws URISyntaxException{
		
		this( parse, false );
	}
	
	public SuperURL( String parse, boolean noHost ) throws URISyntaxException{
		
		this( new URI( parse ), noHost );
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
		
		try {
			
			URI uri;
			if( request.getRequestURL() != null ){
				
    			uri = new URI( request.getRequestURL().toString() );
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
				String[] values = request.getParameterValues( name );
				
				for( String value : values ){
					
					query.addValue( name, value );
				}
				
			}
			
		} catch( URISyntaxException e ) {
			
			// This should not happen since the uri in querstien
			// led us here in the first place
			e.printStackTrace();
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

	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo( UserInfo userInfo ) {
		this.userInfo = userInfo;
	}

	public Host getHost() {
		return host;
	}
	public void setHost( Host host ) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}
	public void setPort( Integer port ) {
		this.port = port;
	}

	public String getFragment() {
		return fragment;
	}
	public void setFragment( String fragment ) {
		this.fragment = fragment;
	}
	
	public Path getPath() {
		return path;
	}
	public void setPath( Path path ) {
		this.path = path;
	}

	public Query getQuery() {
		return query;
	}
	public void setQuery( Query query ) {
		this.query = query;
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
	
	/* --- ToString --- */
	
	public StringBuilder toStringBB( StringBuilder result, boolean encode ){
		
		if( scheme != null ){
			result.append( scheme ).append( "://" );
		}
		
		if( userInfo != null ){
			userInfo.toStringBB( result, encode );
			result.append( '@' );
		}
		
		if( host != null ){
			host.toStringBB( result, encode );
		}
		
		if( port != null ){
			result.append( ':' ).append( port );
		}
		
		if( path != null ){
			path.toStringBB( result, encode );
		}
		
		if( query != null && query.size() > 0 ){
			result.append( '?' );
			query.toStringBB( result, encode );
		}
		
		if( fragment != null ){
			
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
    	public void setParts( LinkedList<String> parts ){
    		this.parts = parts;
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
	
	public static class Path {
		
		private LinkedList<String> path = new LinkedList<String>();
		private boolean endsWithSlash;
		
		public Path( String parseMe ){
			
			if( parseMe.endsWith( "/" ) ) endsWithSlash = true;
			
			if( parseMe.startsWith( "/" ) ) parseMe = parseMe.substring( 1 );
			
			if( parseMe.length() == 0 ) return; // empty path. only domain
			
			String[] parts = parseMe.split( "/" );
			
			path.addAll( Arrays.asList( parts ) );
		}
		
		public int getLength(){
			return path.size();
		}
		
		public String getPart( int index ){
			return path.get( index );
		}
		
		public boolean isEndsWithSlash(){
			return endsWithSlash;
		}
		public void setEndsWithSlash( boolean isDir ){
			endsWithSlash = isDir;
		}
		
		public String getLast(){
			if( path != null && path.size() > 0 ) return path.getLast();
			return null;
		}
		public void replaceLast( String part ){
			if( path != null && path.size() > 0 ) path.set( path.size()-1, part );
		}
		public void addLast( String part ){
			if( path == null ) path = new LinkedList<String>();
			path.addLast( part );
		}
		public void removeLast(){
			if( path != null && path.size() > 0 ) path.removeLast();
		}
		
		public String getFirst(){
			if( path != null && path.size() > 0 ) return path.getFirst();
			return null;
		}
		public void replaceFirst( String part ){
			if( path != null && path.size() > 0 ) path.set( 0, part );
		}
		public void addFirst( String part ){
			if( path == null ) path = new LinkedList<String>();
			path.addFirst( part );
		}
		public void removeFirst(){
			if( path != null && path.size() > 0 ) path.removeFirst();
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
					return parts.getFirst();
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
		
		public void addValue( String key, String value ){
			
			if( ! forName.containsKey( key ) ){
				forName.put( key, new LinkedList<QueryPart>() );
			}
			QueryPart part = new QueryPart( key, value );
			forName.get( key ).addLast( part );
			path.add( part );
		}
		
		public void setValue( String key, String value ){
			remValue( key );
			addValue( key, value );
		}
		public void remValue( String key ){
			
			if( forName.containsKey( key ) ){
				
				LinkedList<QueryPart> parts = forName.get( key );
				
				for( QueryPart part : parts ){
					path.remove( part );
				}
				forName.remove( key );
			}
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
		public void setUser( String user ) {
			this.user = user;
		}

		public String getPass() {
			return pass;
		}
		public void setPass( String pass ) {
			this.pass = pass;
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

}
