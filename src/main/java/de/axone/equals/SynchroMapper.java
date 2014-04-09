package de.axone.equals;

import java.util.Collection;

public interface SynchroMapper {
	
	public Object copyOf( String name, Object object );
	
	public void synchronize( String name, Object dst, Object src );
	
	public Object emptyInstanceOf( String name, Object object ) throws InstantiationException, IllegalAccessException;
	
	public Object find( String name, Collection<?> collection, Object src );
	
}
