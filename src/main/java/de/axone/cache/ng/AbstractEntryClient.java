package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Client;

public abstract class AbstractEntryClient<K,V> {

	public abstract Client.Entry<V> fetchEntry( K key );
	
	public V fetch( K key ){
		Client.Entry<V> entry = fetchEntry( key );
		if( entry == null ) return null;
		return entry.data();
	}
}
