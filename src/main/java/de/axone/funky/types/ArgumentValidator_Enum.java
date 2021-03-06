package de.axone.funky.types;

import java.util.EnumSet;

import de.axone.funky.ArgumentValidator;

public class ArgumentValidator_Enum<T extends Enum<T>> implements ArgumentValidator<T> {
	
	private EnumSet<T> enums;
	
	@SafeVarargs
	public ArgumentValidator_Enum( T first, T ... rest ){
		this.enums = EnumSet.of( first, rest );
	}
	public ArgumentValidator_Enum( Class<T> clazz ){
		this.enums = EnumSet.allOf( clazz );
	}

	@Override
	public String description() {
		return "Must be one of: " + enums;
	}

	@Override
	public String validate( T value ) {
		if( enums.contains( value ) ){
			return null;
		} else {
			return "'" + value + "' is not one of " + enums;
		}
	}


}
