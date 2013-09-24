package de.axone.tools;


public class ConfigurationMissmatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3675032761076410140L;
	public ConfigurationMissmatchException(String message) { super(message); }
	public ConfigurationMissmatchException(Throwable cause) { super(cause); }
	public ConfigurationMissmatchException(String message, Throwable cause) { super(message, cause); }
}
