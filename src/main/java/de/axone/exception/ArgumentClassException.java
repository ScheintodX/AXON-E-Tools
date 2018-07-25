package de.axone.exception;

import de.axone.web.rest.FieldException;

public class ArgumentClassException extends IllegalNamedArgumentException implements FieldException {

	private static final long serialVersionUID = -9172997426679904688L;

	private final String field;
	
	public ArgumentClassException( String name, Class<?> clz ){
		super( name, "is not of class '" + clz.getName() + "'" );
		this.field = name;
	}
	
	public ArgumentClassException( String name, Class<?> clz, Class<?> actual ){
		super( name, "is not of class '" + clz.getName() + "'" + " but '" + actual.getName() + "'" );
		this.field = name;
	}
	
	@Override
	public String getField(){
		return field;
	}
	
}
