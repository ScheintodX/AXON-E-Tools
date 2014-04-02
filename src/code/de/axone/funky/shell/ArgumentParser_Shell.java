package de.axone.funky.shell;

import java.util.Map;
import java.util.TreeMap;

import de.axone.funky.Argument;
import de.axone.funky.ArgumentParser;
import de.axone.funky.FunctionDescription;

public class ArgumentParser_Shell implements ArgumentParser<Caller_Shell> {
	
	public Map<String,Object> parse( FunctionDescription function, String ... args ){
		
		TreeMap<String,Object> result = new TreeMap<>();
		
		for( Argument<?,?> arg : function.arguments() ){
			
			String found = find( arg, args );
			
			if( ! arg.optional() && found == null )
				throw new IllegalArgumentException( "Missing: " + arg.name() );
		}
		return result;
	}
	
	private String find( Argument<?,?> arg, String ... args ){
		return null;
	}

	@Override
	public Map<String,Object> parse( FunctionDescription function, String line ){
		
		return parse( function, line.split( "\\s+" ) );
	}
}
