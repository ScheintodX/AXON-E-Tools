package de.axone.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class MapProxy<K,V> implements Map<K,V>{
	
	public enum Mapping{ hash, tree };
	
	private final Map<K,V> mapped;
	
	protected final Mapping mapping;
	
	public MapProxy(){
		this( Mapping.hash );
	}
	public MapProxy( Mapping mapping ){
		this.mapping = mapping;
		this.mapped = genMap();
	}
	
	public MapProxy( Map<K,V> mapped ){
		this.mapped = mapped;
		this.mapping = null;
	}

	protected Map<K,V> genMap(){
		
		Map<K,V> result;
		
		if( mapping == null ) throw new IllegalStateException( "No mapping set" );
		
		switch( mapping ){
		case hash: result = new HashMap<>(); break;
		case tree: result = new TreeMap<>(); break;
		default:
			throw new IllegalArgumentException( "Unsupported mapping: " + mapping );
		}
		
		return result;
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
