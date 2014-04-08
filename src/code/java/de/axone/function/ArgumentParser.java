package de.axone.function;

public interface ArgumentParser {

	public ArgumentVector parseArgs( ArgumentDescription<?>[] descriptions,
			String[] args ) throws ShellException;

	public String[] parseLine( String userInput );

}