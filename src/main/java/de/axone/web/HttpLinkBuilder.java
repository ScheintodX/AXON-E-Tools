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
	 * Make a link to this page and replace the given parameters
	 * 
	 * Additional specify whether to use host and path in the link
	 * and supply a white list of parameters which are to be kept.
	 * 
	 * @param request
	 * @param noHost use the host part in the link
	 * @param noPath use the path part in the link
	 * @param keepParameters list of parameters which shall be kept or null if all are kept
	 * @param replaceParameters key/value pairs of parameters to set to create or set to new values
	 * @param removeParameters list of parameters to remove from query or null if none are removed
	 * @return
	 */
	public static SuperURL makeLink( HttpServletRequest request, boolean noHost, boolean noPath,
			List<String> keepParameters, Map<String,String> replaceParameters, List<String> removeParameters ) {
		
		SuperURL url = SuperURLBuilders.fromRequest()
				.ignoring( SuperURL.Part.UserInfo, SuperURL.Part.Fragment )
				.ignoringIf( noHost, SuperURL.UPTO_PATH )
				.ignoringIf( noPath, SuperURL.Part.Path )
				.build( request )
				;
		
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
		
		if( removeParameters != null ){
			Query oldQuery = url.getQuery();
			if( oldQuery != null && oldQuery.getPath() != null ){
				Query newQuery = new Query();
				for( QueryPart part : oldQuery.getPath() ){
				
					String key = part.getKey();
					if( !removeParameters.contains( key ) ){
						newQuery.addValue( key, part.getValue() );
					}
				}
				url.setQuery( newQuery );
			}
		}
		
		if( replaceParameters != null ) for( Map.Entry<String,String> entry : replaceParameters.entrySet() ){
			
			url.setQueryParameter( entry.getKey(), entry.getValue() );
		}
		
		return url;
	}
		
}
