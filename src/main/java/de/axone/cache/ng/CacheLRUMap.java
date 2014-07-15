package de.axone.cache.ng;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import de.axone.cache.Watcher;
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
		implements CacheNG.Cache.Direct<K,O>, CacheNG.Cache.Watched {
	
	private final Realm<K,O> realm;
	private final LRUCache<K,Cache.Entry<O>> unsafeBackend;
	private final Map<K,Cache.Entry<O>> backend;
	
	private Watcher watcher = new Watcher();

	public CacheLRUMap( Realm<K,O> realm, int maxCapacity ) {
		this.realm = realm;
		this.unsafeBackend = new LRUCache<K,Cache.Entry<O>>( maxCapacity );
		this.backend = Collections.<K,Cache.Entry<O>>synchronizedMap( unsafeBackend );
	}

	@Override
	public double ratio() {
		
		return watcher.ratio();
	}

	@Override
	public String info() {
		
		return "LRU '" + realm + "'" +
				" (Size: " + size() + " of " + unsafeBackend.getCapacity() + 
				", Hits: " + watcher.hits() + "/" + watcher.accesses() + " = " + Math.round( watcher.ratio() *1000 )/10.0 + "%)";
	}
	
	@Override
	public boolean isCached( K key ){
		
		boolean result = backend.containsKey( key );
		
		if( result ) watcher.hit();
		else watcher.miss();
		
		return result;
	}
	
	@Override
	public Cache.Entry<O> fetchEntry( K key ){
		
		Cache.Entry<O> result = backend.get( key );
		
		if( result != null ) watcher.hit();
		else watcher.miss();
		
		return result;
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
	public void invalidateAllEvent() {
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
