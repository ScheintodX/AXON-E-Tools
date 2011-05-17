package de.axone.tools;

import java.io.File;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.tools.Str.Joiner;

/**
 * Watches a set of Files for a change to any of them
 * 
 * Note that this watcher can't handle file deletions directly.
 * Deleted files are reported on any consequent call so you will
 * need a new watcher for removed files.
 * 
 * This is although true for added files of cause.
 * 
 * @author flo
 */
public class FileSetWatcher implements Watcher {

	public static final Logger log =
			LoggerFactory.getLogger( FileSetWatcher.class );

	private static final double TIMEOUT = 2000; //2 s

	private File [] files;

	private double lastModifiedTime = -1;
	private double lastCheckTime = -1;

	private double timeout;

	/**
	 * Create a FileSetWatcher for one file with the given
	 * timeout.
	 *
	 * @param files to watch
	 * @param timeout which has to pass until a new check is done
	 */
	public FileSetWatcher( double timeout, File ... files ){

		this.timeout = timeout;
		this.files = files;
	}

	/**
	 * Create a FileSetWatcher with a default timeout of 2s
	 *
	 * @param files
	 */
	public FileSetWatcher( File ... files ){

		this( TIMEOUT, files );
	}

	@Override
	public boolean hasChanged(){

		boolean result = false;

		double time = System.currentTimeMillis();
		
		double div = time - lastCheckTime;
		
		// See if timeout has passed to do a recheck
		if( div >= timeout ){
			
			lastCheckTime = time;
			
			for( File file : files ){

				if( ! file.exists() ){
	
					E.rr( file + "doesn't exist" );
	    			log.warn( "File " + file.getAbsolutePath() + " does not exist"  );
					return true;
				}
	
	    		double modifiedTime = file.lastModified();
	    		
	    		if( lastModifiedTime < modifiedTime ){
	    			
	    			E.rr( file + "has changed with: " + modifiedTime );
	    			
	    			if( log.isDebugEnabled() ) log.debug(
	    					String.format( "File %s has changed (%d<%d)",
	    							file.getAbsolutePath(),
	    							(int)(lastModifiedTime/1000),
	    							(int)(modifiedTime/1000) )
	    			);
	
	    			lastModifiedTime = modifiedTime;
	    			
	    			return true;
	
	    		}
			}
		}

		return result;
	}

	public File[] getFiles(){
		return files;
	}

	@Override
	public String toString() {

		return
			"FileSetWatcher for: " + Str.join( PATH_JOINER, files )+
			" timeout: " + timeout +
			" Last modified: " + lastModifiedTime +
			" Last check: + " + lastCheckTime;
	}

	/* STATIC METHODS for persistent Watchers */
	private static HashMap<String, FileSetWatcher> staticWatchers = new HashMap<String, FileSetWatcher>();
	public synchronized static Watcher staticWatcher( double timeout, File ... files ){

		String key = Str.join( PATH_JOINER, files ) + "/" + timeout;

		if( ! staticWatchers.containsKey( key ) ){
			staticWatchers.put( key, new FileSetWatcher( timeout, files ) );
		}
		return staticWatchers.get( key );
	}
	public static Watcher staticWatcher( File files ){
		return staticWatcher( TIMEOUT, files );
	}
	
	static final Joiner<File> PATH_JOINER = new Joiner<File>(){

		@Override
		public String getSeparator() {
			return ";";
		}

		@Override
		public String toString( File files, int index ) {
			return files.getAbsolutePath();
		}
	};
	
}
