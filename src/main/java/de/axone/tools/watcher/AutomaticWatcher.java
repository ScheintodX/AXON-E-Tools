package de.axone.tools.watcher;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.axone.cache.ng.CacheNG.SingleValueAccessor;


/**
 * Automatic DataFileWatcher which uses an {@link SingleValueAccessor} to convert
 * the files data if it has changed.
 * 
 * @author flo
 *
 * @param <W> Watched Type as provided by Http/File Watcher
 * @param <T> Data-Type
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
	
	public T getData( SingleValueAccessor<W,T> accessor ) {

		assert accessor != null;

		// First try to get from cache
		boolean hasChanged;
		
		lock.readLock().lock();
		try{
			hasChanged = backend.haveChanged();
		} finally {
			lock.readLock().unlock();
		}
		
		T result;
		if( ! hasChanged ){

			result = store.getData();
			
		} else {

			lock.writeLock().lock();
			try {
				// Use Accessor to fetch
				result = accessor.fetch( backend.getWatched() );

				store.setData( result );
				
			} finally {
				lock.writeLock().unlock();
			}
		}

    	return result;
	}
	
	@Override
	public String toString() {
		
		return "AutomaticWatcher: " + backend.toString();
	}
}
