package de.axone.funky.shell;

import de.axone.exception.Assert;
import de.axone.funky.Argument;
import de.axone.funky.ArgumentImpl;
import de.axone.funky.ArgumentType;
import de.axone.funky.FunctionDescription;
import de.axone.funky.FunctionDescriptionImpl;
import de.axone.funky.types.ArgumentTypes;
import de.axone.funky.types.ArgumentValidator_OneOf;

/**
 * Build a function description using a shell-like syntax:
 * 
 * <pre>
 * ls [--all] [--color=none|some|full] dir
 * all: Display hidden files e.g. those starting with dots
 * color: Color scheme to use
 * dir: directory to look in
 * </pre>
 * 
 * @author flo
 */
public abstract class FunctionDescriptionBuilder_Shell {
	
	public static FunctionDescription build( String description ){
		
		String [] args = description.split( "\\s+" );
		
		FunctionDescriptionImpl result = new FunctionDescriptionImpl( args[ 0 ] );
		
		for( int i=1; i<args.length; i++ ){
			String arg = args[ i ];
			arg = arg.trim();
			if( arg.length() == 0 ) continue;
			result.argument( buildArgument( args[ i ] ) );
		}
		
		return result;
	}
	
	public static Argument<?,?> buildArgument( String arg ){
		
		boolean optional = false;
		
		if( arg.startsWith( "[" ) ){
			Assert.isTrue( arg.endsWith( "]" ), "must be closed" );
			arg = arg.substring( 1, arg.length()-1 );
			optional = true;
		}
		
		String name;
		String option = null;
		if( arg.contains( "=" ) ){
			
			String [] split = arg.split( "=", 2 );
			name = split[ 0 ];
			option = split[ 1 ];
		} else {
			name = arg;
		}
		
		boolean positional = true;
		if( name.startsWith( "--" ) ){
			name = name.substring( 2 );
			positional = false;
		}
		
		ArgumentImpl<String,ArgumentType<String>> result = new ArgumentImpl<>( ArgumentTypes.STRING, name, null, null, null, optional, positional );
		
		if( option != null ){
			result.validate( new ArgumentValidator_OneOf( option.split( "\\s*\\|\\s*" ) ) );
		}
		
		return result;
	}
}
