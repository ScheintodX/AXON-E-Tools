package de.axone.tools;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class OA {
	
	// == String =====
	public static Object get( Object value, String name ){
		return value;
	}
	public static Object get( Object value, String name, String defaultValue ){
		Object v = get( value, name );
		return v != null ? v : defaultValue;
	}
	public static Object getRequired( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static Object getRequired( Object value, String name, String defaultValue ){
		Object v = get( value, name, defaultValue );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
		// == String =====
	public static String getString( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof String ) )
			throw new IllegalArgumentException( name + " is not a String" );
		else
			return (String) v;
	}
	public static String getString( Object value, String name, String defaultValue ){
		String v = getString( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static String getStringRequired( Object value, String name ){
		String v = getString( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static String getStringRequired( Object value, String name, String defaultValue ){
		String v = getString( value, name );
		if( v == null ) v=defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Integer =====
	public static Integer getInteger( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof Integer ) )
			throw new IllegalArgumentException( name + " is not an Integer" );
		else
			return (Integer) v;
	}
	public static int getInteger( Object value, String name, int defaultValue ){
		Integer v = getInteger( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static int getIntegerRequired( Object value, String name ){
		Integer v = getInteger( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static int getIntegerRequired( Object value, String name, Integer defaultValue ){
		Integer v = getInteger( value, name );
		if( v == null ) v=defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Long =====
	public static Long getLong( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof Long ) )
			throw new IllegalArgumentException( name + " is not a Long" );
		else
			return (Long) v;
	}
	public static long getLong( Object value, String name, long defaultValue ){
		Long v = getLong( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static long getLongRequired( Object value, String name ){
		Long v = getLong( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static long getLongRequired( Object value, String name, Long defaultValue){
		Long v = getLong( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Double =====
	public static Double getDouble( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof Double ) )
			throw new IllegalArgumentException( name + " is not a Double" );
		else
			return (Double) v;
	}
	public static double getDouble( Object value, String name, double defaultValue ){
		Double v = getDouble( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static double getDoubleRequired( Object value, String name ){
		Double v = getDouble( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static double getDoubleRequired( Object value, String name, Double defaultValue ){
		Double v = getDouble( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Boolean =====
	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL", justification="This is how this api works" )
	public static Boolean getBoolean( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof Boolean ) )
			throw new IllegalArgumentException( name + " is not a Boolean" );
		else
			return (Boolean) v;
	}
	public static boolean getBoolean( Object value, String name, boolean defaultValue ){
		Boolean v = getBoolean( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static boolean getBooleanRequired( Object value, String name ){
		Boolean v = getBoolean( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static boolean getBooleanRequired( Object value, String name, boolean defaultValue ){
		Boolean v = getBoolean( value, name );
		if( v == null ) v = defaultValue;
		return v;
	}
	
	// == BigDecimal =====
	public static BigDecimal getBigDecimal( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof BigDecimal ) )
			throw new IllegalArgumentException( name + " is not a BigDecimal" );
		else
			return (BigDecimal) v;
	}
	public static BigDecimal getBigDecimal( Object value, String name, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static BigDecimal getBigDecimalRequired( Object value, String name ){
		BigDecimal v = getBigDecimal( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static BigDecimal getBigDecimalRequired( Object value, String name, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( value, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == Enumeration =====
	public static <T extends Enum<T>> T getEnum( Object value, Class<T> clazz, String name ){
		String v = getString( value, name );
		if( v == null ) return null;
		return Enum.valueOf( clazz, v );
	}
	public static <T extends Enum<T>> T getEnum( Object value, Class<T> clazz, String name, T defaultValue ){
		T v = getEnum( value, clazz, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static <T extends Enum<T>> T getEnumRequired( Object value, Class<T> clazz, String name ){
		T v = getEnum( value, clazz, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static <T extends Enum<T>> T getEnumRequired( Object value, Class<T> clazz, String name, T defaultValue ){
		T v = getEnum( value, clazz, name );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	
	// == List =====
	public static List<?> getList( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof List ) )
			throw new IllegalArgumentException( name + " is not a List" );
		else
			return (List<?>) v;
	}
	public static List<?> getList( Object value, String name, List<?> defaultValue ){
		List<?> v = getList( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static List<?> getListRequired( Object value, String name ){
		List<?> v = getList( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static List<?> getListRequired( Object value, String name, List<?> defaultValue ){
		List<?> v = getList( value, name );
		if( v == null ) v = defaultValue;
		return v;
	}
	
	// == Map =====
	@SuppressWarnings( "unchecked" )
	public static Map<String,?> getMap( Object value, String name ){
		Object v = get( value, name );
		if( v == null ) return null;
		if( ! ( v instanceof Map ) )
			throw new IllegalArgumentException( name + " is not a Map" );
		else
			return (Map<String,?>) v;
	}
	public static Map<String,?> getMap( Object value, String name, Map<String,?> defaultValue ){
		Map<String,?> v = getMap( value, name );
		if( v == null ) return defaultValue;
		return v;
	}
	public static Map<String,?> getMapRequired( Object value, String name ){
		Map<String,?> v = getMap( value, name );
		if( v == null ) throw new NoSuchElementException( name );
		return v;
	}
	public static Map<String,?> getMapRequired( Object value, String name, Map<String,?> defaultValue ){
		Map<String,?> v = getMap( value, name );
		if( v == null ) v = defaultValue;
		return v;
	}

}
