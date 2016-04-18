package de.axone.tools;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Instantiator<T> {
	
	private final Class<T> clazz;
	
	private List<Class<?>> parameterTypes = new ArrayList<>( 4 );
	private List<Object> parameterValues = new ArrayList<>( 4 );
	
	public Instantiator( Class<T> clazz ) {
		this.clazz = clazz;
	}
	public static <Z> Instantiator<Z> forClass( Class<Z> clazz ) {
		return new Instantiator<>( clazz );
	}
	
	public <Z> Instantiator<T> with( Z value ) {
		parameterTypes.add( value.getClass() );
		parameterValues.add( value );
		return this;
	}
	
	public <Z, Z1 extends Z> Instantiator<T> with( Class<Z> type, Z1 value ) {
		parameterTypes.add( type );
		parameterValues.add( value );
		return this;
	}
	
	public T newInstance() throws ReflectiveOperationException {
		
		Constructor<T> constructor = clazz.getDeclaredConstructor(
				parameterTypes.toArray( new Class<?>[ parameterTypes.size() ] ) );
		
		return constructor.newInstance(
				parameterValues.toArray( new Object[ parameterValues.size() ] ) );
	}
	
}
