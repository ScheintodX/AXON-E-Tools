package de.axone.cache;

import java.util.HashMap;

/**
 * BackendCache as implemented by a HashMap
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class CacheHashMap<K,V> extends HashMap<K,V> implements Cache.Direct<K,V> {

	@Override
	public String info() {
		return "HashMap ("+size()+")";
	}
	
}
