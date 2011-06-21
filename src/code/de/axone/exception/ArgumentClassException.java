package de.axone.exception;

public class ArgumentClassException extends IllegalArgumentException {

	public ArgumentClassException( String name, Class<?> clz ){
		super( "Argument '" + name + "' is not of class '" + clz.getName() + "'" );
	}
}
