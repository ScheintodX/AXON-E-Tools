package de.axone.exception;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

public abstract class Assert {

	// != NULL --------------------
	public static void notNull( Object o, String name ){
		if( o == null ) throw Ex.up( new ArgumentNullException( name ) );
	}
	// gt 0
	public static void gt0( Number number, String name ){
		if( number == null ) return;
		if( number.doubleValue() <= 0 ) throw Ex.up( new ArgumentNullException( name ) );
		
	}
	// eq 1
	public static void eq1( Number number, String name ){
		if( number == null ) return;
		if( number.longValue() != 1 ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is not 1" ) );
		
	}
	
	// Not null or empty
	public static void notNullOrEmpty( Object o, String name ){
		if( o == null || o.toString().length() == 0 ) throw Ex.up( new ArgumentNullException( name ) );
	}
	
	// ! Empty String
	public static void notEmpty( Object o, String name ){
		if( o == null ) return;
		if( o.toString().length() == 0 ) throw Ex.up( new ArgumentNullException( name ) );
	}
	
	// ! Empty Collection
	public static void notEmpty( Collection<?> o, String name ){
		if( o == null ) return;
		if( o.size() == 0 ) throw Ex.up( new ArgumentNullException( name ) );
	}
	
	// ! Empty Map
	public static void notEmpty( Map<?,?> o, String name ){
		if( o == null ) return;
		if( o.size() == 0 ) throw Ex.up( new ArgumentNullException( name ) );
	}
	
	// == TRUE --------------------
	public static void isTrue( Boolean o, String name ){
		if( o == null ) return;
		if( o == false ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is 'false'" ) );
	}
	
	// == FALSE --------------------
	public static void isFalse( Boolean o, String name ){
		if( o == null ) return;
		if( o == true ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is 'true'" ) );
	}
	
	// Range --------------------
	public static void inRange( Number number, String name, long lowerBound, long upperBound ){
		if( number == null ) return;
		long n = number.longValue();
		if( n < lowerBound ) throw Ex.up( new ArgumentRangeException( name, "< " + lowerBound, n ) );
		if( n > upperBound ) throw Ex.up( new ArgumentRangeException( name, "> " + upperBound, n ) );
	}
	
	// Length lt ----------------
	public static void lengthLTE( CharSequence text, String name, int length ){
		if( text == null ) return;
		int l = text.length();
		if( l > length ) throw Ex.up( new ArgumentRangeException( name, " length >" + length, l ) );
	}
	
	// Length gt ----------------
	public static void lengthGTE( CharSequence text, String name, int length ){
		if( text == null ) return;
		int l = text.length();
		if( l < length ) throw Ex.up( new ArgumentRangeException( name, " length <" + length, l ) );
	}
	
	// Length eq ----------------
	public static void lengthEQ( CharSequence text, String name, int length ){
		if( text == null ) return;
		int l = text.length();
		if( l != length ) throw Ex.up( new ArgumentRangeException( name, " length ==" + length, l ) );
	}
	
	// Size of countable objects (not Strings!) ----
	// Collections
	public static void size( Collection<?> object, String name, int size ){
		isSize( object != null ? object.size() : null, name, size );
	}
	// Arrays
	public static void size( boolean [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static void size( char [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static void size( short [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static void size( int [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static void size( long [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static void size( float [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static void size( double [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	public static <T>void size( T [] object, String name, int size ){
		isSize( object != null ? object.length : null, name, size );
	}
	private static void isSize( Integer isSize, String name, int size ){
		if( isSize == null ) return;
		if( isSize != size ) throw Ex.up( new ArgumentRangeException( name, " size ==" + size, isSize ), 2 );
	}
	
	// Contains ----------------
	public static void contains( String text, String name, String part ){
		if( text == null ) return;
		if( ! text.contains( part ) ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' doesn't contain '" + part + "'" ) );
	}
	
	// Not Contains ----------------
	public static void containsNot( String text, String name, String part ){
		if( text == null ) return;
		if( text.contains( part ) ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' contains '" + part + "'" ) );
	}
	
	// >= --------------------
	public static void gte( Number number, String name, long bound ){
		if( number == null ) return;
		long n = number.longValue();
		if( n < bound ) throw Ex.up( new ArgumentRangeException( name, ">=" + bound, n ) );
	}
	// <= --------------------
	public static void lte( Number number, String name, long bound ){
		if( number == null ) return;
		long n = number.longValue();
		if( n > bound ) throw Ex.up( new ArgumentRangeException( name, "<=" + bound, n ) );
	}
	
	// > --------------------
	public static void gt( Number number, String name, long bound ){
		if( number == null ) return;
		long n = number.longValue();
		if( n <= bound ) throw Ex.up( new ArgumentRangeException( name, ">" + bound, n ) );
	}
	// < --------------------
	public static void lt( Number number, String name, long bound ){
		if( number == null ) return;
		long n = number.longValue();
		if( n >= bound ) throw Ex.up( new ArgumentRangeException( name, "<" + bound, n ) );
	}
	// eq --------------------
	public static void equal( Object o1, String name, Object o2 ){
		if( o1 == null ) return;
		if( ! o1.equals( o2 ) ) throw Ex.up( new ArgumentRangeException( name, "eq" + o2, o1 ) );
	}
	// compareTo equal -------
	public static <T>void compareEQ( Comparable<T> o1, String name, T o2 ){
		if( o1 == null ) return;
		if( o1.compareTo( o2 ) != 0 ) throw Ex.up( new ArgumentRangeException( name, "compare to" + o2, o1 ) );
	}
	public static <T>void compareEQ( T o1, String name, T o2, Comparator<T> comparator ){
		if( o1 == null ) return;
		if( comparator.compare( o1, o2 ) != 0 ) throw Ex.up( new ArgumentRangeException( name, "compare to" + o2, o1 ) );
	}
	// == --------------------
	public static void ident( Object o1, String name, Object o2 ){
		if( o1 == null ) return;
		if( o1 != o2 ) throw Ex.up( new ArgumentRangeException( name, "==" + o2, o1 ) );
	}
	// instance --------------------
	public static void isInstance( Object o, String name, Class<?> clz ){
		if( o == null ) return;
		if( ! o.getClass().isInstance( clz ) ) throw Ex.up( new ArgumentInstanceException( name, clz ) );
	}
	// class --------------------
	public static void isClass( Object o, String name, Class<?> clz ){
		if( o == null ) return;
		if( ! o.getClass().isInstance( clz ) ) throw Ex.up( new ArgumentClassException( name, clz ) );
	}
	
}
