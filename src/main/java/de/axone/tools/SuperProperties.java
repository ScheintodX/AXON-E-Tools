package de.axone.tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import de.axone.exception.Assert;
import de.axone.exception.Ex;

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
 * <em>Prefixes</em>
 * <p>Prefixes are prepended to the properties key and are set at class
 * creation time. Subsets prepend additional prefixes.
 * E.g. <tt>new SuperProperties().supset( "one" ).subset( "two" )</tt>
 * will prefix all keys in the new class with <tt>"one.two."</tt>.
 * In following if key is used it will be allways prefixed.
 *
 * <em>Accessors</em>
 * <p> Accessors are in multiple versions:<ul>
 * <li><tt>getXyz( key : String )</tt> returns <tt>null</tt> if missing.
 * <li><tt>getXyz( key : String, default : String )</tt> returns <em>default</em> if missing.
 * <li><tt>getXyzRequired( key : String )</tt> throws <em>Exception</em> if missing.
 * </ul>
 *
 * <em>Class creation</em>
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
public class SuperProperties implements StringValueAccessor<String, NoSuchElementException> {

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
	
	@Override
	public NoSuchElementException exception( String key ) {
		return Ex.up( new NoSuchElementException( key ) );
	}
	
	@Override
	public String accessChecked( String key ) {
		return backend.getProperty( realKey( key ) );
	}
	@Override
	public String access( String key ) {
		String realKey = realKey( key );
		if( ! backend.containsKey( realKey ) ) throw exception( key );
		return backend.getProperty( realKey );
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

	public void set( String key, String value ) {
		backend.setProperty( realKey( key ), value );
	}
	public void set( String key, int value ) {
		backend.setProperty( realKey( key ), ""+value );
	}
	
	private File prependBaseDir( File baseDir, File file ){

		if( file == null ) return null;

		if( ! file.isAbsolute() && baseDir != null ){
			file = new File( baseDir, file.getPath() );
		}
		return file;
	}
	
	@Override
	public File getFile( String key ) {

		String filename = get( key );

		if( filename == null ) return null;

		return prependBaseDir( rootDir, new File( filename ) );
	}

	public File getFile( File basedir, String key ) {

		String filename = get( key );

		if( filename == null ) return null;

		File result = prependBaseDir( basedir, new File( filename ) );

		return prependBaseDir( rootDir, result );
	}

	public File getFile( String basedirKey, String key ) {

		String basedirFilename = get( basedirKey );

		if( basedirFilename == null ){
			return getFile( key );
		} else {
			return getFile( new File( basedirFilename), key );
		}
	}

	@Override
	public File getFileRequired( String key ) {

		File result = getFile( key );
		if( result == null ) throw new NoSuchElementException( key );
		return result;
	}

	public File getFileRequired( File basedir, String key ) {

		File result = getFile( basedir, key );
		if( result == null ) throw new NoSuchElementException( key );
		return result;
	}

	public File getFileRequired( String basedirKey, String key ) {

		File result = getFile( basedirKey, key );
		if( result == null ) throw new NoSuchElementException( key );
		return result;
	}


	public SuperProperties subset( String prefix ) {
		Assert.notNull( prefix, "prefix" );
		if( this.prefix == null ){
			return new SuperProperties( prefix, this.backend, this.rootDir );
		} else {
			return new SuperProperties( this.prefix + '.' + prefix, this.backend, this.rootDir );
		}
	}
	
	public List<String> getList( String key ) {

		LinkedList<String> result = new LinkedList<String>();
		String value = null;
		int i = 1;
		do {
			value = get( key + '.' + i );
			if( value != null ) result.addLast( value );
			i++;
		} while( value != null );

		if( result.size() > 0 ) return result;
		else return null;
	}


	@Override
	public String toString(){
		
		StringBuilder result = new StringBuilder();
		
		result	.append( "SuperProperties.\n    Prefix: " ).append( prefix )
				.append( "\n    baseDir: " ).append( rootDir != null ? rootDir.getPath() : "null" )
				.append( "\n    Data: " );
		;
		
		for( Map.Entry<Object,Object>entry : backend.entrySet() ){
			String keyS = (String) entry.getKey();
			if( prefix == null || keyS.startsWith( prefix ) ){
				result	.append( "\n        " ).append( keyS )
						.append( ": " ).append( entry.getValue() );
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
			result = prefix + '.' + key;
		}

		return result;
	}

}
