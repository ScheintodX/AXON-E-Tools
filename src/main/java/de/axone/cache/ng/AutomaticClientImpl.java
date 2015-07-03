package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNG.MultiValueAccessor;
import de.axone.cache.ng.CacheNG.SingleValueAccessor;


/**
 * Automatic Client using Accessors.
 * 
 * This is a frontend to for another cache which adds automatic fetching
 * capabilities and multiple value access.
 * 
 * If a MultiValueDataAccessor is used fetching of more than one item can
 * be done with just one query which should speed up things significantly.
 * 
 * If this' cache doesn't contain the requested data it uses the DataAccessor
 * in order to obtain it.
 * 
 * Normally you would want to use this cache in connections with an CacheLRUMap
 * or CacheEHCache so that it has a limited size but fills automatically.
 * 
 * NOTE: @link AutomaticWatcher has a copy of this. 
 *
 * @author flo
 *
 * @param <K> Key-Type
 * @param <O> Value-Type
 */
public class AutomaticClientImpl<K,O>
		implements CacheNG.AutomaticClient<K,O> {

	final CacheNG.Cache<K,O> backend;
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private final Stats stats = new DefaultStats( this );
	
	public AutomaticClientImpl( CacheNG.Cache<K,O> backend ){
		assert backend != null;
		this.backend = backend;
	}
	
	@Override
	public Map<K,O> fetch( Collection<K> keys, MultiValueAccessor<K,O> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,O> result = new HashMap<K,O>();
		List<K> missed = null;

		lock.readLock().lock();
		try{
    		for( K key : keys ){
    			
    			Cache.Entry<O> found = backend.fetchEntry( key );
    			
    			if( found == null ){
    				
    				stats.miss();
    				if( missed == null ) missed = new LinkedList<K>();
    				missed.add( key );
    			} else {
    				stats.hit();
    				result.put( key, found.data() );
    			}
    		}
		} finally {
			lock.readLock().unlock();
		}

		if( missed != null ){

			List<K> reallyMissed = new LinkedList<>();
			
			lock.writeLock().lock();
			try {
				
				// re-check in case someone else loaded something meanwhile
				for( K key : missed ){
	    			Cache.Entry<O> found = backend.fetchEntry( key );
	    			
	    			if( found == null ) {
	    				reallyMissed.add( key );
	    			} else {
	    				result.put( key, found.data() );
	    			}
				}

				Map<K,O> fetched = accessor.fetch( reallyMissed );
				
				for( K key : reallyMissed ){
					
					O value = fetched.get( key );
					
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
	public O fetch( K key, SingleValueAccessor<K,O> accessor ) {

		assert key != null;
		assert accessor != null;

		// First try to get from cache
		Cache.Entry<O> entry = null;
		
		// deadlock #0
		lock.readLock().lock();
		try{
			entry = backend.fetchEntry( key );
		} finally {
			lock.readLock().unlock();
		}
		
		O result;
		if( entry != null ){

			// If found mark hit
			stats.hit();
			
			result = entry.data();
			
		} else {
			// Else mark miss
			stats.miss();

			// deadlock #1
			lock.writeLock().lock();
			try {
				
				// Try again in case another process has gotten it meanwhile
				entry = backend.fetchEntry( key );
				
				if( entry != null ) result = entry.data();
				
				else {
				
					// Use Accessor to fetch
					result = accessor.fetch( key );
	
					backend.put( key, result );
				}
				
			} finally {
				lock.writeLock().unlock();
			}
		}

    	return result;
	}
	
	@Override
	public int size(){
		
		lock.readLock().lock();
		try {
			return backend.size();
		} finally {
			lock.readLock().unlock();
		}
	}
	
	@Override
	public int capacity() {
		
		lock.readLock().lock();
		try {
			return backend.capacity();
		} finally {
			lock.readLock().unlock();
		}
	}

	
	@Override
	public void invalidate( K key ) {
		
		assert key != null;
		
		lock.writeLock().lock();
		try {
			backend.invalidate( key );
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	@Override
	public void invalidateAll( boolean force ) {
		
		// Clear cache
		lock.writeLock().lock();
		try {
    		backend.invalidateAll( force );
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}

	@Override
	public boolean isCached( K key ) {
		
		lock.readLock().lock();
		try {
			return backend.isCached( key );
		} finally {
			lock.readLock().unlock();
		}
		
	}
	
}
