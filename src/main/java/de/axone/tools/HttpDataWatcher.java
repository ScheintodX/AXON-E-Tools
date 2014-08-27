package de.axone.tools;

import java.io.Serializable;

import de.axone.web.SuperURL;

public class HttpDataWatcher<T> extends HttpWatcher implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private T data;

	public HttpDataWatcher( SuperURL url ) {
		super( url );
	}
	public HttpDataWatcher( SuperURL url, T data ) {
		super( url );
		this.data = data;
	}
	
	public T getData(){ return data; }
	
	public void setData( T data ){ this.data = data; }
}
