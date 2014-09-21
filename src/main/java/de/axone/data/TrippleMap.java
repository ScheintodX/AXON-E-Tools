package de.axone.data;

import java.util.Map;

public class TrippleMap<A,B,C,V> extends MapProxy<Tripple<A,B,C>,V>{
	
	public TrippleMap(){
		this( Mapping.hash );
	}
	
	public TrippleMap( Mapping mapping ){
		super( mapping );
	}
	
	public void put( A a, B b, C c, V v ){
		
		put( key( a, b, c ), v);
	}
	
	public V get( A a, B b, C c ){
		
		return get( key( a, b, c ) );
	}
	
	public boolean containsKey( A a, B b, C c ){
		
		return containsKey( key( a, b, c ) );
	}
	
	private Tripple<A,B,C> key( A a, B b, C c ){
		
		return new Tripple<A,B, C>( a, b, c );
	}
	
	public PairMap<A,B,V> getAB( C c ){
		PairMap<A,B,V> result = new PairMap<>( mapping );
		for( Map.Entry<Tripple<A,B,C>,V> entry : this.entrySet() ){
			Tripple<A,B,C> t = entry.getKey();
			if( t.getC().equals( c ) ){
				result.put( new Pair<A,B>( t.getA(), t.getB() ), entry.getValue() );
			}
		}
		return result;
	}

	public PairMap<A,C,V> getAC( B b ){
		PairMap<A,C,V> result = new PairMap<>( mapping );
		for( Map.Entry<Tripple<A,B,C>,V> entry : this.entrySet() ){
			Tripple<A,B,C> t = entry.getKey();
			if( t.getB().equals( b ) ){
				result.put( new Pair<A,C>( t.getA(), t.getC() ), entry.getValue() );
			}
		}
		return result;
	}

	public PairMap<B,C,V> getBC( A a ){
		PairMap<B,C,V> result = new PairMap<>( mapping );
		for( Map.Entry<Tripple<A,B,C>,V> entry : this.entrySet() ){
			Tripple<A,B,C> t = entry.getKey();
			if( t.getA().equals( a ) ){
				result.put( new Pair<B,C>( t.getB(), t.getC() ), entry.getValue() );
			}
		}
		return result;
	}
	
	public Map<A,V> getA( B b, C c ){
		@SuppressWarnings( "unchecked" )
		Map<A,V> result = (Map<A,V>) genMap();
		for( Map.Entry<Tripple<A,B,C>,V> entry : this.entrySet() ){
			Tripple<A,B,C> t = entry.getKey();
			if( t.getB().equals( b ) && t.getC().equals( c ) ){
				result.put( t.getA(), entry.getValue() );
			}
		}
		return result;
	}

	public Map<B,V> getB( A a, C c ){
		@SuppressWarnings( "unchecked" )
		Map<B,V> result = (Map<B,V>) genMap();
		for( Map.Entry<Tripple<A,B,C>,V> entry : this.entrySet() ){
			Tripple<A,B,C> t = entry.getKey();
			if( t.getA().equals( a ) && t.getC().equals( c ) ){
				result.put( t.getB(), entry.getValue() );
			}
		}
		return result;
	}

	public Map<C,V> getC( A a, B b ){
		@SuppressWarnings( "unchecked" )
		Map<C,V> result = (Map<C,V>) genMap();
		for( Map.Entry<Tripple<A,B,C>,V> entry : this.entrySet() ){
			Tripple<A,B,C> t = entry.getKey();
			if( t.getA().equals( a ) && t.getB().equals( b ) ){
				result.put( t.getC(), entry.getValue() );
			}
		}
		return result;
	}
	
	/**
	 * Build a TrippleMap from a table.
	 * 
	 * The table must look like this:
	 * 
	 * null,    Col1Grp, Col1Grp, Col2Grp, Col3Grp, ...
	 * null,    Col1Key, Col2Key, Col3Key, Col4Key, ...
	 * Row1Key, 1,       2,       3,       4,
	 * Row2Key, 5,       6,       7,       8,
	 * Row3Key, 9,       10,     11,      12,
	 * ...
	 * 
	 * @param mapping
	 * @param table
	 * @param rowKeysType
	 * @param colGroupType
	 * @param colKeysType
	 * @param valueType
	 * @return the generated TrippleMap
	 */
	public static final <R,G,C,V> TrippleMap<R,G,C,V> buildFromTable( Mapping mapping, Object[][] table,
			Class<R> rowKeysType, Class<G> colGroupType, Class<C> colKeysType, Class<V> valueType ){
		
		TrippleMap<R,G,C,V> result = new TrippleMap<>( mapping );
		int width = -1;
		
		for( int i=0; i<table.length; i++ ){
			
			Object [] row = table[ i ];
			if( i == 0 ){
				
				if( row[ 0 ] != null )
					throw new IllegalArgumentException( "["+i+"0,0] has to be 'null'" );
				
				for( int j = 1; j<row.length; j++ )
					if( ! colGroupType.isInstance( row[ j ] ) )
						throw new IllegalArgumentException( "[" + i + "," + j + "] not instance of " + colGroupType.getSimpleName() );
				
				width = row.length;
				
			} else if( i == 1 ){
				
				if( row[ 0 ] != null )
					throw new IllegalArgumentException( "["+i+",0] has to be 'null'" );
				
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
						G g = (G) table[ 0 ][ j ];
						@SuppressWarnings( "unchecked" )
						C c = (C) table[ 1 ][ j ];
						@SuppressWarnings( "unchecked" )
						V v = (V) table[ i ][ j ];
						
						result.put( r, g, c, v );
						
					}
				}
			}
		}
		
		return result;
	}

}
