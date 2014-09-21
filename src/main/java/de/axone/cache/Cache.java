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
 * @param <K> key type
 * @param <V> value type
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
	 * @return something from the cache
	 * 
	 * @param key to query
	 */
	public V get( Object key );
	
	/**
	 * @return true if the cache contains this
	 * 
	 * @param key to query
	 */
	public boolean containsKey( Object key );
	
	/**
	 * Remove something from cache.
	 * 
	 * @param key to query
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
	 * @return some meaningful information. 
	 */
	public String info();
	
	/**
	 * Implements more of Map interface for direct access
	 * 
	 * This is additional because we cannot expect distributed caches
	 * to have a keyset fast (if at all) available.
	 * 
	 * @author flo
	 *
	 * @param <K> The key type
	 * @param <V> The value type
	 */
	public interface Direct<K,V> extends Cache<K,V> {
		
		/**
		 * @return A set of all keys
		 * 
		 * @see java.util.Map#keySet()
		 */
	    Set<K> keySet();
	    
	    /**
	     * @return A collection of all values
	     * 
		 * @see java.util.Map#values()
	     */
	    Collection<V> values();
	}
	
}
