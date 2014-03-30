package de.axone.funky;

import java.util.EnumSet;

public class ArgumentValidator_Enum<T extends Enum<T>> implements ArgumentValidator<T> {
	
	private EnumSet<T> enums;
	
	public ArgumentValidator_Enum( T first, T ... rest ){
		this.enums = EnumSet.of( first, rest );
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
