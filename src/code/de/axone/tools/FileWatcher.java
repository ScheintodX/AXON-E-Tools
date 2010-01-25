package de.axone.tools;

import java.io.File;
import java.util.HashMap;

import de.axone.logging.Log;
import de.axone.logging.Logging;

/**
 * Watches one file and can report if it has changed.
 * To minimize io this check is only done in a given
 * interval.
 *
 * @author flo
 */
public class FileWatcher {

	private static Log log = Logging.getLog( FileWatcher.class );

	private static final double TIMEOUT = 2000; //3 s

	private File file;

	private double lastModifiedTime = -1;
	private double lastCheckTime = -1;

	private double timeout;

	/**
	 * Create a FileWatcher for one file with the given
	 * timeout.
	 *
	 * @param file to watch
	 * @param which has to pass until a new check is done
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

	public boolean hasChanged(){

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
    			
    			log.info( String.format( "File %s has changed (%d<%d)", file.getAbsolutePath(), (int)(lastModifiedTime/1000), (int)(modifiedTime/1000) ) );

    			result = true;

    			lastModifiedTime = modifiedTime;
    		}
		}

		return result;
	}

	public File getFile(){
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

}
