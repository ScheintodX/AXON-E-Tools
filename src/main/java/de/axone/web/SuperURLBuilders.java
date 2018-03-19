package de.axone.web;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.data.Label;
import de.axone.exception.Assert;
import de.axone.tools.Str;
import de.axone.web.SuperURL.Part;
import de.axone.web.SuperURL.Path;
import de.axone.web.SuperURL.Query;
import de.axone.web.SuperURL.Query.QueryPart;
import de.axone.web.SuperURL.UserInfo;

/*
 * NOTE:
 * 
 * http://www.axon-e.de/foo/bar?key=value
 * 
 * request.getRequestURI()  -> /foo/bar?key=value
 * request.getRequestURL()  -> http://www.axon-e.de/foo/bar (without query)
 * request.getPathInfo()    -> /foo/bar (including /)
 * request.getQueryString() -> key=value (without ?)
 * 
 */


public class SuperURLBuilders {
	
	private static final Logger log =
			LoggerFactory.getLogger( SuperURL.class );
	
	static final SuperURLBuilders INSTANCE = new SuperURLBuilders();
	
	public static SuperURLBuilder_String fromString() {
		
		return new SuperURLBuilder_String();
	}
	
	public static SuperURLBuilder_URI fromURI() {
		
		return new SuperURLBuilder_URI();
	}
	
	public static SuperURLBuilder_URL fromURL() {
		
		return new SuperURLBuilder_URL();
	}
	
	public static SuperURLBuilder_Request fromRequest() {
		
		return new SuperURLBuilder_Request();
	}
	
	public static SuperURLBuilder_Copy fromSuperURL() {
		
		return new SuperURLBuilder_Copy();
	}
	
	public static abstract class SuperURLBuilder<T, S extends SuperURLBuilder<T,S>> {
		
		protected Set<Part> using = EnumSet.allOf( Part.class );
		protected S myself;
		
		@SuppressWarnings( "unchecked" )
		SuperURLBuilder() {
			myself = (S)this;
		}
		
		public S using( Set<Part> parts ){
			this.using = parts;
			return myself;
		}
		
		public S using( Part first, Part ... rest ){
			this.using( EnumSet.of( first, rest ) );
			return myself;
		}
		
		public S ignoring( Part first, Part ... rest ){
			return ignoring( EnumSet.of( first, rest ) );
		}
		public S ignoring( Collection<Part> parts ){
			this.using.removeAll( parts );
			return myself;
		}
		public S ignoringIf( boolean condition, Part first, Part ... rest ){
			return ignoringIf( condition, EnumSet.of( first, rest ) );
		}
		public S ignoringIf( boolean condition, Collection<Part> parts ){
			if( condition ) this.using.removeAll( parts );
			return myself;
		}
		
		public SuperURL build( T from ) {
			return update( new SuperURL(), from );
		}
		public abstract SuperURL update( SuperURL dst, T from );

	}
	
	
	public static class SuperURLBuilder_String extends SuperURLBuilder<CharSequence, SuperURLBuilder_String> {
		
		public enum Method {
			uri, url, friendly;
		}
		
		private Method method = Method.uri;
		
		public SuperURLBuilder_String usingMethod( Method method ){
			this.method = method;
			return this;
		}
		
		@Override
		public SuperURL update( SuperURL dst, CharSequence parseMe ){
			
			Assert.notNull( parseMe, "parseMe" );
			
			if( parseMe.length() == 0 ) return dst;
				
			switch( method ){
			
			case uri:
				String fixed = applyFixes( parseMe );
				return updateUrlUsingUri( dst, fixed );
			case url:
				return updateUrlUsingURL( dst, parseMe.toString() );
			case friendly:
				return updateUrlFriendly( dst, parseMe.toString() );
			default:
					throw new IllegalArgumentException( "Unknown method: " + method );
			}
				
		}
			
		//http://flo:blah@www.axon-e.de:8080/a/b/c/?foo=bar&a=b#fragment
		private SuperURL updateUrlFriendly( SuperURL dst, String parseMe ){
			
			throw new UnsupportedOperationException( "Not implemented (yet)" );
		}
		
