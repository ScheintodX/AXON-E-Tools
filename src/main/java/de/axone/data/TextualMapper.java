package de.axone.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TextualMapper {

	public static String toString( Object o ){
		
		for( Method m : o.getClass().getMethods() ){
			
			if( m.getAnnotation( TextualField.class ) != null ){
				
				if( m.getParameterTypes().length != 0 )
					throw new IllegalArgumentException( m.getClass().getSimpleName() +
							'.' + m.getName() + " must be an empty method" );
				
				if( m.getReturnType() != String.class )
					throw new IllegalArgumentException( m.getClass().getSimpleName() +
							'.' + m.getName() + " must return string" );
				
				try {
					return (String) m.invoke( o );
				} catch( IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
					throw new IllegalArgumentException( e );
				}
					
			}
		}
		
		throw new IllegalArgumentException( o.getClass().getSimpleName() + " is not annotated correctly" );
	}
	
	public static List<String> mapByAnnotation( List<?> list ){
		
		List<String> result = new ArrayList<>( list.size() );
		
		for( Object o : list ){
			result.add( toString( o ) );
		}
		return result;
	}
	
	public static List<String> mapByInterface( List<Textual> list ){
		
		List<String> result = new ArrayList<>( list.size() );
		
		for( Textual o : list ){
			result.add( o.getText() );
		}
		return result;
	}
}
