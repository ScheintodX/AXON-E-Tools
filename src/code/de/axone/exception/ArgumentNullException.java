package de.axone.exception;

public class ArgumentNullException extends IllegalNamedArgumentException {

	private static final long serialVersionUID = -7625170534259471973L;

	public ArgumentNullException( String name ){
		super( name, "is null" );
	}
}
