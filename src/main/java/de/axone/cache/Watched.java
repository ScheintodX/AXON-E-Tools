package de.axone.cache;

/**
 * Can be implemented optionally and provides additional runtime information
 * for this cache
 * 
 * @author flo
 */
public interface Watched {
	
	public double ratio();
}