package de.axone.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	private int maxCapacity;

	public LRUCache(int maxCapacity) {

		// initial capacity / load factor / access order
		super( maxCapacity / 4, 0.7f, true );

		this.maxCapacity = maxCapacity;
	}

	@Override
	protected boolean removeEldestEntry( Map.Entry<K, V> eldest ) {

		if( size() > maxCapacity ) {
			return true;
		} else {
			return false;
		}
	}
}
