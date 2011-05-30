package de.axone.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.axone.exception.Assert;

public abstract class Mapper {

	public static <T> HashMap<T,T> hashMap( T ... values ){

		if( (values.length % 2) != 0 )
			throw new IllegalArgumentException( "Only even argument count allowed" );

		HashMap<T,T> result = new HashMap<T,T>();
		for( int i = 0; i < values.length; i+= 2 ){
			result.put( values[ i ], values[ i+1 ] );
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

	public static <T> HashSet<T> hashSet( T ... values ){

		HashSet<T> result = new HashSet<T>( Arrays.asList( values ));
		return result;
	}

	public static <T,X> HashSet<T> hashSet( Converter<T,X> converter, X ... values ){
		
		Assert.notNull( converter, "converter" );
		if( values == null ) return new HashSet<T>();
		
		HashSet<T> result = new HashSet<T>( values.length );
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
		return result;
	}

	public static <T> LinkedList<T> linkedList( T ... values ){

		LinkedList<T> result = new LinkedList<T>( Arrays.asList( values ) );
		return result;
	}
	
	public static <T,X> LinkedList<T> linkedList( Converter<T,X> converter, X ... values ){
		
		LinkedList<T> result = new LinkedList<T>();
		fill( result, converter, values );
		return result;
	}
	
	public static <T> ArrayList<T> arrayList( T ... values ){

		ArrayList<T> result = new ArrayList<T>( Arrays.asList( values ) );
		return result;
	}
	
	public static <T,X> ArrayList<T> arrayList( Converter<T,X> converter, X ... values ){
		
		ArrayList<T> result = new ArrayList<T>( values.length );
		fill( result, converter, values );
		return result;
	}
	
	private static <T,X> void fill( Collection<T> result, Converter<T,X> converter, X ... values ){
		
		Assert.notNull(  converter, "converter" );
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
	}

	public static <T,U> List<String> mapToList( String joinWith, Map<T,U> map ){

		LinkedList<String> result = new LinkedList<String>();

		for( T key : map.keySet() ){

			U value = map.get( key );
			result.add( key.toString() + joinWith + value.toString() );
		}
		return result;
	}
	
	/*
	private static final class NoConverter<T> implements Converter<T,T>{

		@Override
		public T convert( T value ) { return value; }
	}
	*/
	
	public interface Converter<T,X> {
		public T convert( X value );
	}
}
