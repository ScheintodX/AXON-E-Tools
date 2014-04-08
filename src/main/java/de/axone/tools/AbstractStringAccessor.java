package de.axone.tools;

import de.axone.tools.StringAccessor.StringAccessible;


public abstract class AbstractStringAccessor extends StringAccessor implements StringAccessible {
	
	public AbstractStringAccessor(){
		super();
		setAccessible( this );
	}
	
	@Override
	public abstract String doGet( String name );
	
}
