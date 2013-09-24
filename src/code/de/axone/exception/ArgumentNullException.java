package de.axone.exception;

public class ArgumentNullException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7625170534259471973L;

	public ArgumentNullException( String name ){
		super( "Argument '" + name + "' is null" );
	}
}
