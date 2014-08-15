package de.axone.web;

import java.util.Enumeration;
import java.util.Map;


public class SuperURLHttpServletRequest extends TestHttpServletRequest {
	
	private final SuperURL url;

	public SuperURLHttpServletRequest( SuperURL url ){
		this.url = url;
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer( url.toStringEncode( true ) );
	}
	
	@Override
	public String getRequestURI() {
		return url.toStringEncode( false );
	}

	@Override
	public String getParameter( String key ) {
		return url.getQueryParameter( key );
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return url.getQuery().toParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return url.getQuery().getParameterNames();
	}

	@Override
	public String[] getParameterValues( String arg0 ) {
		return url.getQuery().getParameterValues( arg0 );
	}

	@Override
	public String getQueryString() {
		return url.getQuery().toString( true );
	}
	
	
	
}
