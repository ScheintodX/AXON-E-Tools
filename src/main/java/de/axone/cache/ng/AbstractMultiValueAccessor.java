package de.axone.cache.ng;

import java.util.Arrays;
import java.util.Map;

public abstract class AbstractMultiValueAccessor<K,O> implements CacheNG.Accessor<K,O> {

	@Override
	public O fetch( K key ) {
		
		Map<K,O> result = fetch( Arrays.asList( key ) );
		
		return result.get( key );
	}

}
