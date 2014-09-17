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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import de.axone.data.DoubleImmutableSet;
import de.axone.data.Pair;
import de.axone.data.SingleImmutableList;
import de.axone.data.SingleImmutableMap;
import de.axone.data.SingleImmutableSet;
import de.axone.exception.Assert;

public abstract class Mapper {
	
	public static class MapperEntry<X,Y> {
		private final X x;
		private final Y y;
		public MapperEntry( X x, Y y ) {
			this.x = x; this.y = y;
		}
	}
	public static <X,Y> MapperEntry<X,Y> E( X x, Y y ){
		return new MapperEntry<X,Y>( x, y );
	}

	@SafeVarargs
	public static <X,Y> HashMap<X,Y> hashMap( MapperEntry<X,Y> ... values ){
		return map( new HashMap<X,Y>(), false, values );
	}
	@SafeVarargs
	public static <X,Y> TreeMap<X,Y> treeMap( MapperEntry<X,Y> ... values ){
		return map( new TreeMap<X,Y>(), false, values );
	}
	// Maps for Lists
	@SafeVarargs
	public static <T> HashMap<T,T> hashMap( T ... values ){
		return map( new HashMap<T,T>(), false, values );
	}
	
	@SafeVarargs
	public static <T> HashMap<T,T> hashMap( T [] ... values ){
		return map( new HashMap<T,T>(), false, values );
	}
		
	@SafeVarargs
	public static <T> TreeMap<T,T> treeMap( T ... values ){
		return map( new TreeMap<T,T>(), false, values );
	}
	@SafeVarargs
	public static <T> TreeMap<T,T> treeMap( T [] ... values ){
		return map( new TreeMap<T,T>(), false, values );
	}
	
	@SafeVarargs
	public static <T> LinkedHashMap<T,T> linkedHashMap( T ... values ){
		return map( new LinkedHashMap<T,T>(), false, values );
	}
		
	@SafeVarargs
	public static <T> HashMap<T,T> hashMapIgnoreEmptyValues( T ... values ){
		return map( new HashMap<T,T>(), true, values );
	}
		
	@SafeVarargs
	public static <T> TreeMap<T,T> treeMapIgnoreEmptyValues( T ... values ){
		return map( new TreeMap<T,T>(), true, values );
	}
	
	@SafeVarargs
	public static <T> LinkedHashMap<T,T> linkedHashMapIgnoreEmptyValues( T ... values ){
		return map( new LinkedHashMap<T,T>(), true, values );
	}
		
	@SafeVarargs
	public static <T,M extends Map<T,T>> M map( M result, boolean ignoreEmptyValues,  T ... values ){

		if( (values.length % 2) != 0 )
			throw new IllegalArgumentException( "Only even argument count allowed" );

		for( int i = 0; i < values.length; i+= 2 ){
			if( ignoreEmptyValues || values[ i+1 ] != null )
				result.put( values[ i ], values[ i+1 ] );
		}
		return result;
	}
	
	@SafeVarargs
	public static <T,M extends Map<T,T>> M map( M result, boolean ignoreEmptyValues,  T [] ... values ){

		for( T [] value : values ){
			if( ignoreEmptyValues || value[ 1 ] != null )
				result.put( value[ 0 ], value[ 1 ] );
		}
		return result;
	}
	
	@SafeVarargs
	public static <X,Y,M extends Map<X,Y>> M map( M result, boolean ignoreEmptyValues,  MapperEntry<X,Y> ... values ){

		for( MapperEntry<X,Y> e : values ){
			result.put( e.x, e.y );
		}
		return result;
	}
	
	// Map List to Map via key extraction
	public <K,O, M extends Map<K,O>> M map( M result, KeyExtractor<K,O> extractor, Iterable<O> objects ){
		
		for( O value : objects ){
			result.put( extractor.extract( value ), value );
		}
		return result;
	}
	
	public <K,O> HashMap<K,O> hashMap( KeyExtractor<K,O> extractor, Iterable<O> objects ){
		return map( new HashMap<K,O>(), extractor, objects );
	}
	public <K,O> TreeMap<K,O> treeMap( KeyExtractor<K,O> extractor, Iterable<O> objects ){
		return map( new TreeMap<K,O>(), extractor, objects );
	}
	
