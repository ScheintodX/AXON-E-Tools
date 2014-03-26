package de.axone.function;


public class Argument_Long implements Argument<Long> {
	
	private Long value;
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public void parse( String value ) {
		this.value = Long.parseLong( value );
	}

	@Override
	public Long value() {
		return value;
	}

}
