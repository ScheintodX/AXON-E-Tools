package de.axone.data;

public interface KeyValueStore<K,V> {
	
	public V access( K key );
	
}
