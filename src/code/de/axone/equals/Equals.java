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

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.axone.equals.EqualsClass.Select;
import de.axone.exception.Assert;

/**
 * This a Helper class for implementing the equals and the hashcode
 * method. Additionally it provides a synchronize method to 'clone'
 * one instance into another.
 * 
 * The realtion of these three methods is:
 * <pre>
 * Object o1 = makeObject( "some initializer" );
 * Object o2 = makeObject( "another initializer" );
 * 
 * synchronize( o1, o2 );
 * assert o1.equals( o2 );
 * assert o1.hashcode() == o2.hashcode()
 * </pre> 
 * 
 * For control of coping and comparing two annotations are provided:
 * <tt>@@EqualsClass</tt> and <tt>@@EqualsField</tt>
 * 
 * Additionally JPA annotations are supported:
 * 
 * <tt>@@Transient</tt> and <tt>@@Id</tt> implies <tt>@@EqualsField( include=false )</tt>
 * 
 * These default behaviour can be overwritten by specifying
 * <tt>@@EqualsField( include=true )</tt>
 * 
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
	
	public static <T> String strongHashString( T object ){
		return Base64.encodeBase64String( strongHash( object ) ).trim();
		//return String.format( "%08x", strongHash( object ) );
	}
	public static <T> byte[] strongHash( T object ){
		
		Assert.notNull( object, "object" );
		
		StrongHashCodeBuilder<byte[]> builder = new Sha1HashCodeBuilder();
		StrongHashWrapper<byte[],T> wrapper = new StrongHashWrapper<byte[],T>( builder, object );
		/*
		StrongHashCodeBuilder<Integer> builder = new Jenkins96HashCodeBuilder();
		StrongHashWrapper<Integer,T> wrapper = new StrongHashWrapper<Integer,T>( builder, object );
		*/
		
		process( wrapper, object );
		
		return builder.toHashCode();
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
	public static <T extends Synchronizable> T synchronize( T target, T source, SynchroProvider synchro ) {
		
		Assert.notNull( target, "target" );
		Assert.notNull( source, "source" );
		
		// No synchronisation for equal objects
		if( target == source || target.equals( source ) ) return target;
		
		SyncroWrapper<T> wrapper = new SyncroWrapper<T>( target, source, synchro );
		
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
					
					boolean isInclude;
					if( ef != null ){
						isInclude = ef.include();
					} else {
						isInclude = ec.select() == Select.ALL;
						Transient tAn = m.getAnnotation( Transient.class );
						Id idAn = m.getAnnotation( Id.class );
						if( tAn != null ) isInclude = false;
						if( idAn != null ) isInclude = false;
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
							&& isInclude
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
					
					boolean isInclude;
					if( ef != null ){
						isInclude = ef.include();
					} else {
						isInclude = ec.select() == Select.ALL;
						Transient tAn = getter.getAnnotation( Transient.class );
						Id idAn = getter.getAnnotation( Id.class );
						if( tAn != null ) isInclude = false;
						if( idAn != null ) isInclude = false;
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
							&& isInclude
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
			
			/*
			if( (v1 == null && v2 != null) || (v1 != null && !v1.equals( v2 ) ) ){
				E.rr( "### " + m.getName() + " doesn't match: " + v1 + " != " + v2 + " ###" );
			}
			*/
			
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
	
	private static final class StrongHashWrapper<H,T> implements Wrapper<T> {
	
		final StrongHashCodeBuilder<H> builder;
		final T o;
		
		StrongHashWrapper( StrongHashCodeBuilder<H> builder, T o ){
			this.builder = builder;
			this.o = o;
		}
		
		@Override
		public void invoke( Method m, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = m.invoke( o );
			
			value = applyOptions( value, options );
			
			//E.rr( m.getName() + " := " + value );
			
			builder.append( m.invoke( o ) );
		}
	}
	
	private static final class SyncroWrapper<T> implements CopyWrapper<T> {
		
		final T target;
		final T source;
		final SynchroProvider synchro;
		
		SyncroWrapper( T target, T source, SynchroProvider synchro ){
			this.target = target;
			this.source = source;
			this.synchro = synchro;
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
				
				Equals.synchronize( (Synchronizable)oldVal, (Synchronizable)newVal, synchro );
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
								oldSet.add( synchro.copyOf( o ) );
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
								oldList.add( synchro.copyOf( o ) );
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
									oldMap.put( key, synchro.copyOf( value ) );
								}
							} else {
								oldMap.put( key, newMap.get( synchro.copyOf( key ) ) );
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
				} else if( o instanceof String ){
					if( ((String)o).length() == 0 ) return null;
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