	@SuppressWarnings( "unchecked" )
	public <K,O> HashMap<K,O> hashMap( KeyExtractor<K,O> extractor, O ... object ){
		return map( new HashMap<K,O>(), extractor, Arrays.asList( object ) );
	}
	@SuppressWarnings( "unchecked" )
	public <K,O> TreeMap<K,O> treeMap( KeyExtractor<K,O> extractor, O ... object ){
		return map( new TreeMap<K,O>(), extractor, Arrays.asList( object ) );
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

	@SafeVarargs
	public static <K,V> TreeMap<K,V> treeMap( Pair<K,V> ... values ){
		return map( new TreeMap<K,V>(), values );
	}
	@SafeVarargs
	public static <K,V> HashMap<K,V> hashMap( Pair<K,V> ... values ){
		return map( new HashMap<K,V>(), values );
	}
	@SafeVarargs
	public static <K,V,M extends Map<K,V>> M map( M result, Pair<K,V> ... values ){
		for( Pair<K,V> value : values ){
			result.put( value.getLeft(), value.getRight() );
		}
		return result;
	}
		
	// Set
	@SafeVarargs
	public static <T> HashSet<T> hashSet( T ... values ){

		HashSet<T> result = new HashSet<T>( Arrays.asList( values ));
		return result;
	}
	
	@SafeVarargs
	public static <T> TreeSet<T> treeSet( T ... values ){
		
		TreeSet<T> result = new TreeSet<T>( Arrays.asList( values ));
		return result;
	}

	// Converted Set
	@SafeVarargs
	public static <T,X> HashSet<T> hashSet( Converter<T,X> converter, X ... values ){
		
		Assert.notNull( converter, "converter" );
		if( values == null ) return new HashSet<T>();
		
		HashSet<T> result = new HashSet<T>( values.length );
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
		return result;
	}

	@SafeVarargs
	public static <T,X> TreeSet<T> treeSet( Converter<T,X> converter, X ... values ){
		
		Assert.notNull( converter, "converter" );
		if( values == null ) return new TreeSet<T>();
		
		TreeSet<T> result = new TreeSet<T>();
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
		return result;
	}

	@SafeVarargs
	public static <T> LinkedList<T> linkedList( T ... values ){

		return new LinkedList<T>( Arrays.asList( values ) );
	}
	
	@SafeVarargs
	public static <T> ArrayList<T> arrayList( T ... values ){

		return new ArrayList<T>( Arrays.asList( values ) );
	}
	
	@SafeVarargs
	public static <T,X> LinkedList<T> linkedList( Converter<T,X> converter, X ... values ){
		
		return collection( new LinkedList<T>(), converter, values );
	}
	
	@SafeVarargs
	public static <T,X> ArrayList<T> arrayList( Converter<T,X> converter, X ... values ){
		
		return collection( new ArrayList<T>( values.length ), converter, values );
	}
	
	@SafeVarargs
	public static <T,X,C extends Collection<T>> C collection( C result, Converter<T,X> converter, X ... values ){
		
		fill( result, converter, values );
		return result;
	}
		
	@SafeVarargs
	private static <T,X> void fill( Collection<T> result, Converter<T,X> converter, X ... values ){
		
		Assert.notNull(  converter, "converter" );
		
		for( X value : values ){
			result.add( converter.convert( value ) );
		}
	}

	public static <T,U> List<String> mapToList( String joinWith, Map<T,U> map ){

		LinkedList<String> result = new LinkedList<String>();

		for( Map.Entry<T,U> entry : map.entrySet() ) {
			
			result.add( entry.getKey().toString() +
					joinWith + entry.getValue().toString() );
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
		for( Map.Entry<X,Y> entry : map.entrySet() ){
			
			if( entry.getValue() == null ) empty.add( entry.getKey() );
		}
		for( X key : empty ){
			map.remove( key );
		}
		
		return map;
	}
	
	// Fast Sets
	
	public static <T> Set<T> immutableSet( T value ){
		return new SingleImmutableSet<>( value );
	}
	public static <T> Set<T> immutableSet( T value1, T value2 ){
		return new DoubleImmutableSet<>( value1, value2 );
	}
	public static <T> List<T> immutableList( T value ){
		return new SingleImmutableList<>( value );
	}
	public static <K,V> Map<K,V> immutableMap( K key, V value ){
		return new SingleImmutableMap<>( key, value );
	}
	
	public interface Converter<T,X> {
		public T convert( X value );
	}
	public interface KeyExtractor<K,O> {
		public K extract( O object );
	}
}
