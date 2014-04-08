package de.axone.funky.types;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import de.axone.funky.ArgumentValidator;

public class ArgumentValidator_OneOf implements ArgumentValidator<String> {
	
	private final Set<String> values;
	
	@SafeVarargs
	public ArgumentValidator_OneOf( String ... values ){
		this.values = new TreeSet<>( Arrays.asList( values ) );
	}

	@Override
	public String description() {
		return "Must be one of: " + values;
	}

	@Override
	public String validate( String value ) {
		if( values.contains( value ) ){
			return null;
		} else {
			return "'" + value + "' is not one of " + values;
		}
	}


}
