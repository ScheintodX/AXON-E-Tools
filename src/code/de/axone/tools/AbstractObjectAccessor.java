package de.axone.tools;

import de.axone.tools.ObjectAccessor.ObjectAccessible;


public abstract class AbstractObjectAccessor extends ObjectAccessor implements ObjectAccessible {
	
	public AbstractObjectAccessor(){
		super();
		setAccessible( this );
	}
	
	@Override
	public abstract Object doGet( String name );
	
}
