package de.axone.data;

import java.util.List;
import java.util.Map;

public abstract class Maps {

	@FunctionalInterface
	public interface ListGenerator<T, L extends List<T>> {
		public L generate();
	}
	/**
	 * Extends map1 with the values of map2.
	 * 
	 * Missing keys are created. In case the key is already present the list is extended with the values
	 * from the second list.
	 * 
	 * @param map1
	 * @param map2
	 * @param gen Generator for new Lists of type stored in map1
	 * @return map1
	 */
	public static <K,V,L1 extends List<V>, L2 extends List<V>, M1 extends Map<K,L1>, M2 extends Map<K,L2>> M1 extend( M1 map1, M2 map2, ListGenerator<V,L1> gen ) {
		
		for( Map.Entry<K,L2> entry : map2.entrySet() ) {
			
			K key = entry.getKey();
			L2 newList = entry.getValue();
			
			L1 oldList = map1.get( key );
			if( oldList == null ) {
				oldList = gen.generate();
				map1.put( key, oldList );
			}
			
			oldList.addAll( newList );
		}
		
		return map1;
	}

}
