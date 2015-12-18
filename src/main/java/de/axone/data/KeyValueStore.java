package de.axone.data;

public interface KeyValueStore<K,V> {
	
	public V access( K key );
	
	default public boolean has( K key ) {
		return access( key ) != null;
	}
	
}
