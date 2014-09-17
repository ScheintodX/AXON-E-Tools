package de.axone.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import de.axone.refactor.NotTested;
import de.axone.tools.Mapper;

@NotTested
public final class DoubleImmutableSet<T> implements Set<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final T value1, value2;
	private HashSet<T> helper;
	
	public DoubleImmutableSet( T value1, T value2 ){
		this.value1 = value1;
		this.value2 = value2;
	}
	
	private HashSet<T> helper(){
		if( helper == null ){
			helper = Mapper.hashSet( value1, value2 );
		}
		return helper;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains( Object o ) {
		return Objects.equals( value1, o ) || Objects.equals( value2, o );
	}

	@Override
	public Iterator<T> iterator() {
		return helper().iterator();
	}

	@Override
	public Object[] toArray() {
		return new Object[]{ value1, value2 };
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
		
		if( c.size() > 2 ) return false;
		if( c.size() == 0 ) return true;
		
		for( Object o : c ){
			if( ! contains( o ) ) return false;
		}
		return true;
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
	
	@Override
	public int hashCode() {
		int result = 0;
		if( value1 != null ) result += value1.hashCode();
		if( value2 != null ) result += value2.hashCode();
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( obj instanceof Set ){
			
			if( obj instanceof DoubleImmutableSet ){
				@SuppressWarnings( "rawtypes" )
				DoubleImmutableSet other = (DoubleImmutableSet) obj;
				
				return Objects.equals( value1, other.value1 )
				    && Objects.equals( value2, other.value2 )
				    || Objects.equals( value1, other.value2 )
				    && Objects.equals( value2, other.value1 )
				    ;
			} else {
				
				if( ((Set<?>)obj).size() != 2 ) return false;
				
				return containsAll( (Set<?>) obj );
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + value1.toString() + "," + value2.toString() + "]";
	};
	
	
}