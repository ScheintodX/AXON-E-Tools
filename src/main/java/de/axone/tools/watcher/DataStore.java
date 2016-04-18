package de.axone.tools.watcher;

public interface DataStore<T> {

	public T getData();
	
	public void setData( T data );
	
	public void setError( Throwable t );
	
	public static final class DataStoreInError extends Error {
		public DataStoreInError( Throwable cause ){
			super( "Datastore had an error in a previous attempt to fetch data", cause );
		}
	}
}
