package de.axone.cache;

import java.util.Collection;
import java.util.Map;

public interface AutomaticCache<K, V> {

	public abstract Map<K, V> get( Collection<K> keys, DataAccessor<K, V> accessor );

	public abstract V get( K key, DataAccessor<K, V> accessor );

	public abstract int size();

	public abstract void put( K key, V value );

	public abstract void flush();

	public abstract Stats stats();

	public class Stats {
		
		private int accesses, hits;
		private AutomaticCache<?,?> cache;
		
		public Stats( AutomaticCache<?,?> cache ){
			this.cache = cache;
		}
		
		void hit(){
			accesses++;
			hits++;
		}
		
		void miss(){
			accesses++;
		}
		
		@Override
		public String toString(){
			StringBuilder result = new StringBuilder();
			result
				.append( "Ratio: " + hits + "/" + accesses + " hits =" + (int)((double)hits/accesses*100) + "%\n" )
			    .append( " Full: " + cache.size() + " entries" )
			;
			return result.toString();
		}
	}
}
