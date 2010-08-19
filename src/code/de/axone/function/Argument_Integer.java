package de.axone.function;


public class Argument_Integer implements Argument<Integer> {
	
	private Integer value;
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public void parse( String value ) {
		this.value = Integer.parseInt( value );
	}

	@Override
	public Integer value() {
		return value;
	}

}
