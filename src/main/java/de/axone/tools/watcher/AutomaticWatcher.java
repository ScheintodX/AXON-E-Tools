package de.axone.tools.watcher;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.axone.cache.ng.CacheNG.SingleValueAccessor;


/**
 * Automatic DataFileWatcher which uses an {@link SingleValueAccessor} to convert
 * the files data if it has changed.
 * 
 * @author flo
 *
 * @param <T> Data-Type
 * @param <W> Watched Type as provided by Http/File Watcher
 */
public class AutomaticWatcher<W,T> {

	final Watcher<W> backend;
	final DataStore<T> store;
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public AutomaticWatcher( Watcher<W> backend ){
		assert backend != null;
		this.backend = backend;
		this.store = new DataStoreImpl<>();
	}
	
	public T getData( SingleValueAccessor<W,T> processor ) {

		assert processor != null;

		// First try to get from cache
		boolean hasChanged;
		try{
			lock.readLock().lock();
			hasChanged = backend.haveChanged();
			
		} finally { lock.readLock().unlock(); }
		
		T result;
		if( ! hasChanged ){

			result = store.getData();
			
		} else {

			try {
				lock.writeLock().lock();
				
				// Use Accessor to fetch
				result = processor.fetch( backend.getWatched() );

				store.setData( result );
				
			} finally {

				lock.writeLock().unlock();
			}
		}

    	return result;
	}
	
}
