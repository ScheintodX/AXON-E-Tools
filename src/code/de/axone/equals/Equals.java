package de.axone.equals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.axone.equals.EqualsClass.Select;
import de.axone.exception.Assert;

public class Equals {
	
	public static int hash( Object object ){
		
		Assert.notNull( object, "object" );
		
		HashCodeBuilder builder = new HashCodeBuilder();
		HashWrapper wrapper = new HashWrapper( builder, object );
		
		process( wrapper, object );
		
		return builder.toHashCode();
	}
	
	public static boolean equals( Object o1, Object o2 ) {
		
		Assert.notNull( o1, "o1" );
		if( o2 == null ) return false;
		if( o1 == o2 ) return true;
		if( o1.getClass() != o2.getClass() ) return false;
		
		EqualsBuilder builder = new EqualsBuilder();
		EqualsWrapper wrapper = new EqualsWrapper( builder, o1, o2 );
		
		process( wrapper, o1 );
		
		return builder.isEquals();
	}

	private static void process( Wrapper wrapper, Object o ) {
		
		Class<?> clz = o.getClass();
		
		EqualsClass ec = clz.getAnnotation( EqualsClass.class );
		Assert.notNull( ec, "@EqualsClass" );
		
		switch( ec.workOn() ){
		
			case FIELDS:{
				throw new NotImplementedException();
			}
				
			case METHODS:{
				
				for( Method m : clz.getMethods() ){
					
					boolean isPrivate = Modifier.isPrivate( m.getModifiers() );
					
					EqualsField ef = m.getAnnotation( EqualsField.class );
					
					String name = m.getName();
					if( 
							( 
								( name.length() > 3 && name.startsWith( "get" )
										&& Character.isUpperCase( name.charAt( 3 ) )
								|| name.length() > 2 && name.startsWith( "is" )
										&& Character.isUpperCase( name.charAt( 2 ) ) )
							)
							&& m.getReturnType() != Void.class
							&& m.getGenericParameterTypes().length == 0
							&& ( ec.includePrivate() || !isPrivate )
							&& ( 
								( ef != null && ef.include() )
								|| ( ef == null && ec.select() == Select.ALL )
							)
					){
						try {
							wrapper.invoke( m );
						} catch( Exception e ) {
							throw new RuntimeException( e );
						}
					}
				}
			}
		}
	}
	
	private interface Wrapper {
		public void invoke( Method m )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	}
	
	private static final class EqualsWrapper implements Wrapper {
		
		final EqualsBuilder builder;
		final Object o1, o2;
		
		EqualsWrapper( EqualsBuilder builder, Object o1, Object o2 ){
			this.builder = builder;
			this.o1 = o1; this.o2 = o2;
		}

		@Override
		public void invoke( Method m )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			builder.append( m.invoke( o1 ), m.invoke( o2 ) );
		}
	}
	
	private static final class HashWrapper implements Wrapper {
	
		final HashCodeBuilder builder;
		final Object o;
		
		HashWrapper( HashCodeBuilder builder, Object o ){
			this.builder = builder;
			this.o = o;
		}
		
		@Override
		public void invoke( Method m )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			builder.append( m.invoke( o ) );
		}
	}
}
