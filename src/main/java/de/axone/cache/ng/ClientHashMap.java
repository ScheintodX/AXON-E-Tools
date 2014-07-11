package de.axone.cache.ng;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNG.Realm;

/**
 * BackendCache as implemented by a HashMap
 * @author flo
 *
 * @param <K>
 * @param <V>
 */
public class ClientHashMap<K,V> implements Client.Direct<K,V> {
	
	private final Realm name;
	private final Map<K,Client.Entry<V>> backend;
	
	public ClientHashMap( Realm name ){
		
		this.name = name;
		this.backend = Collections.synchronizedMap( new HashMap<K,Client.Entry<V>>() );
	}

	@Override
	public String info() {
		return "HashMap '" + name + "'("+size()+")";
	}

	@Override
	public int capacity() {
		return -1;
	}

	@Override
	public void put( K key, V value ) {
		putEntry( key, new DefaultEntry<>( value ) );
	}

	@Override
	public void putEntry( K key, Client.Entry<V> entry ) {
		backend.put( key, entry );
	}

	@Override
	public V fetch( K key ) {
		Client.Entry<V> entry = fetchEntry( key );
		if( entry == null ) return null;
		return entry.data();
	}

	@Override
	public Client.Entry<V> fetchEntry( K key ) {
		return backend.get( key );
	}

	@Override
	public boolean isCached( K key ) {
		return backend.containsKey( key );
	}

	@Override
	public void invalidate( K key ) {
		backend.remove( key );
	}

	@Override
	public void invalidateAll() {
		backend.clear();
	}

	@Override
	public int size() {
		return backend.size();
	}

	@Override
	public Set<K> keySet() {
		return backend.keySet();
	}

	@Override
	public Iterable<V> values() {
		return new IterableValues<>( backend.values() );
	}
	
	private static class IterableValues<V> implements Iterable<V> {
		
		private final Collection<Entry<V>> values;

		public IterableValues( Collection<Entry<V>> values ) {
			this.values = values;
		}

		@Override
		public Iterator<V> iterator() {
			return new ValueIterator();
		}
		
		private class ValueIterator implements Iterator<V> {
			
			Iterator<Entry<V>> entryIterator = values.iterator();

			@Override
			public boolean hasNext() {
				return entryIterator.hasNext();
			}

			@Override
			public V next() {
				return entryIterator.next().data();
			}

			@Override
			public void remove() {
				entryIterator.remove();
			}
			
			
		}
		
	}
	
}
