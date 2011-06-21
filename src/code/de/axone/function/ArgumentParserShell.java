package de.axone.function;

import java.util.LinkedList;
import java.util.TreeMap;

public class ArgumentParserShell implements ArgumentParser {

	private static final String ARG_PREFIX = "--";

	@Override
	public ArgumentVector parseArgs( 
			ArgumentDescription<?> [] descriptions, 
			String [] args
	) throws ShellException{
		
		ArgumentVector result = new ArgumentVector();
		
		if( descriptions == null || descriptions.length == 0 ){
			
			if( args.length == 1 ){
				return result;
			} else {
				throw new WrongArgumentCountException( 0, args.length-1 );
			}
		}
		
		TreeMap<String,String> named = new TreeMap<String,String>();
		LinkedList<String> unnamed = new LinkedList<String>();
		
		// preparse
		String name = null;
		for( int i = 1; i < args.length; i++ ){
			
			String arg = args[ i ];
			
			if( arg.startsWith( ARG_PREFIX ) ){
				if( name != null ){
					named.put( name, null );
				}
				name = arg.substring( 2 );
				
			} else {
				if( name != null ){
					named.put( name, arg );
					name = null;
				} else {
					unnamed.addLast( arg );
				}
			}
		}
		
		for( ArgumentDescription<?> desc : descriptions ){
			
			String arg = null;
			boolean found = false;
			if( desc.isNamed() && named.containsKey( desc.name() ) ){
					
				arg = named.get( desc.name() );
				found = true;
				
			} else if( (!desc.isNamed()) && unnamed.size() > 0 ){
					
				arg = unnamed.removeFirst();
				found = true;
			}
			
			if( found ){
				
				Argument<?> argument = desc.argumentInstance();
				argument.parse( arg );
				result.put( desc, argument );
				
			} else {
				if( desc.defaultValue() != null ){
					
					Argument<?> argument = desc.argumentInstance();
					argument.parse( desc.defaultValue() );
					result.put( desc, argument );
					
				} else if( desc.isMandatory() ) {
					throw new MissingArgumentException( desc );
				}
			}
				
		}
		
		return result;
	}
	
	private static final char split[] = { ' ', '\t' };
	
	@SuppressWarnings( "null" )
	@Override
	public String [] parseLine( String userInput ){
		
		LinkedList<String> result = new LinkedList<String>();
		StringBuffer temp = null;
		
		boolean inString = false;
		boolean escaped = false;
		
		for( char c : userInput.toCharArray() ){

			// Escape Sequences
			if( escaped ){
				
				// Newline
				if( c == 'n' ){
					temp.append( '\n' );
					escaped = false;
					continue;
				}
				
				// Escape
				if( c == '\\' ){
					temp.append( '\\' );
					escaped = false;
					continue;
				}
				
				// Hochkommas
				if( c == '"' ){
					temp.append( c );
					escaped = false;
					continue;
				}
			}

			// Escape
			if( c == '\\' ){
				
				escaped = true;
				continue;
			}
			
			// Whitespace in Zwischenr�umen
			if( Character.isWhitespace( c ) ) {
				
				// Whitespace am Anfang �berspringen
				if( temp == null ){
					continue;
				}
			}

			// Is Split?
			boolean isSplit = false;
			for( char s : split ){
				if( c == s ){
					isSplit = true;
					break;
				}
			}
			
			if( isSplit ){
				
				// Innerhalb Hochkommas od. escaped
				if( inString || escaped ){
					
					temp.append( c );
					escaped = false;
					continue;
				
				// Argument abgeschlossen
				} else {
					
					result.addLast( temp.toString() );
					temp = null;
					continue;
				}
			}
			
			//Hochkommas
			if( c == '"' ) {
			
				// Schliessend
				if( inString ){
				
					inString = false;
					temp.append( c );
					result.addLast( temp.toString() );
					temp = null;
					continue;
				
				// Oeffnend
				} else {
					inString = true;
					if( temp == null ){
						temp = new StringBuffer();
					} else {
						result.addLast( temp.toString() );
						temp = new StringBuffer();
					}
					temp.append( c );
					continue;
				}
			}
			
			//Kommentare
			if( c == '#' ){
				
				if( inString ){
					temp.append( c );
					continue;
				}

				break;
			}
		
			// Normales Zeichen
			
			// Wenn grade kein Arg. bearbeitet wird, einen neuen Buffer anlegen
			if( temp == null ) temp = new StringBuffer();
			
			escaped = false;
			temp.append( c );
		}
		
		// letzte inhalte
		if( temp != null ){
			result.addLast( temp.toString() );
		}
		
		String [] resultArray = result.toArray( new String[ result.size() ] );
		
		return resultArray;
	}
}
