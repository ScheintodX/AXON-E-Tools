package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Cache;

public abstract class CacheWrapper<K,O> implements CacheNG.Cache<K,O> {
	
	protected final CacheNG.Cache<K,O> wrapped;

	public CacheWrapper( Cache<K, O> wrapped ) {
		this.wrapped = wrapped;
	}
	
	@Override
	public CacheNG.Cache.Entry<O> fetchEntry( K key ) {
		return wrapped.fetchEntry( key );
	}

	@Override
	public O fetch( K key ) {
		return wrapped.fetch( key );
	}

	@Override
	public boolean isCached( K key ) {
		return wrapped.isCached( key );
	}

	@Override
	public void invalidate( K key ) {
		wrapped.invalidate( key );
	}

	@Override
	public void put( K key, O object ) {
		wrapped.put( key, object );
	}

	@Override
	public void invalidateAll() {
		wrapped.invalidateAll();
	}

	@Override
	public int size() {
		return wrapped.size();
	}

	@Override
	public int capacity() {
		return wrapped.capacity();
	}

	@Override
	public String info() {
		return wrapped.info();
	}

}
