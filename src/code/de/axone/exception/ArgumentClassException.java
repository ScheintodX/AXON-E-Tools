package de.axone.exception;

public class ArgumentClassException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172997426679904688L;

	public ArgumentClassException( String name, Class<?> clz ){
		super( "Argument '" + name + "' is not of class '" + clz.getName() + "'" );
	}
}
