package de.axone.data;

import java.util.Map;


public class PairMap<L,R,V> extends MapProxy<Pair<L,R>,V>{
	
	public PairMap(){
		this( Mapping.hash );
	}
	
	public PairMap( Mapping mapping ){
		super( mapping );
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
		Map<R,V> result = (Map<R,V>) genMap();
		for( Map.Entry<Pair<L,R>,V> entry : this.entrySet() ){
			Pair<L,R> t = entry.getKey();
			if( t.getLeft().equals( l ) ){
				result.put( t.getRight(), entry.getValue() );
			}
		}
		return result;
	}

	public Map<L,V> getCol( R r ){
		return getLeft( r );
	}
	public Map<L,V> getLeft( R r ){
		@SuppressWarnings( "unchecked" )
		Map<L,V> result = (Map<L,V>) genMap();
		for( Map.Entry<Pair<L,R>,V> entry : this.entrySet() ){
			Pair<L,R> t = entry.getKey();
			if( t.getRight().equals( r ) ){
				result.put( t.getLeft(), entry.getValue() );
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
	 * @param mapping 
	 * 
	 * @param table
	 * @param rowKeysType 
	 * @param colKeysType 
	 * @param valueType 
	 * @return the generated PairMap
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
