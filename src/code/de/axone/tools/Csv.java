package de.axone.tools;

public abstract class Csv {

	public static String encode( String value ){
		if( value == null ) return null;
		return "\"" + value.replaceAll( "\"", "\"\"" ) + "\"";
	}
	public static String fullEncode( String value ){
		if( value == null ) return "";
		return encode( value );
	}
}
