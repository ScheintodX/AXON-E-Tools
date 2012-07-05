package de.axone.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


/**
 * Automatic Cache using DataProviders.
 * 
 * This is a frontend to for another cache which adds automatic fetching
 * capabilities and multiple value access.
 * 
 * If a MultiValueDataAccessor is used fetching of more than one item can
 * be done with just one query which should speed up things significantly.
 * 
 * If the cache doesn't contain the requested data it uses the DataAccessor
 * in order to obtain it.
 * 
 * Normally you would want to use this cache in connections with an CacheLRUMap
 * so it has a limited size but fills automatically.
 *
 * @author flo
 *
 * @param <K> key
 * @param <V> value
 */
public class AutomaticCacheImpl<K,V> implements AutomaticCache<K, V>{

	private Cache<K,V> cache;
	
	private static final Object NULL_ENTRY = new NullEntry();
	// Makes code more easy below
	// Note that V translates to Object in Java Byte-Code
	@SuppressWarnings( "unchecked" )
	private final V NULL = (V) NULL_ENTRY;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private WriteLock writeLock = lock.writeLock();
	private ReadLock readLock = lock.readLock();

	private Stats stats = new Stats( this );

	public AutomaticCacheImpl( Cache<K,V> backend ){
		
		assert backend != null;
		
		cache = backend;
	}
	
	public static <X,Y> AutomaticCache<X,Y> wrap( Cache<X,Y> cache ){
		return new AutomaticCacheImpl<X,Y>( cache );
	}
	
	@Override
	public Map<K,V> get( Collection<K> keys, DataAccessor<K,V> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,V> result = new HashMap<K,V>();
		Set<K> missed = null;

		try{
			readLock.lock();
    		for( K key : keys ){
    			
    			// Note that apparently no cast is done implicitly here.
    			// Java treats V as Object.
    			// Otherwise a ClassCastException would be thrown in case of NullEntry
    			V found = cache.get( key );

    			if( found == null ){
    				
    				stats.miss();
    				if( missed == null ) missed = new HashSet<K>();
    				missed.add( key );
    			} else {
    				stats.hit();
    				if( !NULL.equals( found ) ) result.put( key, found );
    			}
    		}
		} finally {
			readLock.unlock();
		}

		if( missed != null ){

			Map<K,V> fetched = accessor.get( missed );

			try {
				writeLock.lock();

				for( K key : missed ){
					
					V value = fetched.get( key );
					
					if( value == null ){

						cache.put( key, NULL );
					} else {
						result.put( key, value );
						cache.put( key, value );
					}
				}

			} finally {
				writeLock.unlock();
			}
		}
		
		return result;
	}

	@Override
	public V get( K key, DataAccessor<K,V> accessor ) {

		assert key != null && accessor != null;

		// First try to get from cache
		V result = null;
		try{
			readLock.lock();
			result = cache.get( key );
		} finally { readLock.unlock(); }

		if( result != null ){

			// If found mark hit
			stats.hit();
			
		} else {
			// Else mark miss
			stats.miss();

			// Use Accessor to fetch
			result = accessor.get( key );

			try {
				writeLock.lock();
				
				// Store NULL as NullEntry so to tell apart from no result
    			if( result == null ){

        			cache.put( key, NULL );
    			} else {
    				cache.put( key, result );
    			}

			} finally {

				writeLock.unlock();
			}
		}

		if( NULL.equals( result ) ){
			result = null;
		}

    	return result;
	}
	
	@Override
	public int size(){
		readLock.lock();
		try {
			return cache.size();
		} finally {
			readLock.unlock();
		}
	}
	
	@Override
	public int capacity() {
		readLock.lock();
		try {
			return cache.capacity();
		} finally {
			readLock.unlock();
		}
	}

	
	@Override
	public void put( K key, V value ){
		
		assert key != null && value != null;
		
		try {
			writeLock.lock();
			cache.put( key, value );
		} finally {
			writeLock.unlock();
		}
	}
	
	@Override
	public void flush() {

		// Clear cache
		try {
			writeLock.lock();
    		cache.clear();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}
	
	private static final class NullEntry implements Serializable {
		
		public static final long serialVersionUID = 1L;
		
		@Override
		public String toString(){
			return "== NULL-ENTRY ==";
		}
		
		@Override
		public boolean equals( Object obj ) {
			return obj==null || obj.getClass() == NullEntry.class;
		}

		@Override
		public int hashCode() {
			return 0;
		}

	}

}
