package de.axone.tools;

import java.util.NoSuchElementException;

import de.axone.data.KeyValueStore;

public interface KeyValueAccessor<K,V> extends KeyValueStore<K,V> {
	
	// == String =====
	@Override
	public V access( K key );
	
	public default V get( K key ){
		return access( key );
	}
	public default V get( K key, V defaultValue ){
		V v = get( key );
		return v != null ? v : defaultValue;
	}
	public default V getRequired( K key ){
		V v = get( key );
		if( v == null ) throw new NoSuchElementException( key.toString() );
		return v;
	}
	public default V getRequired( K key, V defaultValue ){
		V v = get( key, defaultValue );
		if( v == null ) throw new NoSuchElementException( key.toString() );
		return v;
	}
	
}