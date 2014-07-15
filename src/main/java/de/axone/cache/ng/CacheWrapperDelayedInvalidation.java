package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNG.InvalidationManager;

public class CacheWrapperDelayedInvalidation<K,O> extends CacheWrapper<K,O> {

	private InvalidationManager<K,O> invalidationManager;
	
	public CacheWrapperDelayedInvalidation( Cache<K, O> wrapped ) {
		super( wrapped );
	}
	
	public void invalidateAllWithin( int milliSeconds ) {
		
		invalidationManager = new TimoutInvalidationManager<K,O>(
				System.currentTimeMillis(), milliSeconds );
	}

	private boolean isAlive( K key, CacheNG.Cache.Entry<O> entry ){
		
		if( invalidationManager != null ){
			
			return invalidationManager.isValid( key, entry );
		}
		
		return true;
	}
	
	@Override
	public Entry<O> fetchEntry( K key ) {
		
		Entry<O> entry = super.fetchEntry( key );
		
		if( entry == null ) return null;
	
		if( ! isAlive( key, entry ) ){
			wrapped.invalidate( key );
			return null;
		}
		
		return entry;
	}

	@Override
	public boolean isCached( K key ) {
		
		// This is quick
		if( ! super.isCached( key ) ) return false;
		
		// Fetch for further checks
		Cache.Entry<O> entry = super.fetchEntry( key );
		
		return entry != null && isAlive( key, entry );
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
