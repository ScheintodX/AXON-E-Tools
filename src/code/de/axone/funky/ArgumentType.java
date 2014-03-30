package de.axone.funky;

public interface ArgumentType<T> {

	public Class<T> type();
	public abstract String name();
	public abstract T parse( String value );
	
}
