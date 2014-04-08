package de.axone.tools;

import java.util.NoSuchElementException;

public abstract class LA {
	
	// == Integer =====
	public static Integer getInteger( Long value, String name ){
		Long v = getLong( value, name );
		if( v == null ) return null;
		if( v.longValue() > Integer.MAX_VALUE || v.longValue() < Integer.MIN_VALUE )
			throw new IllegalArgumentException( "The value exceeds the Integer range" );
		return (int)v.longValue();
	}
	public static int getInteger( Long value, String name, int defaultValue ){
		Integer v = getInteger( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static int getIntegerRequired( Long value, String name ){
		Integer v = getInteger( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static int getIntegerRequired( Long value, String name, Integer defaultValue ){
		Integer v = getInteger( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Long =====
	public static Long getLong( Long value, String name ){
		return value;
	}
	public static long getLong( Long value, String name, long defaultValue ){
		Long v = getLong( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static long getLongRequired( Long value, String name ){
		Long v = getLong( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static long getLongRequired( Long value, String name, Long defaultValue ){
		Long v = getLong( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	
}
