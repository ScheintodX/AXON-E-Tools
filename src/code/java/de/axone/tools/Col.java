package de.axone.tools;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Col {

	/**
	 * Try to make an empty clone of a collection
	 * 
	 * This is done using reflection. Note that this method fails if the given
	 * collections doesn't habe a public contructor
	 * 
	 * @param <V>
	 * @param <C>
	 * @param col
	 * @return an empty clone
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <V,C extends Collection<V>> C emptyClone( C collection )
	throws InstantiationException, IllegalAccessException{
		
		@SuppressWarnings( "unchecked" ) // This should work without warning ...
		Class<? extends C> clazz = (Class<? extends C>)collection.getClass();
		
		C result = clazz.newInstance();
		
		return result;
	}
	
	/**
	 * Processes every element in a Collection using the given Processor
	 * 
	 * The elements are taken from src, then processed and then 
	 * put into dst
	 * 
	 * @param <V>
	 * @param <Cd>
	 * @param <Cs>
	 * @param dst
	 * @param src
	 * @param processor to use for processing
	 * @return a the Collection given as dst with the processed elements
	 */
	public static <V,Cd extends Collection<V>, Cs extends Collection<V>>
	Cd process( Cd dst, Cs src, Processor<V> processor ){
		
		for( V value : src ){
			dst.add( processor.process( value ) );
		}
		return dst;
	}
	
	/**
	 * Process every element in a Collection and return a new Collection
	 * of the same type with the processed elements.
	 * 
	 * This is done using reflection. Note that this method fails if the given
	 * collections doesn't habe a public contructor
	 * 
	 * @param <V>
	 * @param <C>
	 * @param src
	 * @param processor to use for processing
	 * @return a new Collection with the processed elements
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <V,C extends Collection<V>> C process( C src, Processor<V> processor )
	throws InstantiationException, IllegalAccessException{
		
		C dst = emptyClone( src );
		
		for( V value : src ){
			dst.add( processor.process( value ) );
		}
		return dst;
	}
	
	/**
	 * Processes every element in the Collection using the give Processor
	 * 
	 * WARNING: The Collections must be editable. This is not necessarily the
	 * case. E.g. wrapped Arrays are immutable.
	 * 
	 * @param <V>
	 * @param <C>
	 * @param collection
	 * @param processor
	 * @return
	 */
	public static <V, C extends Collection<V>> C processInPlace( C collection, Processor<V> processor ){
	
		ArrayList<V> temp = new ArrayList<V>( collection );
		collection.clear();
		for( V value : temp ){
			collection.add( processor.process( value ) );
		}
		return collection;
	}
	
	/**
	 * Process every element in a Collection and return a new Collection
	 * of the same type with the processed elements.
	 * 
	 * This is like process but does throw Errors instead of catchable exceptions.
	 * 
	 * WARNING: This method should be used with extreme care. It will fail e.g. for
	 * <code>process( Arrays.asList( new String[]{ "A", "B", "C" } ) )</code> because
	 * the resulting Arrays#ArrayList has no public empty constructor.
	 * 
	 * @param <V>
	 * @param <C>
	 * @param src
	 * @param conv
	 * @see #process(Collection, Collection, Processor)
	 * @return
	 */
	public static <V,C extends Collection<V>> C processUnchecked( C src, Processor<V> conv ){
		try {
			E.rr( src );
			return process( src, conv );
		} catch( InstantiationException e ) {
			throw new Error( "Instantiation error while converting", e );
		} catch( IllegalAccessException e ) {
			throw new Error( "Access error while converting", e );
		}
	}
	
	public interface Processor<T> {
		public T process( T value );
	}
	
}
