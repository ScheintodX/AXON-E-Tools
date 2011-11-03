package de.axone.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * BackendCache as implemented by a HashMap
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class CacheHashMap<K,V> implements Cache.Direct<K,V> {
	
	private final String name;
	private final Map<K,V> backend;
	
	public CacheHashMap( String name ){
		
		this.name = name;
		this.backend = Collections.synchronizedMap( new HashMap<K,V>() );
	}

	@Override
	public String info() {
		return "HashMap '" + name + "'("+size()+")";
	}

	@Override
	public int capacity() {
		return -1;
	}

	@Override
	public V put( K key, V value ) {
		return backend.put( key, value );
	}

	@Override
	public V get( Object key ) {
		return backend.get( key );
	}

	@Override
	public boolean containsKey( Object key ) {
		return backend.containsKey( key );
	}

	@Override
	public V remove( Object key ) {
		return backend.remove( key );
	}

	@Override
	public void clear() {
		backend.clear();
	}

	@Override
	public int size() {
		return backend.size();
	}

	@Override
	public Set<K> keySet() {
		return backend.keySet();
	}

	@Override
	public Collection<V> values() {
		return backend.values();
	}
	
}
