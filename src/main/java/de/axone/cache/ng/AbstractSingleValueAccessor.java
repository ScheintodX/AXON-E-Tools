package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSingleValueAccessor<K,O> implements CacheNG.Accessor<K,O> {

	@Override
	public Map<K, O> fetch( Collection<K> keys ) {
		HashMap<K,O> result = new HashMap<K,O>();
		for( K key : keys ){
			result.put( key, fetch( key ) );
		}
		return result;
	}

}
