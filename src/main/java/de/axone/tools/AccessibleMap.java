package de.axone.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import de.axone.exception.Assert;
import de.axone.exception.Ex;

public class AccessibleMap implements StringValueAccessor<String,NoSuchElementException>{

	private final Map<String,String> backend;

	public AccessibleMap(){
		this( new HashMap<>() );
	}
	public AccessibleMap( Map<String,String> backend ) {
		Assert.notNull( backend, "backend" );
		this.backend = backend;
	}

	public Map<String,String> backend(){
		return backend;
	}

	@Override
	public String access( String key ) throws NoSuchElementException {
		if( ! backend.containsKey( key ) ) throw exception( key );
		return accessChecked( key );
	}

	@Override
	public String accessChecked( String key ) {
		return backend.get( key );
	}

	@Override
	public NoSuchElementException exception( String key ) {
		return Ex.up( new NoSuchElementException( key.toString() ) );
	}

	public void put( String key, String value ) {
		backend.put( key, value );
	}

	@Override
	public String toString() {
		return backend().toString();
	}

}
