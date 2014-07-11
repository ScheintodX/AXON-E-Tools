package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


/**
 * BackendCache which does not cache anything
 * 
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class ClientNoCache<K,V> implements CacheNG.Client.Direct<K,V> {

	@Override
	public Set<K> keySet() {
		return new HashSet<K>();
	}

	@Override
	public Collection<V> values() {
		return new LinkedList<V>();
	}

	@Override
	public boolean isCached( K key ) {
		return false;
	}

	@Override
	public V fetch( K key ) {
		return null;
	}

	@Override
	public void put( K key, V value ) {}

	@Override
	public void invalidate( K key ) {}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public String info() {
		return "no caching";
	}

	@Override
	public int capacity() {
		return 0;
	}

	@Override
	public CacheNG.Client.Entry<V> fetchEntry( K key ) {
		return null;
	}

	/*
	@Override
	public void putEntry( K key, CacheNG.Client.Entry<V> entry ) {}
	*/

	@Override
	public void invalidateAll() {}
}
