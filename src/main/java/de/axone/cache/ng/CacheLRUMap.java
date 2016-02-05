package de.axone.cache.ng;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNG.Realm;
import de.axone.data.LRUCache;

/**
 * BackendCache as implemented by a LRUCache
 * @author flo
 *
 * @param <K>
 * @param <O>
 */
public class CacheLRUMap<K,O>
		extends AbstractCache<K,O>
		implements CacheNG.Cache<K,O> {
	
	private final Realm<K,O> realm;
	private final LRUCache<K,Cache.Entry<O>> unsafeBackend;
	private final Map<K,Cache.Entry<O>> backend;
	
	public CacheLRUMap( Realm<K,O> realm, int maxCapacity, boolean synchronize ) {
		this.realm = realm;
		this.unsafeBackend = new LRUCache<K,Cache.Entry<O>>( maxCapacity );
		this.backend = synchronize ? Collections.<K,Cache.Entry<O>>synchronizedMap( unsafeBackend ) : this.unsafeBackend;
	}

	@Override
	public double ratio() {
		
		return -1;
	}

	@Override
	public String info() {
		
		return String.format( "LRU '%s' (Size: %d of %d)", 
				realm, size(), unsafeBackend.getCapacity() );
	}
	
	@Override
	public boolean isCached( K key ){
		
		return backend.containsKey( key );
	}
	
	@Override
	public Cache.Entry<O> fetchEntry( K key ){
		
		return backend.get( key );
	}

	@Override
	public void put( K key, O value ) {
		backend.put( key, new DefaultEntry<>( value ) );
	}

	@Override
	public void invalidateEvent( K key ) {
		backend.remove( key );
	}

	@Override
	public void invalidateAllEvent( boolean force ) {
		backend.clear();
	}

	@Override
	public int size() {
		return backend.size();
	}
	@Override
	public int capacity() {
		return unsafeBackend.getCapacity();
	}

	@Override
	public Set<K> keySet() {
		return backend.keySet();
	}

	@Override
	public Iterable<O> values() {
		return new IterableEntryAsValue<>( backend.values() );
	}

	@Override
	public String toString(){
		return backend.toString();
	}
}
