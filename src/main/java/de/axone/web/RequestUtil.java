package de.axone.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import de.axone.tools.AbstractStringAccessor;
import de.axone.tools.S;
import de.axone.tools.Str;

/**
 * Helper class for accessing information stored in a request
 * 
 * @author flo
 */
public class RequestUtil extends AbstractStringAccessor {
	
	private HttpServletRequest request;
	
	public RequestUtil( HttpServletRequest request ){
		this.request = request;
	}
	
	@Override
	public String doGet( String value ) {
		return request.getParameter( value );
	}
		
	/**
	 * Extract Parameter which is only identified by name
	 * in format <tt>name-value</tt>. This is useful for transmitting parameters
	 * by <tt>&lt;input type="submit"&gt;</tt> and <tt>&lt;button&gt;</tt>
	 * where value cannot be used because it is displayed.
	 *
	 * Example: Request parameter is <tt>item-123=test</tt>.
	 * <tt>getNameParameter( request, "item" )</tt> will return <tt>123</tt>
	 *
	 * @param request The Request we're looking at
	 * @param key to look for
	 * @return the parameter's value
	 */
	public static String getNameParameter( HttpServletRequest request, String key ){

		Enumeration<?> names = request.getParameterNames();

		while( names.hasMoreElements() ){

			String name = (String)names.nextElement();
			String value = extract( name, key );

			if( value != null ) return value;
		}

		return null;
	}

	public static boolean hasNameParameter( HttpServletRequest request, String key ){
		return getNameParameter( request, key ) != null;
	}

	public static String extract( String value, String key ){

		key = key + "-";

		if( value.startsWith( key ) ){

			return value.substring( key.length() );
		}
		return null;
	}
	
	@Override
	public String toString(){
		
		StringBuilder result = new StringBuilder();
		
		Enumeration<?> names = request.getParameterNames();

		while( names.hasMoreElements() ){
			
			String name = (String)names.nextElement();
			String [] value = request.getParameterValues( name );
			
			result
					.append( name )
					.append( ": " )
					.append( Str.join( ", ", value ) )
					.append( S.nl );
		}
		
		return result.toString();
	}
}
