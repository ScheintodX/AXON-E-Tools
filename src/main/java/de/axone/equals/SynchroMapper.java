package de.axone.equals;

import java.util.Collection;

public interface SynchroMapper {
	
	public Object copyOf( String name, Object object );
	
	public Object emptyInstanceOf( String name, Object object ) throws InstantiationException, IllegalAccessException;
	
	public <T> T find( String name, Collection<T> collection, T src );
	
}
