package de.axone.equals;

public interface SynchroMapper {
	
	public Object copyOf( String name, Object object );
	
	public Object emptyInstanceOf( String name, Object object ) throws InstantiationException, IllegalAccessException;
}
