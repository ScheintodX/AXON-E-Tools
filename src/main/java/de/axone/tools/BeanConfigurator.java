package de.axone.tools;

import java.lang.reflect.Method;

public class BeanConfigurator {
	
	private Object bean;
	
	public BeanConfigurator( Object bean ){
		
		this.bean = bean;
	}
	
	public boolean setProtected( String name, String value ){
		
		try {
			set( name, value );
		} catch (Exception e) {
			
			return false;
		}
		return true;
	}
	
	public void set( String name, String value )
	throws ConfigurationMissmatchException {
		
		try {
			doSet( name, value );
		} catch (Exception e) {
			throw new ConfigurationMissmatchException( "Error setting " + name + " to '" + value + "' in " + bean.getClass(), e );
		}
	}
	
	public Object get( String name )
	throws ConfigurationMissmatchException {
		
		try {
			return doGet( name );
		} catch( Exception e ){
			throw new ConfigurationMissmatchException( "Error getting " + name + "' in " + bean.getClass(), e );
		}
	}
	
	private void doSet( String name, String value )
	throws Exception {
		
		//E.rr( "Set " + name + " to '" + value + "'" );
		
		String setterName = "set" +
			name.substring( 0, 1 ).toUpperCase() +
			name.substring( 1 );
		
		Method method = null;
		
		//E.rr( "looking for setter: " + setterName );
		
		try {
			method = bean.getClass().getMethod( setterName, new Class[]{ String.class } );
		} catch( Exception e ){
			
			method = searchMethod( setterName );
		}
		
		if( method == null )
			throw new IllegalArgumentException( "Setter doesn't exist for " + bean.getClass().getCanonicalName() + ": " + name );
			
		Class<?>[] parameters = method.getParameterTypes();
		
		Class<?> parameter = parameters[ 0 ]; // It's always just one according to definition of searchMethod
		
		//E.rr( "Class: " + bean.getClass() + " Method: " + method.getName() + " Parameter: " + parameter );
		
		if( String.class == parameter ){
			
			method.invoke( bean, value );
			
		} else if( ( char.class == parameter || Character.class == parameter ) && value.length() == 1 ){
			
			method.invoke( bean, Character.valueOf( value.charAt( 0 ) ) );
			
		} else if( short.class == parameter || Short.class == parameter ){
			
			method.invoke( bean, Short.parseShort( value ) );
			
		} else if( int.class == parameter || Integer.class == parameter ){
			
			method.invoke( bean, Integer.parseInt( value ) );
			
		} else if( long.class == parameter || Long.class == parameter ){
			
			method.invoke( bean, Long.parseLong( value ) );
			
		} else if( float.class == parameter || Float.class == parameter ){
			
			method.invoke( bean, Float.parseFloat( value ) );
			
		} else if( double.class == parameter || Double.class == parameter ){
			
			method.invoke( bean, Double.parseDouble( value ) );
		
		} else {
			
			// Try as class
			Class<?> c = Class.forName( value );
			
			if( c == parameter ){
			
				Object o = c.newInstance();
				
				method.invoke( bean, o );
			
			} else {
				
				throw new IllegalArgumentException( "Setter doesn't provide a supported type" );
			}
		}
		
	}
	
	private Object doGet( String name )
	throws Exception {
		
		//E.rr( "Set " + name + " to '" + value + "'" );
		
		String getterName = "get" +
			name.substring( 0, 1 ).toUpperCase() +
			name.substring( 1 );
		
		Method method = null;
		
		//E.rr( "looking for setter: " + setterName );
		
		try {
			method = bean.getClass().getMethod( getterName );
		} catch( Exception e ){
			
			throw new IllegalArgumentException( "Getter not found: " + getterName );
		}
		
		return method.invoke( bean );
	}
	
	/*
	 * Look for a method with one parameter
	 */
	private Method searchMethod( String name ){
		
		Method[] methods = bean.getClass().getMethods();
			
		for( Method method : methods ){
			
			if( ! method.getName().equals( name ) ) continue;
			
			Class<?>[] parameters = method.getParameterTypes();
			
			if( parameters.length == 1 )
				return method;
		}
		
		return null;
	}
}
