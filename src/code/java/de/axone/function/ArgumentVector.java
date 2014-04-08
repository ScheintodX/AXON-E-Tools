package de.axone.function;

import java.util.HashMap;

public class ArgumentVector {
	
	private HashMap<String,Argument<?>> arguments =
		new HashMap<String,Argument<?>>();

	public int size(){
		return arguments.size();
	}
	public void put( ArgumentDescription<?> desc, Argument<?> argument ){
		arguments.put( desc.name(), argument );
	}
	public Argument<?> get( String name ){
		return arguments.get( name );
	}
}
