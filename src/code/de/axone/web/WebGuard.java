package de.axone.web;

public abstract class WebGuard {

	public static String guard( String unprotected ){
		
		if( unprotected == null ) return null;
		
		String guarded = unprotected.replaceAll( "<", "&lt;" );
		
		return guarded;
	}
}
