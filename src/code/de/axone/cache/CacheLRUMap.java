package de.axone.cache;

import de.axone.data.LRUCache;

/**
 * BackendCache as implemented by a LRUCache
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class CacheLRUMap<K,V> extends LRUCache<K,V> implements Cache.Direct<K,V> {
	
	private long hits;
	private long misses;

	public CacheLRUMap( int maxCapacity ) {
		super(maxCapacity);
	}

	@Override
	public String info() {
		
		long sum = hits+misses;
		double ratio = sum > 0 ? (double)hits/sum : 0;
		
		return "LRU" +
				" (Size:" + size() + " of " + getCapacity() + 
				", Hits: " + hits + "/" + sum + " = " + Math.round( ratio *1000 )/10 + "%)";
	}
	
	@Override
	public boolean containsKey( Object key ){
		
		boolean result = super.containsKey( key );
		
		synchronized( this ){
			if( result ) hits++;
			else misses++;
		}
		
		return result;
	}
	@Override
	public V get( Object key ){
		
		V result = super.get( key );
		
		synchronized( this ){
			if( result != null ) hits++;
			else misses++;
		}
		
		return result;
	}
	
}
