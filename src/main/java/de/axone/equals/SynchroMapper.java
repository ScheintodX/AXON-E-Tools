package de.axone.equals;

import java.util.Collection;


public interface SynchroMapper {
	
	
	/**
	 * Create a copy of a <em>not Synchronizable</em> object
	 * 
	 * @param name of field to synchonize
	 * @param object content of field
	 * @return the created copy
	 */
	public Object copyOf( String name, Object object );
	
	
	/**
	 * Synchronize one <em>Synchronizable</em> object in another
	 * 
	 * @param name of field to synchonize
	 * @param destination target where to synchronize to
	 * @param source object content of field
	 * @return The destination value. Can be another then the provided
	 */
	public Object synchronize( String name, Object destination, Object source );
	
	
	/**
	 * Returns an emty instance of a <em>Synchronizable</em> object
	 * 
	 * @param name of field to synchonize
	 * @param object content of field
	 * @return the created instance
	 */
	public Object emptyInstanceOf( String name, Object object );
	
	
	/**
	 * Find one object in a collection
	 * 
	 * @param name of field to synchonize
	 * @param collection to look into
	 * @param src object content of field
	 * @return the found object or NULL
	 */
	public Object find( String name, Collection<?> collection, Object src );
	
	
	public static class DefaultSynchroMapper implements SynchroMapper {

		/**
		 * Default implementation of copyOf does not create a copy but
		 * returns a reference to same object.
		 */
		@Override
		public Object copyOf( String name, Object object ) {
			
			Equals.log.debug( "copyOf: {}", name );
			
			return object;
		}

		/**
		 * Default impelmentation uses reflection to create a new object
		 */
		@Override
		public Object emptyInstanceOf( String name, Object object ) {
			
			Equals.log.debug( "emptyInstanceOf: {}", name );
			
			Class<?> clz = object.getClass();
			try {
				return clz.newInstance();
			} catch( ReflectiveOperationException e ) {
				throw new RuntimeException( e );
			}
		}

		/**
		 * Default implementation iterates over all and uses equals to find object
		 */
		@Override
		public Object find( String name, Collection<?> collection, Object src ) {
			
			Equals.log.debug( "find: {}", name );
			
			Object result = null;
			
			for( Object t : collection ){
				if( t.equals( src ) ){
					result = t;
					break;
				}
			}
			
			Equals.log.trace( "find: {} {} -> {}", new Object[]{ name, src, result } );
			
			return result;
		}

		/**
		 * Default implementation calls {@link Equals#synchronize} to synchronize
		 * 
		 * This synchromapper is passed
		 */
		@Override
		public Object synchronize( String name, Object destination, Object source ){
			
			Equals.log.debug( "synchronize: {}", name );
			
			Equals.synchronize( destination, source, this );
			
			return destination;
		}

	}
	
}
