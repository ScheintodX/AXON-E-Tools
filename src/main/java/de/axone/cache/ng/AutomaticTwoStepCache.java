package de.axone.cache.ng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AutomaticTwoStepCache<K,I,V> {
	
	private final CacheNG.AutomaticClient<K,List<I>> intermediatesForKey;
	private final CacheNG.AutomaticClient<I,V> valuesForIntermediate;
	
	public AutomaticTwoStepCache( CacheNG.AutomaticClient<K,List<I>> intermediatesForKey, CacheNG.AutomaticClient<I,V> valuesForIntermediate ){
		
		this.intermediatesForKey = intermediatesForKey;
		this.valuesForIntermediate = valuesForIntermediate;
	}
	
	public List<V> fetch( K key, CacheNG.Accessor<K,List<I>> intermediateAccessor, CacheNG.Accessor<I,V> valueAccessor ){
		
		List<I> intermediates = intermediatesForKey.fetch( key, intermediateAccessor );
		
		if( intermediates == null ) return Collections.emptyList();
		
		Map<I,V> values = valuesForIntermediate.fetch( intermediates, valueAccessor );
		
		List<V> result = new ArrayList<>( intermediates.size() );
		
		for( Map.Entry<I,V> entry : values.entrySet() ) {
			V value = entry.getValue();
			if( value != null ) result.add( value );
		}
		
		return result;
	}
	
}
