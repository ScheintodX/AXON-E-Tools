package de.axone.tools;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.axone.data.KeyValueStore;

public interface KeyValueAccessor<K,V,EX extends Exception> extends KeyValueStore<K,V,EX> {
	
	public default boolean has( @Nonnull K key ) {
		return checkAccess( key );
	}
	
	public default @Nullable V get( @Nonnull K key ) {
		return accessChecked( key );
	}
	public default @Nullable V get( @Nonnull K key, @Nullable V defaultValue ) {
		V v = accessChecked( key );
		return v != null ? v : defaultValue;
	}
	public default @Nullable V get( @Nonnull K key, @Nonnull ValueProvider<V> defaultValueProvider ) {
		V v = getChecked( key );
		return v != null ? v : defaultValueProvider.get();
	}
	public default @Nonnull V getRequired( @Nonnull K key ) throws EX {
		V v = get( key );
		if( v == null ) throw exception( key );
		return v;
	}
	public default @Nonnull V getRequired( @Nonnull K key, @Nonnull V defaultValue ) throws EX {
		V v = get( key, defaultValue );
		if( v == null ) throw exception( key );
		return v;
	}
	public default @Nonnull V getRequired( @Nonnull K key, @Nonnull ValueProvider<V> defaultValueProvider ) throws EX {
		V v = get( key, defaultValueProvider.get() );
		if( v == null ) throw exception( key );
		return v;
	}
	public default @Nullable V getChecked( @Nonnull K key ){
		return accessChecked( key );
	}
	
	@FunctionalInterface
	public interface ValueProvider<V> {
		public V get();
	}
	
}