package de.axone.tools;


public abstract class EasyParser {

	private static final String[] YESSES = new String[]{ "y", "j", "yes", "ja", "true", "x" };
	private static final String[] NOS = new String[]{ "n", "no", "nein", "false", "-" };

	public static boolean isYes( String yesOrNo ) {

		return contains( yesOrNo, YESSES );
	}

	public static boolean isNo( String yesOrNo ) {

		return contains( yesOrNo, NOS );
	}

	public static Boolean yesOrNo( String yesOrNo ){
		if( isYes( yesOrNo ) ) return true;
		if( isNo( yesOrNo ) ) return false;
		return null;
	}

	private static boolean contains( String yesOrNo, String[] options ) {

		for( String yes : options ){
			if( yes.equalsIgnoreCase( yesOrNo ) ) return true;
		}
		return false;
	}

}
