package de.axone.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class SingleImmutableList<T> implements List<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final T value;
	
	public SingleImmutableList( T value ){
		this.value = value;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains( Object o ) {
		return Objects.equals( value, o );
	}

	@Override
	public Iterator<T> iterator() {
		return new MyIterator();
	}

	@Override
	public Object[] toArray() {
		return new Object[]{ value };
	}

	@Override
	public <U> U[] toArray( U[] a ) {
		throw new UnsupportedOperationException( "We don't need this" );
	}

	@Override
	public boolean add( T e ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public boolean remove( Object o ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public boolean containsAll( Collection<?> c ) {
		if( c instanceof SingleImmutableList )
				return Objects.equals( value, ((SingleImmutableList<?>)c).value );
		if( c.size() > 1 ) return false;
		if( c.size() == 0 ) return true;
		Object o = c.iterator().next();
		return value.equals( o );
	}

	@Override
	public boolean addAll( Collection<? extends T> c ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public boolean removeAll( Collection<?> c ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException( "List is immutable" );
	}
	
	private class MyIterator implements ListIterator<T> {
		
		boolean hasNext = true;
		
		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public T next() {
			if( ! hasNext ) throw new NoSuchElementException();
			hasNext = false;
			return value;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "List is immutable" );
		}

		@Override
		public boolean hasPrevious() {
			return !hasNext;
		}

		@Override
		public T previous() {
			if( hasNext ) throw new NoSuchElementException();
			hasNext = true;
			return value;
		}

		@Override
		public int nextIndex() {
			if( hasNext ) return 0;
			return 1;
		}

		@Override
		public int previousIndex() {
			if( !hasNext ) return 0;
			else return -1;
		}

		@Override
		public void set( T e ) {
			throw new UnsupportedOperationException( "List is immutable" );
		}

		@Override
		public void add( T e ) {
			throw new UnsupportedOperationException( "List is immutable" );
		}
		
	}

	@Override
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof List ) ) return false;
		
		if( ((List<?>)obj).size() != 1 ) return false;
		
		if( obj instanceof SingleImmutableList ) {
			return Objects.equals( value, ((SingleImmutableList<?>)obj).value );
		} else {
			return Objects.equals( value, ((List<?>)obj).get( 0 ) );
		}
	}


	@Override
	public String toString() {
		return "[" + value.toString() + "]";
	}

	@Override
	public boolean addAll( int index, Collection<? extends T> c ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public T get( int index ) {
		if( index == 0 ) return value;
		else throw new IndexOutOfBoundsException();
	}

	@Override
	public T set( int index, T element ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public void add( int index, T element ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public T remove( int index ) {
		throw new UnsupportedOperationException( "List is immutable" );
	}

	@Override
	public int indexOf( Object o ) {
		if( Objects.equals( value, o ) ) return 0;
		else return -1;
	}

	@Override
	public int lastIndexOf( Object o ) {
		return indexOf( o );
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator( int index ) {
		return new MyIterator();
	}

	@Override
	public List<T> subList( int fromIndex, int toIndex ) {
		if( fromIndex == toIndex ) return Collections.emptyList();
		if( fromIndex == 0 && toIndex == 1 ) return this;
		throw new IndexOutOfBoundsException();
	};
	
	
}