package de.axone.cache;

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
 * This cache uses an LRU cache as backend if a capacity is specified.
 * 
 * If the cache doesn't contain the requested data is uses the DataAccessor
 * in order to obtain it.
 *
 * @author flo
 *
 * @param <K> key
 * @param <V> value
 */
public class DataCache<K,V>{

	private BackendCache<K,V> cache;
	
	@SuppressWarnings( "unchecked" )
	private final V NULL = (V)new Object();

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private WriteLock writeLock = lock.writeLock();
	private ReadLock readLock = lock.readLock();

	private Stats stats = new Stats();

	public DataCache( BackendCache<K,V> backend ){
		
		assert backend != null;
		
		cache = backend;
	}
	
	@SuppressWarnings( "unchecked")
	public Map<K,V> get( Iterable<K> keys, DataAccessor<K,V> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,V> result = new HashMap<K,V>();
		Set<K> missed = null;

		if( keys == null ) return null;

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

	public V get( K key, DataAccessor<K,V> accessor ) {

		assert key != null && accessor != null;

		if( key == null ) return null;

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
	
	public int size(){
		return cache.size();
	}
	
	public void put( K key, V value ){
		
		assert key != null && value != null;
		
		writeLock.lock();
		try {
			cache.put( key, value );
		} finally {
			writeLock.unlock();
		}
	}
	
	public void flush() {

		// Clear cache
		writeLock.lock();
		try {
    		cache.clear();
		} finally {
			writeLock.unlock();
		}
	}

	public Stats stats(){
		return stats;
	}

	public class Stats {
   		
		private int accesses, hits;
		
		void hit(){
			accesses++;
			hits++;
		}
		
		void miss(){
			accesses++;
		}
		
		@Override
		public String toString(){
			StringBuilder result = new StringBuilder();
			result
				.append( "Ratio: " + hits + "/" + accesses + " hits =" + (int)((double)hits/accesses*100) + "%\n" )
			    .append( " Full: " + cache.size() + " entries" )
			;
			return result.toString();
		}
	}

}
