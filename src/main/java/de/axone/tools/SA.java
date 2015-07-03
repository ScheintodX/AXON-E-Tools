package de.axone.tools;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class SA {
	
	// == String =====
	public static String get( String value ){
		if( value == null || value.trim().length() == 0 ) return null;
		return value;
	}
	public static String getDefault( String value, String defaultValue ){
		String v = get( value );
		return v != null ? v : defaultValue;
	}
	public static String getRequired( String value, String name ){
		String v = get( value );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static String getRequired( String value, String name, String defaultValue ){
		String v = getDefault( value, defaultValue );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
		// == Integer =====
	public static Integer getInteger( String value ){
		String v = get( value );
		if( v == null ) return null;
		return Integer.parseInt( v );
	}
	public static int getInteger( String value, int defaultValue ){
		Integer v = getInteger( value );
		if( v == null ) return defaultValue;
		return v;
	}
	public static int getIntegerRequired( String value, String name ){
		Integer v = getInteger( value );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static int getIntegerRequired( String value, String name, Integer defaultValue ){
		Integer v = getInteger( value );
		if( v == null ) v=defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Long =====
	public static Long getLong( String value ){
		String v = get( value );
		if( v == null ) return null;
		return Long.parseLong( v );
	}
	public static long getLong( String value, long defaultValue ){
		Long v = getLong( value );
		if( v == null ) return defaultValue;
		return v;
	}
	public static long getLongRequired( String value, String name ){
		Long v = getLong( value );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static long getLongRequired( String value, String name, Long defaultValue){
		Long v = getLong( value );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Double =====
	public static Double getDouble( String value ){
		String v = get( value );
		if( v == null ) return null;
		return Double.parseDouble( v );
	}
	public static double getDouble( String value, double defaultValue ){
		Double v = getDouble( value );
		if( v == null ) return defaultValue;
		return v;
	}
	public static double getDoubleRequired( String value, String name ){
		Double v = getDouble( value );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static double getDoubleRequired( String value, String name, Double defaultValue ){
		Double v = getDouble( value );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Boolean =====
	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL", justification="Api" )
	public static Boolean getBoolean( String value ){
		String v = get( value );
		if( v == null ) return null;
		return EasyParser.yesOrNoOrNull( v );
	}
	public static boolean getBoolean( String value, boolean defaultValue ){
		Boolean v = getBoolean( value );
		if( v == null ) return defaultValue;
		return v;
	}
	public static boolean getBooleanRequired( String value, String name ){
		Boolean v = getBoolean( value );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static boolean getBooleanRequired( String value, String name, boolean defaultValue ){
		Boolean v = getBoolean( value );
		if( v == null ) v = defaultValue;
		return v;
	}
	
	// == BigDecimal =====
	public static BigDecimal getBigDecimal( String value ){
		String v = get( value );
		if( v == null ) return null;
		return new BigDecimal( v );
	}
	public static BigDecimal getBigDecimal( String value, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( value );
		if( v == null ) return defaultValue;
		return v;
	}
	public static BigDecimal getBigDecimalRequired( String value, String name ){
		BigDecimal v = getBigDecimal( value );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static BigDecimal getBigDecimalRequired( String value, String name, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( value );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Enumeration =====
	public static <T extends Enum<T>> T getEnum( String value, Class<T> clazz ){
		String v = get( value );
		if( v == null ) return null;
		return Enum.valueOf( clazz, v );
	}
	public static <T extends Enum<T>> T getEnum( String value, Class<T> clazz, T defaultValue ){
		T v = getEnum( value, clazz );
		if( v == null ) return defaultValue;
		return v;
	}
	public static <T extends Enum<T>> T getEnumRequired( String value, Class<T> clazz, String name ){
		T v = getEnum( value, clazz );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static <T extends Enum<T>> T getEnumRequired( String value, Class<T> clazz, String name, T defaultValue ){
		T v = getEnum( value, clazz );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Instances =====
	/*
	public static <T> T getNewInstance( String value, Class<T> clazz, String name ) throws InstantiationException, IllegalAccessException{
		String v = get( value, name );
		if( v == null ) return null;
		return clazz.newInstance();
	}
	public static <T> T getNewInstance( String value, Class<T> clazz, String name, Class<T> defaultValue ) throws InstantiationException, IllegalAccessException{
		T v = getNewInstance( value, clazz, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static <T> T getNewInstanceRequired( String value, Class<T> clazz, String name ) throws InstantiationException, IllegalAccessException{
		T v = getNewInstance( value, clazz, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static <T> T getNewInstanceRequired( String value, Class<T> clazz, String name, T defaultValue ) throws InstantiationException, IllegalAccessException{
		T v = getNewInstance( value, clazz, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	*/
	
}
