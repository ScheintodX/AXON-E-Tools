package de.axone.tools;

import java.io.File;

public class FileDataWatcher<T> extends FileWatcher {
	
	private static final long serialVersionUID = 2L;
	
	private T data;

	public FileDataWatcher( File file ) {
		super( file );
	}
	public FileDataWatcher( File file, T data ) {
		super( file );
		this.data = data;
	}
	
	public T getData(){ return data; }
	
	public void setData( T data ){ this.data = data; }

}
