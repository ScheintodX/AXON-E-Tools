package de.axone.exception;

public class ArgumentRangeException extends IllegalArgumentException {

	public ArgumentRangeException( String name, String test, Object value ){
		super( "Argument '" + name + "' is " + test + ": " + value );
	}
}
