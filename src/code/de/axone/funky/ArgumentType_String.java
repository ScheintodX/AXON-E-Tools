package de.axone.funky;

public class ArgumentType_String implements ArgumentType<String> {

	@Override
	public String name() { return "String"; }

	@Override
	public String parse( String value ) {
		return value;
	}

}
