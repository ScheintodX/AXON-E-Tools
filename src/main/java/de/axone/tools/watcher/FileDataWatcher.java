package de.axone.tools.watcher;

import java.io.File;

public class FileDataWatcher<T> extends FileWatcher implements DataStore<T> {
	
	private static final long serialVersionUID = 3L;
	
	private final DataStore<T> dataStore = new DataStoreImpl<>();

	public FileDataWatcher( File file ) {
		super( file );
	}
	public FileDataWatcher( File file, T data ) {
		super( file );
		this.dataStore.setData( data );
	}
	
	@Override
	public T getData(){ return dataStore.getData(); }
	
	@Override
	public void setData( T data ){ this.dataStore.setData( data ); }
	
	@Override
	public void setError( Throwable t ) { this.dataStore.setError( t ); }

	
}
