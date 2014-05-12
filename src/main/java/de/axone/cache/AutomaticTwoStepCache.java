package de.axone.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutomaticTwoStepCache<K,I,V> {
	
	private final AutomaticCache<K,List<I>> intermediatesForKey;
	private final AutomaticCache<I,V> valuesForIntermediate;
	
	public AutomaticTwoStepCache( AutomaticCache<K,List<I>> intermediatesForKey, AutomaticCache<I,V> valuesForIntermediate ){
		
		this.intermediatesForKey = intermediatesForKey;
		this.valuesForIntermediate = valuesForIntermediate;
	}
	
	public List<V> get( K key, DataAccessor<K,List<I>> intermediateAccessor, DataAccessor<I,V> valueAccessor ){
		
		List<I> intermediates = intermediatesForKey.get( key, intermediateAccessor );
		
		Map<I,V> values = valuesForIntermediate.get( intermediates, valueAccessor );
		
		List<V> result = new ArrayList<>( intermediates.size() );
		
		for( I i : intermediates ){
			result.add( values.get( i ) );
		}
		
		return result;
	}
	
}
