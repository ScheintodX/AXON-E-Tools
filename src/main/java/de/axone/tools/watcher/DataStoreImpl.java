package de.axone.tools.watcher;

import java.io.Serializable;

public class DataStoreImpl<T> implements DataStore<T>, Serializable {
	
	private static final long serialVersionUID = 2L;
	
	private T data;
	private Throwable error;

	@Override
	public T getData() {
		if( error != null ) throw new DataStoreInError( error );
		return data;
	}

	@Override
	public void setData( T data ) {
		this.data = data;
		this.error = null;
	}

	@Override
	public void setError( Throwable t ) {
		this.error = t;
	}

}