		private SuperURL updateUrlUsingURL( SuperURL dst, String parseMe ){
			
			try {
				URL url = new URL( parseMe );
				SuperURL result = new SuperURLBuilder_URL().using( using ).update( dst, url );
				return result;
			} catch( MalformedURLException e ) {
				throw new IllegalArgumentException( "Cannot parse: " + parseMe, e );
			}
			
		}
		
		private SuperURL updateUrlUsingUri( SuperURL dst, String parseMe ){
			
			try {
				URI uri = new URI( parseMe );
				return new SuperURLBuilder_URI().using( using ).update( dst, uri );
			} catch( URISyntaxException e ){
				throw new IllegalArgumentException( "Cannot parse: " + parseMe, e );
			}
		}
		
		
		private String applyFixes( CharSequence old ){
			log.trace( "Parse: {}", old );
			String asString = old.toString();
			asString = fixForChrome( asString );
			asString = fixForNGinx( asString );
			log.trace( "To: {}", asString );
			return asString;
		}
		
		private static final char [] SQUARE_BRACETS = { '[', ']' };
		private static final String [] SQUARE_BRACETS_ENCODED = { "%5B", "%5D" };
		
		// Chrome passes '[' and ']' in places where they not belong
		private static String fixForChrome( String old ){
			if( ! Str.containsOneOf( old, SQUARE_BRACETS ) ) return old;
			if( Str.contains( old, "http://[" ) ) return old;// Ipv6 quickfix.
			return Str.translate( old, SQUARE_BRACETS, SQUARE_BRACETS_ENCODED );
		}
		
		private static final char DQUOTES = '"';
		private static final String DQUOTES_ENCODED = "%22";
		
		// Using NGinx as proxy you get '"' unencoded in the url
		private static String fixForNGinx( String old ){
			if( ! Str.contains( old, DQUOTES ) ) return old;
			return Str.translate( old, DQUOTES, DQUOTES_ENCODED );
		}
		
	}
	
	public static class SuperURLBuilder_URL extends SuperURLBuilder<URL, SuperURLBuilder_URL> {

		@Override
		public SuperURL update( SuperURL dst, URL url ) {
			
			if( using.contains( Part.Scheme ) ){
	    		dst.setScheme( url.getProtocol() );
			}
	    		
			if( using.contains( Part.UserInfo ) ){
	    		if( url.getUserInfo() != null ){
	        		dst.setUserInfo( UserInfo.parse( url.getUserInfo(), true ) );
	    		} else {
	    			dst.setUserInfo( null );
	    		}
			}
	    		
			if( using.contains( Part.Host ) ){
	    		if( url.getHost() != null ){
	        		dst.setHost( Host().parse( url.getHost(), true ).build() );
	    		} else {
	    			dst.setHost( null );
	    		}
			}
	    		
			if( using.contains( Part.Port ) ){
	    		if( url.getPort() >= 0 ){
	        		dst.setPort( url.getPort() );
	    		} else {
	    			dst.setPort( null );
	    		}
			}
			
			if( using.contains( Part.Path ) ){
				if( url.getPath() != null ){
		    		dst.setPath( Path().parse( url.getPath(), true ).build() );
				} else {
					dst.setPath( null );
				}
			}
			
			if( using.contains( Part.Query ) ){
				if( url.getQuery() != null ){
		    		dst.setQuery( Query().parse( url.getQuery(), true ).build() );
				} else {
					dst.setQuery( null );
				}
			}
			
			if( using.contains( Part.Fragment ) ){
	    		dst.setFragment( url.getRef() );
			}
	    		
			return dst;
		}
	}
	
	public static class SuperURLBuilder_URI extends SuperURLBuilder<URI, SuperURLBuilder_URI> {
		
