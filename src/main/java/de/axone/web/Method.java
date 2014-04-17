package de.axone.web;

import javax.servlet.http.HttpServletRequest;

public enum Method {
	
	GET, PUT, POST, DELETE;
	
	public boolean is( HttpServletRequest request ){
		return name().equalsIgnoreCase( request.getMethod() );
	}
	
	public static Method from( HttpServletRequest request ){
		return valueOf( request.getMethod().toUpperCase() );
	}
	
}

