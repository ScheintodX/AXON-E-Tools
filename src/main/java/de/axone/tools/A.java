package de.axone.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	// Generic
	@SafeVarargs
	public static <X> X[] Array( X ... values ){ return values; }
	
	public static <X> X[] Array( Collection<X> values, Class<X> clazz ){
		
		if( values == null || values.size() == 0 )
			throw new IllegalArgumentException( "values is null or empty" );
		
		@SuppressWarnings( "unchecked" )
		//X[] result = (X[])Array.newInstance( values.iterator().next().getClass(), values.size() );
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
	
	
}
