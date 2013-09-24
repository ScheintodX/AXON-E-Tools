package de.axone.exception;

public class ArgumentRangeException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 397362047156526806L;

	public ArgumentRangeException( String name, String test, Object value ){
		super( "Argument '" + name + "' " + test + ": " + value );
	}
}
