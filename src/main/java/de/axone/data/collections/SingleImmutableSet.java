package de.axone.data.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public final class SingleImmutableSet<T> implements Set<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final T value;
	
	public SingleImmutableSet( T value ){
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
		throw new UnsupportedOperationException( "Set is immutable" );
	}

	@Override
	public boolean remove( Object o ) {
		throw new UnsupportedOperationException( "Set is immutable" );
	}

	@Override
	public boolean containsAll( Collection<?> c ) {
		if( c instanceof SingleImmutableSet )
				return Objects.equals( value, ((SingleImmutableSet<?>)c).value );
		if( c.size() > 1 ) return false;
		if( c.size() == 0 ) return true;
		Object o = c.iterator().next();
		return Objects.equals( value, o );
	}

	@Override
	public boolean addAll( Collection<? extends T> c ) {
		throw new UnsupportedOperationException( "Set is immutable" );
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		throw new UnsupportedOperationException( "Set is immutable" );
	}

	@Override
	public boolean removeAll( Collection<?> c ) {
		throw new UnsupportedOperationException( "Set is immutable" );
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException( "Set is immutable" );
	}
	
	private class MyIterator implements Iterator<T> {
		
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
			throw new UnsupportedOperationException( "Set is immutable" );
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
		if( !( obj instanceof Set ) ) return false;
		
		if( ((Set<?>)obj).size() != 1 ) return false;
		
		if( obj instanceof SingleImmutableSet ) {
			return Objects.equals( value, ((SingleImmutableSet<?>)obj).value );
		} else {
			return Objects.equals( value, ((Set<?>)obj).iterator().next() );
		}
	}

	@Override
	public String toString() {
		return "[" + value.toString() + "]";
	};
	
	
}