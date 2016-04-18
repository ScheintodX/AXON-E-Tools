package de.axone.data.collections;

import java.util.HashMap;
import java.util.List;

public class CountingMap extends HashMap<String,Integer> {

	public void count( String key ) {
		
		count( key, 1 );
	}
	
	public void count( String key, int amount ) {
		
		Integer stored = super.get( key );
		
		if( stored == null ) stored = 0;
		
		stored += amount;
		
		put( key, stored );
	}

	public void count( List<String> ids ) {
		
		for( String id : ids ) {
			
			count( id );
		}
	}
}
