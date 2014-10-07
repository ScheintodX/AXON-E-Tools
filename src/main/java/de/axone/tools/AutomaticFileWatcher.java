package de.axone.tools;

import java.io.File;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.axone.cache.ng.CacheNG.SingleValueAccessor;


/**
 * Automatic DataFileWatcher which uses an {@link SingleValueAccessor} to convert
 * the files data if it has changed.
 * 
 * @author flo
 *
 * @param <T> Data-Type
 */
public class AutomaticFileWatcher<T> {

	final FileDataWatcher<T> backend;
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public AutomaticFileWatcher( FileDataWatcher<T> backend ){
		assert backend != null;
		this.backend = backend;
	}

	public T getData( SingleValueAccessor<File,T> accessor ) {

		assert accessor != null;

		// First try to get from cache
		boolean hasChanged;
		try{
			lock.readLock().lock();
			hasChanged = backend.haveChanged();
			
		} finally { lock.readLock().unlock(); }
		
		T result;
		if( ! hasChanged ){

			result = backend.getData();
			
		} else {

			try {
				lock.writeLock().lock();
				
				// Use Accessor to fetch
				result = accessor.fetch( backend.getFile() );

				backend.setData( result );
				
			} finally {

				lock.writeLock().unlock();
			}
		}

    	return result;
	}
	
}
