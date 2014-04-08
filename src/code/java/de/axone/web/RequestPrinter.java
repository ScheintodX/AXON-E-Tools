package de.axone.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestPrinter {

	public static StringBuilder buildParameters( HttpServletRequest request ){
		
		StringBuilder result = new StringBuilder();
		
		Enumeration<?> names = request.getParameterNames();
		
		while( names.hasMoreElements() ){
			
			String name = (String)names.nextElement();
			
			result
				.append( name )
				.append( ": " )
				.append( request.getParameter( name ) )
				.append( '\n' )
			;
		}
		
		return result;
	}
	
	public static StringBuilder buildInfo( HttpServletRequest request ){
		
		StringBuilder result = new StringBuilder();
		
		result
			.append( request.getRequestURL() ).append( '\n' )
			.append( buildParameters( request ) )
		;
		
		return result;
	}
}
