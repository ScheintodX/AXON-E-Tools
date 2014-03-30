package de.axone.funky.types;

import de.axone.funky.ArgumentType;
import de.axone.tools.EasyParser;

public class ArgumentType_Boolean implements ArgumentType<Boolean> {

	@Override
	public String name() { return "String"; }

	@Override
	public Boolean parse( String value ) {
		return EasyParser.yesOrNoOrNull( value );
	}

}
