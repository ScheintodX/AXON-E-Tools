package de.axone.tools.watcher;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.tools.Str;

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
public class FileListWatcher implements Watcher<List<File>> {

	public static final Logger log =
			LoggerFactory.getLogger( FileListWatcher.class );

	private static final double TIMEOUT = 2000; //2 s

	private List<File> files;

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
	public FileListWatcher( double timeout, List<File> files ){

		this.timeout = timeout;
		this.files = files;
	}

	/**
	 * Create a FileSetWatcher with a default timeout of 2s
	 *
	 * @param files
	 */
	public FileListWatcher( List<File> files ){

		this( TIMEOUT, files );
	}

	@Override
	public boolean haveChanged(){

		boolean result = false;

		double time = System.currentTimeMillis();
		
		double div = time - lastCheckTime;
		
		// See if timeout has passed to do a recheck
		if( div >= timeout ){
			
			lastCheckTime = time;
			
			for( File file : files ){

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
	
	    			lastModifiedTime = modifiedTime;
	    			
	    			return true;
	
	    		}
			}
		}

		return result;
	}

	@Override
	public List<File> getWatched(){
		return files;
	}

	@Override
	public String toString() {

		return
			"FileSetWatcher for: " + Str.join( (file,i) -> file.getAbsolutePath(), files )+
			" timeout: " + timeout +
			" Last modified: " + lastModifiedTime +
			" Last check: + " + lastCheckTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( files == null ) ? 0 : files.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( !( obj instanceof FileListWatcher ) )
			return false;
		FileListWatcher other = (FileListWatcher) obj;
		if( files == null ) {
			if( other.files != null )
				return false;
		} else if( !files.equals( other.files ) )
			return false;
		return true;
	}

}
