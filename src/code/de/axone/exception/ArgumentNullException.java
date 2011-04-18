package de.axone.exception;

public class ArgumentNullException extends IllegalArgumentException {

	public ArgumentNullException( String name ){
		super( "Argument '" + name + "' is null" );
	}
}
