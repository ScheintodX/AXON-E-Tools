package de.axone.cache;

import java.util.Collection;
import java.util.Map;

/**
 * A DataAccessor accesses Objects in a Way that the Cache can handle
 * them.
 *
 * This method is needed so that the cache can manage the fetching of
 * missing objects. This fetching ist done by the Accessor who knows
 * how.
 *
 * @author flo
 *
 * @param <K> The key for getching
 * @param <V> The Objects which are to be fetched
 */
public interface DataAccessor<K,V> {

	public V get( K key );
	public Map<K,V> get( Collection<K> keys );

}
