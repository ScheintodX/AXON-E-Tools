package de.axone.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServletRequest Wrapper which can be used to override
 * request parameters.
 * 
 * @author flo
 *
 */
public class FeatureHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private HashMap<String,String> parametersOverride =
		new HashMap<String,String>();
	
	public FeatureHttpServletRequestWrapper( HttpServletRequest wrapped ){
		
		super( wrapped );
	}

	public void overrideParameter( String key, String value ){
		
		parametersOverride.put( key, value );
	}

	@Override
	public String getParameter( String arg0 ) {
		
		String value = parametersOverride.get( arg0 );
		
		if( value != null ) return value;
		
		return super.getParameter( arg0 );
	}
}
