package de.axone.funky;

public interface ArgumentType<V> {

	public Class<V> type();
	public abstract String name();
	public abstract V parse( String value );
	
}
