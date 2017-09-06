package de.axone.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.axone.test.TestClass;

@TestClass( "ATest" )
public class A {

	// Boolean
	public static boolean[] arrayBool( Collection<Boolean> values ){
		if( values == null || values.size() == 0 ) return new boolean[0];
		boolean [] result = new boolean[ values.size() ];
		int i=0; for( Boolean b : values ){
			result[i++] = b;
		}
		return result;
	}
	public static boolean[] array( boolean ... values ){ return values; }
	public static Boolean[] objects( boolean ... values ){
		Boolean [] result = new Boolean[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Boolean> list( boolean ... values ){
		return Arrays.asList( objects( values ) ); }
	public static Set<Boolean> set( boolean ... values ){
		return new HashSet<Boolean>( list( values ) );
	}
	public static int indexOf( int start, boolean needle, boolean ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, boolean needle, boolean ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( boolean needle, boolean ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( boolean needle, boolean ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Byte
	public static byte[] array( byte ... values ){ return values; }
	public static Byte[] objects( byte ... values ){
		Byte [] result = new Byte[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Byte> list( byte ... values ){
		return Arrays.asList( objects( values ) );
	}
	public static Set<Byte> set( byte ... values ){
		return new HashSet<Byte>( list( values ) );
	}
	public static int indexOf( int start, byte needle, byte ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, byte needle, byte ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( byte needle, byte ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( byte needle, byte ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	
	// Character
	public static char[] array( char ... values ){ return values; }
	public static Character[] objects( char ... values ){
		Character [] result = new Character[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Character> list( char ... ints ){
		return Arrays.asList( objects( ints ) );
	}
	public static Set<Character> set( char ... values ){
		return new HashSet<Character>( list( values ) );
	}
	public static int indexOf( int start, char needle, char ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, char needle, char ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( char needle, char ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( char needle, char ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Short
	public static short[] array( short ... values ){ return values; }
	public static Short[] objects( short ... values ){
		Short [] result = new Short[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Short> list( short ... ints ){
		return Arrays.asList( objects( ints ) );
	}
	public static Set<Short> set( short ... values ){
		return new HashSet<Short>( list( values ) );
	}
	public static int indexOf( int start, short needle, short ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, short needle, short ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( short needle, short ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( short needle, short ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Integer
	public static int[] arrayInt( Collection<Integer> values ){
		if( values == null || values.size() == 0 ) return new int[0];
		int [] result = new int[ values.size() ];
		int i=0; for( Integer b : values ){
			result[i++] = b;
		}
		return result;
	}
	public static int[] array( int ... values ){ return values; }
	public static Integer[] objects( int ... values ){
		Integer [] result = new Integer[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Integer> list( int ... ints ){
		return Arrays.asList( objects( ints ) );
	}
	public static Set<Integer> set( int ... values ){
		return new HashSet<Integer>( list( values ) );
	}
	public static int indexOf( int start, int needle, int ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, int needle, int ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( int needle, int ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( int needle, int ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Long
	public static long[] array( long ... values ){ return values; }
	public static Long[] objects( long ... values ){
		Long [] result = new Long[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Long> list( long ... values ){
		return Arrays.asList( objects( values ) );
	}
	public static Set<Long> set( long ... values ){
		return new HashSet<Long>( list( values ) );
	}
	public static int indexOf( int start, long needle, long ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, long needle, long ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( long needle, long ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( long needle, long ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Float
	public static float[] array( float ... values ){ return values; }
	public static Float[] objects( float ... values ){
		Float [] result = new Float[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Float> list( float ... values ){
		return Arrays.asList( objects( values ) );
	}
	public static Set<Float> set( float ... values ){
		return new HashSet<Float>( list( values ) );
	}
	
	/**
	 * Note that this compares floats via == which you should only use if you
	 * *exactly* know what you are doing.
	 * 
	 * @param start 
	 * @param needle
	 * @param haystack
	 * @return first index of needle in haystack
	 */
	public static int indexOf( int start, float needle, float ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, float needle, float ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( float needle, float ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( float needle, float ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Double
	public static double[] array( double ... values ){ return values; }
	public static Double[] objects( double ... values ){
		Double [] result = new Double[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Double> list( double ... values ){
		return Arrays.asList( objects( values ) );
	}
	public static Set<Double> set( double ... values ){
		return new HashSet<Double>( list( values ) );
	}
	
	/**
	 * Note that this compares floats via == which you should only use if you
	 * *exactly* know what you are doing.
	 * 
	 * @param start 
	 * @param needle
	 * @param haystack
	 * @return first index of needle in haystack
	 */
	public static int indexOf( int start, double needle, double ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int lastIndexOf( int start, double needle, double ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	public static int indexOf( double needle, double ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	public static int lastIndexOf( double needle, double ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	// Generic
	@SafeVarargs
	public static <X> X[] Array( X ... values ){ return values; }
	
	// TODO: This is a common case. Make it more comfortable and quicker.
	public static String [] Array( Collection<String> values ){
		
		return values.toArray( new String[ values.size() ] );
	}
	
	public static <X> X[] Array( Collection<X> values, Class<X> clazz ){
		
		if( values == null || values.size() == 0 )
			throw new IllegalArgumentException( "values is null or empty" );
		
		@SuppressWarnings( "unchecked" )
		X[] result = (X[])Array.newInstance( clazz, values.size() );
		
		return values.toArray( result );
	}
	
	public static <X> X[][] Array2D( Class<X> xClass, Collection<? extends Collection<X>> values ){
		
		@SuppressWarnings( "unchecked" )
		X[][] result = (X[][])Array.newInstance( xClass , values.size(), 0 );
		
		int i=0;
		for( Collection<X> row : values ){
			
			@SuppressWarnings( "unchecked" )
			X[] rowAsArray = (X[])Array.newInstance( xClass, row.size() );
			result[i] = row.toArray( rowAsArray );
			i++;
		}
		return result;
	}
	
	public static <X> List<List<X>> List2D( X[][] values ){
		
		List<List<X>> result = new ArrayList<>( values.length );
		
			for( X [] row : values ){
				result.add( Arrays.asList( row ) );
			}
		
		return result;
	}
	
	@SafeVarargs
	public static <X> List<X> List( X ... values ){
		return Arrays.asList( values );
	}
	@SafeVarargs
	public static <X> Set<X> set( X ... values ){
		return new HashSet<X>( List( values ) );
	}
	
	// Object
	public static Object[] objectArray( Object ... values ){ return values; }
	
	public static List<Object> objectList( Object ... values ){
		return Arrays.asList( values );
	}
	public static Set<Object> objectSet( Object ... values ){
		return new HashSet<Object>( objectList( values ) );
	}
	
	@SafeVarargs
	public static <T> int indexOf( int start, T needle, T ... haystack ) {
		for( int c = start; c < haystack.length; c++ ) {
			if( needle.equals( haystack[ c ] ) ) return c;
		}
		return -1;
	}
	@SafeVarargs
	public static <T> int lastIndexOf( int start, T needle, T ... haystack ) {
		for( int c = start; c >= 0; c-- ) {
			if( haystack[ c ] == needle ) return c;
		}
		return -1;
	}
	@SafeVarargs
	public static <T> int indexOf( T needle, T ... haystack ) {
		return indexOf( 0, needle, haystack );
	}
	@SafeVarargs
	public static <T> int lastIndexOf( T needle, T ... haystack ) {
		return lastIndexOf( haystack.length-1, needle, haystack );
	}
	
	/**
	 * @param arr 
	 * @return array with leading trailing null removed
	 */
	public static byte [] trim( byte [] arr ) {
		
		if( arr == null ) return null;
		if( arr.length == 0 ) return arr;
		
		int start=0,
		    end = arr.length -1
		    ;
		
		if( arr[ start ] != 0 && arr[ end ] != 0 ) return arr;
		
		while( start < end && arr[ start ] == 0 ) start++;
		while( end >= start && arr[ end ] == 0 ) end--;
		
		int len = end-start+1;
		
		byte [] result = new byte[ len ];
		System.arraycopy( arr, start, result, 0, len );
		
		return result;
		
	}
	
	public static char [] union( char [] a, char ... b ){
		
		char [] result = new char[ a.length + b.length ];
		
		System.arraycopy( a, 0, result, 0, a.length );
		System.arraycopy( b, 0, result, a.length, b.length );
		
		return result;
	}

	public static String [] union( String [] a, String ... b ){
		
		String [] result = new String[ a.length + b.length ];
		
		System.arraycopy( a, 0, result, 0, a.length );
		System.arraycopy( b, 0, result, a.length, b.length );
		
		return result;
	}
	public static String [] union( String a, String ... b ){
		
		String [] result = new String[ 1 + b.length ];
		
		result[ 0 ] = a;
		System.arraycopy( b, 0, result, 1, b.length );
		
		return result;
	}
	
	public static Object [] unionO( Object [] a, Object ... b ){
		
		Object [] result = new Object[ a.length + b.length ];
		
		System.arraycopy( a, 0, result, 0, a.length );
		System.arraycopy( b, 0, result, a.length, b.length );
		
		return result;
	}
	
	public static Object [] unionO( Object a, Object ... b ){
		
		Object [] result = new Object[ 1 + b.length ];
		
		result[ 0 ] = a;
		System.arraycopy( b, 0, result, 1, b.length );
		
		return result;
	}
	
	
	
}
