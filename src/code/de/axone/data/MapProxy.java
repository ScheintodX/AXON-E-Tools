package de.axone.data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class MapProxy<K,V> implements Map<K,V>{
	
	public enum Mapping{ hash, tree };
	
	private final Map<K,V> mapped;
	
	public MapProxy( Map<K,V> mapped ){
		this.mapped = mapped;
	}

	@Override
	public int size() {
		return mapped.size();
	}

	@Override
	public boolean isEmpty() {
		return mapped.isEmpty();
	}

	@Override
	public boolean containsKey( Object key ) {
		return mapped.containsKey( key );
	}

	@Override
	public boolean containsValue( Object value ) {
		return mapped.containsValue( value );
	}

	@Override
	public V get( Object key ) {
		return mapped.get( key );
	}

	@Override
	public V put( K key, V value ) {
		return mapped.put( key, value );
	}

	@Override
	public V remove( Object key ) {
		return mapped.remove( key );
	}

	@Override
	public void putAll( Map<? extends K, ? extends V> m ) {
		mapped.putAll( m );
	}

	@Override
	public void clear() {
		mapped.clear();
	}

	@Override
	public Set<K> keySet() {
		return mapped.keySet();
	}

	@Override
	public Collection<V> values() {
		return mapped.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return mapped.entrySet();
	}

}
