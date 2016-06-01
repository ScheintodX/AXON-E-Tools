package de.axone.data.collections;

import java.util.Iterator;
import java.util.function.Function;

public class MappingIterator<X,T> implements Iterator<T> {
	
	private final Iterator<X> it;
	private final Function<X,T> mapper;
		
	public MappingIterator( Iterator<X> it, Function<X, T> mapper ) {
		this.it = it;
		this.mapper = mapper;
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public T next() {
		return mapper.apply( it.next() );
	}

}
