package de.axone.function;

import de.axone.tools.EasyParser;


public class Argument_Boolean implements Argument<Boolean> {
	
	private Boolean value;
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public void parse( String value ) {
		this.value = EasyParser.yesOrNoOrNull( value );
	}

	@Override
	public Boolean value() {
		return value;
	}

}
