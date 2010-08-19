package de.axone.function;


public class Argument_String implements Argument<String> {
	
	private String value;

	@Override
	public String toString() {
		return value;
	}

	@Override
	public void parse( String value ) {
		this.value = value;
	}

	@Override
	public String value() {
		return value;
	}

}
