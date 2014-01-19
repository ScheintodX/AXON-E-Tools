package de.axone.exception;

public class ArgumentRangeException extends IllegalNamedArgumentException implements FieldException {

	private static final long serialVersionUID = 397362047156526806L;

	private final String field;
	
	public ArgumentRangeException( String name, String test, Object value ){
		super( name, test + ": " + value );
		this.field = name;
	}
	
	@Override
	public String getField(){
		return field;
	}
}
