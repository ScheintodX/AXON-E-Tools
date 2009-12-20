package de.axone.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * A replacement and facade for <tt>Properties</tt>
 *
 * Supports not any method of properties but although additional
 * stuff like direcly getting a <tt>File</tt> or building subsets.
 *
 * This class is intentionally not subclassed from Properties
 * but a drop in replacement for most of the methods.
 *
 * TODO: Check EMogul for Method usage because there is much duplicate stuff.
 *
 * @author flo
 */
public class SuperProperties {

	private Properties backend;
	private String prefix;
	private File rootDir;

	public SuperProperties() {
		this( new Properties() );
	}

	public SuperProperties(Properties backend) {
		this( null, backend, null );
	}

	private SuperProperties(String prefix, Properties backend, File rootDir ) {
		this.backend = backend;
		this.prefix = prefix;
		this.rootDir = rootDir;
	}

	// BaseDir
	public void setRootDir( File rootDir ){
		this.rootDir = rootDir;
	}
	public void setRootDir( String rootDirKey ){
		this.rootDir = getFile( rootDirKey );
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

	public String getProperty( String key ) {

		return backend.getProperty( realKey( key ) );
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

		String className = getProperty( realKey( key ) );
		if( className == null )
			return null;
		ClassConfigurator confi = new ClassConfigurator();
		return confi.create( className );
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

		String fileName = getProperty( realKey( key ));

		return prependBaseDir( rootDir, new File( fileName ) );
	}

	public File getFile( File basedir, String key ) {

		String filename = getProperty( realKey( key ));

		if( filename == null ) return null;

		File result = prependBaseDir( basedir, new File( filename ) );

		return prependBaseDir( rootDir, result );
	}

	public File getFile( String basedirKey, String key ) {

		String basedirFilename = getProperty( realKey( basedirKey ) );

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
		if( ! result.isFile() ) throw new PropertyFileNotFoundException( key, result );
		return result;
	}

	public File getFileRequired( File basedir, String key ) throws PropertiesException {

		File result = getFile( key );
		if( result == null ) throw new PropertyNotFoundException( key );
		if( ! result.isFile() ) throw new PropertyFileNotFoundException( key, result );
		return result;
	}

	public File getFileRequired( String basedirKey, String key ) throws PropertiesException {

		File result = getFile( key );
		if( result == null ) throw new PropertyNotFoundException( key );
		if( ! result.isFile() ) throw new PropertyFileNotFoundException( key, result );
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
		return "SuperProperties. Prefix: " + prefix + " baseDir: " + (rootDir != null ? rootDir.getPath() : "null");
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
		private PropertiesException( String cause ){ super( cause ); }
	}

	private static class PropertyNotFoundException extends PropertiesException {
		private PropertyNotFoundException( String propertyName ){
			super( "Property not found: " + propertyName );
		}
	}

	private static class PropertyEmptyException extends PropertiesException {
		private PropertyEmptyException( String propertyName ){
			super( "Property is empty: " + propertyName );
		}
	}

	private static class PropertyConversationException extends PropertiesException {
		private enum Format {BOOL,INT,FLOAT,DOUBLE};
		private PropertyConversationException( String propertyName, String value, Format format ){
			super( "Cannot convert value of " + propertyName + " (" + value + ") to " + format );
		}
	}

	private static class PropertyFileNotFoundException extends PropertiesException {
		private PropertyFileNotFoundException( String propertyName, File file ){
			super( "File not found for " + propertyName + ": " + file.getAbsolutePath() );
		}
	}
}
