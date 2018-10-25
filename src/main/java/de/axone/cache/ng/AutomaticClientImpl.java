package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Predicate;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNG.CacheNGInterrupted;
import de.axone.cache.ng.CacheNG.CacheNGTimeout;
import de.axone.cache.ng.CacheNG.MultiValueAccessor;
import de.axone.cache.ng.CacheNG.Realm;
import de.axone.cache.ng.CacheNG.SingleValueAccessor;
import de.axone.test.TestClass;


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
@TestClass( { "CacheNGTest_FetchFresh", "u.a." } )
public class AutomaticClientImpl<K,O>
		implements CacheNG.AutomaticClient<K,O> {
	
	private static final long DEFAULT_TIMEOUT = 10_000;
	
	private static final boolean FAIR = true;

	final CacheNG.Cache<K,O> backend;
	final Locker LOCK;
	
	private final Stats stats = new DefaultStats( this );
	
	public AutomaticClientImpl( CacheNG.Cache<K,O> backend ){
		this( backend, Realm.Hint.STRICT );
	}
	
	public AutomaticClientImpl( CacheNG.Cache<K,O> backend, Realm.Hint hint ){
		
		/*
		assert backend != null;
		this.backend = backend;
		LOCK = new SimpleLocker();
		*/
		
		this( backend, DEFAULT_TIMEOUT, hint );
		
	}
	
	private AutomaticClientImpl( CacheNG.Cache<K,O> backend, long timeout, Realm.Hint hint ){
		assert backend != null;
		this.backend = backend;
		LOCK = hint == Realm.Hint.STRICT ? new TimedLocker( timeout ) : new TimedLockerLoose( timeout );
	}
	
	@Override
	public O fetch( K key, SingleValueAccessor<K,O> accessor ) {

		assert key != null;
		assert accessor != null;

		// First try to get from cache
		Cache.Entry<O> entry = null;
		
		// deadlock #1
		LOCK.__READLOCK__();
		try{
			entry = backend.fetchEntry( key );
		} finally {
			LOCK.__READUNLOCK__();
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
			LOCK.__FETCHLOCK__();
			
			try {
				
				// Try again in case another process has gotten it meanwhile
				if( LOCK.needsRefetch() ) entry = backend.fetchEntry( key );
				
				if( entry != null ){
					
					result = entry.data();
				
				} else {
				
					// Use Accessor to fetch
					result = accessor.fetch( key );
	
					// deadlock #4
					LOCK.__WRITELOCK__();
					try {
						backend.put( key, result );
					} finally {
						LOCK.__WRITEUNLOCK__();
					}
				}
				
			} finally {
				LOCK.__FETCHUNLOCK__();
			}
		}

    	return result;
	}
	
	@Override
	public Map<K,O> fetch( Collection<K> keys, MultiValueAccessor<K,O> accessor ){
		
		assert keys != null && accessor != null;

		HashMap<K,O> result = new HashMap<K,O>();
		List<K> missed = null;
		
		LOCK.__READLOCK__();
		
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
			LOCK.__READUNLOCK__();
		}
		
		if( missed != null ){

			LOCK.__FETCHLOCK__();
			
			try {
					
				List<K> reallyMissed;
					
				if( LOCK.needsRefetch() ) {
					
					// re-check in case someone else loaded something meanwhile
					
					reallyMissed = new LinkedList<>();
					
					for( K key : missed ){
		    			Cache.Entry<O> found = backend.fetchEntry( key );
		    			
		    			if( found == null ) {
		    				reallyMissed.add( key );
		    			} else {
		    				if( found.data() != null ) 
				    				result.put( key, found.data() );
		    			}
					}
					
				} else {
					
					reallyMissed = missed;
				}

				Map<K,O> fetched = accessor.fetch( reallyMissed );
				
				for( K key : reallyMissed ){
					
					O value = fetched.get( key );
					
					LOCK.__WRITELOCK__();
					
					try {
						if( value == null ){
	
							backend.put( key, null );
						} else {
							result.put( key, value );
							backend.put( key, value );
						}
					} finally {
						LOCK.__WRITEUNLOCK__();
					}
				}

			} finally {
				LOCK.__FETCHUNLOCK__();
			}
		}
		
		return result;
	}


	@Override
	public O fetchFresh( K key, SingleValueAccessor<K,O> accessor, Predicate<O> refresh ) {
		
		O entry = fetch( key, accessor );
		
		if( refresh.test( entry ) ){
			// we can (and must) synchonize this because we want to refetch for sure
			// and we know it is invalid (bacause we say so)
			// Note that this is a slow path but it should be taken only in a small fraction of calls
			// or the whole caching concept would be flawed.
			synchronized( this ) {
				invalidate( key );
				return fetch( key, accessor );
			}
		}
		
		return entry;
	}
	
	@Override
	public Map<K,O> fetchFresh( Collection<K> keys, MultiValueAccessor<K,O> accessor, Predicate<O> refresh ){
		
		Map<K,O> result = fetch( keys, accessor );
		
		Set<K> invalid = null;
		
		for( Map.Entry<K,O> entry : result.entrySet() ) {
			
			if( refresh.test( entry.getValue() ) ) {
				
				if( invalid == null ) invalid = new HashSet<>();
				invalid.add( entry.getKey() );
			}
		}
		
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
		
		return result;
	}

	@Override
	public int size(){
		
		LOCK.__READLOCK__();
		
		try {
			return backend.size();
		} finally {
			LOCK.__READUNLOCK__();
		}
	}
	
	@Override
	public int capacity() {
		
		LOCK.__READLOCK__();

		try {
			return backend.capacity();
		} finally {
			LOCK.__READUNLOCK__();
		}
	}

	
	@Override
	public void invalidate( K key ) {
		
		assert key != null;
		
		LOCK.__WRITELOCK__();

		try {
			backend.invalidate( key );
		} finally {
			LOCK.__WRITEUNLOCK__();
		}
	}
	
	void invalidate( Collection<K> keys ) {
		
		LOCK.__WRITELOCK__();
		
		try {
			for( K key : keys ) {
				backend.invalidate( key );
			}
		} finally {
			LOCK.__WRITEUNLOCK__();
		}
	}
	
	@Override
	public void invalidateAll( boolean force ) {
		
		// Clear cache
		LOCK.__WRITELOCK__();

		try {
    		backend.invalidateAll( force );
		} finally {
			LOCK.__WRITEUNLOCK__();
		}
	}

	@Override
	public Stats stats(){
		return stats;
	}

	@Override
	public boolean isCached( K key ) {
		
		LOCK.__READLOCK__();

		try {
			return backend.isCached( key );
		} finally {
			LOCK.__READUNLOCK__();
		}
		
	}
	
	public CacheNG.Cache<K, O> backend(){ return backend; }
	
	private interface Locker {
		
		void __READLOCK__();
		void __WRITELOCK__();
		void __FETCHLOCK__();
		
		void __READUNLOCK__();
		void __WRITEUNLOCK__();
		void __FETCHUNLOCK__();
		
		boolean needsRefetch();
	}
	
	@SuppressWarnings( "unused" )
	private class SimpleLocker implements Locker {
		
		private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock( FAIR );
	
		private final ReadLock __read__ = lock.readLock();
		private final WriteLock __write__ = lock.writeLock();
		private final ReentrantLock __fetch__ = new ReentrantLock( FAIR );
	
		@Override public void __READLOCK__() {
			__read__.lock();
		}
		@Override public void __WRITELOCK__() {
			__write__.lock();
		}
		@Override public void __FETCHLOCK__() {
			__fetch__.lock();
		}
		
		@Override public void __READUNLOCK__() {
			__read__.unlock();
		}
		@Override public void __WRITEUNLOCK__() {
			__write__.unlock();
		}
		@Override public void __FETCHUNLOCK__() {
			__fetch__.unlock();
		}
		@Override
		public boolean needsRefetch() {
			return true;
		}
		
	}
	
	private class TimedLocker implements Locker {
		
		private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock( FAIR );
	
		private final ReadLock __read__ = lock.readLock();
		private final WriteLock __write__ = lock.writeLock();
		
		private final long timeout;
		
		TimedLocker( long timeout ) {
			this.timeout = timeout;
		}
	
		@Override public void __READLOCK__() {
			try {
				if( ! ( __read__.tryLock() || __read__.tryLock( timeout, TimeUnit.MILLISECONDS ) ) )
						throw new CacheNGTimeout( timeout );
			} catch( InterruptedException e ) {
				throw new CacheNGInterrupted( e );
			}
		}
		@Override public void __WRITELOCK__() {
		}
		@Override public void __FETCHLOCK__() {
			try {
				if( ! ( __write__.tryLock() || __write__.tryLock( timeout, TimeUnit.MILLISECONDS ) ) )
						throw new CacheNGTimeout( timeout );
			} catch( InterruptedException e ) {
				throw new CacheNGInterrupted( e );
			}
		}
		
		@Override public void __READUNLOCK__() {
			__read__.unlock();
		}
		@Override public void __WRITEUNLOCK__() {
		}
		@Override public void __FETCHUNLOCK__() {
			__write__.unlock();
		}
		
		@Override
		public boolean needsRefetch() {
			return true;
		}
	}
	
	private class TimedLockerLoose implements Locker {
		
		private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock( FAIR );
	
		private final ReadLock __read__ = lock.readLock();
		private final WriteLock __write__ = lock.writeLock();
		
		private final long timeout;
		
		TimedLockerLoose( long timeout ) {
			this.timeout = timeout;
		}
	
		@Override public void __READLOCK__() {
			try {
				if( ! ( __read__.tryLock() || __read__.tryLock( timeout, TimeUnit.MILLISECONDS ) ) )
						throw new CacheNGTimeout( timeout );
			} catch( InterruptedException e ) {
				throw new CacheNGInterrupted( e );
			}
		}
		@Override public void __WRITELOCK__() {
			try {
				if( ! ( __write__.tryLock() || __write__.tryLock( timeout, TimeUnit.MILLISECONDS ) ) )
						throw new CacheNGTimeout( timeout );
			} catch( InterruptedException e ) {
				throw new CacheNGInterrupted( e );
			}
		}
		@Override public void __FETCHLOCK__() {
		}
		
		@Override public void __READUNLOCK__() {
			__read__.unlock();
		}
		@Override public void __WRITEUNLOCK__() {
			__write__.unlock();
		}
		@Override public void __FETCHUNLOCK__() {
		}
		
		@Override
		public boolean needsRefetch() {
			return false;
		}
	}
	
}
