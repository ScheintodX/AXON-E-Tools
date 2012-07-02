package de.axone.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import de.axone.exception.Assert;

public abstract class Mapper {

	// Maps for Lists
	public static <T> HashMap<T,T> hashMap( T ... values ){
		return map( new HashMap<T,T>(), values );
	}
		
	public static <T> TreeMap<T,T> treeMap( T ... values ){
		return map( new TreeMap<T,T>(), values );
	}
		
	public static <T> LinkedHashMap<T,T> linkedHashMap( T ... values ){
		return map( new LinkedHashMap<T,T>(), values );
	}
		
	public static <T,M extends Map<T,T>> M map( M result, T ... values ){

		if( (values.length % 2) != 0 )
			throw new IllegalArgumentException( "Only even argument count allowed" );

		for( int i = 0; i < values.length; i+= 2 ){
			result.put( values[ i ], values[ i+1 ] );
		}
		return result;
	}

	// Maps for two Arrays
	public static <K,V> HashMap<K,V> hashMap( K[] k, V[] v ){
		return map( new HashMap<K,V>(), k, v );
	}
	
	public static <K,V> TreeMap<K,V> treeMap( K[] k, V[] v ){
		return map( new TreeMap<K,V>(), k, v );
	}
	
	public static <K,V> LinkedHashMap<K,V> linkedHashMap( K[] k, V[] v ){
		return map( new LinkedHashMap<K,V>(), k, v );
	}
	
	public static <K,V,M extends Map<K,V>> M map( M result, K[] k, V[] v ){

		if( k.length != v.length )
			throw new IllegalArgumentException( "Argument count missmatch: " + k.length + "/" + v.length );

		for( int i = 0; i < k.length; i++ ){
			result.put( k[ i ], v[ i ] );
		}
		return result;
	}

	// Set
	public static <T> HashSet<T> hashSet( T ... values ){

		HashSet<T> result = new HashSet<T>( Arrays.asList( values ));
		return result;
	}
	
	public static <T> TreeSet<T> treeSet( T ... values ){
		
		TreeSet<T> result = new TreeSet<T>( Arrays.asList( values ));
		return result;
	}

	// Converted Set
	public static <T,X> HashSet<T> hashSet( Converter<T,X> converter, X ... values ){
		
		Assert.notNull( converter, "converter" );
		if( values == null ) return new HashSet<T>();
		
		HashSet<T> result = new HashSet<T>( values.length );
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
		return result;
	}

	public static <T,X> TreeSet<T> treeSet( Converter<T,X> converter, X ... values ){
		
		Assert.notNull( converter, "converter" );
		if( values == null ) return new TreeSet<T>();
		
		TreeSet<T> result = new TreeSet<T>();
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
		return result;
	}

	public static <T> LinkedList<T> linkedList( T ... values ){

		return new LinkedList<T>( Arrays.asList( values ) );
	}
	
	public static <T> ArrayList<T> arrayList( T ... values ){

		return new ArrayList<T>( Arrays.asList( values ) );
	}
	
	public static <T,X> LinkedList<T> linkedList( Converter<T,X> converter, X ... values ){
		
		return collection( new LinkedList<T>(), converter, values );
	}
	
	public static <T,X> ArrayList<T> arrayList( Converter<T,X> converter, X ... values ){
		
		return collection( new ArrayList<T>( values.length ), converter, values );
	}
	
	public static <T,X,C extends Collection<T>> C collection( C result, Converter<T,X> converter, X ... values ){
		
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
	
	public static Map<String,String> split( String rs, String fs, String joined ){
		
		Map<String,String> result = new TreeMap<String,String>();
		String[] records = joined.split( rs );
		for( String record : records ){
			
			String[] fields = record.split( fs );
			for( int i=0; i<2; i++ ) fields[i] = fields[i].trim();
			
			result.put( fields[0], fields[1] );
		}
		return result;
	}
	
	public static <X,Y,M extends Map<X,Y>> M clean( M map ){
		
		LinkedList<X> empty = new LinkedList<X>();
		for( X key : map.keySet() ){
			
			if( map.get( key ) == null ) empty.add( key );
		}
		for( X key : empty ){
			map.remove( key );
		}
		
		return map;
	}
	
	public interface Converter<T,X> {
		public T convert( X value );
	}
}
