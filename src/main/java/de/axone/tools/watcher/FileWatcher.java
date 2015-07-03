package de.axone.tools.watcher;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Watches one file and can report if it has changed.
 * To minimize io this check is only done in a given
 * interval.
 * 
 * @author flo
 */
public class FileWatcher implements Watcher<File>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final Logger log =
			LoggerFactory.getLogger( FileWatcher.class );

	private static final double TIMEOUT = 2000; //2 s

	private File file;

	private double lastModifiedTime = -1;
	private double lastCheckTime = -1;

	private double timeout;

	/**
	 * Create a FileWatcher for one file with the given
	 * timeout.
	 *
	 * @param file to watch
	 * @param timeout which has to pass until a new check is done
	 */
	public FileWatcher( File file, double timeout ){

		this.file = file;
		this.timeout = timeout;
	}

	/**
	 * Create a Filewatcher with a default timeout of 2s
	 *
	 * @param file
	 */
	public FileWatcher( File file ){

		this( file, TIMEOUT );
	}

	@Override
	public boolean haveChanged(){

		boolean result = false;

		double time = System.currentTimeMillis();
		
		double div = time - lastCheckTime;
		
		// See if timeout has passed to do a recheck
		if( div >= timeout ){
			
			lastCheckTime = time;

			if( ! file.exists() ){

    			log.warn( "File " + file.getAbsolutePath() + " does not exist"  );
				return true;
			}

    		double modifiedTime = file.lastModified();
    		
    		if( lastModifiedTime < modifiedTime ){
    			
    			if( log.isDebugEnabled() ) log.debug(
    					String.format( "File %s has changed (%d<%d)",
    							file.getAbsolutePath(),
    							(int)(lastModifiedTime/1000),
    							(int)(modifiedTime/1000) )
    			);

    			result = true;

    			lastModifiedTime = modifiedTime;
    		}
		}

		return result;
	}

	@Override
	public File getWatched(){
		return file;
	}

	@Override
	public String toString(){

		return
			"FileWatcher for: " + file.getAbsolutePath() +
			" timeout: " + timeout +
			" Last modified: " + lastModifiedTime +
			" Last check: + " + lastCheckTime;
	}

	/* STATIC METHODS for persistent Watchers */
	private static HashMap<String, FileWatcher> staticWatchers = new HashMap<String, FileWatcher>();
	public synchronized static FileWatcher staticWatcher( File file, double timeout ){

		String key = file.getAbsolutePath() + "/" + timeout;

		if( ! staticWatchers.containsKey( key ) ){
			staticWatchers.put( key, new FileWatcher( file, timeout ) );
		}
		return staticWatchers.get( key );
	}
	public static FileWatcher staticWatcher( File file ){
		return staticWatcher( file, TIMEOUT );
	}

	/**
	 * HashCode and equals work only on the unterlying file making FileWatcher
	 * useable as Map key for files
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( file == null ) ? 0 : file.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( !( obj instanceof FileWatcher ) )
			return false;
		FileWatcher other = (FileWatcher) obj;
		if( file == null ) {
			if( other.file != null )
				return false;
		} else if( !file.equals( other.file ) )
			return false;
		return true;
	}

}
