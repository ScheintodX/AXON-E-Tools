package de.axone.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collector;

import de.axone.data.WeightedCollectionContained.Container;

public class WeightedCollectionContained<T>
extends WeightedCollectionAbstract<WeightedCollectionContained<T>, Container<T>>
implements Serializable {
	
	public WeightedCollectionContained() {
		super();
	}

	public WeightedCollectionContained( Collection<Container<T>> items ) {
		super( items );
	}

	@Override
	protected double weight( Container<T> item ) {
		return item.weight;
	}

	@Override
	protected Container<T> clone( Container<T> item, double newWeight ) {
		return new Container<T>( item.contained, newWeight );
	}

	@Override
	protected WeightedCollectionContained<T> create() {
		return new WeightedCollectionContained<>();
	}
	
	public WeightedCollectionContained<T> add( T item, double weight ){
		
		return add( new Container<>( item, weight ) );
	}
	
	public static <T> Collector<Container<T>,WeightedCollectionContained<T>, WeightedCollectionContained<T>> COLLECTOR(){
		
		return new ItemCollector<Container<T>, WeightedCollectionContained<T>>( WeightedCollectionContained::new );
	}

	public static class Container<T> implements Serializable {
		private final double weight;
		private final T contained;
		
		public Container( T contained, double weight ) {
			super();
			this.contained = contained;
			this.weight = weight;
		}
		
		public T contained(){ return contained; }
		public double weight(){ return weight; }
		
		@Override
		public int hashCode() {
			return contained != null ? contained.hashCode(): 0;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj )
				return true;
			if( obj == null )
				return false;
			if( !( obj instanceof Container ) )
				return false;
			Container<?> other = (Container<?>) obj;
			if( contained == null ) {
				if( other.contained != null )
					return false;
			}
			
			return contained.equals( other.contained );
		}

		@Override
		public String toString() {
			
			return String.format( "%s(%.2f)", contained, weight );
		}
	}

}
