package de.axone.equals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
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
	
	/**
	 * Generate a java hash code for the given object
	 * 
	 * The object's class must be annotated with @@EqualsClass
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 */
	public static <T> int hash( T object ){
		
		Assert.notNull( object, "object" );
		
		HashCodeBuilder builder = new HashCodeBuilder();
		HashWrapper<T> wrapper = new HashWrapper<T>( builder, object );
		
		process( wrapper, object );
		
		return builder.toHashCode();
	}
	
	/**
	 * Compares two objects for equality
	 * 
	 * The objects' class must be annotated with @@EqualsClass
	 * 
	 * @param <T>
	 * @param o1
	 * @param o2
	 * @return
	 */
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
	 * Generate a strong hash code on the given object
	 * 
	 * This works in a similar way than hashCode but generates
	 * a cryptographic strong hash code
	 * 
	 * @param <T>
	 * @param object the object to hash
	 * @return a base 64 encoded hash code string
	 */
	public static <T> String strongHashString( T object ){
		return Base64.encodeBase64String( strongHash( object ) ).trim();
	}
	
	/**
	 * Generate a strong hash code on the given object
	 * 
	 * This works in a similar way than hashCode but generates
	 * a cryptographic strong hash code
	 * 
	 * @param <T>
	 * @param object the object to hash
	 * @return the strong hash code as byte array
	 */
	public static <T> byte[] strongHash( T object ){
		
		Assert.notNull( object, "object" );
		
		StrongHashCodeBuilder<byte[]> builder = new CryptoHashCodeBuilder();
		StrongHashWrapper<byte[],T> wrapper = new StrongHashWrapper<byte[],T>( builder, object );
		
		process( wrapper, object );
		
		return builder.toHashCode();
	}
	
	/**
	 * Synchronise 'target' so that it will equals source.
	 * 
	 * Formally: <tt>synchronize( o1, o2 ) => equals( o1, o2 ) == true</tt>
	 * 
	 * This is done 'in place'. No copy of 'target' is created
	 * However if 'target' is null a new instance will be created using
	 * the sources 'newInstance' method;
	 * 
	 * Do this with the the fewest possible (hopefully) amount of fields
	 * 
	 * You can specify a SynchroProvider which can process the fields which
	 * are copied. (But not the ones which aren't)
	 * 
	 * @throws NoSuchMethodException if there is a mismatch between setter and getter
	 * 
	 * @param <T>
	 * @param target
	 * @param source
	 * @param synchroProvider to do coping or <tt>null</tt>
	 * @return the target (not a copy)
	 */
	public static <T extends Synchronizable<T>> T synchronize(
			T target, T source, SynchroMapper synchroProvider ) {
		
		Assert.notNull( source, "source" );
		
		if( target == null ) target = source.emptyInstance();
		
		// No synchronisation for equal objects
		if( target == source || target.equals( source ) ) return target;
		
		SyncroWrapper<T> wrapper = new SyncroWrapper<T>( target, source, synchroProvider );
		
		process( wrapper, target, source );
		
		return target;
	}

	private static <T> void process( Wrapper<T> wrapper, T o ) {
		
		Class<?> clz = o.getClass();
		
		EqualsClass equalsClassA = clz.getAnnotation( EqualsClass.class );
		Assert.notNull( equalsClassA, "@EqualsClass for " + o.getClass().getSimpleName() );
		
		EnumSet<EqualsOption.Option> globalOptions = EnumSet.noneOf( EqualsOption.Option.class );
		EqualsOption equalsOptionA = clz.getAnnotation( EqualsOption.class );
		if( equalsOptionA != null ){
			globalOptions = EnumSet.copyOf( Arrays.asList( equalsOptionA.options()  ) );
		}
		
		switch( equalsClassA.workOn() ){
		
			case FIELDS:{
				// Sort methods because order matters for hashCode
				Field [] fields = clz.getDeclaredFields();
				Arrays.sort( fields, FIELD_SORTER );
				
				for( Field field : fields ){
					
					boolean isPrivate = Modifier.isPrivate( field.getModifiers() );
					
					EqualsField equalsFieldA = field.getAnnotation( EqualsField.class );
					
					equalsOptionA = field.getAnnotation( EqualsOption.class );
					EnumSet<EqualsOption.Option> localOptions = EnumSet.copyOf( globalOptions );
					if( equalsOptionA != null ){
						localOptions.addAll( Arrays.asList( equalsOptionA.options() ) );
					}
					
					boolean isInclude;
					if( equalsFieldA != null ){
						isInclude = equalsFieldA.include();
					} else {
						isInclude = equalsClassA.select() == Select.ALL;
						Transient transientA = field.getAnnotation( Transient.class );
						Id idA = field.getAnnotation( Id.class );
						if( transientA != null ) isInclude = false;
						if( idA != null ) isInclude = false;
					}
					
					String name = field.getName();
					if( 
							( equalsClassA.includePrivate() || !isPrivate )
							&& isInclude
					){
						try {
							wrapper.invoke( field, localOptions );
						} catch( Exception e ) {
							throw new RuntimeException( "Error in " + name, e );
						}
					}
				}
			} break;
				
			case METHODS:{
				
				// Sort methods because order matters for hashCode
				Method [] methods = clz.getDeclaredMethods();
				Arrays.sort( methods, METHOD_SORTER );
				
				for( Method method : methods ){
					
					boolean isPrivate = Modifier.isPrivate( method.getModifiers() );
					
					EqualsField equalsFieldA = method.getAnnotation( EqualsField.class );
					
					equalsOptionA = method.getAnnotation( EqualsOption.class );
					EnumSet<EqualsOption.Option> localOptions = EnumSet.copyOf( globalOptions );
					if( equalsOptionA != null ){
						localOptions.addAll( Arrays.asList( equalsOptionA.options() ) );
					}
					
					boolean isInclude;
					if( equalsFieldA != null ){
						isInclude = equalsFieldA.include();
					} else {
						isInclude = equalsClassA.select() == Select.ALL;
						Transient transientA = method.getAnnotation( Transient.class );
						Id idA = method.getAnnotation( Id.class );
						if( transientA != null ) isInclude = false;
						if( idA != null ) isInclude = false;
					}
					
					String name = method.getName();
					if( 
							( 
								( name.length() > 3 && name.startsWith( "get" )
										&& Character.isUpperCase( name.charAt( 3 ) )
								|| name.length() > 2 && name.startsWith( "is" )
										&& Character.isUpperCase( name.charAt( 2 ) ) )
							)
							&& method.getReturnType() != Void.class
							&& method.getGenericParameterTypes().length == 0
							&& ( equalsClassA.includePrivate() || !isPrivate )
							&& isInclude
					){
						try {
							wrapper.invoke( method, localOptions );
						} catch( Exception e ) {
							throw new RuntimeException( "Error in " + name, e );
						}
					}
				}
			}
		}
	}
	
	private static final Comparator<Method> METHOD_SORTER  = new Comparator<Method>() {

		@Override
		public int compare( Method o1, Method o2 ) {
			return o1.getName().compareTo( o2.getName() );
		}
		
	};
	
	private static final Comparator<Field> FIELD_SORTER  = new Comparator<Field>() {

		@Override
		public int compare( Field o1, Field o2 ) {
			return o1.getName().compareTo( o2.getName() );
		}
		
	};
	
	private static <T extends Synchronizable<?> > void process( CopyWrapper<T> wrapper, T target, T source ) {
		
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
				
				for( Field field : targetClz.getDeclaredFields() ){
					
					//Accessor getter = null;
					
					boolean isPrivate = Modifier.isPrivate( field.getModifiers() );
					
					EqualsField ef = field.getAnnotation( EqualsField.class );
					
					eo = field.getAnnotation( EqualsOption.class );
					EnumSet<EqualsOption.Option> localOptions = EnumSet.copyOf( globalOptions );
					if( eo != null ){
						localOptions.addAll( Arrays.asList( eo.options() ) );
					}
					
					boolean isInclude;
					if( ef != null ){
						isInclude = ef.include();
					} else {
						isInclude = ec.select() == Select.ALL;
						Transient tAn = field.getAnnotation( Transient.class );
						Id idAn = field.getAnnotation( Id.class );
						if( tAn != null ) isInclude = false;
						if( idAn != null ) isInclude = false;
					}
					
					String name = field.getName();
					if( 
							( ec.includePrivate() || !isPrivate )
							&& isInclude
					){
						
						try {
							
							wrapper.invoke( field, localOptions );
							
						} catch( Exception e ) {
							throw new RuntimeException( "Error in " + name, e );
						}
						
					}
				}
			} break;
				
			case METHODS:{
				
				for( Method getter : targetClz.getDeclaredMethods() ){
					
					//Accessor getter = null;
					
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
	
	/*
	public static interface Accessor {
		public boolean isPrivate();
		public <T extends Annotation> T getAnnotation( Class<T> annotationClass );
		public String getName();
		public boolean isGetable();
		public boolean isSetable();
	}
	
	private static class FieldAccessor implements Accessor {
		private final Field field;

		public FieldAccessor( Field field ) {
			this.field = field;
		}

		@Override
		public boolean isPrivate() {
			return Modifier.isPrivate( field.getModifiers() );
		}
		@Override
		public <T extends Annotation> T getAnnotation( Class<T> annotationClass ){
			return field.getAnnotation( annotationClass );
		}
		@Override
		public String getName(){
			return field.getName();
		}
	}
	private static class MethodAccessor implements Accessor {
		private static Method method;
		public MethodAccessor( Method method ){
			this.method = method;
		}
		@Override
		public boolean isPrivate() {
			return Modifier.isPrivate( method.getModifiers() );
		}
		
		@Override
		public <T extends Annotation> T getAnnotation( Class<T> annotationClass ){
			return method.getAnnotation( annotationClass );
		}
		@Override
		public String getName(){
			return method.getName();
		}
		@Override
		public boolean isGetable() {
			String name = method.getName();
			return
					( 
						( name.length() > 3 && name.startsWith( "get" )
								&& Character.isUpperCase( name.charAt( 3 ) )
						|| name.length() > 2 && name.startsWith( "is" )
								&& Character.isUpperCase( name.charAt( 2 ) ) )
					)
					&& method.getReturnType() != Void.class
					&& method.getGenericParameterTypes().length == 0
			;
		}
		@Override
		public boolean isSetable() {
			// TODO Auto-generated method stub
		}
	}
	*/
	
	private static String m2n( String methodName ){
		
		if( methodName.startsWith( "get" ) ) return methodName.substring( 3 );
		else if( methodName.startsWith( "is" ) ) return methodName.substring( 2 );
		else throw new IllegalArgumentException( "Not a valid getter: " + methodName );
		
	}
	
	private interface Wrapper<T> {
		public void invoke( Method method, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
		public void invoke( Field field, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	}
	
	private interface CopyWrapper<T> {
		public void invoke( Method setter, Method getter, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException;
		public void invoke( Field field, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException;
	}
	
	/*
	 * Uses commons EqualsBuilder to process equals
	 */
	private static final class EqualsWrapper<T> implements Wrapper<T> {
		
		final EqualsBuilder builder;
		final T o1, o2;
		
		EqualsWrapper( EqualsBuilder builder, T o1, T o2 ){
			this.builder = builder;
			this.o1 = o1; this.o2 = o2;
		}

		@Override
		public void invoke( Method method, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object v1 = method.invoke( o1 );
			Object v2 = method.invoke( o2 );
			
			v1 = applyOptions( v1, options );
			v2 = applyOptions( v2, options );
			
			v1 = reasonable( v1 );
			v2 = reasonable( v2 );
			
			builder.append( v1, v2 );
		}
		
		@Override
		public void invoke( Field field, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object v1 = field.get( o1 );
			Object v2 = field.get( o2 );
			
			v1 = applyOptions( v1, options );
			v2 = applyOptions( v2, options );
			
			v1 = reasonable( v1 );
			v2 = reasonable( v2 );
			
			builder.append( v1, v2 );
		}
	}
	
	/*
	 * Uses commons HashCodeBuilder to generate a hash code
	 */
	private static final class HashWrapper<T> implements Wrapper<T> {
	
		final HashCodeBuilder builder;
		final T o;
		
		HashWrapper( HashCodeBuilder builder, T o ){
			this.builder = builder;
			this.o = o;
		}
		
		@Override
		public void invoke( Method method, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = method.invoke( o );
			
			value = applyOptions( value, options );
			value = reasonable( value );
			
			builder.append( value );
			
		}
		
		@Override
		public void invoke( Field field, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = field.get( o );
			
			value = applyOptions( value, options );
			value = reasonable( value );
			
			builder.append( value );
		}
		
	}
	
	/*
	 * Uses own strong hash code builder
	 */
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
			value = reasonable( value );
			
			builder.append( value );
		}
		
		@Override
		public void invoke( Field field, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = field.get( o );
			
			value = applyOptions( value, options );
			value = reasonable( value );
			
			builder.append( value );
		}
	}
	
	/*
	 * directly sync on object into another
	 * uses SynchroProvider to do customisation
	 */
	private static final class SyncroWrapper<T extends Synchronizable<T>> implements CopyWrapper<T> {
		
		final T target;
		final T source;
		final SynchroMapper synchro;
		
		SyncroWrapper( T target, T source, SynchroMapper synchro ){
			this.target = target;
			this.source = source;
			this.synchro = synchro;
		}
		
		private Object sync( Object o ){
			
			Object result;
			
			// return original if no SynchroProvider
			if( synchro == null ) result = o;
			
			// use providers copyOf to make a copy
			else result = synchro.copyOf( o );
			
			return result;
		}
		
		private Object cloneEmpty( Object o ) throws InstantiationException, IllegalAccessException{
			
			if( o == null ) return 0;
			
			if( synchro == null ){
				Class<?> clz = o.getClass();
				return clz.newInstance();
			} else {
				return synchro.emptyInstanceOf( o );
			}
			
		}
		
		@Override
		public void invoke( Method setter, Method getter, EnumSet<EqualsOption.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException, InstantiationException {
			
			//Class<?> type = getter.getReturnType();
			
			Object tarVal = getter.invoke( target );
			Object srcVal = getter.invoke( source );
			
			tarVal = applyOptions( tarVal, options );
			srcVal = applyOptions( srcVal, options );
			
			// Do nothing if values are same
			if( tarVal == srcVal ) return;
			
			// Set null to null
			// From now srcVal cannot be null;
			if( srcVal == null ){
				setter.invoke( target, (Object)null );
				return;
			}
			
			// Do nothing if values equal
			if( srcVal.equals( tarVal ) ) return;
			
			// Cascade synchronise synchronisable values
			if( srcVal instanceof Synchronizable ){
				
				// This is ugly but i don't know better.
				@SuppressWarnings( { "rawtypes", "unchecked", "unused" } )
				Synchronizable t2 = Equals.synchronize(
						(Synchronizable)tarVal, (Synchronizable)srcVal, synchro );
				
				// This may be setting value to itself or setting a null to a new value
				setter.invoke( target, tarVal );
				
			} else {
				
				if( tarVal == null && (
						srcVal instanceof Set
						|| srcVal instanceof List
						|| srcVal instanceof Map
				) ){
					
					// Note that we *can't* handle unmodifiable sets
					// But they are of no great use to this anyway.
					
					tarVal = cloneEmpty( srcVal );
					setter.invoke( target, sync( tarVal ) );
						
				}
				
				// Set --------------------
				if( srcVal instanceof Set ){
					
					if( tarVal == null )
						throw new IllegalArgumentException( "'tarVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					Set<Object>
							srcSet = (Set<Object>)srcVal,
							tarSet = (Set<Object>)tarVal;
					
					// Delete from old what's not in new
					tarSet.retainAll( srcSet );
					
					// Keep the old ones and add only the new ones.
					for( Object o : srcSet ){
						if( ! tarSet.contains( o ) ){
							tarSet.add( sync( o ) );
						}
					}
					
				// List --------------------
				} else if( srcVal instanceof List ){
					
					if( tarVal == null )
						throw new IllegalArgumentException( "'tarVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					List<Object>
							srcList = (List<Object>)srcVal,
							tarList = (List<Object>)tarVal;
					
					for( Iterator<Object> it = tarList.iterator(); it.hasNext(); ){
						Object o = it.next();
						if( ! srcList.contains( o ) ){
							it.remove();
						}
					}
					for( Object o : srcList ){
						if( ! tarList.contains( o ) ){
							tarList.add( sync( o ) );
						}
					}
					Collections.sort( tarList, new List2ListSorter( srcList ) );
					
				// Map --------------------
				} else if( srcVal instanceof Map ){
					
					if( tarVal == null )
						throw new IllegalArgumentException( "'tarVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					Map<Object,Object>
							srcMap = (Map<Object,Object>)srcVal,
							tarMap = (Map<Object,Object>)tarVal;
					
					// Remove vanished keys
					List<Object> removeMe = new LinkedList<Object>();
					for( Object key : tarMap.keySet() ){
						if( ! srcMap.containsKey( key ) )
							removeMe.add( key );
					}
					for( Object key : removeMe ){
						tarMap.remove( key );
					}
					
					// Copy all and new keys
					for( Object key : srcMap.keySet() ){
						
						Object srcValue = srcMap.get( key );
						Object tarValue = tarMap.get( key );
							
						if( tarValue == null || !tarValue.equals( srcValue ) ){
							
							Object x = sync(srcValue);
							
							tarMap.put( key, x );
						}
					}
				
				} else {
				
					setter.invoke( target, sync( srcVal ) );
				}
			}
			
		}
		
		@Override
		public void invoke( Field field, EnumSet<EqualsOption.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException, InstantiationException {
			
			//Class<?> type = getter.getReturnType();
			
			Object tarVal = field.get( target );
			Object srcVal = field.get( source );
			
			tarVal = applyOptions( tarVal, options );
			srcVal = applyOptions( srcVal, options );
			
			// Do nothing if values are same
			if( tarVal == srcVal ) return;
			
			// Set null to null
			// From now srcVal cannot be null;
			if( srcVal == null ){
				field.set( target, (Object)null );
				return;
			}
			
			// Do nothing if values equal
			if( srcVal.equals( tarVal ) ) return;
			
			// Cascade synchronise synchronisable values
			if( srcVal instanceof Synchronizable ){
				
				// This is ugly but i don't know better.
				@SuppressWarnings( { "rawtypes", "unchecked", "unused" } )
				Synchronizable t2 = Equals.synchronize(
						(Synchronizable)tarVal, (Synchronizable)srcVal, synchro );
				
				// This may be setting value to itself or setting a null to a new value
				field.set( target, tarVal );
				
			} else {
				
				if( tarVal == null && (
						srcVal instanceof Set
						|| srcVal instanceof List
						|| srcVal instanceof Map
				) ){
					
					// Note that we *can't* handle unmodifiable sets
					// But they are of no great use to this anyway.
					
					tarVal = cloneEmpty( srcVal );
					field.set( target, sync( tarVal ) );
						
				}
				
				// Set --------------------
				if( srcVal instanceof Set ){
					
					if( tarVal == null )
						throw new IllegalArgumentException( "'tarVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					Set<Object>
							srcSet = (Set<Object>)srcVal,
							tarSet = (Set<Object>)tarVal;
					
					// Delete from old what's not in new
					tarSet.retainAll( srcSet );
					
					// Keep the old ones and add only the new ones.
					for( Object o : srcSet ){
						if( ! tarSet.contains( o ) ){
							tarSet.add( sync( o ) );
						}
					}
					
				// List --------------------
				} else if( srcVal instanceof List ){
					
					if( tarVal == null )
						throw new IllegalArgumentException( "'tarVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					List<Object>
							srcList = (List<Object>)srcVal,
							tarList = (List<Object>)tarVal;
					
					for( Iterator<Object> it = tarList.iterator(); it.hasNext(); ){
						Object o = it.next();
						if( ! srcList.contains( o ) ){
							it.remove();
						}
					}
					for( Object o : srcList ){
						if( ! tarList.contains( o ) ){
							tarList.add( sync( o ) );
						}
					}
					Collections.sort( tarList, new List2ListSorter( srcList ) );
					
				// Map --------------------
				} else if( srcVal instanceof Map ){
					
					if( tarVal == null )
						throw new IllegalArgumentException( "'tarVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					Map<Object,Object>
							srcMap = (Map<Object,Object>)srcVal,
							tarMap = (Map<Object,Object>)tarVal;
					
					// Remove vanished keys
					List<Object> removeMe = new LinkedList<Object>();
					for( Object key : tarMap.keySet() ){
						if( ! srcMap.containsKey( key ) )
							removeMe.add( key );
					}
					for( Object key : removeMe ){
						tarMap.remove( key );
					}
					
					// Copy all and new keys
					for( Object key : srcMap.keySet() ){
						
						Object srcValue = srcMap.get( key );
						Object tarValue = tarMap.get( key );
							
						if( tarValue == null || !tarValue.equals( srcValue ) ){
							
							Object x = sync(srcValue);
							
							tarMap.put( key, x );
						}
					}
				
				} else {
				
					field.set( target, sync( srcVal ) );
				}
			}
			
		}
		
	}
			
	/*
	 * make common adjustments to get this at least less insane
	 */
	private static final Object reasonable( Object o ){
		if( o != null ) {
			// Special treatment for currency which has no hashcode method
			// This leads to a somewhat changed behaviour since this is stable
			// over program runs and calling hashcode isn't.
			if( o instanceof Currency ){
				o = ( ((Currency)o).getCurrencyCode() );
			}
			// Enums have not stable hash code either
			// but a stable string representation
			if( o instanceof Enum<?> ) o = ((Enum<?>)o).name();
		}
		return o;
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
	
	public interface Synchronizable<T> {
		public void synchronizeFrom( T other );
		public T emptyInstance();
	}
	
	/*
	 * Sort one list so that it has the same object order as the other list
	 */
	private static final class List2ListSorter implements Comparator<Object> {
		
		List<?> list;
		List2ListSorter( List<?> list ){
			this.list = list;
		}

		@Override
		public int compare( Object o1, Object o2 ) {
			
			return Integer.valueOf( list.indexOf( o1 ) )
					.compareTo( Integer.valueOf( list.indexOf( o2 ) ) );
			
		}
		
	}
	
}
