package de.axone.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class PairMap<L,R,V> extends MapProxy<Pair<L,R>,V>{
	
	private final Mapping mapping;
	
	public PairMap(){
		this( Mapping.hash );
	}
	
	@SuppressWarnings( "unchecked" )
	public PairMap( Mapping mapping ){
		super( (Map<Pair<L,R>, V>) genMap( mapping ) );
		this.mapping = mapping;
	}
	
	private static Map<?,?> genMap( Mapping mapping ){
		
		Map<?,?> result;
		
		switch( mapping ){
		case hash: result = new HashMap<>(); break;
		case tree: result = new TreeMap<>(); break;
		default:
			throw new IllegalArgumentException( "Unsupported mapping: " + mapping );
		}
		
		return result;
	}
	
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

	public Map<R,V> getRow( L l ){
		return getRight( l );
	}
	public Map<R,V> getRight( L l ){
		@SuppressWarnings( "unchecked" )
		Map<R,V> result = (Map<R,V>) genMap( mapping );
		for( Pair<L,R> t : this.keySet() ){
			if( t.getLeft().equals( l ) ){
				result.put( t.getRight(), get( t ) );
			}
		}
		return result;
	}

	public Map<L,V> getCol( R r ){
		return getLeft( r );
	}
	public Map<L,V> getLeft( R r ){
		@SuppressWarnings( "unchecked" )
		Map<L,V> result = (Map<L,V>) genMap( mapping );
		for( Pair<L,R> t : this.keySet() ){
			if( t.getRight().equals( r ) ){
				result.put( t.getLeft(), get( t ) );
			}
		}
		return result;
	}
	
	/**
	 * Build a pairmap from a table.
	 * 
	 * The table must look like this:
	 * 
	 * null,    Col1Key, Col2Key, Col4Key, ...
	 * Row1Key, 1,       2,       3
	 * Row2Key, 4,       5,       6
	 * ...
	 * 
	 * @param table
	 * @param rowKeys
	 * @param colKeys
	 * @return
	 */
	public static final <R,C,V> PairMap<R,C,V> buildFromTable( Mapping mapping,
			Object[][] table, Class<R> rowKeysType, Class<C> colKeysType, Class<V> valueType ){
		
		PairMap<R,C,V> result = new PairMap<>( mapping );
		int width = -1;
		
		for( int i=0; i<table.length; i++ ){
			
			Object [] row = table[ i ];
			if( i == 0 ){
				
				if( row[ 0 ] != null )
					throw new IllegalArgumentException( "[0,0] has to be 'null'" );
				
				for( int j = 1; j<row.length; j++ )
					if( ! colKeysType.isInstance( row[ j ] ) )
						throw new IllegalArgumentException( "[" + i + "," + j + "] not instance of " + colKeysType.getSimpleName() );
				
				width = row.length;
				
			} else {
				
				if( row.length != width )
					throw new IllegalArgumentException( "Missmatch length in row " + i +
							". Should be: " + width + " but is: " + row.length );
			
				for( int j=0; j<row.length; j++ ){
					
					if( j==0 ){
						if( ! rowKeysType.isInstance( row[ j ] ) )
							throw new IllegalArgumentException( "[" + i + "," + j + "] not instance of " + rowKeysType.getSimpleName() );
					} else {
						
						@SuppressWarnings( "unchecked" )
						R r = (R) table[ i ][ 0 ];
						@SuppressWarnings( "unchecked" )
						C c = (C) table[ 0 ][ j ];
						@SuppressWarnings( "unchecked" )
						V v = (V) table[ i ][ j ];
						
						result.put( r, c, v );
						
					}
				}
			}
		}
		
		return result;
	}
	

}
