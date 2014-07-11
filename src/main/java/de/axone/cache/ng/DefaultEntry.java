package de.axone.cache.ng;

import java.io.Serializable;

import de.axone.cache.ng.CacheNG.Client;

public class DefaultEntry<O> implements Client.Entry<O>, Serializable {
			
	private static final long serialVersionUID = 1L;
	
	private final long creation;
	private final O data;
	
	public DefaultEntry( O data ) {
		this.creation = System.currentTimeMillis();
		this.data = data;
	}
	public DefaultEntry( O data, long creation ){
		this.creation = creation;
		this.data = data;
	}
	
	@Override
	public O data() { return data;
	}
	@Override
	public long creation() { return creation; }
	
	@Override
	public int hashCode() {
		if( data == null ) return 0;
		return data.hashCode();
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof DefaultEntry ) ) return false;
		
		DefaultEntry<?> other = (DefaultEntry<?>) obj;
		
		if( data == null ) {
			if( other.data != null ) return false;
		} else if( !data.equals( other.data ) ) return false;
		
		return true;
	}
		

}
