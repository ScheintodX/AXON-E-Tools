package de.axone.cache.ng;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.axone.cache.ng.CacheNG.Cache;

public abstract class AbstractCache<K,O>
		extends AbstractCacheEventProvider<K,O>
		implements CacheNG.CacheEventListener<K> {

	public abstract Cache.Entry<O> fetchEntry( K key );
	
	public @Nullable O fetch( @Nonnull K key ){
		Cache.Entry<O> entry = fetchEntry( key );
		if( entry == null ) return null;
		return entry.data();
	}
	
	public void invalidate( K key ){
		
		listenersInvalidate( key );
		invalidateEvent( key );
	}
	
	public void invalidateAll( boolean force ){
		
		listenersInvalidateAll( force );
		invalidateAllEvent( force );
	}

}
