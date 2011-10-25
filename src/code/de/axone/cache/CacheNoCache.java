package de.axone.cache;

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
public class CacheNoCache<K,V> implements Cache.Direct<K,V> {

	@Override
	public Set<K> keySet() {
		return new HashSet<K>();
	}

	@Override
	public Collection<V> values() {
		return new LinkedList<V>();
	}

	@Override
	public void clear() {
	}

	@Override
	public boolean containsKey( Object key ) {
		return false;
	}

	@Override
	public V get( Object key ) {
		return null;
	}

	@Override
	public V put( K key, V value ) {
		return value;
	}

	@Override
	public V remove( Object key ) {
		return null;
	}

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
}
