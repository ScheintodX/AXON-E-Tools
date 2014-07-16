package de.axone.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListWrapper<T> implements List<T> {
	
	protected final List<T> wrapped;

	public ListWrapper( List<T> wrapped ) {
		this.wrapped = wrapped;
	}

	@Override
	public int size() {
		return wrapped.size();
	}

	@Override
	public boolean isEmpty() {
		return wrapped.isEmpty();
	}

	@Override
	public boolean contains( Object o ) {
		return wrapped.contains( o );
	}

	@Override
	public Iterator<T> iterator() {
		return wrapped.iterator();
	}

	@Override
	public Object[] toArray() {
		return wrapped.toArray();
	}

	@Override
	public <X> X[] toArray( X[] a ) {
		return wrapped.toArray( a );
	}

	@Override
	public boolean add( T e ) {
		return wrapped.add( e );
	}

	@Override
	public boolean remove( Object o ) {
		return wrapped.remove( o );
	}

	@Override
	public boolean containsAll( Collection<?> c ) {
		return wrapped.containsAll( c );
	}

	@Override
	public boolean addAll( Collection<? extends T> c ) {
		return wrapped.addAll( c );
	}

	@Override
	public boolean addAll( int index, Collection<? extends T> c ) {
		return wrapped.addAll( index, c );
	}

	@Override
	public boolean removeAll( Collection<?> c ) {
		return wrapped.removeAll( c );
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		return wrapped.retainAll( c );
	}

	@Override
	public void clear() {
		wrapped.clear();
	}

	@Override
	public T get( int index ) {
		return wrapped.get( index );
	}

	@Override
	public T set( int index, T element ) {
		return wrapped.set( index, element );
	}

	@Override
	public void add( int index, T element ) {
		wrapped.add( index, element );
	}

	@Override
	public T remove( int index ) {
		return wrapped.remove( index );
	}

	@Override
	public int indexOf( Object o ) {
		return wrapped.indexOf( o );
	}

	@Override
	public int lastIndexOf( Object o ) {
		return wrapped.lastIndexOf( o );
	}

	@Override
	public ListIterator<T> listIterator() {
		return wrapped.listIterator();
	}

	@Override
	public ListIterator<T> listIterator( int index ) {
		return wrapped.listIterator( index );
	}

	@Override
	public List<T> subList( int fromIndex, int toIndex ) {
		return wrapped.subList( fromIndex, toIndex );
	}

}
