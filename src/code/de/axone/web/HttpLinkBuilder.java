package de.axone.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

// this is a URL: 
// http://flo@www.xyz.de:8081/blah/blug/blae.txt?a=1&b=2&c=3

public class HttpLinkBuilder {

	public static String makeLink( HttpServletRequest request ){
		
		return makeLink( request, null );
	}
	
	public static String makeLink( HttpServletRequest request, boolean noHost, boolean noPath, Map<String,String> replaceParameter ) {
		
		SuperURL url = new SuperURL( request, noHost );
		
		if( noPath ) url.setPath( null );
		
		if( replaceParameter != null ) for( String key : replaceParameter.keySet() ){
			
			url.setQueryParameter( key, replaceParameter.get( key ) );
		}
		
		return url.toString( true );
	}
	public static String makeLink( HttpServletRequest request, Map<String,String> replaceParameter ) {
		
		SuperURL url = new SuperURL( request );
		
		if( replaceParameter != null ) for( String key : replaceParameter.keySet() ){
			
			url.setQueryParameter( key, replaceParameter.get( key ) );
		}
		return url.toString( true );
	}
}
