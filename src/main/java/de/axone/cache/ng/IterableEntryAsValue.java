package de.axone.cache.ng;

import java.util.Collection;
import java.util.Iterator;

import de.axone.cache.ng.CacheNG.Cache.Entry;

public class IterableEntryAsValue<O> implements Iterable<O> {
	
	private final Collection<Entry<O>> values;

	public IterableEntryAsValue( Collection<Entry<O>> values ) {
		this.values = values;
	}

	@Override
	public Iterator<O> iterator() {
		return new ValueIterator();
	}
	
	private class ValueIterator implements Iterator<O> {
		
		Iterator<Entry<O>> entryIterator = values.iterator();

		@Override
		public boolean hasNext() {
			return entryIterator.hasNext();
		}

		@Override
		public O next() {
			return entryIterator.next().data();
		}

		@Override
		public void remove() {
			entryIterator.remove();
		}
		
		
	}
	
}