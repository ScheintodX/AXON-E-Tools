package de.axone.data;

public class Compare {
	
	public static int invert( int value ) {
		if( value == 0 ) return 0;
		if( value < 0 ) return 1;
		return -1;
	}
}
