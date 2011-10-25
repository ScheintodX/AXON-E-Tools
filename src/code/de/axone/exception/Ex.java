package de.axone.exception;


public abstract class Ex {

	public static <T extends Throwable> T up( T e ){
		return up( e, 1 );
	}
	/*
	// This kills in combination with assert some debugging info.
	// Don't know why
	public static <T extends Throwable> T up( T e, int steps ){
		
		StackTraceElement [] trace = e.getStackTrace();
		
		e.setStackTrace( Arrays.copyOfRange( trace, steps, trace.length ) );
		
		return e;
	}
	*/
	// TODO: This is for debugging. Remove if ready
	public static <T extends Throwable> T up( T e, int steps ){
		return e;
	}

}
