package de.axone.data;

import java.util.Iterator;

public abstract class IterableWrapper<SRC,DST> implements Iterable<DST> {

		private final Iterable<SRC> items;
			
		protected IterableWrapper( Iterable<SRC> items ){
			this.items = items;
		}
		
		protected abstract DST wrap( SRC source );

		@Override
		public Iterator<DST> iterator() {
			return new MyIterator( items.iterator() );
		}
		
		private class MyIterator implements Iterator<DST> {
			
			private final Iterator<SRC> it;
			
			MyIterator( Iterator<SRC> it ){
				this.it = it;
			}
	
			@Override
			public boolean hasNext() { return it.hasNext(); }
	
			@Override
			public DST next() { return wrap( it.next() ); }
	
			@Override
			public void remove() { it.remove(); }
	
		}
}
