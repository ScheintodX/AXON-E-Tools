package de.axone.function;


public class MissingArgumentException extends ShellException {

	public MissingArgumentException( ArgumentDescription<?> desc ) {
		super( "Missing argument: " + desc.name().toUpperCase() );
	}
}
