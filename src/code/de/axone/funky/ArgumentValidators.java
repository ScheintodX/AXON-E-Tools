package de.axone.funky;

public abstract class ArgumentValidators {

	public static <X extends Enum<X>> ArgumentValidator<X> EnumOf( X first, X ... rest ){
		return new ArgumentValidator_Enum<X>( first, rest );
	}
}
