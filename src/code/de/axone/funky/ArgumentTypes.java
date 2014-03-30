package de.axone.funky;

import de.axone.funky.types.ArgumentType_Boolean;
import de.axone.funky.types.ArgumentType_String;

public abstract class ArgumentTypes {

	public static ArgumentType<Boolean> BOOLEAN = new ArgumentType_Boolean();
	public static ArgumentType<String> STRING = new ArgumentType_String();
	
}