		@Override
		public SuperURL update( SuperURL dst, URI uri ){
			
			if( using.contains( Part.Scheme ) ){
	    		dst.setScheme( uri.getScheme() );
			}
	    		
			if( using.contains( Part.UserInfo ) ){
	    		if( uri.getUserInfo() != null ){
	        		dst.setUserInfo( UserInfo.parse( uri.getRawUserInfo(), true ) );
	    		} else {
	    			dst.setUserInfo( null );
	    		}
			}
	    		
			if( using.contains( Part.Fragment ) ){
	    		dst.setFragment( uri.getFragment() );
			}
	    		
			if( using.contains( Part.Host ) ){
	    		if( uri.getHost() != null ){
	        		dst.setHost( Host().parse( uri.getHost(), false ).build() );
	    		} else {
	    			dst.setHost( null );
	    		}
			}
	    		
			if( using.contains( Part.Port ) ){
	    		if( uri.getPort() >= 0 ){
	        		dst.setPort( uri.getPort() );
	    		} else {
	    			dst.setPort( null );
	    		}
			}
			
			if( using.contains( Part.Path ) ){
				if( uri.getPath() != null ){
					Path path = Path().parse( uri.getRawPath(), true ).build();
		    		dst.setPath( path );
				} else {
					dst.setPath( null );
				}
			}
			
			if( using.contains( Part.Query ) ){
				if( uri.getQuery() != null ){
		    		dst.setQuery( Query().parse( uri.getRawQuery(), true ).build() );
				} else {
					dst.setQuery( null );
				}
			}
			
			return dst;
		}
		
	}
	
	public static class SuperURLBuilder_Request extends SuperURLBuilder<HttpServletRequest,SuperURLBuilder_Request> {
		
		public enum Method {
			URI, URL, Parameters, QueryString, CombinedString;
		}

		private Method method = Method.CombinedString;
		
		public SuperURLBuilder_Request usingMethod( Method method ){
			Assert.notNull( method, "method" );
			this.method = method;
			return this;
		}
		
		@Override
		public SuperURL update( SuperURL dst, HttpServletRequest request ) {
			
			/*
			E.rr( "URI: " + request.getRequestURI() );
			E.rr( "URL: " + request.getRequestURL() );
			E.rr( "QuS: " + request.getPathInfo() );
			E.rr( "PIn: " + request.getQueryString() );
			*/
			
			switch( method ){
			case Parameters:
				return updateUsingRequestAndParameters( dst, request );
			case QueryString:
				return updateParsingRequest( dst, request );
			case URI:
				return updateUsingRequestURI( dst, request );
			case URL:
				return updateUsingRequestURL( dst, request );
			case CombinedString:
				return updateUsingCombinedString( dst, request );
			default:
				throw new IllegalArgumentException( "Unknown method: " + method );
			
			}
		}
		
		private SuperURL updateUsingCombinedString( SuperURL dst,
				HttpServletRequest request ) {
			
			StringBuffer url = request.getRequestURL();
			String query = request.getQueryString();
			
			if( query != null )
					url.append( '?' ).append( query );
			
			return SuperURLBuilders.fromString().using( using ).build( url.toString() );
		}

		/**
		 * Construct a SuperURL using the HttpServletRequest itself.
		 * 
		 * This uses the requests methods the get the needed paramters
		 * and includes GET AND POST parameters.
		 * 
		 * This is not what you want if you only want to parse the
		 * URL. So use {@link #updateUsingRequestURI(SuperURL, HttpServletRequest)} or
		 * {@link #updateUsingRequestURL(SuperURL, HttpServletRequest)} instead.
		 * 
		 * 
		 * @param dst The url to be updated
		 * @param request the HttpServletRequest to use for updating
		 * @return the updated url
		 */
		public SuperURL updateParsingRequest( SuperURL dst, HttpServletRequest request ) {
			
			if( using.contains( Part.Scheme ) ){
				dst.setScheme( request.getScheme() );
			}
			
			if( using.contains( Part.Host ) ){
				String serverName = request.getServerName();
				if( serverName != null )
						dst.setHost( Host().parse( serverName, false ).build() );
			}
			
			if( using.contains( Part.Port ) ){
				dst.setPort( request.getServerPort() );
			}
			
			if( using.contains( Part.Path ) ){
				String pathInfo = request.getPathInfo();
				if( pathInfo != null )
						dst.setPath( Path().parse( request.getPathInfo(), true ).build() );
			}
			
			if( using.contains( Part.Query ) ){
				String queryString = request.getQueryString();
				if( queryString != null )
						dst.setQuery( Query().parse( queryString, true ).build() );
			}
			
			return dst;
		}

