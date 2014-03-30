package de.axone.funky.shell;

import de.axone.funky.Argument;
import de.axone.funky.ArgumentValidator;
import de.axone.funky.Caller_Shell;
import de.axone.funky.FunctionDescription;
import de.axone.funky.Manual;
import de.axone.tools.S;
import de.axone.tools.Text;

public class Manual_Shell implements Manual<Caller_Shell>{
	
	private static final int INDENT_DESCRIPTION = 20;

	@Override
	public CharSequence explain( FunctionDescription description ) {
		
		StringBuilder result = new StringBuilder();
		
		result
			.append( description.name() ).append( ": " )
			.append( description.description() )
			.append( S.NL )
			.append( Text.line( '=', 79 ) )
			.append( S.NL )
		;
		
		for( Argument<?,?> arg : description.arguments() ){
			result
				.append( explain( arg, false ) )
				.append( S.NL )
			;
		}
		
		return result;
	}

	@Override
	public CharSequence explain( Argument<?, ?> arg, boolean longVersion ) {
		
		StringBuilder result = new StringBuilder();
		
		if( arg.optional() ) result.append( '[' );
		
		if( !arg.positional() ) result.append( "--" );
		result.append( arg.name() );
		
		if( arg.shortName() != null ){
			result.append( "(-" ).append( arg.shortName() ).append( ')' );
		}
		if( arg.optional() ) result.append( ']' );
		
		if( arg.description() != null ){
			result.append( Text.line( ' ', INDENT_DESCRIPTION-result.length() ) );
			result.append( arg.description() );
		}
		if( arg.validators().size() > 0 ){
			result.append( '[' );
			for( ArgumentValidator<?> val : arg.validators() ){
				result.append( " " ).append( val.description() );
			}
			result.append( " ]" );
		}
		
		if( longVersion && arg.longDescription() != null ){
			
			result.append( S.nl )
				.append( arg.longDescription() )
				.append( S.nl );
		}
		
		return result;
	}
	
}
