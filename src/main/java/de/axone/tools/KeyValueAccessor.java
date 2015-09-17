package de.axone.tools;

import java.util.NoSuchElementException;

import de.axone.data.KeyValueStore;

public interface KeyValueAccessor<K,V> extends KeyValueStore<K,V> {
	
	// == String =====
	@Override
	public V access( K key );
	
	public default boolean has( K key ){
		return access( key ) != null;
	}
	
	public default V get( K key ){
		return access( key );
	}
	public default V get( K key, V defaultValue ){
		V v = get( key );
		return v != null ? v : defaultValue;
	}
	public default V get( K key, ValueProvider<V> defaultValueProvider ){
		V v = get( key );
		return v != null ? v : defaultValueProvider.get();
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
	public default V getRequired( K key, ValueProvider<V> defaultValueProvider ){
		V v = get( key, defaultValueProvider.get() );
		if( v == null ) throw new NoSuchElementException( key.toString() );
		return v;
	}
	
	@FunctionalInterface
	public interface ValueProvider<V> {
		public V get();
	}
	
	
}