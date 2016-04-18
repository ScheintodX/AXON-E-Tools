package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Predicate;

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
 * If this cache doesn't contain the requested data it uses the DataAccessor
 * to obtain it.
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
	
	private static final boolean FAIR = true;

	final CacheNG.Cache<K,O> backend;
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock( FAIR );
	private final ReadLock __read__ = lock.readLock();
	private final WriteLock __write__ = lock.writeLock();
	private final ReentrantLock __fetch__ = new ReentrantLock( FAIR );

	private final Stats stats = new DefaultStats( this );
	
	public AutomaticClientImpl( CacheNG.Cache<K,O> backend ){
		assert backend != null;
		this.backend = backend;
	}
	
	@Override
	public O fetch( K key, SingleValueAccessor<K,O> accessor ) {

		assert key != null;
		assert accessor != null;

		// First try to get from cache
		Cache.Entry<O> entry = null;
		
		// deadlock #1
		__read__.lock();
		try{
			entry = backend.fetchEntry( key );
		} finally {
			__read__.unlock();
		}
		
		O result;
		if( entry != null ){

			// If found mark hit
			stats.hit();
			
			result = entry.data();
			
		} else {
			// Else mark miss
			stats.miss();

			// deadlock #2
			__fetch__.lock();
			
			try {
				
				// Try again in case another process has gotten it meanwhile
				entry = backend.fetchEntry( key );
				
				if( entry != null ) result = entry.data();
				
				else {
				
					// Use Accessor to fetch
					result = accessor.fetch( key );
	
					// deadlock #4
					__write__.lock();
					try {
						backend.put( key, result );
					} finally {
						__write__.unlock();
					}
				}
				
			} finally {
				__fetch__.unlock();
			}
		}

    	return result;
	}
	
	@Override
	public Map<K,O> fetch( Collection<K> keys, MultiValueAccessor<K,O> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,O> result = new HashMap<K,O>();
		List<K> missed = null;

		__read__.lock();
		try{
    		for( K key : keys ){
    			
    			Cache.Entry<O> found = backend.fetchEntry( key );
    			
    			if( found == null ){
    				
    				stats.miss();
    				if( missed == null ) missed = new LinkedList<K>();
    				missed.add( key );
    			} else {
    				stats.hit();
    				if( found.data() != null ) 
		    				result.put( key, found.data() );
    			}
    		}
		} finally {
			__read__.unlock();
		}

		if( missed != null ){

			__fetch__.lock();
			
			try {
				List<K> reallyMissed = new LinkedList<>();
				
				// re-check in case someone else loaded something meanwhile
				for( K key : missed ){
	    			Cache.Entry<O> found = backend.fetchEntry( key );
	    			
	    			if( found == null ) {
	    				reallyMissed.add( key );
	    			} else {
	    				if( found.data() != null ) 
			    				result.put( key, found.data() );
	    			}
				}

				Map<K,O> fetched = accessor.fetch( reallyMissed );
				
				for( K key : reallyMissed ){
					
					O value = fetched.get( key );
					
					__write__.lock();
					try {
						if( value == null ){
	
							backend.put( key, null );
						} else {
							result.put( key, value );
							backend.put( key, value );
						}
					} finally {
						__write__.unlock();
					}
				}

			} finally {
				__fetch__.unlock();
			}
		}
		
		return result;
	}


	// TODO: Testen
	@Override
	public O fetchFresh( K key, SingleValueAccessor<K,O> accessor, Predicate<O> refresh ) {
		
		Cache.Entry<O> entry;
		
		// deadlock #3
		__read__.lock();
		try {
			entry = backend.fetchEntry( key );
		} finally {
			__read__.unlock();
		}
		
		if( entry == null ) return fetch( key, accessor );
		
		if( refresh.test( entry.data() ) ){
			invalidate( key );
			return fetch( key, accessor );
		}
		
		return entry.data();
		
	}
	
	// TODO: Testen testen testen
	@Override
	public Map<K,O> fetchFresh( Collection<K> keys, MultiValueAccessor<K,O> accessor, Predicate<O> refresh ){
		
		Map<K,O> result;
		
		__read__.lock();
		try {
			result = fetch( keys, accessor );
		} finally {
			__read__.unlock();
		}
		
		Set<K> invalid = null;
		
		for( Map.Entry<K,O> entry : result.entrySet() ) {
			
			if( refresh.test( entry.getValue() ) ) {
				invalidate( entry.getKey() );
				
				if( invalid == null ) invalid = new HashSet<>();
				invalid.add( entry.getKey() );
			}
		}
		
		if( invalid != null ) {
			
			Map<K,O> revalid = fetch( invalid, accessor );
			
			for( Map.Entry<K,O> entry : revalid.entrySet() ) {
				
				result.put( entry.getKey(), entry.getValue() );
			}
		}
		
		return result;
	}

	@Override
	public int size(){
		
		__read__.lock();
		try {
			return backend.size();
		} finally {
			__read__.unlock();
		}
	}
	
	@Override
	public int capacity() {
		
		__read__.lock();
		try {
			return backend.capacity();
		} finally {
			__read__.unlock();
		}
	}

	
	@Override
	public void invalidate( K key ) {
		
		assert key != null;
		
		__write__.lock();
		try {
			backend.invalidate( key );
		} finally {
			__write__.unlock();
		}
	}
	
	@Override
	public void invalidateAll( boolean force ) {
		
		// Clear cache
		__write__.lock();
		try {
    		backend.invalidateAll( force );
		} finally {
			__write__.unlock();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}

	@Override
	public boolean isCached( K key ) {
		
		__read__.lock();
		try {
			return backend.isCached( key );
		} finally {
			__read__.unlock();
		}
		
	}
	
	public CacheNG.Cache<K, O> backend(){ return backend; }
	
}
