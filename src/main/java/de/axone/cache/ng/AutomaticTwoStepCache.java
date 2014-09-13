package de.axone.cache.ng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AutomaticTwoStepCache<K,I,O> {
	
	private final CacheNG.AutomaticClient<K,List<I>> intermediatesForKey;
	private final CacheNG.AutomaticClient<I,O> valuesForIntermediate;
	
	public AutomaticTwoStepCache( CacheNG.AutomaticClient<K,List<I>> intermediatesForKey, CacheNG.AutomaticClient<I,O> valuesForIntermediate ){
		
		this.intermediatesForKey = intermediatesForKey;
		this.valuesForIntermediate = valuesForIntermediate;
	}
	
	public List<O> fetch( K key, CacheNG.SingleValueAccessor<K,List<I>> intermediateAccessor, CacheNG.MultiValueAccessor<I,O> valueAccessor ){
		
		List<I> intermediates = intermediatesForKey.fetch( key, intermediateAccessor );
		
		if( intermediates == null ) return Collections.emptyList();
		
		Map<I,O> values = valuesForIntermediate.fetch( intermediates, valueAccessor );
		
		List<O> result = new ArrayList<>( intermediates.size() );
		
		for( Map.Entry<I,O> entry : values.entrySet() ) {
			O value = entry.getValue();
			if( value != null ) result.add( value );
		}
		
		return result;
	}
	
}
