package de.axone.function;

public class ArgumentParseException extends ShellException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8060043237807552228L;

	public ArgumentParseException( String value, String example, Throwable cause ) {
		super( "Error parsing '" + value + "'. Must look like: '"+example+"'", cause);
	}


}
