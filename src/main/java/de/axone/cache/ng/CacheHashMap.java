package de.axone.cache.ng;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNG.Realm;

/**
 * BackendCache as implemented by a HashMap
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class CacheHashMap<K,V>
		extends AbstractEntryCache<K,V>
		implements Cache.Direct<K,V> {
	
	private final Realm<K,V> name;
	private final Map<K,Cache.Entry<V>> backend;
	
	public CacheHashMap( Realm<K,V> name ){
		
		this.name = name;
		this.backend = Collections.synchronizedMap( new HashMap<K,Cache.Entry<V>>() );
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
	public void put( K key, V entry ) {
		backend.put( key, new DefaultEntry<>( entry ) );
	}

	@Override
	public Cache.Entry<V> fetchEntry( K key ) {
		return backend.get( key );
	}

	@Override
	public boolean isCached( K key ) {
		return backend.containsKey( key );
	}

	@Override
	public void invalidate( K key ) {
		backend.remove( key );
	}

	@Override
	public void invalidateAll() {
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
	public Iterable<V> values() {
		return new IterableEntryAsValue<>( backend.values() );
	}
	
}
