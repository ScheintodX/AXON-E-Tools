package de.axone.cache;

import de.axone.data.LRUCache;

/**
 * BackendCache as implemented by a LRUCache
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class BackendCacheLRUMap<K,V> extends LRUCache<K,V> implements BackendCache.Direct<K,V> {

	public BackendCacheLRUMap( int maxCapacity ) {
		super(maxCapacity);
	}
}
