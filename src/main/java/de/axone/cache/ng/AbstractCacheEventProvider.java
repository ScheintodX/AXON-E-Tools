package de.axone.cache.ng;

import java.util.LinkedList;
import java.util.List;

import de.axone.cache.ng.CacheNG.CacheEventListener;
import de.axone.cache.ng.CacheNG.CacheEventProvider;

public class AbstractCacheEventProvider<K,O> implements CacheEventProvider<K> {
	
	private List<CacheEventListener<K>> listeners;

	@Override
	public void registerListener( CacheEventListener<K> listener ) {
		if( listeners == null ) listeners = new LinkedList<>();
		listeners.add( listener );
	}

	@Override
	public void listenersInvalidate( K key ) {
		
		if( listeners != null ) for( CacheEventListener<K> listener : listeners ){
			listener.invalidateEvent( key );
		}
	}

	@Override
	public void listenersInvalidateAll( boolean force ) {
		
		if( listeners != null ) for( CacheEventListener<K> listener : listeners ){
			listener.invalidateAllEvent( force );
		}
	}

}
