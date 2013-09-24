package de.axone.exception;

public class ArgumentInstanceException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6814876555941105845L;

	public ArgumentInstanceException( String name, Class<?> clz ){
		super( "Argument '" + name + "' is not a instance of class '" + clz.getName() + "'" );
	}
}
