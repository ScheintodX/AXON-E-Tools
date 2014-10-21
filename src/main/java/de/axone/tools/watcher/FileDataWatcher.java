package de.axone.tools.watcher;

import java.io.File;

public class FileDataWatcher<T> extends FileWatcher implements DataStore<T> {
	
	private static final long serialVersionUID = 2L;
	
	private T data;

	public FileDataWatcher( File file ) {
		super( file );
	}
	public FileDataWatcher( File file, T data ) {
		super( file );
		this.data = data;
	}
	
	@Override
	public T getData(){ return data; }
	
	@Override
	public void setData( T data ){ this.data = data; }

}
