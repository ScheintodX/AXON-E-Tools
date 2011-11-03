package de.axone.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import de.axone.data.LRUCache;

/**
 * BackendCache as implemented by a LRUCache
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class CacheLRUMap<K,V> implements Cache.Direct<K,V> {
	
	private final String name;
	private final LRUCache<K,V> unsafeBackend;
	private final Map<K,V> backend;
	
	private AtomicLong hits = new AtomicLong(0);
	private AtomicLong misses = new AtomicLong(0);

	public CacheLRUMap( String name, int maxCapacity ) {
		this.name = name;
		this.unsafeBackend = new LRUCache<K,V>(maxCapacity);
		this.backend = Collections.synchronizedMap( unsafeBackend );
	}

	@Override
	public String info() {
		
		long hits = this.hits.get();
		long misses = this.misses.get();
		
		long sum = hits+misses;
		double ratio = sum > 0 ? (double)hits/sum : 0;
		
		return "LRU '" + name + "'" +
				" (Size: " + size() + " of " + unsafeBackend.getCapacity() + 
				", Hits: " + hits + "/" + sum + " = " + Math.round( ratio *1000 )/10 + "%)";
	}
	
	@Override
	public boolean containsKey( Object key ){
		
		boolean result = backend.containsKey( key );
		
		if( result ) hits.incrementAndGet();
		else misses.incrementAndGet();
		
		return result;
	}
	@Override
	public V get( Object key ){
		
		V result = backend.get( key );
		
		if( result != null ) hits.incrementAndGet();
		else misses.incrementAndGet();
		
		return result;
	}

	@Override
	public V put( K key, V value ) {
		return backend.put( key, value );
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
	public int capacity() {
		return unsafeBackend.getCapacity();
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
