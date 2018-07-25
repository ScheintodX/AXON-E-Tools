package de.axone.tools;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class ToString {
	
	private final String name;

	// Ordered
	private Map<String,Object> values = new LinkedHashMap<>();
	
	private boolean ignoreEmpty = true;
	private boolean extended = false;
	
	public ToString( String name ) {
		this.name = name;
	}
	public static ToString build( String name ){
		return new ToString( name );
	}
	public static ToString build( Class<?> clazz ) {
		return build( clazz.getSimpleName() );
	}
	
	public ToString ignoreEmpty( boolean ignoreEmpty ){
		
		this.ignoreEmpty = ignoreEmpty;
		
		return this;
	}
	
	public ToString extended( boolean extended ){
		
		this.extended = extended;
		
		return this;
	}
	
	public ToString append( String key, Object value ) {
		
		if( value != null || !ignoreEmpty )
				values.put( key, value );
		
		return this;
	}
	
	public ToString fromGetters( Object obj ) {
		
		Method[] methods = obj.getClass().getDeclaredMethods();
		
		for( Method m : methods ){
			
			if( m.getName().length() > 3 && m.getName().startsWith( "get" ) && m.getParameterCount() == 0 ){
				
				String name = m.getName().substring( 3 );
				
				try {
					append( name, m.invoke( obj ) );
				} catch( ReflectiveOperationException | IllegalArgumentException e ) {
					append( name, "( -ERROR- )" );
				}
					
			}
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		
		if( !extended )
				return name + F.ormat( values );
		else {
			StringBuilder result = new StringBuilder();
			
			for( Map.Entry<String,Object> entry : values.entrySet() ) {
				result.append( name ).append( '\n' )
				      .append( entry.getKey() )
				      .append( "............" )
				      .append( F.ormat( entry.getValue() ) )
				      .append( '\n' )
				      ;
			}
			
			return result.toString();
		}
	}

}
