package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSingleValueAccessor<K,V> implements CacheNG.Accessor<K,V> {

	@Override
	public Map<K, V> fetch( Collection<K> keys ) {
		HashMap<K,V> result = new HashMap<K,V>();
		for( K key : keys ){
			result.put( key, fetch( key ) );
		}
		return result;
	}

}