		/**
		 * Construct a SuperURL using the HttpServletRequest itself.
		 * 
		 * This uses the requests methods the get the needed paramters
		 * and includes GET AND POST parameters.
		 * 
		 * This is not what you want if you only want to parse the
		 * URL. So use {@link #updateUsingRequestURI(SuperURL, HttpServletRequest)} or
		 * {@link #updateUsingRequestURL(SuperURL, HttpServletRequest)} instead.
		 * 
		 * @param dst The url to be updated
		 * @param request the HttpServletRequest to use for updating
		 * @return the updated url
		 */
		public SuperURL updateUsingRequestAndParameters( SuperURL dst, HttpServletRequest request ) {
			
			if( using.contains( Part.Scheme ) ){
				dst.setScheme( request.getScheme() );
			}
			if( using.contains( Part.Host ) ){
				dst.setHost( Host().parse( request.getServerName(), false ).build() );
			}
			if( using.contains( Part.Port ) ){
				dst.setPort( request.getServerPort() );
			}
			if( using.contains( Part.Path ) ){
				dst.setPath( Path().parse( request.getPathInfo() ).build() );
			}
			if( using.contains( Part.Query ) ){
			
				Query query = null;
				
				Enumeration<?> names = request.getParameterNames();
				while( names.hasMoreElements() ){
					
					if( query == null ) query = new SuperURL.Query();
					
					String name = (String)names.nextElement();
					String[] values = request.getParameterValues( name ); // Buggy (warum?)
					
					for( String v : values ){
						query.addValue( name, trimQueryValue( v ) );
					}
					
				}
				dst.setQuery( query );
			}
				
			return dst;
		}
		
		private static String trimQueryValue( String queryValue ){
			
			if( queryValue.length() == 0 ) return null;
			
			return queryValue;
		}
		
		public SuperURL updateUsingRequestURI( SuperURL dst, HttpServletRequest request ) {
			
			try {
				URI uri = new URI( request.getRequestURI() );
				return new SuperURLBuilder_URI().using( using ).update( dst, uri );
			} catch( URISyntaxException e ) {
				throw new IllegalArgumentException( "Cannot parse URI: " + request.getRequestURI() );
			}
			
		}
		
		public SuperURL updateUsingRequestURL( SuperURL dst, HttpServletRequest request ) {
			
			try {
				URL url = new URL( request.getRequestURL().toString() );
				return new SuperURLBuilder_URL().using( using ).update( dst, url );
			} catch( MalformedURLException e ) {
				throw new IllegalArgumentException( "Cannot parse URL: " + request.getRequestURI() );
			}
		}
		
	}
	
	public static class SuperURLBuilder_Copy extends SuperURLBuilder<SuperURL, SuperURLBuilder_Copy> {
		
		private boolean deep = true;
		
		public SuperURLBuilder_Copy usingDeepCopy( boolean deep ){
			
			this.deep = deep;
			return this;
		}

		@Override
		public SuperURL update( SuperURL dst, SuperURL from ) {
			
			if( using.contains( Part.Scheme ) ) {
				dst.setScheme( from.getScheme() );
				dst.setIncludeScheme( from.isIncludeScheme() );
			}
			if( using.contains( Part.UserInfo ) ){
				if( deep ){
					dst.setUserInfo( from.getUserInfo().copy() );
				} else {
					dst.setUserInfo( from.getUserInfo() );
				}
				dst.setIncludeUserInfo( from.isIncludeUserInfo() );
			}
			if( using.contains( Part.Host ) ) {
				if( deep ){
					dst.setHost( from.getHost().copy() );
				} else {
					dst.setHost( from.getHost() );
				}
				dst.setIncludeHost( from.isIncludeHost() );
			}
			if( using.contains( Part.Port ) ) {
				dst.setPort( from.getPort() );
				dst.setIncludePort( from.isIncludePort() );
			}
			if( using.contains( Part.Path ) ) {
				if( deep ){
					dst.setPath( from.getPath().copy() );
				} else {
					dst.setPath( from.getPath() );
				}
				dst.setIncludePath( from.isIncludePath() );
			}
			if( using.contains( Part.Query ) ) {
				if( deep ){
					dst.setQuery( from.getQuery().copy() );
				} else {
					dst.setQuery( from.getQuery() );
				}
				dst.setIncludeQuery( from.isIncludeQuery() );
			}
			if( using.contains( Part.Fragment ) ) {
				dst.setFragment( from.getFragment() );
				dst.setIncludeFragment( from.isIncludeFragment() );
			}
			return dst;
		}
		
	}
	
