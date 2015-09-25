package de.axone.tools;

import java.io.File;
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
	public default int getInteger( String key, IntegerValueProvider defaultValueProvider ){
		
		Integer v = getInteger( key );
		if( v == null ) return defaultValueProvider.get();
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
	public default int getIntegerRequired( String key, IntegerValueProvider defaultValueProvider ){
		Integer v = getInteger( key );
		if( v == null ) v=defaultValueProvider.get();
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	@FunctionalInterface
	public interface IntegerValueProvider {
		public Integer get();
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
	public default long getLong( String key, LongValueProvider defaultValueProvider ){
		Long v = getLong( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default long getLongRequired( String key ){
		Long v = getLong( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default long getLongRequired( String key, Long defaultValue ){
		Long v = getLong( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default long getLongRequired( String key, LongValueProvider defaultValueProvider ){
		Long v = getLong( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	@FunctionalInterface
	public interface LongValueProvider {
		public Long get();
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
	public default double getDouble( String key, DoubleValueProvider defaultValueProvider ){
		Double v = getDouble( key );
		if( v == null ) return defaultValueProvider.get();
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
	public default double getDoubleRequired( String key, DoubleValueProvider defaultValueProvider ){
		Double v = getDouble( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	@FunctionalInterface
	public interface DoubleValueProvider {
		public Double get();
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
	public default boolean getBoolean( String key, BooleanValueProvider defaultValueProvider ){
		Boolean v = getBoolean( key );
		if( v == null ) return defaultValueProvider.get();
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
	public default boolean getBooleanRequired( String key, BooleanValueProvider defaultValueProvider ){
		Boolean v = getBoolean( key );
		if( v == null ) v = defaultValueProvider.get();
		return v;
	}
	
	@FunctionalInterface
	public interface BooleanValueProvider {
		public Boolean get();
	}
	
	// == Files ==
	public default File getFile( String key ){
		String v = get( key );
		if( v == null ) return null;
		return new File( v );
	}
	public default File getFile( String key, File defaultValue ) {
		File v = getFile( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default File getFile( String key, FileValueProvider defaultValueProvider ) {
		File v = getFile( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default File getFileRequired( String key ) {
		File v = getFile( key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default File getFileRequired( String key, File defaultValue ) {
		File v = getFile( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default File getFileRequired( String key, FileValueProvider defaultValueProvider ) {
		File v = getFile( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	public default File absolute( File file, File optionalBasedir ) {
		if( file == null ) return null;
		if( file.isAbsolute() ) return file;
		else {
			if( optionalBasedir == null ) return file;
			return new File( optionalBasedir, file.getPath() );
		}
	}
	public default File absolute( File file, String optionalBasedirKey ) {
		if( file == null ) return null;
		if( file.isAbsolute() ) return file;
		else {
			File basedir = getFileRequired( optionalBasedirKey );
			if( basedir == null ) return file;
			return new File( basedir, file.getPath() );
		}
	}
	public default File absolute( File file, FileValueProvider optionalBasedirProvider ) {
		if( file == null ) return null;
		if( file.isAbsolute() ) return file;
		else {
			File basedir =  optionalBasedirProvider.get();
			if( basedir == null ) return file;
			return new File( basedir, file.getPath() );
		}
	}
	
	public default File absoluteRequired( File file, String optionalBasedirKey ) {
		File v = absolute( file, optionalBasedirKey );
		if( v == null ) return null;
		if( v.isAbsolute() ) return v;
		else throw new IllegalArgumentException( "Resulting file '" + file.getPath() + "' is not absolute" );
	}
	
	public default File absoluteRequired( File file, FileValueProvider optionalBasedirProvider ) {
		File v = absolute( file, optionalBasedirProvider );
		if( v == null ) return null;
		if( v.isAbsolute() ) return v;
		else throw new IllegalArgumentException( "Resulting file '" + file.getPath() + "' is not absolute" );
	}
	
	@FunctionalInterface
	public interface FileValueProvider {
		public File get();
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
	public default BigDecimal getBigDecimal( String key, BigDecimalValueProvider defaultValueProvider ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) return defaultValueProvider.get();
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
	public default BigDecimal getBigDecimalRequired( String key, BigDecimalValueProvider defaultValueProvider ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	@FunctionalInterface
	public interface BigDecimalValueProvider {
		public BigDecimal get();
	}
	
	// == Enumeration =====
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, String key ){
		String v = get( key );
		if( v == null ) return null;
		if( v.length() == 0 ) return null;
		return Enum.valueOf( clazz, v );
	}
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, String key, T defaultValue ){
		T v = getEnum( clazz, key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, String key, EnumValueProvider<T> defaultValueProvider ){
		T v = getEnum( clazz, key );
		if( v == null ) return defaultValueProvider.get();
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
	public default <T extends Enum<T>> T getEnumRequired( Class<T> clazz, String key, EnumValueProvider<T> defaultValueProvider ){
		T v = getEnum( clazz, key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	@FunctionalInterface
	public interface EnumValueProvider<T extends Enum<T>> {
		public T get();
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
	public default <T, T1 extends T, T2 extends T> T getNewInstance( Class<T1> clazz, String key, ObjectValueProvider<T2> defaultValueProvider ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key );
		Class<T2> defaultValue = defaultValueProvider.get();
		if( v == null ) return defaultValue != null ? defaultValue.newInstance() : null;
		return v;
	}
	public default <T, T1 extends T> T getNewInstanceRequired( Class<T1> clazz, String key ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key );
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstanceRequired( Class<T1> clazz, String key, ObjectValueProvider<T2> defaultValueProvider ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key );
		Class<T2> defaultValue = defaultValueProvider.get();
		if( v == null ) v = defaultValue != null ? defaultValue.newInstance() : null;
		if( v == null ) throw new NoSuchElementException( key );
		return v;
	}
	
	@FunctionalInterface
	public interface ObjectValueProvider<T> {
		public Class<T> get();
	}
}