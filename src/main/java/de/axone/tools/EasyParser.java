package de.axone.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


public abstract class EasyParser {

	private static final String [] YESSES 
			= new String[]{ "true", "on", "y", "j", "yes", "ja", "x", "1" };
	private static final String [] NOS 
			= new String[]{ "false", "off", "n", "no", "nein", "-", "", "0" };
	private static final Collection<String> YESSET 
			= new TreeSet<String>( Arrays.asList( YESSES ) );
	private static final Collection<String> NOSET 
			= new TreeSet<String>( Arrays.asList( NOS ) );
	
	public static boolean isYes( @Nullable String yesOrNo ) {
		
		return contains( YESSET, yesOrNo );
	}

	public static boolean isNo( @Nullable String yesOrNo ) {

		return contains( NOSET, yesOrNo );
	}

	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL" )
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

	private static boolean contains( Collection<String> options, @Nullable String yesOrNo ) {
		
		if( yesOrNo == null ) return false;
		
		yesOrNo = yesOrNo.trim().toLowerCase();

		return options.contains( yesOrNo );
	}

}
