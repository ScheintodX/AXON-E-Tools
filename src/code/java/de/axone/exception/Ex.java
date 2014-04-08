package de.axone.exception;

import java.util.Arrays;



public abstract class Ex {

	public static <T extends Throwable> T up( T e ){
		return up( e, 1 );
	}
	public static <T extends Throwable> T up( T e, int steps ){
		
		// This kills in combination with assert some debugging info.
		// Don't know why
		StackTraceElement [] trace = e.getStackTrace();
		
		e.setStackTrace( Arrays.copyOfRange( trace, steps, trace.length ) );
		
		return e;
	}

}