package de.axone.cache.ng;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import de.axone.cache.Watcher;
import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNG.Realm;
import de.axone.data.LRUCache;

/**
 * BackendCache as implemented by a LRUCache
 * @author flo
 *
 * @param <K>
 * @param <O>
 */
public class ClientLRUMap<K,O>
		extends AbstractEntryClient<K,O>
		implements CacheNG.Client.Direct<K,O>, CacheNG.Client.Watched {
	
	private final Realm realm;
	private final LRUCache<K,Client.Entry<O>> unsafeBackend;
	private final Map<K,Client.Entry<O>> backend;
	
	private Watcher watcher = new Watcher();

	public ClientLRUMap( Realm realm, int maxCapacity ) {
		this.realm = realm;
		this.unsafeBackend = new LRUCache<K,Client.Entry<O>>( maxCapacity );
		this.backend = Collections.<K,Client.Entry<O>>synchronizedMap( unsafeBackend );
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
	public Client.Entry<O> fetchEntry( K key ){
		
		Client.Entry<O> result = backend.get( key );
		
		if( result != null ) watcher.hit();
		else watcher.miss();
		
		return result;
	}

	@Override
	public void put( K key, O value ) {
		backend.put( key, new DefaultEntry<>( value ) );
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
