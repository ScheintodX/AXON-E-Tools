package de.axone.data;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class SingleImmutableMap<K,V> implements Map<K,V>{
	
	private final K key;
	private final V value;
	private Set<K> keySet;
	private Set<V> valueSet;
	private Set<Map.Entry<K,V>> entrySet;

	public SingleImmutableMap( K key, V value ){
		this.key = key;
		this.value = value;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey( Object key ) {
		return Objects.equals( key, this.key );
	}

	@Override
	public boolean containsValue( Object value ) {
		return Objects.equals( value, this.value );
	}

	@Override
	public V get( Object key ) {
		if( Objects.equals( key, this.key ) ) return value;
		else return null;
	}

	@Override
	public V put( K key, V value ) {
		throw new UnsupportedOperationException( "Map is immutable" );
	}

	@Override
	public V remove( Object key ) {
		throw new UnsupportedOperationException( "Map is immutable" );
	}

	@Override
	public void putAll( Map<? extends K, ? extends V> m ) {
		throw new UnsupportedOperationException( "Map is immutable" );
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException( "Map is immutable" );
	}

	@Override
	public Set<K> keySet() {
		if( keySet == null ) keySet = new SingleImmutableSet<K>( key );
		return keySet;
	}

	@Override
	public Collection<V> values() {
		if( valueSet == null ) valueSet = new SingleImmutableSet<V>( value );
		return valueSet;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		if( entrySet == null ) entrySet = new SingleImmutableSet<Map.Entry<K,V>>( new MyMapEntry() );
		return entrySet;
	}
	
	private final class MyMapEntry implements Map.Entry<K,V> {

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue( V value ) {
			throw new UnsupportedOperationException( "Map is immutable" );
		}
		
	}
}
