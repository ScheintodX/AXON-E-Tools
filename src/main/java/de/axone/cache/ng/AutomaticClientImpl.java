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
		//E.t( "R" );
		__read__.lock();
		//E.t( "R>" );
		try{
			entry = backend.fetchEntry( key );
		} finally {
			//E.t( "R<" );
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
			//E.t( "F" );
			__fetch__.lock();
			//E.t( "F>" );
			
			try {
				
				// Try again in case another process has gotten it meanwhile
				entry = backend.fetchEntry( key );
				
				if( entry != null ){
					
					result = entry.data();
				
				} else {
				
					// Use Accessor to fetch
					result = accessor.fetch( key );
	
					// deadlock #4
					//E.t( "W" );
					__write__.lock();
					//E.t( "W>" );
					try {
						backend.put( key, result );
					} finally {
						//E.t( "W<" );
						__write__.unlock();
					}
				}
				
			} finally {
				//E.t( "F<" );
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
		
		//E.t( "R" );
		__read__.lock();
		//E.t( "R>" );
		
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
			//E.t( "R<" );
			__read__.unlock();
		}
		
		if( missed != null ){

			//E.t( "F" );
			__fetch__.lock();
			//E.t( "F>" );
			
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
					
					//E.t( "W" );
					__write__.lock();
					//E.t( "W>" );
					
					try {
						if( value == null ){
	
							backend.put( key, null );
						} else {
							result.put( key, value );
							backend.put( key, value );
						}
					} finally {
						//E.t( "W<" );
						__write__.unlock();
					}
				}

			} finally {
				//E.t( "F<" );
				__fetch__.unlock();
			}
		}
		
		return result;
	}


	// TODO: Testen
	@Override
	public O fetchFresh( K key, SingleValueAccessor<K,O> accessor, Predicate<O> refresh ) {
		
		O entry = fetch( key, accessor );
		
		if( refresh.test( entry ) ){
			// we can (and must) synchonize this because we want to refetch for sure
			// and we know it is invalid (bacause we say so)
			synchronized( this ) {
				invalidate( key );
				return fetch( key, accessor );
			}
		}
		
		return entry;
	}
	
	// TODO: Testen testen testen
	@Override
	public Map<K,O> fetchFresh( Collection<K> keys, MultiValueAccessor<K,O> accessor, Predicate<O> refresh ){
		
		//E.t( "o" );
		
		Map<K,O> result = fetch( keys, accessor );
		
		//E.t( ".o" );
		
		Set<K> invalid = null;
		
		for( Map.Entry<K,O> entry : result.entrySet() ) {
			
			if( refresh.test( entry.getValue() ) ) {
				
				if( invalid == null ) invalid = new HashSet<>();
				invalid.add( entry.getKey() );
			}
		}
		
		//E.t( "..o" );
		
		if( invalid != null ) {
			
			Map<K,O> revalid;
			synchronized( this ) { // s. above
				invalidate( invalid );
				revalid = fetch( invalid, accessor );
			}
			
			for( Map.Entry<K,O> entry : revalid.entrySet() ) {
				
				result.put( entry.getKey(), entry.getValue() );
			}
		}
		
		//E.t( "...o" );
		
		return result;
	}

	@Override
	public int size(){
		
		//E.t( "R" );
		__read__.lock();
		//E.t( "R>" );
		try {
			return backend.size();
		} finally {
			//E.t( "R<" );
			__read__.unlock();
		}
	}
	
	@Override
	public int capacity() {
		
		//E.t( "R" );
		__read__.lock();
		//E.t( "R>" );
		try {
			return backend.capacity();
		} finally {
			//E.t( "R<" );
			__read__.unlock();
		}
	}

	
	@Override
	public void invalidate( K key ) {
		
		assert key != null;
		
		//E.t( "W" );
		__write__.lock();
		//E.t( "W>" );
		try {
			backend.invalidate( key );
		} finally {
			//E.t( "W<" );
			__write__.unlock();
		}
	}
	
	void invalidate( Collection<K> keys ) {
		
		__write__.lock();
		try {
			for( K key : keys ) {
				backend.invalidate( key );
			}
		} finally {
			//E.t( "W<" );
			__write__.unlock();
		}
	}
	
	@Override
	public void invalidateAll( boolean force ) {
		
		// Clear cache
		//E.t( "W" );
		__write__.lock();
		//E.t( "W>" );
		try {
    		backend.invalidateAll( force );
		} finally {
			//E.t( "W<" );
			__write__.unlock();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}

	@Override
	public boolean isCached( K key ) {
		
		//E.t( "R" );
		__read__.lock();
		//E.t( "R>" );
		try {
			return backend.isCached( key );
		} finally {
			//E.t( "R<" );
			__read__.unlock();
		}
		
	}
	
	public CacheNG.Cache<K, O> backend(){ return backend; }
	
}
