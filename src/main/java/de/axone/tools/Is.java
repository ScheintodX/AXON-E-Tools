package de.axone.tools;

/**
 * Class which contains common checks
 * 
 * @author flo
 */
public abstract class Is {

	@SafeVarargs
	public static <T extends Enum<T>> boolean oneOf( T test, T ... options ) {
		for( T option : options ) {
			if( test == option ) return true;
		}
		return false;
	}
}
