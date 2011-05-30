package de.axone.equals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.axone.equals.EqualsClass.Select;
import de.axone.exception.Assert;

/**
 * @author flo
 *
 */
public class Equals {
	
	public static <T> int hash( T object ){
		
		Assert.notNull( object, "object" );
		
		HashCodeBuilder builder = new HashCodeBuilder();
		HashWrapper<T> wrapper = new HashWrapper<T>( builder, object );
		
		process( wrapper, object );
		
		return builder.toHashCode();
	}
	
	public static <T> boolean equals( T o1, T o2 ) {
		
		Assert.notNull( o1, "o1" );
		if( o2 == null ) return false;
		if( o1 == o2 ) return true;
		if( o1.getClass() != o2.getClass() ) return false;
		
		EqualsBuilder builder = new EqualsBuilder();
		EqualsWrapper<T> wrapper = new EqualsWrapper<T>( builder, o1, o2 );
		
		process( wrapper, o1 );
		
		return builder.isEquals();
	}
	
	/**
	 * Synchronize 'target' so that ist will equals source.
	 * 
	 * This is done 'in place'. No copy of 'target' is created
	 * 
	 * Do this with the the fewest possible (hopefully) amount of fields
	 * 
	 * @throws NoSuchMethodException if there is a mismatch between setter and getter
	 * 
	 * @param <T>
	 * @param target
	 * @param source
	 * @return target (not a copy)
	 */
	public static <T extends Synchronizable> T synchronize( T target, T source ) {
		
		Assert.notNull( target, "target" );
		Assert.notNull( source, "source" );
		
		// No synchronisation for equal objects
		if( target == source || target.equals( source ) ) return target;
		
		SyncroWrapper<T> wrapper = new SyncroWrapper<T>( target, source );
		
		process( wrapper, target, source );
		
		return target;
	}

