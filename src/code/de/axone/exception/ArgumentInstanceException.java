package de.axone.exception;

import de.axone.web.rest.FieldException;

public class ArgumentInstanceException extends IllegalNamedArgumentException implements FieldException {

	private static final long serialVersionUID = -6814876555941105845L;

	private final String field;
	
	public ArgumentInstanceException( String name, Class<?> clz ){
		super( name, "is not a instance of class '" + clz.getName() + "'" );
		this.field = name;
	}
	
	@Override
	public String getField(){
		return field;
	}
}
