package de.axone.cache;

import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractMultiValueDataAccessor<K,V> implements DataAccessor<K, V> {

	@Override
	public V get( K key ) {
		
		LinkedList<K> list = new LinkedList<K>();
		list.add( key );
		
		Map<K,V> result = get( list );
		
		return result.get( key );
	}

}
