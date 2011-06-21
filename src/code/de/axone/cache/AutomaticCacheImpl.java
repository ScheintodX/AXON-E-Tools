package de.axone.cache;

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
	
	@SuppressWarnings( "unchecked" )
	private final V NULL = (V)new Object();

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private WriteLock writeLock = lock.writeLock();
	private ReadLock readLock = lock.readLock();

	private Stats stats = new Stats( this );

	public AutomaticCacheImpl( Cache<K,V> backend ){
		
		assert backend != null;
		
		cache = backend;
	}
	
	@Override
	@SuppressWarnings( "unchecked")
	public Map<K,V> get( Collection<K> keys, DataAccessor<K,V> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,V> result = new HashMap<K,V>();
		Set<K> missed = null;

		readLock.lock();
		try{
    		for( K key : keys ){

    			Object found = cache.get( key );

    			if( found == null ){

    				stats.miss();
    				if( missed == null ) missed = new HashSet<K>();
    				missed.add( key );
    			} else {
    				stats.hit();
    				if( found != NULL ) result.put( key, (V)found );
    			}
    		}
		} finally {
			readLock.unlock();
		}

		if( missed != null ){

			Map<K,V> fetched = accessor.get( missed );

			writeLock.lock();
			try {

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

		readLock.lock();
		V result = null;
		try{
			result = cache.get( key );
		} finally { readLock.unlock(); }

		if( result != null ){

			stats.hit();
		} else {
			stats.miss();

			result = accessor.get( key );

			writeLock.lock();
			try {
    			if( result == null ){

        			cache.put( key, NULL );
    			} else {
    				cache.put( key, result );
    			}

			} finally {

				writeLock.unlock();
			}
		}

		if( result == NULL ){
			result = null;
		}

    	return result;
	}
	
	@Override
	public int size(){
		return cache.size();
	}
	
	@Override
	public void put( K key, V value ){
		
		assert key != null && value != null;
		
		writeLock.lock();
		try {
			cache.put( key, value );
		} finally {
			writeLock.unlock();
		}
	}
	
	@Override
	public void flush() {

		// Clear cache
		writeLock.lock();
		try {
    		cache.clear();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}

}
