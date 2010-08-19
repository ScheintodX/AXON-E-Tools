package de.axone.function;


public interface Command {

	public String name();
	public String description();
	public Result run( ArgumentVector args ) throws ShellException;
	public ArgumentDescription<?>[] arguments();
	
}
