package de.axone.exception;

public class ArgumentInstanceException extends IllegalArgumentException {

	public ArgumentInstanceException( String name, Class<?> clz ){
		super( "Argument '" + name + "' is not a instance of class '" + clz.getName() + "'" );
	}
}
