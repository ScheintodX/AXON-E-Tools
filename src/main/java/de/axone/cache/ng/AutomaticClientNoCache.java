package de.axone.cache.ng;

import java.util.Collection;
import java.util.Map;

public class AutomaticClientNoCache<K,O>
	extends AbstractCacheEventProvider<K,O>
	implements CacheNG.AutomaticClient<K,O> {

	private final Stats stats = new DefaultStats( this );
	
	@Override
	public Map<K, O> fetch( Collection<K> keys,CacheNG.MultiValueAccessor<K, O> accessor ) {
		stats.miss();
		return accessor.fetch( keys );
	}

	@Override
	public O fetch( K key, CacheNG.SingleValueAccessor<K, O> accessor ) {
		stats.miss();
		return accessor.fetch( key );
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
	public void invalidateAll( boolean force ) {
		listenersInvalidateAll( force );
	}
	
	@Override
	public void invalidate( K key ) {
		listenersInvalidate( key );
	}
	
	@Override
	public boolean isCached( K key ) {
		return false;
	}

}
