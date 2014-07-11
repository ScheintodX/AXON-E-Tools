package de.axone.cache.ng;

import java.util.Collection;
import java.util.Iterator;

import de.axone.cache.ng.CacheNG.Client.Entry;

public class IterableEntryAsValue<V> implements Iterable<V> {
	
	private final Collection<Entry<V>> values;

	public IterableEntryAsValue( Collection<Entry<V>> values ) {
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