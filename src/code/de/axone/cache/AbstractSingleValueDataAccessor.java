package de.axone.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSingleValueDataAccessor<K,V> implements DataAccessor<K,V> {

	@Override
	public Map<K, V> get( Collection<K> keys ) {
		HashMap<K,V> result = new HashMap<K,V>();
		for( K key : keys ){
			result.put( key, get( key ) );
		}
		return result;
	}

}
