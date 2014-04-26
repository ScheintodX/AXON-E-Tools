package de.axone.funky.shell;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.axone.funky.ArgumentParser;
import de.axone.funky.FunctionDescription;

public class ArgumentParser_Shell implements ArgumentParser<Caller_Shell> {
	
	public Map<String,Object> parse( FunctionDescription function, final List<String> args ){
		
		// Make a copy to we don't change arguments
		//LinkedList<String> myArgs = new LinkedList<>( args );
		//LinkedList<Argument<?,?>> myArguments = new LinkedList<>( function.arguments() );
		
		TreeMap<String,Object> result = new TreeMap<>();
		
		/*
		for( Argument<?,?> arg : function.arguments() ){
			
			String found = find( arg, myArgs );
			
			if( ! arg.optional() && found == null )
				throw new IllegalArgumentException( "Missing: " + arg.name() );
			
			Object value = arg.type().parse( found );
			
			result.put( arg.name(), value );
		}
		int pos = 0;
		for( String arg : args ){
			Argument<?,?> a = find( pos, function.arguments(), arg );
		}
		*/
		return result;
	}
	
	/*
	private Argument<?,?> find( int i, List<Argument<?,?>> args, String arg ){
		
		NameValue nv = FunctionDescriptionBuilder_Shell.parseArg( arg );
		
		int pos = 0;
		for( Argument<?,?> a : args ){
		}
	}
	*/
	
	/*
	private String find( Argument<?,?> arg, List<String> args ){
		
		for( String a : args ){
			
			NameValue nv = FunctionDescriptionBuilder_Shell.parseArg( a );
		}
		
		return null;
	}
	*/

	@Override
	public Map<String,Object> parse( FunctionDescription function, String line ){
		
		return parse( function, Arrays.asList( line.split( "\\s+" ) ) );
	}
}
