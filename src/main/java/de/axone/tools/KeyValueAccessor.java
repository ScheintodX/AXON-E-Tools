package de.axone.tools;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.axone.data.KeyValueStore;

public interface KeyValueAccessor<K,V> extends KeyValueStore<K,V> {
	
	// == String =====
	@Override
	public V access( K key );
	
	public default NoSuchElementException exception( K key ) {
		return new NoSuchElementException( key.toString() );
	}
	
	public default @Nullable V get( @Nonnull K key ){
		return access( key );
	}
	public default @Nullable V get( @Nonnull K key, @Nullable V defaultValue ){
		V v = get( key );
		return v != null ? v : defaultValue;
	}
	public default @Nullable V get( @Nonnull K key, @Nonnull ValueProvider<V> defaultValueProvider ){
		V v = get( key );
		return v != null ? v : defaultValueProvider.get();
	}
	public default @Nonnull V getRequired( @Nonnull K key ){
		V v = get( key );
		if( v == null ) throw exception( key );
		return v;
	}
	public default @Nonnull V getRequired( @Nonnull K key, @Nonnull V defaultValue ){
		V v = get( key, defaultValue );
		if( v == null ) throw exception( key );
		return v;
	}
	public default @Nonnull V getRequired( @Nonnull K key, @Nonnull ValueProvider<V> defaultValueProvider ){
		V v = get( key, defaultValueProvider.get() );
		if( v == null ) throw exception( key );
		return v;
	}
	public default @Nullable V getIgnorant( @Nonnull K key ){
		try {
			return get( key );
		} catch( Throwable t ) {
			return null;
		}
	}
	public default @Nullable V getChecked( @Nonnull K key ){
		if( ! has( key ) ) return null;
		return get( key );
	}
	
	@FunctionalInterface
	public interface ValueProvider<V> {
		public V get();
	}
	
}