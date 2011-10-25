package de.axone.cache;

import java.util.Collection;
import java.util.Set;

/**
 * Minimal interface for caches which should be implementable for any cache
 * 
 * Note that caches *must be threadsafe*!!!!
 * 
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K,V> {
	
	/**
	 * Put something in the cache
	 * 
	 * @param key
	 * @param value
	 * @return the given value
	 */
	public V put( K key, V value );
	
	/**
	 * Get something from the cache
	 * 
	 * @param key
	 * @return
	 */
	public V get( Object key );
	
	/**
	 * Returns true if the cache contains this
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey( Object key );
	
	/**
	 * Remove something from cache.
	 * 
	 * @param key
	 * @return the removed value
	 */
	public V remove( Object key );
	
	/**
	 * Clear the complete cache
	 */
	public void clear();
	
	/**
	 * Return the used size of the cache
	 * 
	 * @return the size in number of entries
	 */
	public int size();
	
	/**
	 * Return the available capacity of the cache
	 * 
	 * @return the capacity in number of entries or -1 for infinite
	 */
	public int capacity();
	
	/**
	 * Get some meaningful information. 
	 * 
	 * (At least the size should be returned)
	 * 
	 * @return
	 */
	public String info();
	
	/**
	 * Implements more of Map interface for direct access
	 * 
	 * @author flo
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static interface Direct<K,V> extends Cache<K,V> {
		
		/**
		 * @see java.util.Map#keySet()
		 * @return A set of all keys
		 */
	    Set<K> keySet();
	    
	    /**
		 * @see java.util.Map#values()
	     * @return A collection of all values
	     */
	    Collection<V> values();
	}
	
}
