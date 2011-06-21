package de.axone.equals;

public interface SynchroMapper {
	public Object copyOf( Object object );
	public Object emptyInstanceOf( Object object ) throws InstantiationException, IllegalAccessException;
}
