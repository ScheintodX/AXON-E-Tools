package de.axone.function;


public class MissingArgumentException extends ShellException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3183895695423648935L;

	public MissingArgumentException( ArgumentDescription<?> desc ) {
		super( "Missing argument: " + desc.name().toUpperCase() );
	}
}
