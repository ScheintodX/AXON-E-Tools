package de.axone.tools;

import java.io.File;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public interface StringValueAccessor<K,EX extends Exception> extends KeyValueAccessor<K,String,EX> {
	
	// == Integer =====
	public default Integer getInteger( K key ) {
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return Integer.parseInt( v );
	}
	public default int getInteger( K key, int defaultValue ){
		Integer v = getInteger( key );
		if( v == null ) return defaultValue;
		return (int)v;
	}
	public default Integer getInteger( K key, Integer defaultValue ){
		Integer v = getInteger( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default Integer getInteger( K key, ValueProvider<Integer> defaultValueProvider ){
		
		Integer v = getInteger( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default int getIntegerRequired( K key ) throws EX {
		Integer v = getInteger( key );
		if( v == null ) throw exception( key );
		return (int)v;
	}
	public default int getIntegerRequired( K key, Integer defaultValue ) throws EX {
		Integer v = getInteger( key );
		if( v == null ) v=defaultValue;
		if( v == null ) throw exception( key );
		return (int)v;
	}
	public default int getIntegerRequired( K key, ValueProvider<Integer> defaultValueProvider ) throws EX {
		Integer v = getInteger( key );
		if( v == null ) v=defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return (int)v;
	}
	
	
	// == Long =====
	public default Long getLong( K key ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return Long.parseLong( v );
	}
	public default long getLong( K key, long defaultValue ){
		Long v = getLong( key );
		if( v == null ) return defaultValue;
		return (long)v;
	}
	public default Long getLong( K key, Long defaultValue ){
		Long v = getLong( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default Long getLong( K key, ValueProvider<Long> defaultValueProvider ){
		Long v = getLong( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default long getLongRequired( K key ) throws EX {
		Long v = getLong( key );
		if( v == null ) throw exception( key );
		return (long)v;
	}
	public default long getLongRequired( K key, Long defaultValue ) throws EX {
		Long v = getLong( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return (long)v;
	}
	public default long getLongRequired( K key, ValueProvider<Long> defaultValueProvider ) throws EX {
		Long v = getLong( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return (long)v;
	}
	
	
	// == Double =====
	public default Double getDouble( K key ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return Double.parseDouble( v );
	}
	public default double getDouble( K key, double defaultValue ){
		Double v = getDouble( key );
		if( v == null ) return defaultValue;
		return (double)v;
	}
	public default Double getDouble( K key, Double defaultValue ){
		Double v = getDouble( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default Double getDouble( K key, ValueProvider<Double> defaultValueProvider ){
		Double v = getDouble( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default double getDoubleRequired( K key ) throws EX {
		Double v = getDouble( key );
		if( v == null ) throw exception( key );
		return (double)v;
	}
	public default double getDoubleRequired( K key, Double defaultValue ) throws EX {
		Double v = getDouble( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return (double)v;
	}
	public default double getDoubleRequired( K key, ValueProvider<Double> defaultValueProvider ) throws EX {
		Double v = getDouble( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return (double)v;
	}
	
	
	// == Boolean =====
	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL", justification="Api" )
	public default Boolean getBoolean( K key ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return EasyParser.yesOrNoOrNull( v );
	}
	public default boolean getBoolean( K key, boolean defaultValue ){
		Boolean v = getBoolean( key );
		if( v == null ) return defaultValue;
		return (boolean)v;
	}
	public default Boolean getBoolean( K key, Boolean defaultValue ){
		Boolean v = getBoolean( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default Boolean getBoolean( K key, ValueProvider<Boolean> defaultValueProvider ){
		Boolean v = getBoolean( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default boolean getBooleanRequired( K key ) throws EX {
		Boolean v = getBoolean( key );
		if( v == null ) throw exception( key );
		return (boolean)v;
	}
	public default boolean getBooleanRequired( K key, Boolean defaultValue ) throws EX {
		Boolean v = getBoolean( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return (boolean)v;
	}
	public default boolean getBooleanRequired( K key, ValueProvider<Boolean> defaultValueProvider ) throws EX {
		Boolean v = getBoolean( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return (boolean)v;
	}
	
	
	// == Files ==
	public default File getFile( K key ) {
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return new File( v );
	}
	public default File getFile( K key, File defaultValue ) {
		File v = getFile( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default File getFile( K key, ValueProvider<File> defaultValueProvider ) {
		File v = getFile( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default File getFileRequired( K key ) throws EX {
		File v = getFile( key );
		if( v == null ) throw exception( key );
		return v;
	}
	public default File getFileRequired( K key, File defaultValue ) throws EX {
		File v = getFile( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return v;
	}
	public default File getFileRequired( K key, ValueProvider<File> defaultValueProvider ) throws EX {
		File v = getFile( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
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
	public default File absolute( File file, K optionalBasedirKey ) throws EX {
		if( file == null ) return null;
		if( file.isAbsolute() ) return file;
		else {
			File basedir = getFileRequired( optionalBasedirKey );
			if( basedir == null ) return file;
			return new File( basedir, file.getPath() );
		}
	}
	public default File absolute( File file, ValueProvider<File> optionalBasedirProvider ) {
		if( file == null ) return null;
		if( file.isAbsolute() ) return file;
		else {
			File basedir =  optionalBasedirProvider.get();
			if( basedir == null ) return file;
			return new File( basedir, file.getPath() );
		}
	}
	
	public default File absoluteRequired( File file, K optionalBasedirKey ) throws EX {
		File v = absolute( file, optionalBasedirKey );
		if( v == null ) return null;
		if( v.isAbsolute() ) return v;
		else throw new IllegalArgumentException( "Resulting file '" + file.getPath() + "' is not absolute" );
	}
	
	public default File absoluteRequired( File file, ValueProvider<File> optionalBasedirProvider ) throws EX {
		File v = absolute( file, optionalBasedirProvider );
		if( v == null ) return null;
		if( v.isAbsolute() ) return v;
		else throw new IllegalArgumentException( "Resulting file '" + file.getPath() + "' is not absolute" );
	}
	
	
	
	// == Path ==
	public default Path getPath( K key ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return Paths.get( v );
	}
	public default Path getPath( K key, Path defaultValue ) {
		Path v = getPath( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default Path getPath( K key, ValueProvider<Path> defaultValueProvider ) {
		Path v = getPath( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default Path getPathRequired( K key ) throws EX {
		Path v = getPath( key );
		if( v == null ) throw exception( key );
		return v;
	}
	public default Path getPathRequired( K key, Path defaultValue ) throws EX {
		Path v = getPath( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return v;
	}
	public default Path getPathRequired( K key, ValueProvider<Path> defaultValueProvider ) throws EX {
		Path v = getPath( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return v;
	}
	
	public default Path absolute( Path path, Path optionalBasedir ) {
		if( path == null ) return null;
		if( path.isAbsolute() ) return path;
		else {
			if( optionalBasedir == null ) return path;
			return optionalBasedir.resolve( path );
		}
	}
	public default Path absolute( Path path, K optionalBasedirKey ) {
		if( path == null ) return null;
		if( path.isAbsolute() ) return path;
		else {
			Path basedir = getPath( optionalBasedirKey );
			if( basedir == null ) return path;
			return basedir.resolve( path );
		}
	}
	public default Path absolute( Path path, ValueProvider<Path> optionalBasedirProvider ) {
		if( path == null ) return null;
		if( path.isAbsolute() ) return path;
		else {
			Path basedir =  optionalBasedirProvider.get();
			if( basedir == null ) return path;
			return basedir.resolve( path );
		}
	}
	
	public default Path absoluteRequired( Path path, K optionalBasedirKey ) {
		Path v = absolute( path, optionalBasedirKey );
		if( v == null ) return null;
		if( v.isAbsolute() ) return v;
		else throw new IllegalArgumentException( "Resulting path '" + path.toString() + "' is not absolute" );
	}
	
	public default Path absoluteRequired( Path path, ValueProvider<Path> optionalBasedirProvider ) {
		Path v = absolute( path, optionalBasedirProvider );
		if( v == null ) return null;
		if( v.isAbsolute() ) return v;
		else throw new IllegalArgumentException( "Resulting path '" + path.toString() + "' is not absolute" );
	}
	
	
	
	// == BigDecimal =====
	public default BigDecimal getBigDecimal( K key ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return new BigDecimal( v );
	}
	public default BigDecimal getBigDecimal( K key, MathContext mox ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return new BigDecimal( v, mox );
	}
	public default BigDecimal getBigDecimal( K key, BigDecimal defaultValue ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default BigDecimal getBigDecimal( K key, BigDecimal defaultValue, MathContext mox ){
		BigDecimal v = getBigDecimal( key, mox );
		if( v == null ) return defaultValue;
		return v;
	}
	public default BigDecimal getBigDecimal( K key, ValueProvider<BigDecimal> defaultValueProvider ){
		BigDecimal v = getBigDecimal( key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default BigDecimal getBigDecimal( K key, ValueProvider<BigDecimal> defaultValueProvider, MathContext mox ){
		BigDecimal v = getBigDecimal( key, mox );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default BigDecimal getBigDecimalRequired( K key ) throws EX {
		BigDecimal v = getBigDecimal( key );
		if( v == null ) throw exception( key );
		return v;
	}
	public default BigDecimal getBigDecimalRequired( K key, MathContext mox ) throws EX {
		BigDecimal v = getBigDecimal( key, mox );
		if( v == null ) throw exception( key );
		return v;
	}
	public default BigDecimal getBigDecimalRequired( K key, BigDecimal defaultValue ) throws EX {
		BigDecimal v = getBigDecimal( key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return v;
	}
	public default BigDecimal getBigDecimalRequired( K key, BigDecimal defaultValue, MathContext mox ) throws EX {
		BigDecimal v = getBigDecimal( key, mox );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return v;
	}
	public default BigDecimal getBigDecimalRequired( K key, ValueProvider<BigDecimal> defaultValueProvider ) throws EX {
		BigDecimal v = getBigDecimal( key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return v;
	}
	public default BigDecimal getBigDecimalRequired( K key, ValueProvider<BigDecimal> defaultValueProvider, MathContext mox ) throws EX {
		BigDecimal v = getBigDecimal( key, mox );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return v;
	}
	
	
	// == Enumeration =====
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, K key ){
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		return Enum.valueOf( clazz, v.toUpperCase() );
	}
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, K key, T defaultValue ){
		T v = getEnum( clazz, key );
		if( v == null ) return defaultValue;
		return v;
	}
	public default <T extends Enum<T>> T getEnum( Class<T> clazz, K key, ValueProvider<T> defaultValueProvider ){
		T v = getEnum( clazz, key );
		if( v == null ) return defaultValueProvider.get();
		return v;
	}
	public default <T extends Enum<T>> T getEnumRequired( Class<T> clazz, K key ) throws EX {
		T v = getEnum( clazz, key );
		if( v == null ) throw exception( key );
		return v;
	}
	public default <T extends Enum<T>> T getEnumRequired( Class<T> clazz, K key, T defaultValue ) throws EX {
		T v = getEnum( clazz, key );
		if( v == null ) v = defaultValue;
		if( v == null ) throw exception( key );
		return v;
	}
	public default <T extends Enum<T>> T getEnumRequired( Class<T> clazz, K key, ValueProvider<T> defaultValueProvider ) throws EX {
		T v = getEnum( clazz, key );
		if( v == null ) v = defaultValueProvider.get();
		if( v == null ) throw exception( key );
		return v;
	}
	
	
	// == Object instantiation =====
	public default <T, T1 extends T> T getNewInstance( Class<T1> clazz, K key, Object ... constructorParameters ) throws ReflectiveOperationException {
		String v = get( key );
		if( v == null ) return null;
		v = v.trim();
		if( v.length() == 0 ) return null;
		@SuppressWarnings( "unchecked" )
		Class<T> vClass = (Class<T>)Class.forName( v );
		int clen = constructorParameters.length;
		if( clen > 0 ) {
			Class<?> constructorTypes[] = new Class[ clen ];
			for( int i=0;i<clen; i++) constructorTypes[ i ] = constructorParameters[ i ].getClass();
			Constructor<T> constr = vClass.getDeclaredConstructor( constructorTypes );
			return constr.newInstance( constructorParameters );
		} else {
			return vClass.newInstance();
		}
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstance( Class<T1> clazz, K key, Class<T2> defaultValue, Object ... constructorParameters ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key, constructorParameters );
		if( v == null ) return defaultValue != null ? defaultValue.newInstance() : null;
		return v;
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstance( Class<T1> clazz, K key, ValueProvider<Class<T2>> defaultValueProvider, Object ... constructorParameters ) throws ReflectiveOperationException {
		T v = getNewInstance( clazz, key, constructorParameters );
		Class<T2> defaultValue = defaultValueProvider.get();
		if( v == null ) return defaultValue != null ? defaultValue.newInstance() : null;
		return v;
	}
	public default <T, T1 extends T> T getNewInstanceRequired( Class<T1> clazz, K key, Object ... constructorParameters ) throws EX, ReflectiveOperationException {
		T v = getNewInstance( clazz, key, constructorParameters );
		if( v == null ) throw exception( key );
		return v;
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstanceRequired( Class<T1> clazz, K key, ValueProvider<Class<T2>> defaultValueProvider, Object ... constructorParameters ) throws EX, ReflectiveOperationException {
		T v = getNewInstance( clazz, key, constructorParameters );
		Class<T2> defaultValue = defaultValueProvider.get();
		if( v == null ) v = defaultValue != null ? defaultValue.newInstance() : null;
		if( v == null ) throw exception( key );
		return v;
	}
	
	
	@SuppressWarnings( "unchecked" )
	public default <T, T1 extends T> Instantiator<? extends T> getInstantiator(
			Class<T> clazz, K key, Class<T1> defaultValue, Class<?> ... parameterTypes )
			throws ReflectiveOperationException {
		
		String v = get( key );
		
		Class<? extends T> vClass = v != null ? (Class<T>) Class.forName( v ) : defaultValue;
		
		return Instantiator.forClass( vClass );
	}
	
	// == Object instantiation =====
	public default <T, T1 extends T> T getNewInstanceUC( Class<T1> clazz, K key ) {
		try {
			return getNewInstance( clazz, key );
		} catch( ReflectiveOperationException e ) {
			throw new InstantiationError( key, e );
		}
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstanceUC( Class<T1> clazz, K key, Class<T2> defaultValue ) {
		try {
			return getNewInstance( clazz, key, defaultValue );
		} catch( ReflectiveOperationException e ) {
			throw new InstantiationError( key, e );
		}
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstanceUC( Class<T1> clazz, K key, ValueProvider<Class<T2>> defaultValueProvider ) {
		try {
			return getNewInstance( clazz, key, defaultValueProvider );
		} catch( ReflectiveOperationException e ) {
			throw new InstantiationError( key, e );
		}
	}
	public default <T, T1 extends T> T getNewInstanceRequiredUC( Class<T1> clazz, K key ) throws EX {
		try {
			return getNewInstanceRequired( clazz, key );
		} catch( ReflectiveOperationException e ) {
			throw new InstantiationError( key, e );
		}
	}
	public default <T, T1 extends T, T2 extends T> T getNewInstanceRequiredUC( Class<T1> clazz, K key, ValueProvider<Class<T2>> defaultValueProvider ) throws EX {
		try {
			return getNewInstanceRequired( clazz, key, defaultValueProvider );
		} catch( ReflectiveOperationException e ) {
			throw new InstantiationError( key, e );
		}
	}
	
	
	public static final class InstantiationError extends Error {
		public InstantiationError( Object key, Throwable cause ) {
			super( key.toString(), cause );
		}
	}
	
}