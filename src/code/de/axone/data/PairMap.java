package de.axone.data;

import java.util.HashMap;


public class PairMap<L,R,V> extends HashMap<Pair<L,R>,V>{
	
	public void put( L l, R r, V v ){
		
		put( key( l, r ), v);
	}
	
	public V get( L l, R r ){
		
		return get( key( l, r ) );
	}
	
	public boolean containsKey( L l, R r ){
		
		return containsKey( key( l, r ) );
	}
	
	private Pair<L,R> key( L l, R r ){
		
		return new Pair<L,R>( l, r );
	}

}
