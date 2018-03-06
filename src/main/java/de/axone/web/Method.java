package de.axone.web;

import javax.servlet.http.HttpServletRequest;

public enum Method {
	
	GET, PUT, POST, DELETE, HEAD, OPTION;
	
	public boolean is( HttpServletRequest request ){
		return name().equalsIgnoreCase( request.getMethod() );
	}
	
	public boolean is( String name ){
		return name().equalsIgnoreCase( name );
	}
	
	public static Method from( String name ){
		return valueOf( name.toUpperCase() );
	}
	
	public static Method from( HttpServletRequest request ){
		return valueOf( request.getMethod().toUpperCase() );
	}
	
}

