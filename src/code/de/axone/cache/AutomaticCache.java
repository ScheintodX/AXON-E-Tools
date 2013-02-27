package de.axone.cache;

import java.util.Collection;
import java.util.Formatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public interface AutomaticCache<K, V> {

	public abstract Map<K, V> get( Collection<K> keys, DataAccessor<K, V> accessor );

	public abstract V get( K key, DataAccessor<K, V> accessor );

	public abstract int size();
	
	public abstract int capacity();

	void put( K key, V value );

	public abstract void flush();

	public abstract Stats stats();

	public class Stats {
		
		private AtomicLong accesses = new AtomicLong(), hits = new AtomicLong();
		private AutomaticCache<?,?> cache;
		
		public Stats( AutomaticCache<?,?> cache ){
			this.cache = cache;
		}
		
		void hit(){
			accesses.incrementAndGet();
			hits.incrementAndGet();
		}
		
		void miss(){
			accesses.incrementAndGet();
		}
		
		@Override
		public String toString(){
			StringBuilder result = new StringBuilder();
			@SuppressWarnings( "resource" )
			Formatter format = new Formatter( result );
			long hits = this.hits.get();
			long accesses = this.accesses.get();
			format.format( "Ratio: %d/%d hits = %.1f%% | Full: %d/%d entries = %.1f%%",
					hits, accesses, 100.0*hits/accesses,
					cache.size(), cache.capacity(), 100.0*cache.size()/cache.capacity() );
			return result.toString();
		}
	}
}
