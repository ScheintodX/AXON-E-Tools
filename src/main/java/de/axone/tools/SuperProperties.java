package de.axone.tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * A replacement and facade for <tt>Properties</tt>
 *
 * <p>Supports not any method of properties but although additional
 * stuff like direcly getting a <tt>File</tt> or building subsets.
 *
 * <p>This class is intentionally not subclassed from Properties
 * but a drop in replacement for most of the methods.
 *
 * <p>The purpose of this class is to make life more easy.
 *
 * <p>SuperProperties are backed by a normal <tt>Properties</tt> class
 * and encasulates access to its methods.
 *
 * Main features are: <ul>
 * <li>Usage of a prefix
 * <li>Subsets
 * <li>Multiple accessor methods
 * <li>Exceptions if needed
 * <li>File creation
 * <li>Class creation
 * <li>Lists
 * </ul>
 *
 * <h5>Prefixes</h5>
 * <p>Prefixes are prepended to the properties key and are set at class
 * creation time. Subsets prepend additional prefixes.
 * E.g. <tt>new SuperProperties().supset( "one" ).subset( "two" )</tt>
 * will prefix all keys in the new class with <tt>"one.two."</tt>.
 * In following if key is used it will be allways prefixed.
 *
 * <h5>Accessors</h5>
 * <p> Accessors are in multiple versions:<ul>
 * <li><tt>getXyz( key : String )</tt> returns <tt>null</tt> if missing.
 * <li><tt>getXyz( key : String, default : String )</tt> returns <em>default</em> if missing.
 * <li><tt>getXyzRequired( key : String )</tt> throws <em>Exception</em> if missing.
 * </ul>
 *
 * <h5>Class creation</h5>
 * <p>Classes can be directly created and eventually configured if they confirm
 * to the class syntax used by <tt>ClassConfigurator</tt><ul>
 * <li>Have empty contructor
 * <li>have setters (String) for configurable parameters
 * </ul>
 *
 * TODO: Check EMogul for Method usage because there is much duplicate stuff.
 *
 * @author flo
 */
public class SuperProperties {

	private final Properties backend;
	private final String prefix;
	private File rootDir;

	/**
	 * Create Super Properties with empty Properties
	 */
	public SuperProperties() {
		this( new Properties() );
	}

	/**
	 * Create Super Properties using backend as properties
	 *
	 * @param backend
	 */
	private SuperProperties(Properties backend) {
		this( null, backend, null );
	}

	/**
	 * Create Super properties using backend, prefix and root-dir
	 *
	 * @param prefix
	 * @param backend
	 * @param rootDir
	 */
	private SuperProperties(String prefix, Properties backend, File rootDir ) {
		this.backend = backend;
		this.prefix = prefix;
		this.rootDir = rootDir;
	}

	public String getPrefix(){
		return prefix;
	}
	public File getRootDir(){
		return rootDir;
	}
	public Properties getBackend(){
		return backend;
	}

	// BaseDir
	public void setRootDir( File rootDir ){
		this.rootDir = rootDir;
	}
	public void addRootDir( String rootDirKey ){
		this.rootDir = getFile( rootDirKey );
	}
	public void addRootDir( File addDir ){
		this.rootDir = new File( this.rootDir, addDir.getPath() );
	}

	// From Properties
	public void load( InputStream in ) throws IOException{
		backend.load( in );
	}
	public void load( Reader reader ) throws IOException {
		backend.load( reader );
	}
	public void store( OutputStream out ) throws IOException{
		backend.store( out, null );
	}
	public void store( Writer writer ) throws IOException {
		backend.store( writer, null );
	}
	public void store( File file ) throws IOException {
		try( FileWriter out = new FileWriter( file ) ){
			backend.store( out, null );
		}
	}
	public void load( File file ) throws IOException {
		try( FileReader in = new FileReader( file ) ){
			load( in );
		}
	}

