package de.axone.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import de.axone.web.SuperURL.Query.QueryPart;


public class SuperURLHttpServletRequest extends TestHttpServletRequest {
	
	private final SuperURL url;

	public SuperURLHttpServletRequest( SuperURL url ){
		this.url = url;
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer( url.toString( true ) );
	}

	@Override
	public String getParameter( String key ) {
		return url.getQueryParameter( key );
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String,String[]> result = new HashMap<>();
		for( QueryPart part : url.getQuery().getPath() ){
			String key = part.getKey();
			String value = part.getValue();
			if( ! result.containsKey( key ) ){
				result.put( key, new String[]{ value } );
			} else {
				String [] oldA = result.get( key );
				String [] newA = new String[ oldA.length+1 ];
				System.arraycopy( oldA, 0, newA, 0, oldA.length );
				newA[ newA.length-1 ] = value;
				result.put( key, newA );
			}
		}
		return result;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return url.getQuery().getParameterNames();
	}

	@Override
	public String[] getParameterValues( String arg0 ) {
		return url.getQuery().getParameterValues( arg0 );
	}
	
	
}
