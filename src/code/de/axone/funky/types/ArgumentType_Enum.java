package de.axone.funky.types;

import de.axone.funky.ArgumentType;

public class ArgumentType_Enum<T extends Enum<T>> implements ArgumentType<T> {
	
	private final Class<T> clazz;
	
	ArgumentType_Enum( Class<T> clazz ){
		this.clazz = clazz;
	}
	
	public static <X extends Enum<X>> ArgumentType_Enum<X> Instance( Class<X> clazz ){
		return new ArgumentType_Enum<X>( clazz );
	}

	@Override
	public String name() { return "Enum"; }

	@Override
	public T parse( String value ) {
		return Enum.valueOf( clazz, value );
	}


}
