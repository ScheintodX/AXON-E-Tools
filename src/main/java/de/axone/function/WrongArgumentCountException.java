package de.axone.function;

public class WrongArgumentCountException extends ShellException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7218367044725367015L;
	public WrongArgumentCountException( int expected, int is ) {
		super( "Expected: " + expected + " but is: " + is );
	}
	public WrongArgumentCountException( int is ) {
		super( "Expected more than: " + is );
	}
}
