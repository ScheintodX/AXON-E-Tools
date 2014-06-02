package de.axone.equals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.equals.EqualsClass.Select;
import de.axone.exception.Assert;

/**
 * This a Helper class for implementing the equals and the hashcode
 * method. Additionally it provides a synchronize method to 'clone'
 * one instance into another.
 * 
 * The relation of these three methods is:
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
 * TODO: "Synchronizable" ist eigentlich vollkommen überflüssig.
 * Die Annotations sollte das alleine hinkriegen. Ausserdem ist immer
 * noch nicht klar, was nun genau das Interface oder die Annotation auslösen.
 * Generell ist das hier noch alles zu chaotisch/unübersichtlich
 *
 */
public class Equals {
	
	private static final Logger log = LoggerFactory.getLogger( Equals.class );
	
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
		
		process( wrapper, null, object );
		
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
		
		process( wrapper, null, o1 );
		
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
		
		process( wrapper, null, object );
		
		return builder.toHashCode();
	}
	
	/**
	 * Synchronise 'destination' so that it will equals source.
	 * 
	 * Formally: <tt>synchronize( o1, o2 ) => equals( o1, o2 ) == true</tt>
	 * 
	 * This is done 'in place'. No copy of 'target' is created
	 * However if 'target' is null a new instance will be created using
	 * the the default constructor.
	 * 
	 * If custom behaviour is needed a custom SynchroMapper can be provided.
	 * 
	 * Do this with the the fewest possible (hopefully) amount of fields
	 * 
	 * You can specify a SynchroMapper which can process the fields which
	 * are copied. (But not the ones which aren't)
	 * 
	 * @throws NoSuchMethodException if there is a mismatch between setter and getter
	 * 
	 * @param <T>
	 * @param destination
	 * @param source
	 * @param synchroMapper to do coping or <tt>null</tt> to use DefaultSynchroMapper
	 * @return the destination (the original, not a copy!)
	 */
	@SuppressWarnings( "unchecked" )
	public static <T extends Synchronizable<T>> T synchronize(
			T destination, T source, SynchroMapper synchroMapper ) {
		
		Assert.notNull( source, "source" );
		
		if( synchroMapper == null )
			synchroMapper = DEFAULT_SYNCHRO_MAPPER;
		
		if( destination == null ){
			destination = (T)synchroMapper.emptyInstanceOf( null, source );
		}
		
		// No synchronisation for equal objects
		if( destination == source || destination.equals( source ) ) return destination;
		
		SyncroWrapper<T> wrapper = new SyncroWrapper<T>( destination, source, synchroMapper );
		
		process( wrapper, destination, source );
		
		return destination;
	}
	
	private static <T> EnumSet<EqualsOption.Option> globalOptions( Class<?> clz ){
		
		EnumSet<EqualsOption.Option> globalOptions = EnumSet.noneOf( EqualsOption.Option.class );
		EqualsOption equalsOptionA = clz.getAnnotation( EqualsOption.class );
		if( equalsOptionA != null ){
			globalOptions = EnumSet.copyOf( Arrays.asList( equalsOptionA.options()  ) );
		}
		
		return globalOptions;
		
	}
	
	private static <T> void process( Wrapper<T> wrapper, T destination, T source ) {
		
		Class<?> sourceClz = source.getClass();
		if( destination != null ){
			Class<?> destinationClz = destination.getClass();
			Assert.equal( destinationClz, "desstination and source class", sourceClz );
			Assert.isTrue( wrapper instanceof CopyWrapper, "Must use an CopyWrapper" );
		}
		
		// Class's annotation
		EqualsClass equalsClassA = sourceClz.getAnnotation( EqualsClass.class );
		Assert.notNull( equalsClassA, "@EqualsClass for " + sourceClz.getSimpleName() );
		
		EnumSet<EqualsOption.Option> globalOptions = globalOptions( sourceClz );
		
		// Build accessor list either of fields or methods
		List<Accessor> accessors;
		switch( equalsClassA.workOn() ){
		
			case FIELDS:{
				// Sort methods because order matters for hashCode
				Field [] fields = sourceClz.getDeclaredFields();
				Arrays.sort( fields, FIELD_SORTER );
				
				accessors = new ArrayList<>( fields.length );
				for( Field field : fields ){
					accessors.add( new FieldAccessor( field ) );
				}
			} break;
				
			case METHODS:{
				
				// Sort methods because order matters for hashCode
				Method [] methods = sourceClz.getDeclaredMethods();
				Arrays.sort( methods, METHOD_SORTER );
				
				accessors = new ArrayList<>( methods.length );
				for( Method method : methods ){
					
					accessors.add( new MethodAccessor( sourceClz, method ) );
				}
			} break;
			
			default:
				throw new IllegalArgumentException( "Unknown workOn: " + equalsClassA.workOn() );
		}
		
		for( Accessor accessor : accessors ){
			
			if( accessor.isGetable() ){
				
				// Field's annotation
				EqualsField equalsFieldA = accessor.getAnnotation( EqualsField.class );
				
				// Field's options + global options
				EqualsOption equalsOptionA = accessor.getAnnotation( EqualsOption.class );
				EnumSet<EqualsOption.Option> localOptions = EnumSet.copyOf( globalOptions );
				if( equalsOptionA != null ){
					localOptions.addAll( Arrays.asList( equalsOptionA.options() ) );
				}
				
				boolean isInclude;
				if( equalsFieldA != null ){
					isInclude = equalsFieldA.include();
				} else {
					isInclude = equalsClassA.select() == Select.ALL;
					Transient tAn = accessor.getAnnotation( Transient.class );
					Id idAn = accessor.getAnnotation( Id.class );
					if( tAn != null ) isInclude = false;
					if( idAn != null ) isInclude = false;
				}
				
				if( 
						!accessor.isStatic()
						&& ( equalsClassA.includePrivate() || !accessor.isPrivate() )
						&& isInclude
				){
					
					try {
						
						if( destination != null && !accessor.isSetable() ){
							log.warn( "Skipping missing setter for: {}/{}", sourceClz.getSimpleName(), accessor.getName() );
							continue;
						}
				
						wrapper.invoke( accessor, localOptions );
						
					} catch( Exception e ) {
						throw new RuntimeException( "Error in " + accessor.getName(), e );
					}
					
				}
			}
		}
	}
	
	private interface Wrapper<T> {
		public void invoke( Accessor accessor, EnumSet<EqualsOption.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException;
	}
	
	private interface CopyWrapper<T> extends Wrapper<T> {}
	
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
		public void invoke( Accessor accessor, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object v1 = accessor.get( o1 );
			Object v2 = accessor.get( o2 );
			
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
		public void invoke( Accessor accessor, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = accessor.get( o );
			
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
		public void invoke( Accessor accessor, EnumSet<EqualsOption.Option> options )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			
			Object value = accessor.get( o );
			
			value = applyOptions( value, options );
			value = reasonable( value );
			
			builder.append( value );
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
	
	public static interface Accessor {
		public boolean isPrivate();
		public boolean isStatic();
		public <T extends Annotation> T getAnnotation( Class<T> annotationClass );
		public String getName();
		public boolean isGetable();
		public boolean isSetable();
		public Object get( Object o )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
		public void set( Object o, Object value )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException;
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
		public boolean isStatic() {
			return Modifier.isStatic( field.getModifiers() );
		}
		@Override
		public <T extends Annotation> T getAnnotation( Class<T> annotationClass ){
			return field.getAnnotation( annotationClass );
		}
		@Override
		public String getName(){ return field.getName(); }

		@Override
		public boolean isGetable() { return true; }

		@Override
		public boolean isSetable() { return true; }

		@Override
		public Object get( Object o ) throws IllegalArgumentException, IllegalAccessException {
			return field.get( o );
		}

		@Override
		public void set( Object o, Object value ) throws IllegalArgumentException, IllegalAccessException {
			field.set( o, value );
		}
	}
	
	private static class MethodAccessor implements Accessor {
		
		private final Class<?> clazz;
		private final Method getter;
		private Method setter; // cached
		private String subname; // cached
		
		public MethodAccessor( Class<?> clazz, Method getter ){
			this.clazz = clazz;
			this.getter = getter;
		}
		@Override
		public boolean isPrivate() {
			return Modifier.isPrivate( getter.getModifiers() );
		}
		@Override
		public boolean isStatic() {
			return Modifier.isStatic( getter.getModifiers() );
		}
		
		@Override
		public <T extends Annotation> T getAnnotation( Class<T> annotationClass ){
			return getter.getAnnotation( annotationClass );
		}
		
		@Override
		public String getName(){
			
			String subname = subname();
			
			if( subname == null )
				throw new IllegalArgumentException( "Must start with get/is" );
			
			return tLc( subname );
			
		}
		
		private static final String tLc( String name ){
			return name.substring( 0,1 ).toLowerCase() + name.substring( 1 );
		}
	
		
		@Override
		public boolean isGetable() {
			
			String subname = subname();
			return
					subname != null 
					&& getter.getReturnType() != Void.class
					&& getter.getGenericParameterTypes().length == 0
			;
		}
		
		@Override
		public boolean isSetable() {
				try {
					setter();
				} catch( NoSuchMethodException | SecurityException e ) {
					return false;
				}
				return true;
		}
		
		private String subname(){
			
			if( subname == null ){
				String name = getter.getName();
				if( name.startsWith( "get" ) ) subname = name.substring( 3 );
				else if( name.startsWith( "is" ) ) subname = name.substring( 2 );
			}
			return subname;
			
		}
		
		private Method setter() throws NoSuchMethodException, SecurityException{
			
			if( setter == null ){
			
				String subname = subname();
				
				if( subname == null )
					throw new NoSuchMethodException( "Must start with get/is" );
				
				String setterName = "set" + subname();
				
				Class<?> getterType = getter.getReturnType();
				
				setter = clazz.getMethod( setterName, getterType );
			}
			return setter;
		}
		
		@Override
		public Object get( Object o ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException {
			return getter.invoke( o );
		}
		@Override
		public void set( Object o, Object value )
				throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
			setter().invoke( o, value );
			
		}
	}
	
	private static final DefaultSynchroMapper DEFAULT_SYNCHRO_MAPPER = new DefaultSynchroMapper();
	
	public static class DefaultSynchroMapper implements SynchroMapper {

		/**
		 * Default implementation of copyOf does not create a copy but
		 * returns a reference to same object.
		 */
		@Override
		public Object copyOf( String name, Object object ) {
			
			log.debug( "copyOf: {}", name );
			
			return object;
		}

		@Override
		public Object emptyInstanceOf( String name, Object object ) {
			
			log.debug( "emptyInstanceOf: {}", name );
			
			if( object == null ) return null;
			
			Class<?> clz = object.getClass();
			try {
				return clz.newInstance();
			} catch( ReflectiveOperationException e ) {
				throw new RuntimeException( e );
			}
		}

		@Override
		public Object find( String name, Collection<?> collection, Object src ) {
			
			Object result = null;
			
			for( Object t : collection ){
				if( t.equals( src ) ){
					result = t;
					break;
				}
			}
			
			log.trace( "find: {} {} -> {}", new Object[]{ name, src, result } );
			
			return result;
		}

		@SuppressWarnings( { "rawtypes", "unchecked" } )
		@Override
		public void synchronize( String name, Object dst, Object src ) {
			
			if( src instanceof Synchronizable ){
				/*
				Equals.synchronize(
						(Synchronizable)dst, (Synchronizable)src, this );
				*/
				((Synchronizable)dst).synchronizeFrom( src );
			} else {
				throw new IllegalArgumentException( name + " not Synchronizable" );
			}
		}
	}
	
	/*
	 * directly sync on object into another
	 * uses SynchroMapper to do customisation
	 */
	private static final class SyncroWrapper<T extends Synchronizable<T>> implements CopyWrapper<T> {
		
		final T destination;
		final T source;
		final SynchroMapper sm;
		
		SyncroWrapper( T destination, T source, SynchroMapper mapper ){
			this.destination = destination;
			this.source = source;
			this.sm = mapper;
		}
		
		@Override
		public void invoke( Accessor accessor, EnumSet<EqualsOption.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		
			Object dstVal = accessor.get( destination );
			Object srcVal = accessor.get( source );
			
			String dstClz = dstVal != null ? dstVal.getClass().getSimpleName() : "/NULL/";
			
			log.debug( "{}, {} <-- {}", new Object[]{ accessor.getName(), "("+dstClz+")", dstVal, srcVal } );
			
			dstVal = applyOptions( dstVal, options );
			srcVal = applyOptions( srcVal, options );
			
			// Do nothing if values are same
			if( dstVal == srcVal ) return;
			
			// Set null to null
			// From now srcVal cannot be null;
			if( srcVal == null ){
				accessor.set( destination, (Object)null );
				return;
			}
			
			// Do nothing if values equal
			if( srcVal.equals( dstVal ) ) return;
			
			// Cascade synchronise synchronisable values
			if( srcVal instanceof Synchronizable ){
				
				if( dstVal == null ) dstVal = sm.emptyInstanceOf( accessor.getName(), srcVal );
				
				sm.synchronize( accessor.getName(), dstVal, srcVal );
				
				// This may be setting value to itself or setting a null to a new value
				accessor.set( destination, dstVal );
				
			} else {
				
				// First create Set / List / Map if none there
				if( dstVal == null && (
						srcVal instanceof Set
						|| srcVal instanceof List
						|| srcVal instanceof Map
				) ){
					
					// Note that we *can't* handle unmodifiable sets
					// But they are of no great use to this anyway.
					
					dstVal = sm.emptyInstanceOf( accessor.getName(), srcVal );
					accessor.set( destination, sm.copyOf( accessor.getName(), dstVal ) );
						
				}
				
				
				// List --------------------
				if( srcVal instanceof Collection ){
					
					if( dstVal == null )
						throw new IllegalArgumentException( "'dstVal' is missing" );
					
					@SuppressWarnings( { "unchecked" } )
					Collection<Object>
							src = (Collection<Object>)srcVal,
							dst = (Collection<Object>)dstVal;
					
					for( Iterator<Object> it = dst.iterator(); it.hasNext(); ){
						Object t = it.next();
						Object s = sm.find( accessor.getName(), src, t );
						if( s == null ){
							it.remove();
						}
					}
					for( Iterator<Object> it = src.iterator(); it.hasNext(); ){
						Object s = it.next();
						Object d = sm.find( accessor.getName(), dst, s );
						if( d != null ){
							if( d instanceof Synchronizable ){
								sm.synchronize( accessor.getName(), d, s );
							} else {
								dst.remove( d );
								dst.add( sm.copyOf( accessor.getName(), s ) );
							}
						} else {
							dst.add( sm.copyOf( accessor.getName(), s ) );
						}
					}
					
					if( dst instanceof List && src instanceof List ){
						@SuppressWarnings( "unchecked" )
						// Must be srcVal/dstVal instead of src/dst because javac complains if run from ant
						List<T> srcList = (List<T>)srcVal,
						        dstList = (List<T>)dstVal;
						Collections.sort( dstList, new List2ListSorter( srcList ) );
					}
					
				// Map --------------------
				} else if( srcVal instanceof Map ){
					
					if( dstVal == null )
						throw new IllegalArgumentException( "'dstVal' is missing" );
					
					@SuppressWarnings( "unchecked" )
					Map<Object,Object>
							srcMap = (Map<Object,Object>)srcVal,
							dstMap = (Map<Object,Object>)dstVal;
					
					// Remove vanished keys
					List<Object> removeMe = new LinkedList<Object>();
					for( Object key : dstMap.keySet() ){
						if( ! srcMap.containsKey( key ) )
							removeMe.add( key );
					}
					for( Object key : removeMe ){
						dstMap.remove( key );
					}
					
					// Copy all and new keys
					for( Object key : srcMap.keySet() ){
						
						Object srcValue = srcMap.get( key );
						Object dstValue = dstMap.get( key );
							
						if( dstValue != null ){
							
							if( !dstValue.equals( srcValue ) ){
							
								if( dstValue instanceof Synchronizable ){
									sm.synchronize( accessor.getName(), dstValue, srcValue );
								} else {
									Object x = sm.copyOf( accessor.getName(), srcValue );
									dstMap.put( key, x );
								}
							}
						} else {
							Object x = sm.copyOf( accessor.getName(), srcValue );
							dstMap.put( key, x );
						}
								
					}
				
				} else {
				
					accessor.set( destination, sm.copyOf( accessor.getName(), srcVal ) );
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
	
	/*
	 * change value according to options
	 */
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
		//public T emptyInstance();
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
