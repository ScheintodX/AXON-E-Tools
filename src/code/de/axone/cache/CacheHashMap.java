package de.axone.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * BackendCache as implemented by a HashMap
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class CacheHashMap<K,V> extends ConcurrentHashMap<K,V> implements Cache.Direct<K,V> {

	@Override
	public String info() {
		return "HashMap ("+size()+")";
	}

	@Override
	public int capacity() {
		return -1;
	}
	
}
