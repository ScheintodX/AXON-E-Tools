package de.axone.tools;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class SA {
	
	// == String =====
	public static String get( String value, String name ){
		if( value == null || value.trim().length() == 0 ) return null;
		return value;
	}
	public static String get( String value, String name, String defaultValue ){
		String v = get( value, name );
		return v != null ? v : defaultValue;
	}
	public static String getRequired( String value, String name ){
		String v = get( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static String getRequired( String value, String name, String defaultValue ){
		String v = get( value, name, defaultValue );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
		// == Integer =====
	public static Integer getInteger( String value, String name ){
		String v = get( value, name );
		if( v == null ) return null;
		return Integer.parseInt( v );
	}
	public static int getInteger( String value, String name, int defaultValue ){
		Integer v = getInteger( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static int getIntegerRequired( String value, String name ){
		Integer v = getInteger( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static int getIntegerRequired( String value, String name, Integer defaultValue ){
		Integer v = getInteger( value, name );
		if( v == null ) v=defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Long =====
	public static Long getLong( String value, String name ){
		String v = get( value, name );
		if( v == null ) return null;
		return Long.parseLong( v );
	}
	public static long getLong( String value, String name, long defaultValue ){
		Long v = getLong( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static long getLongRequired( String value, String name ){
		Long v = getLong( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static long getLongRequired( String value, String name, Long defaultValue){
		Long v = getLong( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Double =====
	public static Double getDouble( String value, String name ){
		String v = get( value, name );
		if( v == null ) return null;
		return Double.parseDouble( v );
	}
	public static double getDouble( String value, String name, double defaultValue ){
		Double v = getDouble( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static double getDoubleRequired( String value, String name ){
		Double v = getDouble( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static double getDoubleRequired( String value, String name, Double defaultValue ){
		Double v = getDouble( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Boolean =====
	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL", justification="Api" )
	public static Boolean getBoolean( String value, String name ){
		String v = get( value, name );
		if( v == null ) return null;
		return EasyParser.yesOrNoOrNull( v );
	}
	public static boolean getBoolean( String value, String name, boolean defaultValue ){
		Boolean v = getBoolean( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static boolean getBooleanRequired( String value, String name ){
		Boolean v = getBoolean( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static boolean getBooleanRequired( String value, String name, boolean defaultValue ){
		Boolean v = getBoolean( value, name );
		if( v == null ) v = defaultValue;
		return v;
	}
	
	// == BigDecimal =====
	public static BigDecimal getBigDecimal( String value, String name ){
		String v = get( value, name );
		if( v == null ) return null;
		return new BigDecimal( v );
	}
	public static BigDecimal getBigDecimal( String value, String name, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static BigDecimal getBigDecimalRequired( String value, String name ){
		BigDecimal v = getBigDecimal( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static BigDecimal getBigDecimalRequired( String value, String name, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Enumeration =====
	public static <T extends Enum<T>> T getEnum( String value, Class<T> clazz, String name ){
		String v = get( value, name );
		if( v == null ) return null;
		return Enum.valueOf( clazz, v );
	}
	public static <T extends Enum<T>> T getEnum( String value, Class<T> clazz, String name, T defaultValue ){
		T v = getEnum( value, clazz, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static <T extends Enum<T>> T getEnumRequired( String value, Class<T> clazz, String name ){
		T v = getEnum( value, clazz, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static <T extends Enum<T>> T getEnumRequired( String value, Class<T> clazz, String name, T defaultValue ){
		T v = getEnum( value, clazz, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
}
