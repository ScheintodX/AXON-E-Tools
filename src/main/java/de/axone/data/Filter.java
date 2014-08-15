package de.axone.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public abstract class Filter<T> {

	public abstract boolean ok( T object );
	
	public <U extends Collection<T>> U filter( U list ){
		
		for( Iterator<T> it = list.iterator(); it.hasNext();  ){
			
			T item = it.next();
			if( !ok( item ) ) it.remove();
		}
		
		return list;
	}
	
	public <U extends Map<?,T>> U filter( U map ){
		
		LinkedList<Object> failed = new LinkedList<Object>();
		for( Map.Entry<?,T> entry : map.entrySet() ){
			if( !ok( entry.getValue() ) ) failed.add( entry.getKey() );
		}
		for( Object key : failed ){
			map.remove( key );
		}
		
		return map;
	}
}