	public String getProperty( String key ) {
		return backend.getProperty( realKey( key ) );
	}
	public void setProperty( String key, String value ) {
		backend.setProperty( realKey( key ), value );
	}
	public void setProperty( String key, int value ) {
		backend.setProperty( realKey( key ), ""+value );
	}
	public String getProperty( String key, String defaultValue ) {
		return backend.getProperty( realKey( key ), defaultValue );
	}
	public String getPropertyRequired( String key ) throws PropertiesException {
		String result = getProperty( key );
		if( result == null ) throw new PropertyNotFoundException( key );
		if( result.length() == 0 ) throw new PropertyEmptyException( key );
		return result;
	}

	public Object instantiate( String key ) throws SecurityException,
			IllegalArgumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {

		String className = getProperty( key );
		if( className == null ) return null;
		return ClassConfigurator.create( className );
	}

	public Object instantiateRequired( String key ) throws SecurityException,
			IllegalArgumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			PropertyInstantiationException {

		Object result = instantiate( key );
		if( result == null )
			throw new PropertyInstantiationException( key, getProperty( key ) );
		return result;
	}

	public List<String> getList( String key ) {

		LinkedList<String> result = new LinkedList<String>();
		String value = null;
		int i = 1;
		do {
			value = getProperty( key + "." + i );
			if( value != null ) result.addLast( value );
			i++;
		} while( value != null );

		if( result.size() > 0 ) return result;
		else return null;
	}

	public File getFile( String key ) {

		String filename = getProperty( key );

		if( filename == null ) return null;

		return prependBaseDir( rootDir, new File( filename ) );
	}

	public File getFile( File basedir, String key ) {

		String filename = getProperty( key );

		if( filename == null ) return null;

		File result = prependBaseDir( basedir, new File( filename ) );

		return prependBaseDir( rootDir, result );
	}

	public File getFile( String basedirKey, String key ) {

		String basedirFilename = getProperty( basedirKey );

		if( basedirFilename == null ){
			return getFile( key );
		} else {
			return getFile( new File( basedirFilename), key );
		}
	}

	private File prependBaseDir( File baseDir, File file ){

		if( file == null ) return null;

		if( ! file.isAbsolute() && baseDir != null ){
			file = new File( baseDir, file.getPath() );
		}
		return file;
	}

	public File getFileRequired( String key ) throws PropertiesException {

		File result = getFile( key );
		if( result == null ) throw new PropertyNotFoundException( key );
		return result;
	}

	public File getFileRequired( File basedir, String key ) throws PropertiesException {

		File result = getFile( basedir, key );
		if( result == null ) throw new PropertyNotFoundException( key );
		return result;
	}

	public File getFileRequired( String basedirKey, String key ) throws PropertiesException {

		File result = getFile( basedirKey, key );
		if( result == null ) throw new PropertyNotFoundException( key );
		return result;
	}

	public Integer getInt( String key ){

		String value = getProperty( key );
		if( value == null ) return null;

		return Integer.valueOf( value );
	}

	public int getInt( String key, int defaultValue ){

		Integer value = getInt( key );

		if( value == null ) return defaultValue;

		return value;
	}

	public int getIntRequired( String key ) throws PropertiesException {

		String value = getPropertyRequired( key );
		int result;
		try {
			result = Integer.parseInt( value );
		} catch( NumberFormatException e ){
			throw new PropertyConversationException( key, value, PropertyConversationException.Format.INT );
		}

		return result;
	}

	public Long getLong( String key ){

		String value = getProperty( key );
		if( value == null ) return null;

		return Long.valueOf( value );
	}

	public long getLong( String key, int defaultValue ){

		Long value = getLong( key );

		if( value == null ) return defaultValue;

		return value;
	}

	public long getLongRequired( String key ) throws PropertiesException {

		String value = getPropertyRequired( key );
		long result;
		try {
			result = Long.parseLong( value );
		} catch( NumberFormatException e ){
			throw new PropertyConversationException( key, value, PropertyConversationException.Format.INT );
		}

		return result;
	}

	public Double getDouble( String key ){

		String value = getProperty( key );
		if( value == null ) return null;

		return Double.valueOf( value );
	}

