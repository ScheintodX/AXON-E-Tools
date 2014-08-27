package de.axone.funky;

import java.util.List;

/**
 * Represens one argument for a function
 * 
 * @author flo
 *
 * @param <T> The result type in which this argument is parsed
 */
public interface Argument<V,T extends ArgumentType<V>> {
	
	//public static enum Type { INTEGER, FLOAT, STRING, BOOLEAN, OBJECT; }
	
	/**
	 * Returns the type of this argument
	 */
	public T type();
	
	/**
	 * Returns the name of this argument
	 * 
	 * E.g. "all"
	 * 
	 * @return
	 */
	public String name();
	
	/**
	 * Returns an optional short name of this argument
	 * 
	 * This is used by the shell parser
	 * 
	 * E.g. "a" -> "-a"
	 * 
	 * @return a short name
	 */
	public String shortName();
	
	/**
	 * Returns a description of what this argument does
	 * 
	 * @return
	 */
	public String description();
	
	/**
	 * Returns a detailed description of what this does
	 * 
	 * @return
	 */
	public String longDescription();
	
	/**
	 * Returns if this argument is optional
	 * 
	 * @return
	 */
	public boolean optional();
	
	/**
	 * Returns if this argument is positional
	 * 
	 * @return
	 */
	public boolean positional();
	
	/**
	 * Returns the default value for optional arguments
	 * 
	 * @return
	 */
	public V defaultValue();
	
	/**
	 * Return list of validators
	 * 
	 * @return
	 */
	public List<ArgumentValidator<V>> validators();
	
}
