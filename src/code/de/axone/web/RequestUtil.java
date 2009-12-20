package de.axone.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	/**
	 * Extract Parameter which is only identified by name
	 * in format <tt>name-value<tt>. This is useful for transmitting parameters
	 * by <tt>&lt;input type="submit"&gt;</tt> and <tt>&lt;button&gt;</tt>
	 * where value cannot be used because it is displayed.
	 *
	 * Example: Request parameter is <tt>item-123=test</tt>.
	 * <tt>getNameParameter( request, "item" )</tt> will return <tt>123</tt>
	 *
	 * @param request
	 * @param key.
	 * @return
	 */
	public static String getNameParameter( HttpServletRequest request, String key ){

		@SuppressWarnings("unchecked")
		Enumeration<String> names = request.getParameterNames();

		while( names.hasMoreElements() ){

			String name = names.nextElement();
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
}
