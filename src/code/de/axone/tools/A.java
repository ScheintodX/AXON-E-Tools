package de.axone.tools;

import java.util.Arrays;
import java.util.List;

public class A {

	// Boolean
	public static Boolean[] toObjects( boolean ... values ){
		Boolean [] result = new Boolean[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Boolean> toList( boolean ... values ){
		return Arrays.asList( toObjects( values ) );
	}
	
	// Character
	public static Character[] toObjects( char ... values ){
		Character [] result = new Character[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public List<Character> toList( char ... ints ){
		return Arrays.asList( toObjects( ints ) );
	}
	
	// Short
	public static Short[] toObjects( short ... values ){
		Short [] result = new Short[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Short> toList( short ... ints ){
		return Arrays.asList( toObjects( ints ) );
	}
	
	// Integer
	public static Integer[] toObjects( int ... values ){
		Integer [] result = new Integer[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Integer> toList( int ... ints ){
		return Arrays.asList( toObjects( ints ) );
	}
	
	// Long
	public static Long[] toObjects( long ... values ){
		Long [] result = new Long[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Long> toList( long ... values ){
		return Arrays.asList( toObjects( values ) );
	}
	
	// Float
	public static Float[] toObjects( float ... values ){
		Float [] result = new Float[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Float> toList( float ... values ){
		return Arrays.asList( toObjects( values ) );
	}
	
	// Double
	public static Double[] toObjects( double ... values ){
		Double [] result = new Double[ values.length ];
		for( int i=0; i<values.length; i++ ) result[ i ] = values[ i ];
		return result;
	}
	public static List<Double> toList( double ... values ){
		return Arrays.asList( toObjects( values ) );
	}
	
}
