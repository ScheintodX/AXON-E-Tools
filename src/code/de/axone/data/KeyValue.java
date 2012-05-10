package de.axone.data;

public class KeyValue<K,V> extends Pair<K,V> {

	public KeyValue( K left, V right ) {
		super( left, right );
	}

	public K key(){
		return getLeft();
	}
	public V value(){
		return getRight();
	}

}
