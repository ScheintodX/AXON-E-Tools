package de.axone.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;


public abstract class EasyParser {

	private static final String [] YESSES 
			= new String[]{ "y", "j", "yes", "ja", "true", "x", "1" };
	private static final String [] NOS 
			= new String[]{ "n", "no", "nein", "false", "-", "", "0" };
	private static final Collection<String> YESSET 
			= new TreeSet<String>( Arrays.asList( YESSES ) );
	private static final Collection<String> NOSET 
			= new TreeSet<String>( Arrays.asList( NOS ) );
	
	public static boolean isYes( String yesOrNo ) {
		
		return contains( yesOrNo, YESSET );
	}

	public static boolean isNo( String yesOrNo ) {

		return contains( yesOrNo, NOSET );
	}

	public static Boolean yesOrNoOrNull( String yesOrNo ){
		if( isYes( yesOrNo ) ) return true;
		if( isNo( yesOrNo ) ) return false;
		return null;
	}

	public static boolean yesOrNo( String yesOrNo, boolean defaultValue ){
		if( isYes( yesOrNo ) ) return true;
		if( isNo( yesOrNo ) ) return false;
		return defaultValue;
	}

	private static boolean contains( String yesOrNo, Collection<String> options ) {
		
		if( yesOrNo == null ) return false;
		
		yesOrNo = yesOrNo.trim().toLowerCase();

		return options.contains( yesOrNo );
	}

}