	private static <T> void process( Wrapper<T> wrapper, T o ) {
		
		Class<?> clz = o.getClass();
		
		EqualsClass ec = clz.getAnnotation( EqualsClass.class );
		Assert.notNull( ec, "@EqualsClass for " + o.getClass().getSimpleName() );
		
		EnumSet<EqualsOption.Option> globalOptions = EnumSet.noneOf( EqualsOption.Option.class );
		EqualsOption eo = clz.getAnnotation( EqualsOption.class );
		if( eo != null ){
			globalOptions = EnumSet.copyOf( Arrays.asList( eo.options()  ) );
		}
		
		switch( ec.workOn() ){
		
			case FIELDS:{
				throw new NotImplementedException();
			}
				
			case METHODS:{
				
				for( Method m : clz.getDeclaredMethods() ){
					
					boolean isPrivate = Modifier.isPrivate( m.getModifiers() );
					
					EqualsField ef = m.getAnnotation( EqualsField.class );
					
					eo = m.getAnnotation( EqualsOption.class );
					EnumSet<EqualsOption.Option> localOptions = EnumSet.copyOf( globalOptions );
					if( eo != null ){
						localOptions.addAll( Arrays.asList( eo.options() ) );
					}
					
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
							wrapper.invoke( m, localOptions );
						} catch( Exception e ) {
							throw new RuntimeException( "Error in " + name, e );
						}
					}
				}
			}
		}
	}
	
	private static <T extends Synchronizable > void process( CopyWrapper<T> wrapper, T target, T source ) {
		
		Class<?> targetClz = target.getClass();
		Class<?> sourceClz = source.getClass();
		Assert.equal( targetClz, "target and source class", sourceClz );
		
		EqualsClass ec = targetClz.getAnnotation( EqualsClass.class );
		Assert.notNull( ec, "@EqualsClass for " + target.getClass().getSimpleName() );
		
		EnumSet<EqualsOption.Option> globalOptions = EnumSet.noneOf( EqualsOption.Option.class );
		EqualsOption eo = targetClz.getAnnotation( EqualsOption.class );
		if( eo != null ){
			globalOptions = EnumSet.copyOf( Arrays.asList( eo.options()  ) );
		}
		
		switch( ec.workOn() ){
		
			case FIELDS:{
				throw new NotImplementedException();
			}
				
			case METHODS:{
				
				for( Method getter : targetClz.getDeclaredMethods() ){
					
					boolean isPrivate = Modifier.isPrivate( getter.getModifiers() );
					
					EqualsField ef = getter.getAnnotation( EqualsField.class );
					
					eo = getter.getAnnotation( EqualsOption.class );
					EnumSet<EqualsOption.Option> localOptions = EnumSet.copyOf( globalOptions );
					if( eo != null ){
						localOptions.addAll( Arrays.asList( eo.options() ) );
					}
					
					String name = getter.getName();
					if( 
							( 
								( name.length() > 3 && name.startsWith( "get" )
										&& Character.isUpperCase( name.charAt( 3 ) )
								|| name.length() > 2 && name.startsWith( "is" )
										&& Character.isUpperCase( name.charAt( 2 ) ) )
							)
							&& getter.getReturnType() != Void.class
							&& getter.getGenericParameterTypes().length == 0
							&& ( ec.includePrivate() || !isPrivate )
							&& ( 
								( ef != null && ef.include() )
								|| ( ef == null && ec.select() == Select.ALL )
							)
					){
						
						String setterName = "set"+m2n( name );
						
						try {
							
							Method setter = targetClz.getMethod( setterName,
									new Class<?> []{ getter.getReturnType() } );
							
							wrapper.invoke( setter, getter, localOptions );
							
						} catch( Exception e ) {
							throw new RuntimeException( "Error in " + name, e );
						}
						
					}
				}
			}
			
		}
	}
	
	private static String m2n( String methodName ){
		
		if( methodName.startsWith( "get" ) ) return methodName.substring( 3 );
		else if( methodName.startsWith( "is" ) ) return methodName.substring( 2 );
		else throw new IllegalArgumentException( "Not a valid getter: " + methodName );
		
	}
	
	private interface Wrapper<T> {
		public void invoke( Method m, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	}
	
	private interface CopyWrapper<T> {
		public void invoke( Method setter, Method getter, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	}
	
	private static final class EqualsWrapper<T> implements Wrapper<T> {
		
		final EqualsBuilder builder;
		final T o1, o2;
		
		EqualsWrapper( EqualsBuilder builder, T o1, T o2 ){
			this.builder = builder;
			this.o1 = o1; this.o2 = o2;
		}

		@Override
		public void invoke( Method m, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object v1 = m.invoke( o1 );
			Object v2 = m.invoke( o2 );
			
			v1 = applyOptions( v1, options );
			v2 = applyOptions( v2, options );
			
			builder.append( v1, v2 );
		}
	}
	
	private static final class HashWrapper<T> implements Wrapper<T> {
	
		final HashCodeBuilder builder;
		final T o;
		
		HashWrapper( HashCodeBuilder builder, T o ){
			this.builder = builder;
			this.o = o;
		}
		
		@Override
		public void invoke( Method m, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = m.invoke( o );
			
			value = applyOptions( value, options );
			
			builder.append( m.invoke( o ) );
		}
	}
	
	private static final class SyncroWrapper<T> implements CopyWrapper<T> {
		
		final T target;
		final T source;
		
		SyncroWrapper( T target, T source ){
			this.target = target;
			this.source = source;
		}

		@Override
		//@SuppressWarnings( { "unchecked", "rawtypes" } )
		public void invoke( Method setter, Method getter, EnumSet<EqualsOption.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException {
			
			//Class<?> type = getter.getReturnType();
			
			Object oldVal = getter.invoke( target );
			Object newVal = getter.invoke( source );
			
			oldVal = applyOptions( oldVal, options );
			newVal = applyOptions( newVal, options );
			
			if( oldVal == newVal ) return;
			if( oldVal != null && oldVal.equals( newVal ) ) return;
			
			if( newVal != null && oldVal != null && newVal instanceof Synchronizable ){
				
				Equals.synchronize( (Synchronizable)oldVal, (Synchronizable)newVal );
			} else {
				
				if( newVal != null && oldVal != null ){
					
					if( newVal instanceof Set ){
						@SuppressWarnings( { "rawtypes", "unchecked" } )
						Set<Object>
								newSet = (Set)newVal,
								oldSet = (Set)oldVal;
						
						// Delete from old what's not in new
						oldSet.retainAll( newSet );
						
						// Keep the old ones and add only the new ones.
						for( Object o : newSet ){
							if( ! oldSet.contains( o ) ){
								oldSet.add( o );
							}
						}
						return;
						
					} else if( newVal instanceof List ){
						
						@SuppressWarnings( { "rawtypes", "unchecked" } )
						List<Object>
								newList = (List)newVal,
								oldList = (List)oldVal;
						
						for( Iterator<Object> it = oldList.iterator(); it.hasNext(); ){
							Object o = it.next();
							if( ! newList.contains( o ) ){
								it.remove();
							}
						}
						for( Object o : newList ){
							if( ! oldList.contains( o ) ){
								oldList.add( o );
							}
						}
						Collections.sort( oldList, new List2ListSorter( newList ) );
						return;
						
					} else if( newVal instanceof Map ){
						
						@SuppressWarnings( { "rawtypes", "unchecked" } )
						Map<Object,Object>
								newMap = (Map)newVal,
								oldMap = (Map)oldVal;
						
						// Remove vanished keys
						List<Object> removeMe = new LinkedList<Object>();
						for( Object key : oldMap.keySet() ){
							if( ! newMap.containsKey( key ) )
								removeMe.add( key );
						}
						
						for( Object key : removeMe ){
							oldMap.remove( key );
						}
						
						for( Object key : newMap.keySet() ){
							if( oldMap.containsKey( key ) ){
								Object value = newMap.get( key );
								if( ! oldMap.get( key ).equals( value ) ){
									// No deep copy here!
									oldMap.put( key, value );
								}
							} else {
								oldMap.put( key, newMap.get( key ) );
							}
						}
						
					}
					
				}
				setter.invoke( target, newVal );
			}
		}
		
	}
			
	private static final Object applyOptions( Object o, EnumSet<EqualsOption.Option> options ){
		
		if( options.contains( EqualsOption.Option.EMPTY_IS_NULL ) ){
			if( o != null ){
				if( o instanceof Collection ){
					if( ((Collection<?>)o).size() == 0 ) return null;
				} else if( o instanceof Map ){
					if( ((Map<?,?>)o).size() == 0 ) return null;
				}
			}
		}
		return o;
	}
	
	public interface Synchronizable {}
	
	private static final class List2ListSorter implements Comparator<Object> {
		
		List<?> list;
		
		List2ListSorter( List<?> list ){
			this.list = list;
		}

		@Override
		public int compare( Object o1, Object o2 ) {
			
			return new Integer( list.indexOf( o1 ) )
					.compareTo( new Integer( list.indexOf( o2 ) ) );
			
		}
		
	}
}
