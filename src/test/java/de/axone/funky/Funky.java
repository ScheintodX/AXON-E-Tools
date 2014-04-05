package de.axone.funky;

import org.testng.annotations.Test;

import de.axone.funky.shell.FunctionDescriptionBuilder_Shell;
import de.axone.funky.shell.Shell;
import de.axone.funky.types.ArgumentTypes;
import de.axone.funky.types.ArgumentValidators;
import de.axone.tools.E;


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
								ArgumentTypes.ENUM( COLORS.class ),
								"color", "Color status" )
						.validate( ArgumentValidators.EnumOf( COLORS.none, COLORS.full ) )
						//.validate( ArgumentValidators.Enum( COLORS.class ) )
				);
		;
		
		Shell shell = new Shell();
		shell.man( ls );
		
		String description = "ls [--all] [--color=none|some|full] dir";
		
		FunctionDescription ls2 = FunctionDescriptionBuilder_Shell.build( description );
				
		E.rr( description );
		shell.man( ls2 );
		
		//Map<String,Object> values = shell.parser().parse( ls, "ls --all --color=none /home/flo" );
		//E.rr( values );
		
	}
}
