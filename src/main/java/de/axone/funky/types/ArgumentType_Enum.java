package de.axone.funky.types;

import de.axone.funky.ArgumentType;

public class ArgumentType_Enum<T extends Enum<T>> implements ArgumentType<T> {
	
	private final Class<T> clazz;
	
	public ArgumentType_Enum( Class<T> clazz ){
		this.clazz = clazz;
	}
	
	@Override
	public Class<T> type() { return clazz; }
	
	@Override
	public String name() { return "Enum"; }

	@Override
	public T parse( String value ) {
		return Enum.valueOf( clazz, value );
	}



}
