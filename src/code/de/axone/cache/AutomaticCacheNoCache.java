package de.axone.cache;

import java.util.Collection;
import java.util.Map;

public class AutomaticCacheNoCache<K,V> implements AutomaticCache<K, V> {

	private final Stats stats = new Stats( this );
	
	@Override
	public void flush() {}

	@Override
	public Map<K, V> get( Collection<K> keys, DataAccessor<K, V> accessor ) {
		stats.miss();
		return accessor.get( keys );
	}

	@Override
	public V get( K key, DataAccessor<K, V> accessor ) {
		stats.miss();
		return accessor.get( key );
	}

	@Override
	public void put( K key, V value ) {
		throw new UnsupportedOperationException( "Cannot put in NoCache" );
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Stats stats() {
		return stats;
	}

	@Override
	public int capacity() {
		return 0;
	}

	@Override
	public void remove( K key ) {
		// NOP
	}

}
