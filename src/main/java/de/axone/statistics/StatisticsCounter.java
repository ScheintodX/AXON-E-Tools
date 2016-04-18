package de.axone.statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StatisticsCounter {
	
	private static final Map<Integer,Integer> storage =
			new HashMap<>();
	
	final String title, c1Title, c2Title;
	
	public StatisticsCounter( String title, String c1Title, String c2Title ) {
		
		this.title = title;
		this.c1Title = c1Title;
		this.c2Title = c2Title;
	}

	public void log( int key, int value ) {
		
		Integer stored = storage.get( key );
		
		if( stored == null ) stored = 0;
		
		storage.put( key, stored+value );
	}
	
	@Override
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		
		result.append( title ).append( '\n' );
		result.append( c1Title ).append( ';' ).append( c2Title ).append( '\n' );
		
		for( Entry<Integer,Integer> entry : storage.entrySet() ) {
			
			result.append( entry.getKey() )
					.append( "; " )
					.append( entry.getValue() )
					.append( '\n' )
					;
		}
		return result.toString();
	}
}
