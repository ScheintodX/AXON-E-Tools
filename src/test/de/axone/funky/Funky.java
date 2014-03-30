package de.axone.funky;

import org.testng.annotations.Test;

import de.axone.funky.shell.FunctionDescriptionBuilder_Shell;
import de.axone.funky.shell.Shell;
import de.axone.funky.types.ArgumentType_Enum;
import de.axone.funky.types.ArgumentTypes;
import de.axone.funky.types.ArgumentValidators;


@Test( groups="tools.funky" )
public class Funky {
	
	private enum COLORS { none, some, full; }
	
	public void test() throws Exception {
	
		FunctionDescription ls = new FunctionDescriptionImpl( "ls" )
				.description( "List directory content" )
				.required( ArgumentTypes.FILE, "dir", "Directory to look at" )
				.optional( ArgumentTypes.BOOLEAN, "all", "Include hidden files (e.g. files starting with dot (.)" )
				.argument(
						ArgumentImpl.Optional(
								ArgumentType_Enum.Instance( COLORS.class ),
								"color", "Color status" )
						//.validate( ArgumentValidators.EnumOf( COLORS.none, COLORS.full ) )
						.validate( ArgumentValidators.Enum( COLORS.class ) )
				);
		;
		
		Shell shell = new Shell();
		shell.man( ls );
		
		FunctionDescription ls2 = FunctionDescriptionBuilder_Shell.build(
				"ls [--all] [--color=none|some|full] dir" );
		shell.man( ls2 );
		
	}
}
