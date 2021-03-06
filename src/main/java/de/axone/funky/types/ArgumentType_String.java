package de.axone.funky.types;

import de.axone.funky.ArgumentType;

public class ArgumentType_String implements ArgumentType<String> {

	@Override
	public Class<String> type() { return String.class; }
	
	@Override
	public String name() { return "String"; }

	@Override
	public String parse( String value ) {
		return value;
	}


}
