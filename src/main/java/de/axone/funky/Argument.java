package de.axone.funky;

import java.util.List;

/**
 * Represens one argument for a function
 * 
 * @author flo
 *
 * @param <T> The result type in which this argument is parsed
 * @param <V> The value type of this argument
 */
public interface Argument<V,T extends ArgumentType<V>> {
	
	//public static enum Type { INTEGER, FLOAT, STRING, BOOLEAN, OBJECT; }
	
	/**
	 * @return the type of this argument
	 */
	public T type();
	
	/**
	 * Returns the name of this argument
	 * 
	 * E.g. "all"
	 * 
	 * @return the name of this argument
	 */
	public String name();
	
	/**
	 * Returns an optional short name of this argument
	 * 
	 * This is used by the shell parser
	 * 
	 * E.g. "a" -&gt; "-a"
	 * 
	 * @return a short name
	 */
	public String shortName();
	
	/**
	 * @return a description of what this argument does
	 */
	public String description();
	
	/**
	 * @return a detailed description of what this does
	 */
	public String longDescription();
	
	/**
	 * @return if this argument is optional
	 */
	public boolean optional();
	
	/**
	 * @return if this argument is positional
	 */
	public boolean positional();
	
	/**
	 * @return the default value for optional arguments
	 */
	public V defaultValue();
	
	/**
	 * @return list of validators
	 */
	public List<ArgumentValidator<V>> validators();
	
}
