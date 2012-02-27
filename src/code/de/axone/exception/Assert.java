package de.axone.exception;


public abstract class Assert {

	// != NULL --------------------
	public static void notNull( Object o, String name ){
		if( o == null ) throw Ex.up( new ArgumentNullException( name ) );
	}
	
	// ! Empty String
	public static void notEmpty( Object o, String name ){
		if( o == null ) return;
		if( o.toString().length() == 0 ) throw Ex.up( new ArgumentNullException( name ) );
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
		if( n < lowerBound ) throw Ex.up( new ArgumentRangeException( name, "<=" + lowerBound, n ) );
		if( n > upperBound ) throw Ex.up( new ArgumentRangeException( name, ">=" + upperBound, n ) );
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
		if( ! o1.equals( o2 ) ) throw Ex.up( new ArgumentRangeException( name, "eq " + o2, o1 ) );
	}
	// == --------------------
	public static void ident( Object o1, String name, Object o2 ){
		if( o1 == null ) return;
		if( o1 != o2 ) throw Ex.up( new ArgumentRangeException( name, "== " + o2, o1 ) );
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
