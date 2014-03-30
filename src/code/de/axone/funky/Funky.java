package de.axone.funky;

import de.axone.funky.shell.FunctionDescriptionBuilder;
import de.axone.funky.shell.Shell;
import de.axone.funky.types.ArgumentType_Enum;


public class Funky {
	
	private enum COLORS { none, some, full; }
	
	public static void main( String [] args ) throws Exception {
	
		FunctionDescription ls = new FunctionDescriptionImpl( "ls" )
				.description( "List directory content" )
				.required( ArgumentTypes.STRING, "dir", "Directory to look at" )
				.optional( ArgumentTypes.BOOLEAN, "all", "Include hidden files (e.g. files starting with dot (.)" )
				.argument(
						ArgumentImpl.Optional(
								ArgumentType_Enum.Instance( COLORS.class ),
								"color", "Color status" )
						.validate( ArgumentValidators.EnumOf( COLORS.none, COLORS.some, COLORS.full ) )
				);
		;
		
		Shell shell = new Shell();
		shell.man( ls );
		
		FunctionDescription ls2 = FunctionDescriptionBuilder.build(
				"ls [--all] [--color=none|some|full] dir" );
		shell.man( ls2 );
		
	}
}
