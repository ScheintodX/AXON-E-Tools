package de.axone.tools;

import de.axone.tools.KeyValueAccessor.ValueProvider;

public interface If {

	public static <T> T Otherwise( T value, T defaultValue ) {
		return value != null ? value : defaultValue;
	}

	public static <T> T Otherwise( T value, ValueProvider<T> defaultValueProvider ) {
		return value != null ? value : defaultValueProvider.get();
	}

	public static String EmptyOtherwise( String value, String defaultValue ) {
		return value != null && value.trim().length() > 0 ? value : defaultValue;
	}

	public static String EmptyOtherwise( String value, ValueProvider<String> defaultValueProvider ) {
		return value != null && value.trim().length() > 0 ? value : defaultValueProvider.get();
	}

	@SafeVarargs
	public static <T> T NotNull( T ... values ) {
		for( T t : values ) if( t != null ) return t;
		return null;
	}

}
