package de.axone.function;

public class CommandNotFoundException extends ShellException {

	private static final String COMMAND_NOT_FOUND = "command not found: ";
	
	public CommandNotFoundException( String command ) {
		super( COMMAND_NOT_FOUND + command );
	}

}
