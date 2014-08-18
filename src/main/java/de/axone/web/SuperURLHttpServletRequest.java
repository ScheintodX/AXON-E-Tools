package de.axone.web;

import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Map;

import de.axone.web.SuperURL.Part;

/*
 * http://www.axon-e.de/foo/bar?key=value
 * 
 * request.getRequestURI()  -> /foo/bar?key=value
 * request.getRequestURL()  -> http://www.axon-e.de/foo/bar (without query)
 * request.getPathInfo()    -> /foo/bar (including /)
 * request.getQueryString() -> key=value (without ?)
 */

public class SuperURLHttpServletRequest extends TestHttpServletRequest {
	
	private final SuperURL url;
	
	private static final SuperURLPrinter printer = SuperURLPrinter.FullEncoded;

	public SuperURLHttpServletRequest( String url ){
		this.url = SuperURLBuilders.fromString().build( url );
	}
	
	public SuperURLHttpServletRequest( SuperURL url ){
		this.url = url;
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer( printer.exclude( EnumSet.of( Part.Query )).toString( url ) );
	}
	
	@Override
	public String getRequestURI() {
		return printer.include( Part.FROM_PATH ).toString( url );
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
		return printer.toString( url.getQuery() );
	}
	
	
	@Override
	public String toString(){
		
		return "\n" + 
		         "ME:  " + url.toDebug() + "\n" +
				 "URI: " + getRequestURI() + "\n" +
				 "URL: " + getRequestURL() + "\n" +
				 "PIn: " + getPathInfo() + "\n" +
				 "QSt: " + getQueryString() + "\n"
				 ;
	}
	
}
