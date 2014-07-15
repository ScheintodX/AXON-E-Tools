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
 * @param <O>
 */
public class CacheHashMap<K,O>
		extends AbstractCache<K,O>
		implements Cache.Direct<K,O> {
	
	private final Realm<K,O> name;
	private final Map<K,Cache.Entry<O>> backend;
	
	public CacheHashMap( Realm<K,O> name ){
		
		this.name = name;
		this.backend = Collections.synchronizedMap( new HashMap<K,Cache.Entry<O>>() );
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
	public void put( K key, O entry ) {
		backend.put( key, new DefaultEntry<>( entry ) );
	}

	@Override
	public Cache.Entry<O> fetchEntry( K key ) {
		return backend.get( key );
	}

	@Override
	public boolean isCached( K key ) {
		return backend.containsKey( key );
	}

	@Override
	public void invalidateEvent( K key ) {
		backend.remove( key );
	}

	@Override
	public void invalidateAllEvent() {
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
	public Iterable<O> values() {
		return new IterableEntryAsValue<>( backend.values() );
	}
	
}
