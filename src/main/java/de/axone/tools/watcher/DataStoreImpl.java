package de.axone.tools.watcher;

public class DataStoreImpl<T> implements DataStore<T> {
	
	private T data;

	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData( T data ) {
		this.data = data;
	}

}