	public static HostBuilder Host(){
		return new HostBuilder();
	}
	public static PathBuilder Path(){
		return new PathBuilder();
	}
	public static QueryBuilder Query(){
		return new QueryBuilder();
	}
	
	public static class HostBuilder {
		
		private SuperURL.Host result = new SuperURL.Host();
		
		public HostBuilder parse( String parseMe ){
			return parse( parseMe, true );
		}
		
		public HostBuilder parse( String parseMe, boolean decode ){
			
			String [] parts = Str.splitFast( parseMe, '.' );
			
			if( decode ){
				for( int i=0; i<parts.length; i++ ){
					parts[ i ] = SuperURL.decode( parts[ i ] );
				}
			}
			
			result.parts = new ArrayList<>( Arrays.asList( parts ) );
			
			return this;
		}
		
		public SuperURL.Host build(){
			return result;
		}
	}
	
	public static class PathBuilder {
		
		private SuperURL.Path result = new SuperURL.Path();
		
		public PathBuilder of( String ... part ){
			result.addAll( part );
			return this;
		}
		
		public PathBuilder parse( String parseMe ){
			return parse( parseMe, true );
		}
		
		public PathBuilder parse( String parseMe, boolean decode ){
			
			if( parseMe == null || parseMe.length() == 0 ){
				return this;
			}
			
			if( "/".equals( parseMe ) ){
				result.setStartsWithSlash( true );
				return this;
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
				
				if( decode ) part = SuperURL.decode( part );
				
				asList.add( part );
			}
			
			result.path.addAll( asList );
			
			return this;
		}
		
		
		public SuperURL.Path build(){
			return result;
		}
	}
	
	public static class QueryBuilder {
		
		SuperURL.Query result = new SuperURL.Query();
		
		public QueryBuilder part( QueryPart part ){
			
			if( part != null ) result.add( part );
			
			return this;
		}
		
		public QueryBuilder parts( Query parameters ) {
			
			if( parameters != null ) result.addAll( parameters );
			
			return this;
		}
		
		public QueryBuilder label( String key, Label value ){
			
			return value( key, value != null ? value.label() : null );
		}
		
		public QueryBuilder value( String key, String value ){
			
			return part( new QueryPart( key, value ) );
		}
		
		public SuperURL.Query build(){
			
			return result;
		}
		
		public QueryBuilder parse( String parseMe ){
			
			return parse( parseMe, true );
		}
		
		public QueryBuilder parse( String parseMe, boolean decode ){
			
			String[] parts = Str.splitFast( parseMe, '&' );
        			
			for( String part : parts ){
        				
				QueryPart qPart = QueryPart.parse( part, decode );
				
				result.add( qPart );
			}
			
			return this;
		}
		
		public QueryBuilder fromPlainMap( Map<String,String> parameters ){
		
			for( Map.Entry<String,String> entry : parameters.entrySet() ){
				result.addValue( entry.getKey(), entry.getValue() );
			}
			return this;
		}
		
		public QueryBuilder fromMultiMap( Map<String,String[]> parameters ){
			
			for( Map.Entry<String,String[]> paras : parameters.entrySet() ){
				
				for( String value : paras.getValue() ){
					
					result.addValue( paras.getKey(), value );
				}
			}
			return this;
		}
		
		public QueryBuilder fromArray( String ... parameters ){
			
			if( parameters.length % 2 != 0 )
					throw new IllegalArgumentException( "Only even parameter count allowed" );
			
			for( int i=0; i<parameters.length; i+=2 ){
				
				result.addValue( parameters[ i ], parameters[ i+1 ] );
			}
			
			return this;
		}
		
	}
	
}
