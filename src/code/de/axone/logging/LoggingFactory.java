package de.axone.logging;

public interface LoggingFactory {

	public Log makeLog( Class<?> clazz );
	public Log makeLog( String className );
}
