package de.axone.exception;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

public abstract class Assert {

	// != NULL --------------------
	public static<T> T notNull( T o, String name ){
		if( o == null ) throw Ex.up( new ArgumentNullException( name ) );
		return o;
	}
	public static void isNull( Object o, String name ){
		if( o != null ) throw Ex.up( new ArgumentNullException( name ) );
	}
	public static void gt0( Number number, String name ){
		if( number == null ) return;
		if( number.doubleValue() <= 0 ) throw Ex.up( new ArgumentNullException( name ) );
	}
	public static void eq1( Number number, String name ){
		if( number == null ) return;
		if( number.longValue() != 1 ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is not 1" ) );
	}
	// != ZERO --------------------
	public static void zero( Number number, String name ){
		if( number == null ) return;
		if( Double.compare( number.doubleValue(), 0 ) != 0 )
				throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is not zero" ) );
	}
	public static void notZero( Number number, String name ){
		if( number == null ) return;
		if( Double.compare( number.doubleValue(), 0 ) == 0 )
				throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is zero" ) );
	}
	public static void notNullNorZero( Number number, String name ) {
		if( number == null ) throw Ex.up( new ArgumentNullException( name ) );
		if( Double.compare( number.doubleValue(), 0 ) == 0 )
				throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is zero" ) );
	}
	public static void allNull( String name, Object ... os ) {
		for( Object o : os ) {
			if( o != null ) throw Ex.up( new ArgumentNullException( name ) );
		}
	}
	public static void noneNull( String name, Object ... os ) {
		for( Object o : os ) {
			if( o == null ) throw Ex.up( new ArgumentNullException( name ) );
		}
	}
	public static void anyNotNull( String name, Object ... os ) {
		for( Object o : os ) {
			if( o != null ) return;
		}
		throw Ex.up( new ArgumentNullException( name ) );
	}

	// Not null or empty
	public static void notNullOrEmpty( Object o, String name ){
		if( o == null || o.toString().length() == 0 ) throw Ex.up( new ArgumentNullException( name ) );
	}

	// ! Empty String
	public static void notEmpty( Object o, String name ){
		if( o == null ) return;
		if( o.toString().length() == 0 ) throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' is empty" ) );
	}

	public static <T extends Object> void notEmpty( T [] o, String name ){
		if( o == null ) return;
		if( o.length == 0 ) throw Ex.up( new ArgumentNullException( name ) );
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

	public static void lengthEQ( String [] value, String name, int length ){
		if( value == null ) return;
		int l = value.length;
		if( l != length ) throw Ex.up( new ArgumentRangeException( name, " length ==" + length, l ) );
	}

	public static <T> void lengthEQ( T [] value, String name, int length ){
		if( value == null ) return;
		int l = value.length;
		if( l != length ) throw Ex.up( new ArgumentRangeException( name, " length ==" + length, l ) );
	}

	public static void lengthEQ( Collection<?> value, String name, int length ){
		if( value == null ) return;
		int l = value.size();
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

	public static <T> T one( Collection<T> values, String name ){
		isSize( values.size(), name, 1 );
		return values.iterator().next();
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
	// zero or one
	public static void zor1( Number number, String name ){
		if( number == null ) return;
		long n = number.longValue();
		if( n < 0 || n > 1 ) throw Ex.up( new ArgumentRangeException( name, "!=(0,1)", n ) );
	}
	public static void one( Number number, String name ){
		if( number == null ) return;
		long n = number.longValue();
		if( n != 1 ) throw Ex.up( new ArgumentRangeException( name, "!=1", n ) );
	}
	// eq --------------------
	public static void equal( Object o1, String name, Object o2 ){
		if( o1 == null ) return;
		if( ! o1.equals( o2 ) ) throw Ex.up( new ArgumentRangeException( name, "eq " + o2, o1 ) );
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
	// string stuff
	public static void startsWith( String o, String name, String startsWith ) {
		if( o == null ) return;
		if( ! o.startsWith( startsWith ) )
				throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' needs to start with: '" + startsWith + "'" ) );
	}
	public static void startsWithIgnoreCase( String o, String name, String startsWith ) {
		if( o == null ) return;
		if( ! o.toLowerCase().startsWith( startsWith.toLowerCase() ) )
				throw Ex.up( new IllegalArgumentException( "Argument '" + name + "' needs to start with: '" + startsWith + "'" ) );
	}
	// == --------------------
	public static void ident( Object o1, String name, Object o2 ){
		if( o1 == null ) return;
		if( o1 != o2 ) throw Ex.up( new ArgumentRangeException( name, "==" + o2, o1 ) );
	}
	// instance --------------------
	@SuppressWarnings( "unchecked" )
	public static <T, X extends T> X isInstance( Object o, String name, Class<T> clz ){
		if( o == null ) return null;
		if( ! clz.isInstance( o ) ) throw Ex.up( new ArgumentInstanceException( name, clz ) );
		return (X)o;
	}
	public static <T> T canCast( Object o, String name, Class<T> clz ) {
		if( o == null ) return null;
		try {
			return clz.cast( o );
		} catch( ClassCastException e ) {
			throw Ex.up( new IllegalArgumentException( name + ": Cannot cast " + o.getClass().getSimpleName() + " to " + clz.getSimpleName() ) );
		}
	}
	public static void notClass( Object o, String name, Class<?> clz ) {

		if( o == null ) return;
		if( o.getClass() == clz ) throw Ex.up( new ArgumentInstanceException( name, clz ) );
	}
	// class --------------------
	@SuppressWarnings( "unchecked" )
	public static <T> T isClass( Object o, String name, Class<?> clz ){
		if( o == null ) return null;
		if( ! ( o.getClass() == clz || o.getClass().isInstance( clz ) ) ) throw Ex.up( new ArgumentClassException( name, clz, o.getClass() ) );
		return (T)o;
	}
	@SuppressWarnings( "unchecked" )
	public static <T> T isExactClass( Object o, String name, Class<?> clz ){
		if( o == null ) return null;
		if( o.getClass() != clz ) throw Ex.up( new ArgumentClassException( name, clz ) );
		return (T)o;
	}

	// composite rules

	// has some content
	public static void existsWithContent( Iterable<?> list, String name ){
		notNull( list, name );
		isTrue( list.iterator().hasNext(), name + " content" );
	}


	// Advanced stuff
	public static void canRead( File file, String name ) {
		if( file == null ) return;
		if( ! file.canRead() )
				throw new IllegalArgumentException( "Cannot read '" + name + "' / '" + file.getAbsolutePath() + "'" );
	}

	// Enum stuff
	@SafeVarargs
	public static <T extends Enum<T>> boolean isOneOf( T o, String name, T ... oneOf ) {

		for( T x : oneOf ) if( o == x ) return true;
		return false;
	}
}
