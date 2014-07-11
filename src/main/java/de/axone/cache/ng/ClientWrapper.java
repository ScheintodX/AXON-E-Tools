package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Client;

public abstract class ClientWrapper<K,O> implements CacheNG.Client<K,O> {
	
	protected final CacheNG.Client<K,O> wrapped;

	public ClientWrapper( Client<K, O> wrapped ) {
		this.wrapped = wrapped;
	}
	
	@Override
	public CacheNG.Client.Entry<O> fetchEntry( K key ) {
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
	public void putEntry( K key, CacheNG.Client.Entry<O> entry ) {
		wrapped.putEntry( key, entry );
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