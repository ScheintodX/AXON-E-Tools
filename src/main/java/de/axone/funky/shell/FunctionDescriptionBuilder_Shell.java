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
 * ls [-a] [-c none|some|full] dir
 * ls [-a|--all] [-c|--color=none|some|full] dir
 * all: Display hidden files e.g. those starting with dots
 * color: Color scheme to use
 * dir: directory to look in
 * </pre>
 * 
 * Call of shell functions:
 * 
 * ls -a -c full /etc
 * or:
 * ls --all --color=full /etc
 * but although:
 * ls /etc --all -c full
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
		
		NameValue nv = parseArg( arg );
		
		ArgumentImpl<String,ArgumentType<String>> result = new ArgumentImpl<>(
				ArgumentTypes.STRING, nv.name, null, null, null, optional, nv.positional );
		
		if( nv.value != null ){
			result.validate( new ArgumentValidator_OneOf( nv.value.split( "\\s*\\|\\s*" ) ) );
		}
		
		return result;
	}
	
	static class NameValue {
		String name, value;
		boolean positional;
	}
	
	static NameValue parseArg( String arg ){
		
		NameValue result = new NameValue();
		
		if( arg.contains( "=" ) ){
			
			String [] split = arg.split( "=", 2 );
			result.name = split[ 0 ];
			result.value = split[ 1 ];
		} else {
			result.name = arg;
		}
		
		if( result.name.startsWith( "--" ) ){
			result.name = result.name.substring( 2 );
			result.positional = false;
			
		} else if( result.name.startsWith( "-" ) ){
			result.name = result.name.substring( 1 );
			result.positional = false;
		} else {
			result.positional = true;
		}
		return result;
	}
}
