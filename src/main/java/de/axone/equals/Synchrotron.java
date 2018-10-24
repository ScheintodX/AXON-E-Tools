package de.axone.equals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

// TODO: Evtl. Konzepte von Equals übernehmen
/**
 * Synchronizes Lists of different types while converting one type into another
 * 
 * Keeps old collection elements if they haven't changed
 * 
 * Keeps old collectoin intact
 * 
 * @author flo
 */
public class Synchrotron {

	public static <D,S, DC extends Collection<D>, SC extends Collection<S>, K>
			DC sync( DC dst, SC src, Function<S,D> createF, BiConsumer<D,S> updateF, Function<D,K> dstKeyF, Function<S,K> srcKeyF ) {
		
		return sync( dst, src, new SynOp<D,S,K>() {

			@Override
			public D create( S src ) { return createF.apply( src ); }

			@Override
			public void update( D dst, S src ) { updateF.accept( dst, src ); }

			@Override
			public K dstKey( D dst ) { return dstKeyF.apply( dst ); }

			@Override
			public K srcKey( S src ) { return srcKeyF.apply( src ); }
			
		} );
	}
	
	public static <D,S, DC extends Collection<D>, SC extends Collection<S>, K>
			DC sync(DC dst, SC src,  SynOp<D,S,K> SYNC  ) {
		
		Map<K,D> dstMap = new HashMap<>( src.size() );
		Map<K,S> srcMap = new HashMap<>( dst.size() );
		
		for( D it : dst ) dstMap.put( SYNC.dstKey( it ), it );
		for( S it : src ) srcMap.put( SYNC.srcKey( it ), it );
		
		// 1. remove surplus elements in dst
		for( D it : dstMap.values() ) {
			if( ! srcMap.containsKey( SYNC.dstKey( it ) ) ) {
				dst.remove( it );
			}
		}
		
		// 2. create / copy elements
		for( S it : srcMap.values() ) {
			
			K key = SYNC.srcKey( it );
			D dstIt = dstMap.get( key );
			
			if( dstIt == null ) {
				dstIt = SYNC.create( it );
				dst.add( dstIt );
			}
			
			SYNC.update( dstIt, it );
		}
		
		return dst;
	}

	/**
	 * Provides Methods for synchronization
	 * 
	 * @author flo
	 *
	 * @param <D> destination type
	 * @param <S> source type
	 * @param <K> key type for identity
	 */
	public interface SynOp<D,S,K> {
		
		/**
		 * @return one destination object
		 * 
		 * @param src
		 */
		D create( S src );
		
		/**
		 * updated object
		 * 
		 * @param dst
		 * @param src
		 */
		void update( D dst, S src );
		
		/**
		 * @param dst
		 * 
		 * @return one object identifying the given object. Must be useable as map key
		 */
		K dstKey( D dst );
		
		/**
		 * @param src
		 * 
		 * @return one object identifying the given object. Must be useable as map key
		 */
		K srcKey( S src );
	}

}
