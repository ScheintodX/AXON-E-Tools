package de.axone.tools.watcher;

public interface DataStore<T> {

	public T getData();
	
	public void setData( T data );
}
