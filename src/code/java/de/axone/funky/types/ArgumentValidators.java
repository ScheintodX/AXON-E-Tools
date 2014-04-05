package de.axone.funky.types;

import de.axone.funky.ArgumentValidator;

public abstract class ArgumentValidators {

	@SafeVarargs
	public static <X extends Enum<X>> ArgumentValidator<X> EnumOf( X first, X ... rest ){
		return new ArgumentValidator_Enum<X>( first, rest );
	}
	public static <X extends Enum<X>> ArgumentValidator<X> Enum( Class<X> clazz ){
		return new ArgumentValidator_Enum<X>( clazz );
	}
}
