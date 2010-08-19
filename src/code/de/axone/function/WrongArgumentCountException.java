package de.axone.function;

public class WrongArgumentCountException extends ShellException {

	public WrongArgumentCountException( int expected, int is ) {
		super( "Expected: " + expected + " but is: " + is );
	}
	public WrongArgumentCountException( int is ) {
		super( "Expected more than: " + is );
	}
}
