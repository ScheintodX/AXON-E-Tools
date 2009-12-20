package de.axone.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Mapper {

	public static <T> HashMap<T,T> hashMap( T ... params ){

		if( (params.length % 2) != 0 )
			throw new IllegalArgumentException( "Only even argument count allowed" );

		HashMap<T,T> result = new HashMap<T,T>();
		for( int i = 0; i < params.length; i+= 2 ){
			result.put( params[ i ], params[ i+1 ] );
		}
		return result;
	}

	public static <K,V> HashMap<K,V> hashMap( K[] k, V[] v ){

		if( k.length != v.length )
			throw new IllegalArgumentException( "Argument count missmatch: " + k.length + "/" + v.length );

		HashMap<K,V> result = new HashMap<K,V>();
		for( int i = 0; i < k.length; i++ ){
			result.put( k[ i ], v[ i ] );
		}
		return result;
	}

	public static <T> HashSet<T> hashSet( T ... params ){

		HashSet<T> result = new HashSet<T>( Arrays.asList( params ));
		return result;
	}

	public static <T> LinkedList<T> linkedList( T ... params ){

		LinkedList<T> result = new LinkedList<T>( Arrays.asList( params ) );
		return result;
	}

	public static <T> ArrayList<T> arrayList( T ... params ){

		ArrayList<T> result = new ArrayList<T>( Arrays.asList( params ) );
		return result;
	}

	public static <T,U> List<String> mapToList( String joinWith, Map<T,U> map ){

		LinkedList<String> result = new LinkedList<String>();

		for( T key : map.keySet() ){

			U value = map.get( key );
			result.add( key.toString() + joinWith + value.toString() );
		}
		return result;
	}
}
