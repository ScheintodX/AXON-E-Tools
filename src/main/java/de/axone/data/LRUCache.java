package de.axone.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3846790577344139807L;
	private int capacity;

	public LRUCache(int capacity) {

		// initial capacity / load factor / access order
		super( capacity / 4, 0.7f, true );

		this.capacity = capacity;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	@Override
	protected boolean removeEldestEntry( Map.Entry<K, V> eldest ) {
		
		return size() > capacity;
		/*
		boolean removeMe = size() > capacity;
		
		if( removeMe ){
			
			if( eldest instanceof CacheRemoveListener ){
				((CacheEventListener<?>) eldest).invalidateAllEvent( true );
			}
		}
		
		return removeMe;
		*/
	}

	/*
	@Override
	public void clear() {
		
		for( V value : this.values() ) {
			if( value instanceof CacheRemoveListener ){
				((CacheEventListener<?>) value).invalidateAllEvent( true );
			}
		}
		
		super.clear();
	}

	@Override
	public V remove( Object key ) {
		
		V value = get( key );
		
		if( value != null && value instanceof CacheRemoveListener ){
			((CacheEventListener<?>) value).invalidateAllEvent( true );
		}
		
		return super.remove( key );
	}
	*/
	
	
}
