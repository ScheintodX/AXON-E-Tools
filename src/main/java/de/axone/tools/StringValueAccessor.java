package de.axone.tools;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public interface StringValueAccessor extends KeyValueAccessor<String,String> {
	
	// == String =====
	@Override
	public String access( String key );
	
	// == Integer =====
	public default Integer getInteger( String key ){
		String v = get( key );
		if( v == null ) return null;
		return Integer.parseInt( v );
	}
	public default int getInteger( String key, int defaultValue ){
		Integer v = getInteger( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default int getIntegerRequired( String key ){
		Integer v = getInteger( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default int getIntegerRequired( String key, Integer defaultValue ){
		Integer v = getInteger( key );
		if( v == null ) v=defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	// == Long =====
	public default Long getLong( String key ){
		String v = get( key );
		if( v == null ) return null;
		return Long.parseLong( v );
	}
	public default long getLong( String key, long defaultValue ){
		Long v = getLong( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default long getLongRequired( String key ){
		Long v = getLong( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default long getLongRequired( String key, Long defaultValue){
		Long v = getLong( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	// == Double =====
	public default Double getDouble( String key ){
		String v = get( key );
		if( v == null ) return null;
		return Double.parseDouble( v );
	}
	public default double getDouble( String key, double defaultValue ){
		Double v = getDouble( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default double getDoubleRequired( String key ){
		Double v = getDouble( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default double getDoubleRequired( String key, Double defaultValue ){
		Double v = getDouble( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	// == Boolean =====
	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL", justification="Api" )
	public default Boolean getBoolean( String key ){
		String v = get( key );
		if( v == null ) return null;
		return EasyParser.yesOrNoOrNull( v );
	}
	public default boolean getBoolean( String key, boolean defaultValue ){
		Boolean v = getBoolean( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default boolean getBooleanRequired( String key ){
		Boolean v = getBoolean( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default boolean getBooleanRequired( String key, boolean defaultValue ){
		Boolean v = getBoolean( key );
		if( v == null ) v = defaultValue;
		return v;
	}
	
	// == BigDecimal =====
	public default BigDecimal getBigDecimal( String key ){
		String v = get( key );
		if( v == null ) return null;
		return new BigDecimal( v );
	}
	public default BigDecimal getBigDecimal( String key, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default BigDecimal getBigDecimalRequired( String key ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default BigDecimal getBigDecimalRequired( String key, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	// == Enumeration =====
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, String key ){
		String v = get( key );
		if( v == null ) return null;
		return Enum.valueOf( clazz, v );
	}
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, String key, T defaultValue ){
		T v = getEnum( clazz, key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default <T extends Enum<T>> T getEnumRequired( Class<T> clazz, String key ){
		T v = getEnum( clazz, key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default <T extends Enum<T>> T getEnumRequired( Class<T> clazz, String key, T defaultValue ){
		T v = getEnum( clazz, key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	
	// == Object instantiation =====
	public default <T, T1 extends T> T getNewInstance( Class<T1> clazz, String key ) throws ReflectiveOperationException {
		String v = get( key );
		if( v == null ) return null;
		@SuppressWarnings( "unchecked" )
		Class<T> vClass = (Class<T>)Class.forName( v );
		return vClass.newInstance();
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstance( Class<T1> clazz, String key, Class<T2> defaultValue ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key );
		if( v == null ) return defaultValue != null ? defaultValue.newInstance() : null;
		return v;
	}
	public default <T, T1 extends T> T getNewInstanceRequired( Class<T1> clazz, String key ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstanceRequired( Class<T1> clazz, String key, Class<T2> defaultValue ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key );
		if( v == null ) v = defaultValue != null ? defaultValue.newInstance() : null;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
}