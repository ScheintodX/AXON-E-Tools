package de.axone.function;

public class CommandNotFoundException extends ShellException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8638103687126882462L;
	private static final String COMMAND_NOT_FOUND = "command not found: ";
	
	public CommandNotFoundException( String command ) {
		super( COMMAND_NOT_FOUND + command );
	}

}
