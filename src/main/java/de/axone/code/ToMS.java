package de.axone.code;

//for converting ms to another tom
public abstract class ToMS {

	public static long s( long s ) {

		return s * 1000L;
	}

	public static long min( long min ) {

		return min * 60*1000;
	}

	public static long h( long h ) {

		return h * 60*60*1000;
	}

	public static long days( long days ) {

		return days * 24*60*60*1000;
	}

}
