package de.axone.cache.ng;

import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractMultiValueAccessor<K,V> implements CacheNG.Accessor<K, V> {

	@Override
	public V fetch( K key ) {
		
		LinkedList<K> list = new LinkedList<K>();
		list.add( key );
		
		Map<K,V> result = fetch( list );
		
		return result.get( key );
	}

}
