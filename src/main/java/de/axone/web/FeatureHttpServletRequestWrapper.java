package de.axone.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import de.axone.tools.F;

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
		
		if( getParameter( key ) == null )
				throw new IllegalArgumentException( "Can only overwrite existing parameter" );
		
		parametersOverride.put( key, value );
	}

	@Override
	public String getParameter( String arg0 ) {
		
		String value = parametersOverride.get( arg0 );
		
		if( value != null ) return value;
		
		return super.getParameter( arg0 );
	}
	
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		
		result.append( "Request " )
		      .append( this.getRequestURI() )
		      .append( " [" ).append( this.getMethod() ).append( "]:" )
		      .append( F.ormat( getParameterMap() ) )
		      ;
		
		return result.toString();
	}
}
