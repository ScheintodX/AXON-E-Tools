package de.axone.tools;


public abstract class Debug {

	public static String debugString( String string ){

		StringBuilder b = new StringBuilder();

		for( int i = 0; i < string.length(); i++ ){

			char ch = string.charAt( i );

			b	.append( ch )
    			.append( '[' )
    			.append( String.format( "%04x", (int)ch ) )
    			.append( ']' );
		}
		return b.toString();
	}
}
