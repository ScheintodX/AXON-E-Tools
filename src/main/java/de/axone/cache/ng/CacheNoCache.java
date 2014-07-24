package de.axone.cache.ng;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.axone.cache.ng.CacheNG.Realm;


/**
 * BackendCache which does not cache anything
 * 
 * @author flo
 *
 * @param <K>
 * @param <O>
 */
public class CacheNoCache<K,O> implements CacheNG.Cache<K,O> {
	
	private final Realm<?,?> realm;
	
	public CacheNoCache( Realm<?,?> realm ){
		this.realm = realm;
	}

	@Override
	public Set<K> keySet() {
		return new HashSet<K>();
	}

	@Override
	public Collection<O> values() {
		return new LinkedList<O>();
	}

	@Override
	public boolean isCached( K key ) {
		return false;
	}

	@Override
	public O fetch( K key ) {
		return null;
	}

	@Override
	public void put( K key, O value ) {}

	@Override
	public void invalidate( K key ) {}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public String info() {
		return "no caching: " + realm.name();
	}

	@Override
	public int capacity() {
		return 0;
	}

	@Override
	public CacheNG.Cache.Entry<O> fetchEntry( K key ) {
		return null;
	}

	@Override
	public void invalidateAll( boolean force ) {}

	@Override
	public String toString() {
		return info();
	}

	@Override
	public double ratio() {
		// No caching so zero is correct
		return 0;
	}
	
	
}
