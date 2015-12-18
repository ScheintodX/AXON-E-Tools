package de.axone.tools;

import java.lang.reflect.Field;

public abstract class Inspector {
	
	public static void inspect( Object object, String fieldName ) {
		
		Class<?> clazz = object.getClass();
		
		try {
			Field field = clazz.getDeclaredField( fieldName );
			
			Object value = field.get( object );
			
			_inspect( value );
			
		} catch( NoSuchFieldException e ) {
			throw new Error( "Cannot find '" + fieldName + '\'', e );
		} catch( SecurityException | IllegalAccessException e ) {
			throw new Error( "Cannot access '" + fieldName + '\'', e );
		}
	}

	public static void inspect( Object object ) {
		_inspect( object );
	}
	
	private static void _inspect( Object object  ) {
		
		StringBuilder out = new StringBuilder();
		
		out.append( "INSPECT: " + object.getClass().getSimpleName() + '\n' );
		
		Class<?> clazz = object.getClass();
		
		Field [] fields = clazz.getDeclaredFields();
		
		for( Field field : fields ) {
			
			field.setAccessible(true);
				
			try {
				out.append( field.getName() )
						.append( ": \"" )
						.append( F.ormat( field.get( object ) ) )
						.append( "\"\n" )
						;
			} catch( IllegalAccessException e ) {
				throw new Error( "Cannot access '" + field.getName() + '\'', e );
			}
		}
		
		E.echo( System.err, 3, true, true, false, out.toString() );
		
	}
}
