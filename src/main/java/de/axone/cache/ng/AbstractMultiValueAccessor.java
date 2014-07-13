package de.axone.cache.ng;

import java.util.Arrays;
import java.util.Map;

public abstract class AbstractMultiValueAccessor<K,V> implements CacheNG.Accessor<K, V> {

	@Override
	public V fetch( K key ) {
		
		Map<K,V> result = fetch( Arrays.asList( key ) );
		
		return result.get( key );
	}

}
