package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.axone.cache.ng.CacheNG.Accessor;
import de.axone.cache.ng.CacheNG.Cache;
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

	final CacheNG.Cache<K,V> backend;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private InvalidationManager<K,V> invalidationManager;
	
	private Stats stats = new DefaultStats( this );
	
	// Simplification for testing
	AutomaticClientImpl( CacheNG.Realm<K,V> realm ){
		backend = new CacheHashMap<>( realm );
	}

	public AutomaticClientImpl( CacheNG.Cache<K,V> backend ){
		
		assert backend != null;
		
		this.backend = backend;
	}
	
	@Override
	public boolean isCached( K key ) {
		
		// This is quick
		if( ! backend.isCached( key ) ) return false;
		
		// Fetch for further checks
		Cache.Entry<V> entry = backend.fetchEntry( key );
		
		return entry != null && isAlive( key, entry );
	}
	
	private boolean isAlive( K key, CacheNG.Cache.Entry<V> entry ){
		
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
			lock.readLock().lock();
    		for( K key : keys ){
    			
    			Cache.Entry<V> found = backend.fetchEntry( key );
    			
				if( found != null && ! isAlive( key, found ) ){
					invalidate( key );
					found = null;
				}

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
			lock.readLock().unlock();
		}

		if( missed != null ){

			Map<K,V> fetched = accessor.fetch( missed );

			try {
				lock.writeLock().lock();

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
				lock.writeLock().unlock();
			}
		}
		
		return result;
	}


	@Override
	public V fetch( K key, Accessor<K,V> accessor ) {

		assert key != null;
		assert accessor != null;

		// First try to get from cache
		Cache.Entry<V> entry = null;
		try{
			lock.readLock().lock();
			entry = backend.fetchEntry( key );
		} finally { lock.readLock().unlock(); }
		
		if( entry != null && ! isAlive( key, entry ) ) {
			invalidate( key );
			entry = null;
		}
			
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
				lock.writeLock().lock();
				
				backend.put( key, result );
				
			} finally {

				lock.writeLock().unlock();
			}
		}

    	return result;
	}
	
	@Override
	public int size(){
		try {
			lock.readLock().lock();
			return backend.size();
		} finally {
			lock.readLock().unlock();
		}
	}
	
	@Override
	public int capacity() {
		try {
			lock.readLock().lock();
			return backend.capacity();
		} finally {
			lock.readLock().unlock();
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
			lock.writeLock().lock();
			backend.invalidate( key );
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public void invalidateAll() {
		
		// TODO: EVENTS!!!!!
		// (oder rauswerfen)

		// Clear cache
		try {
			lock.writeLock().lock();
    		backend.invalidateAll();
		} finally {
			lock.writeLock().unlock();
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
		public boolean isValid( K key, CacheNG.Cache.Entry<O> value ) {
			
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
