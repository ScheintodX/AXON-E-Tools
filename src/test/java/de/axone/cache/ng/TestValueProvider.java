package de.axone.cache.ng;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class TestValueProvider<K extends Comparable<K>,V> {

	final boolean threadsafe;
	final Range<K> validKeys;
	final Function<K,V> valueGen;
	final int delay;
	
	Map<K,AtomicInteger> count = new HashMap<>();
	
	boolean inAccess;
	
	TestValueProvider( Range<K> validKeys, Function<K,V> valueGen, boolean threadsafe, int delay ) {
		this.validKeys = validKeys;
		this.valueGen = valueGen;
		this.threadsafe = threadsafe;
		this.delay = delay;
	}
	
	TestValueProvider( Function<K,V> valueGen ) {
		this( null, valueGen, true, 0 );
	}
	
	public TestValueProvider<K,V> delayed( int ms ) {
		return new TestValueProvider<>( validKeys, valueGen, threadsafe, ms );
	}
	public TestValueProvider<K,V> threadsafe( boolean threadsafe) {
		return new TestValueProvider<>( validKeys, valueGen, threadsafe, delay );
	}
	public TestValueProvider<K,V> ranged( Range<K> validKeys ) {
		return new TestValueProvider<>( validKeys, valueGen, threadsafe, delay );
	}
	
	public boolean provides( K key ) {
		
		if( validKeys != null && ! validKeys.contains( key ) ) return false;
		else return true;
	}
	
	public int getCount( K key ) {
		
		AtomicInteger c = count.get( key );
		if( c == null ) return 0;
		return c.get();
	}
	public void resetCount() {
		count.clear();
	}
	
	
	public V get( K key ) {
		
		if( !threadsafe && inAccess )
				throw new ConcurrentModificationException( "Speak friend and enter. But only one at a time." );
		
		inAccess = true;
		try {
		
			if( !provides( key ) ) return null;
			
			if( delay > 0 ){
				try {
					Thread.sleep( delay );
				} catch( InterruptedException e ) {
					throw new RuntimeException( "Should not happen" );
				}
			}
			
			count( key );
			
			return valueGen.apply( key );
			
		} finally {
			
			inAccess = false;
		}
	}
	
	private synchronized void count( K key ) {
		
		AtomicInteger c = count.get( key );
		if( c == null ) {
			c = new AtomicInteger( 0 );
			count.put( key, c );
		}
		c.incrementAndGet();
	}
	
	public Map<K,V> get( Iterable<K> keys ) {
		
		HashMap<K,V> result = new HashMap<>();
		
		for( K key : keys ) {
			
			result.put( key, get( key ) );
		}
		
		return result;
	}
	
	static class Range<K extends Comparable<K>> {
		
		final K min, max;
		
		private Range( K min, K max ) {
			this.min = min;
			this.max = max;
		}
		
		public static <K extends Comparable<K>, V> Range<K> of( K min, K max ) {
			
			return new Range<>( min, max );
		}
		
		public boolean contains( K val ) {
			
			return val.compareTo( min ) >= 0 && val.compareTo( max ) <= 0;
		}
	}
	
}
