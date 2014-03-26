package de.axone.function;


public class ArgumentParserTag implements ArgumentParser {

	@Override
	public ArgumentVector parseArgs( 
			ArgumentDescription<?> [] descriptions, 
			String [] args
	) throws ShellException{
		
		return null;
	}
	
	@Override
	public String [] parseLine( String userInput ){
		
		return null;
	}
}
