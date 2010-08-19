package de.axone.function;

public class ArgumentParseException extends ShellException {

	public ArgumentParseException( String value, String example, Throwable cause ) {
		super( "Error parsing '" + value + "'. Must look like: '"+example+"'", cause);
	}


}