	public double getDouble( String key, double defaultValue ){

		Double value = getDouble( key );

		if( value == null ) return defaultValue;

		return value;
	}

	public double getDoubleRequired( String key ) throws PropertiesException {

		String value = getPropertyRequired( key );
		double result;
		try {
			result = Double.parseDouble( value );
		} catch( NumberFormatException e ){
			throw new PropertyConversationException( key, value, PropertyConversationException.Format.DOUBLE );
		}

		return result;
	}

	@SuppressFBWarnings( value="NP_BOOLEAN_RETURN_NULL", justification="Api" )
	public Boolean getBoolean( String key ){

		String value = getProperty( key );
		if( value == null ) return null;

		if( EasyParser.isYes( value ) ) return true;
		if( EasyParser.isNo( value ) ) return false;
		return null;
	}

	public boolean getBoolean( String key, boolean defaultValue ){

		String value = getProperty( key );
		if( value == null ) return defaultValue;

		if( EasyParser.isYes( value ) ) return true;
		if( EasyParser.isNo( value ) ) return false;
		return defaultValue;
	}

	public boolean getBooleanRequired( String key ) throws PropertiesException {

		String value = getPropertyRequired( key );

		if( EasyParser.isYes( value ) ) return true;
		if( EasyParser.isNo( value ) ) return false;

		throw new PropertyConversationException( key, value, PropertyConversationException.Format.BOOL );
	}

	@Deprecated
	public SuperProperties getSubset( String prefix ){
		return new SuperProperties( prefix, this.backend, this.rootDir );
	}

	public SuperProperties subset( String prefix ) {
		if( this.prefix == null ){
			return new SuperProperties( prefix, this.backend, this.rootDir );
		} else {
			return new SuperProperties( this.prefix + "." + prefix, this.backend, this.rootDir );
		}
	}
	/*
	public SuperProperties getSuperset(){
		return new SuperProperties( null, this.backend );
	}
	*/

	@Override
	public String toString(){
		
		StringBuilder result = new StringBuilder();
		
		result	.append( "SuperProperties.\n    Prefix: " ).append( prefix )
				.append( "\n    baseDir: " ).append( rootDir != null ? rootDir.getPath() : "null" )
				.append( "\n    Data: " );
		;
		
		for( Object key : backend.keySet() ){
			String keyS = (String) key;
			if( keyS.startsWith( prefix ) ){
				result	.append( "\n        " ).append( keyS )
						.append( ": " ).append( backend.get( keyS ) );
			}
		}
		
		return result.toString();
	}

	/* *************** private *************** */

	private String realKey( String key ) {

		String result;

		if( prefix == null ){
			result = key;
		} else {
			result = prefix + "." + key;
		}

		return result;
	}

	/* *************** exceptions *************** */

	public static class PropertiesException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1399195266886800097L;

		private PropertiesException( String cause ){ super( cause ); }
	}

	public static class PropertyNotFoundException extends PropertiesException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7014470488286327371L;

		private PropertyNotFoundException( String propertyName ){
			super( "Property not found: " + propertyName );
		}
	}

	public static class PropertyEmptyException extends PropertiesException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8619269284604950197L;

		private PropertyEmptyException( String propertyName ){
			super( "Property is empty: " + propertyName );
		}
	}

	public static class PropertyConversationException extends PropertiesException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 30336261348480483L;
		private enum Format {BOOL,INT,FLOAT,DOUBLE};
		private PropertyConversationException( String propertyName, String value, Format format ){
			super( "Cannot convert value of " + propertyName + " (" + value + ") to " + format );
		}
	}

	public static class PropertyInstantiationException extends PropertiesException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7399765717886263434L;

		private PropertyInstantiationException( String propertyName, String value ){
			super( "Cannot instantiate " + propertyName + ": '" + value + "'" );
		}
	}

	/*
	private static class PropertyFileNotFoundException extends PropertiesException {
		private PropertyFileNotFoundException( String propertyName, File file ){
			super( "File not found for " + propertyName + ": " + file.getAbsolutePath() );
		}
	}
	*/
}
