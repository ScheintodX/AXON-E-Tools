package de.axone.cache.ng;

@FunctionalInterface
public interface CacheProvider<K,O> {

	public CacheNG.Cache<K,O> getCache();
}
