package de.axone.tools;


public class ConfigurationMissmatchException extends Exception {

	public ConfigurationMissmatchException(String message) { super(message); }
	public ConfigurationMissmatchException(Throwable cause) { super(cause); }
	public ConfigurationMissmatchException(String message, Throwable cause) { super(message, cause); }
}
