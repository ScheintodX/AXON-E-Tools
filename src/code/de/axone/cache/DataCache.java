package de.axone.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import de.axone.data.LRUCache;


/**
 * Automatic Cache using DataProviders etc.
 *
 * @author flo
 *
 * @param <K> key
 * @param <V> value
 */
public class DataCache<K,V>{

	private int capacity;

	private HashMap<K,Object> cache;
	private static final Object NULL = new Object();

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private WriteLock writeLock = lock.writeLock();
	private ReadLock readLock = lock.readLock();

	private Stats stats = new Stats();

	public DataCache( int capacity ){

		this.capacity = capacity;

		if( capacity > 0 ){
			cache = new LRUCache<K,Object>( capacity );
		} else {
			cache = new HashMap<K,Object>();
		}
	}

	@SuppressWarnings( "unchecked")
	public Map<K,V> get( Iterable<K> keys, DataAccessor<K,V> accessor ){

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

		if( key == null ) return null;

		readLock.lock();
		Object result = null;
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

    	@SuppressWarnings( "unchecked")
		V v = (V)result;
    	return v;
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
			    .append( " Full: " + cache.size() + " of " + capacity + " entries = " + (int)((double)cache.size()/capacity*100) + "%" )
			;
			return result.toString();
		}
	}

}
