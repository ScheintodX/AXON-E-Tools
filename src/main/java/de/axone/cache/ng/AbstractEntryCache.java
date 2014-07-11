package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Cache;

public abstract class AbstractEntryCache<K,V> {

	public abstract Cache.Entry<V> fetchEntry( K key );
	
	public V fetch( K key ){
		Cache.Entry<V> entry = fetchEntry( key );
		if( entry == null ) return null;
		return entry.data();
	}
}
