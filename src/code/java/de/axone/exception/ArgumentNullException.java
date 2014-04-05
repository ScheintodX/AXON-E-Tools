package de.axone.exception;

public class ArgumentNullException extends IllegalNamedArgumentException implements FieldException {

	private static final long serialVersionUID = -7625170534259471973L;
	
	private final String field;

	public ArgumentNullException( String name ){
		super( name, "is null" );
		this.field = name;
	}
	
	@Override
	public String getField(){
		return field;
	}
}
