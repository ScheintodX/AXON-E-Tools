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
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.equals.EqualsClass.Select;
import de.axone.equals.SynchroMapper.DefaultSynchroMapper;
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
	
	static final Logger log = LoggerFactory.getLogger( Equals.class );
	
	private static final DefaultSynchroMapper DEFAULT_SYNCHRO_MAPPER = new DefaultSynchroMapper();
	
	
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
		HashVisitor<T> wrapper = new HashVisitor<T>( builder, object );
		
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
		EqualsVisitor<T> wrapper = new EqualsVisitor<T>( builder, o1, o2 );
		
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
		StrongHashVisitor<byte[],T> wrapper = new StrongHashVisitor<byte[],T>( builder, object );
		
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
	public static <T> T synchronize(
			T destination, T source, SynchroMapper synchroMapper ) {
		
		Assert.notNull( source, "source" );
		
		// Nothing to see here
		if( destination == null && source == null ) return null;
		
		T any = source != null ? source : destination;
		
		if( synchroMapper == null ){
			
			if( any instanceof Synchronizable ){
				
				synchroMapper = ((Synchronizable) any).mapper();
			} else {
				synchroMapper = DEFAULT_SYNCHRO_MAPPER;
			}
		}
		
		if( destination == null ){
			destination = (T)synchroMapper.emptyInstanceOf( null, source );
		}
		
		// No synchronisation for equal objects
		if( destination == source || destination.equals( source ) ) return destination;
		
		SyncroVisitor<T> wrapper = new SyncroVisitor<T>( destination, source, synchroMapper );
		
		process( wrapper, destination, source );
		
		return destination;
	}
	
	
	private static <T> EnumSet<EqualsOptions.Option> globalOptions( Class<?> clz ){
		
		EnumSet<EqualsOptions.Option> globalOptions = EnumSet.noneOf( EqualsOptions.Option.class );
		EqualsOptions equalsOptionA = clz.getAnnotation( EqualsOptions.class );
		if( equalsOptionA != null ){
			globalOptions = EnumSet.copyOf( Arrays.asList( equalsOptionA.value()  ) );
		}
		
		return globalOptions;
		
	}
	
	
	private static <T> void process( Visitor<T> wrapper, T destination, T source ) {
		
		Class<?> sourceClz = source.getClass();
		if( destination != null ){
			Class<?> destinationClz = destination.getClass();
			Assert.equal( destinationClz, "desstination and source class", sourceClz );
		}
		
		// Class's annotation
		EqualsClass equalsClassA = sourceClz.getAnnotation( EqualsClass.class );
		Assert.notNull( equalsClassA, "@EqualsClass for " + sourceClz.getSimpleName() );
		
		EnumSet<EqualsOptions.Option> globalOptions = globalOptions( sourceClz );
		
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
				EqualsOptions equalsOptionA = accessor.getAnnotation( EqualsOptions.class );
				EnumSet<EqualsOptions.Option> localOptions = EnumSet.copyOf( globalOptions );
				if( equalsOptionA != null ){
					localOptions.addAll( Arrays.asList( equalsOptionA.value() ) );
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
						throw new RuntimeException( "Error in '" + accessor.getName() + "'", e );
					}
					
				}
			}
		}
	}
	
	
	private interface Visitor<T> {
		
		public void invoke( Accessor accessor, EnumSet<EqualsOptions.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException;
		
	}
	
	
	/*
	 * Uses commons EqualsBuilder to process equals
	 */
	private static final class EqualsVisitor<T> implements Visitor<T> {
		
		final EqualsBuilder builder;
		final T o1, o2;
		
		EqualsVisitor( EqualsBuilder builder, T o1, T o2 ){
			this.builder = builder;
			this.o1 = o1; this.o2 = o2;
		}

		@Override
		public void invoke( Accessor accessor, EnumSet<EqualsOptions.Option> options )
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
	private static final class HashVisitor<T> implements Visitor<T> {
	
		final HashCodeBuilder builder;
		final T o;
		
		HashVisitor( HashCodeBuilder builder, T o ){
			this.builder = builder;
			this.o = o;
		}
		
		@Override
		public void invoke( Accessor accessor, EnumSet<EqualsOptions.Option> options )
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
	private static final class StrongHashVisitor<H,T> implements Visitor<T> {
	
		final StrongHashCodeBuilder<H> builder;
		final T o;
		
		StrongHashVisitor( StrongHashCodeBuilder<H> builder, T o ){
			this.builder = builder;
			this.o = o;
		}
		
		@Override
		public void invoke( Accessor accessor, EnumSet<EqualsOptions.Option> options )
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
		public Class<?> getType();
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
		
		@Override
		public Class<?> getType(){
			return field.getType();
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
		
		@Override
		public Class<?> getType(){
			return getter.getReturnType();
		}
	}
	
	
	/*
	 * directly sync on object into another
	 * uses SynchroMapper to do customisation
	 */
	private static final class SyncroVisitor<T> implements Visitor<T> {
		
		final T destination;
		final T source;
		final SynchroMapper sm;
		
		SyncroVisitor( T destination, T source, SynchroMapper mapper ){
			this.destination = destination;
			this.source = source;
			this.sm = mapper;
		}
		
		@Override
		public void invoke( Accessor accessor, EnumSet<EqualsOptions.Option> options ) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		
			Object dstVal = accessor.get( destination );
			Object srcVal = accessor.get( source );
			String name = accessor.getName();
			Class<?> type = accessor.getType();
			
			log.debug( "{}, {} <-- {}", new Object[]{ name, "("+type.getSimpleName()+")", dstVal, srcVal } );
			
			dstVal = applyOptions( dstVal, options );
			srcVal = applyOptions( srcVal, options );
			
			Object synced = sync( sm, name, dstVal, srcVal );
			
			// Do nothing if values are same
			if( synced == dstVal ) return;
			
			// This may be setting value to itself or setting a null to a new value
			accessor.set( destination, synced );
			
		}
		
		private static Object sync( SynchroMapper sm, String name, Object dstVal, Object srcVal ){
			
			// this my lead to set( null )
			if( srcVal == null ) return null;
			
			// Do nothing if values equal. This prevents setting.
			if( srcVal.equals( dstVal ) ) return dstVal;
			
			// Cascade synchronise values
			if( isEqualsAnnotated( srcVal ) ){
				
				if( dstVal == null ) dstVal = sm.emptyInstanceOf( name, srcVal );
				
				sm.synchronize( name, dstVal, srcVal );
				
				return dstVal;
				
			// "Normal values"
			} else if( srcVal instanceof Collection ){
					
				if( dstVal == null )
					dstVal = sm.emptyInstanceOf( name, srcVal );
				
				if( dstVal == null )
					throw new IllegalArgumentException( "'dstVal' is missing but should have been created beforehand. Perhaps unsupported Set" );
				
				@SuppressWarnings( { "unchecked" } )
				Collection<Object>
						src = (Collection<Object>)srcVal,
						dst = (Collection<Object>)dstVal;
				
				for( Iterator<Object> it = dst.iterator(); it.hasNext(); ){
					
					Object t = it.next();
					Object s = sm.find( name, src, t );
					
					if( s == null ){
						it.remove();
					}
				}
				
				for( Iterator<Object> it = src.iterator(); it.hasNext(); ){
					
					Object s = it.next();
					Object d = sm.find( name, dst, s );
					
					Object synced = sync( sm, name, d, s );
					
					if( synced != d ) dst.add( synced );
				}
				
				// Sort to original order if List
				if( src instanceof List ){
					List<?> srcList = (List<?>)srcVal,
					        dstList = (List<?>)dstVal;
					Collections.sort( dstList, new List2ListSorter( srcList ) );
				}
				
				return dstVal;
					
			// Map --------------------
			} else if( srcVal instanceof Map ){
					
				if( dstVal == null )
					dstVal = sm.emptyInstanceOf( name, srcVal );
				
				@SuppressWarnings( "unchecked" )
				Map<Object,Object>
						srcMap = (Map<Object,Object>)srcVal,
						dstMap = (Map<Object,Object>)dstVal;
				
				// Remove vanished keys
				for( Iterator<?> it = dstMap.keySet().iterator(); it.hasNext(); ){
					
					Object key = it.next();
					
					if( ! srcMap.containsKey( key ) ){
						it.remove();
					}
				}
				
				// Copy all and new keys
				for( Object key : srcMap.keySet() ){
					
					Object srcValue = srcMap.get( key );
					Object dstValue = dstMap.get( key );
					
					Object synced = sync( sm, name, dstValue, srcValue );
					
					if( synced != dstValue ){
						dstMap.put( key, synced );
					}
						
				}
				
				return dstMap;
			
			// Simple Value
			} else {
			
				return sm.copyOf( name, srcVal );
			}
			
		}
		
	}
	
	
	private static final boolean isEqualsAnnotated( Object o ){
		
		if( o == null ) return false;
		
		return o.getClass().getAnnotation( EqualsClass.class ) != null;
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
	private static final Object applyOptions( Object o, EnumSet<EqualsOptions.Option> options ){
		
		if( options.contains( EqualsOptions.Option.EMPTY_IS_NULL ) ){
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
