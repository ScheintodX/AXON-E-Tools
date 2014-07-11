package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import de.axone.cache.ng.CacheNG.Accessor;
import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNG.InvalidationManager;


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
public class AutomaticClientImpl<K,V>
		extends AbstractCacheEventProvider<K,V>
		implements CacheNG.AutomaticClient<K,V> {

	final CacheNG.Client<K,V> backend;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private WriteLock writeLock = lock.writeLock();
	private ReadLock readLock = lock.readLock();

	private InvalidationManager<K,V> invalidationManager;
	
	private Stats stats = new DefaultStats( this );
	
	// Simplification for testing
	AutomaticClientImpl( CacheNG.Realm realm ){
		backend = new ClientHashMap<>( realm );
	}

	public AutomaticClientImpl( CacheNG.Client<K,V> backend ){
		
		assert backend != null;
		
		this.backend = backend;
	}
	
	@Override
	public boolean isCached( K key ) {
		
		// This is quick
		if( ! backend.isCached( key ) ) return false;
		
		// Fetch for further checks
		Client.Entry<V> entry = backend.fetchEntry( key );
		
		return isAlive( key, entry );
	}
	
	private boolean isAlive( K key, CacheNG.Client.Entry<V> entry ){
		
		// May happen multithreaded
		if( entry == null ) return false;
		
		if( invalidationManager != null ){
			
			return invalidationManager.isValid( key, entry );
		}
		
		return true;
	}
	

	@Override
	public Map<K,V> fetch( Collection<K> keys, Accessor<K,V> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,V> result = new HashMap<K,V>();
		Set<K> missed = null;

		try{
			readLock.lock();
    		for( K key : keys ){
    			
    			// Note that apparently no cast is done implicitly here.
    			// Java treats V as Object.
    			// Otherwise a ClassCastException would be thrown in case of NullEntry
    			Client.Entry<V> found = backend.fetchEntry( key );
    			
				if( ! isAlive( key, found ) ) found = null;

    			if( found == null ){
    				
    				stats.miss();
    				if( missed == null ) missed = new HashSet<K>();
    				missed.add( key );
    			} else {
    				stats.hit();
    				if( found.data() != null ) result.put( key, found.data() );
    			}
    		}
		} finally {
			readLock.unlock();
		}

		if( missed != null ){

			Map<K,V> fetched = accessor.fetch( missed );

			try {
				writeLock.lock();

				for( K key : missed ){
					
					V value = fetched.get( key );
					
					if( value == null ){

						backend.put( key, null );
					} else {
						result.put( key, value );
						backend.put( key, value );
					}
				}

			} finally {
				writeLock.unlock();
			}
		}
		
		return result;
	}


	@Override
	public V fetch( K key, Accessor<K,V> accessor ) {

		assert key != null;
		assert accessor != null;

		// First try to get from cache
		Client.Entry<V> entry = null;
		try{
			readLock.lock();
			entry = backend.fetchEntry( key );
		} finally { readLock.unlock(); }
		
		if( ! isAlive( key, entry ) ) entry = null;
			
		V result;
		if( entry != null ){

			// If found mark hit
			stats.hit();
			
			result = entry.data();
			
		} else {
			// Else mark miss
			stats.miss();

			// Use Accessor to fetch
			result = accessor.fetch( key );

			try {
				writeLock.lock();
				
				backend.put( key, result );
				
			} finally {

				writeLock.unlock();
			}
		}

    	return result;
	}
	
	@Override
	public int size(){
		readLock.lock();
		try {
			return backend.size();
		} finally {
			readLock.unlock();
		}
	}
	
	@Override
	public int capacity() {
		readLock.lock();
		try {
			return backend.capacity();
		} finally {
			readLock.unlock();
		}
	}

	
	@Override
	public void invalidate( K key ){
		
		notifyListeners( key );
		
		invalidateEvent( key );
	}

	@Override
	public void invalidateEvent( K key ) {
		
		assert key != null;
		
		try {
			writeLock.lock();
			backend.invalidate( key );
		} finally {
			writeLock.unlock();
		}
	}
	
	@Override
	public void invalidateAll() {
		
		// TODO: EVENTS!!!!!
		// (oder rauswerfen)

		// Clear cache
		try {
			writeLock.lock();
    		backend.invalidateAll();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}
	
	@Override
	public void invalidateAllWithin( int milliSeconds ) {
		
		invalidationManager = new TimoutInvalidationManager<K,V>(
				System.currentTimeMillis(), milliSeconds );
	}
	
	static class TimoutInvalidationManager<K,O>
			implements CacheNG.InvalidationManager<K, O> {
		
		private final long timeoutStart,
		                   timeoutDuration;
		
		TimoutInvalidationManager( long timeoutStart, long timeoutDuration ){
			
			this.timeoutStart = timeoutStart;
			this.timeoutDuration = timeoutDuration;
		}
	
		@Override
		public boolean isValid( K key, CacheNG.Client.Entry<O> value ) {
			
			// Entry is newer than starting of timeout.
			// This happend regularily if the entry is re-fetched after invalidation
			if( value.creation() > timeoutStart ) return true;
			
			
			long elapsed = System.currentTimeMillis() - timeoutStart;
			
			// Invalid immediately if > duration
			if( elapsed >= timeoutDuration ) return false;
			
			long stretch = Integer.MAX_VALUE / timeoutDuration;
			
			int random = RandomMapper.positiveInteger( key.hashCode() );
			
			if( stretch * elapsed < random ){
				
				return false;
			}
			
			return true;
		}
		
	}

}
