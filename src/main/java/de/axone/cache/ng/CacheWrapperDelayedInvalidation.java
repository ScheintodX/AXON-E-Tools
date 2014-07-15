package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Cache;

public class CacheWrapperDelayedInvalidation<K,O> extends CacheWrapper<K,O> {

	public CacheWrapperDelayedInvalidation( Cache<K, O> wrapped ) {
		super( wrapped );
	}
	
	public void invalidateAllWithin( int millis ){
		
	}

}
