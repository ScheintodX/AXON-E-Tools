package de.axone.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import de.axone.web.SuperURL.Query;
import de.axone.web.SuperURL.Query.QueryPart;

// this is a URL: 
// http://flo@www.xyz.de:8081/blah/blug/blae.txt?a=1&b=2&c=3

/**
 * Build a http link starting with the url of the current page.
 * 
 * This is usefull for paging functionality
 */
public class HttpLinkBuilder {

	/**
	 * Make a link to this page
	 * 
	 * @param request
	 * @return
	 */
	public static String makeLink( HttpServletRequest request ){
		
		return makeLink( request, null );
	}
	
	/**
	 * Make a link to this page and replace the given parameters
	 * 
	 * @param request
	 * @param replaceParameters
	 * @return
	 */
	public static String makeLink( HttpServletRequest request, Map<String,String> replaceParameters ) {
		
		return makeLink( request, false, false, null, replaceParameters );
	}
	
	/**
	 * Make a link to this page and replace the given parameters
	 * 
	 * Additional specify whether to use host and path in the link
	 * 
	 * @param request
	 * @param noHost use the host part in the link
	 * @param noPath use the path part in the link
	 * @param replaceParameters
	 * @return
	 */
	public static String makeLink( HttpServletRequest request, boolean noHost, boolean noPath, Map<String,String> replaceParameters ) {
		
		return makeLink( request, noHost, noPath, null, replaceParameters );
	}
	
	/**
	 * Make a link to this page and replace the given parameters
	 * 
	 * Additional specify whether to use host and path in the link
	 * and supply a white list of parameters which are to be kept.
	 * 
	 * @param request
	 * @param noHost use the host part in the link
	 * @param noPath use the path part in the link
	 * @param keepParameters list of parameters which shall be kept
	 * @param replaceParameters
	 * @return
	 */
	public static String makeLink( HttpServletRequest request, boolean noHost, boolean noPath, List<String> keepParameters, Map<String,String> replaceParameters ) {
		
		SuperURL url = new SuperURL( request, noHost );
		
		if( noPath ) url.setPath( null );
		
		if( keepParameters != null ){
			Query oldQuery = url.getQuery();
			if( oldQuery != null && oldQuery.getPath() != null ){
				Query newQuery = new Query();
				for( QueryPart part : oldQuery.getPath() ){
				
					String key = part.getKey();
					if( keepParameters.contains( key ) ){
						newQuery.addValue( key, part.getValue() );
					}
				}
				url.setQuery( newQuery );
			}
		}
		
		if( replaceParameters != null ) for( String key : replaceParameters.keySet() ){
			
			url.setQueryParameter( key, replaceParameters.get( key ) );
		}
		
		return url.toString( true );
	}
}
