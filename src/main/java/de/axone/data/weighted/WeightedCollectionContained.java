package de.axone.data.weighted;

import java.io.Serializable;
import java.util.stream.Collector;

import de.axone.data.weighted.WeightedCollectionContained.Container;

public class WeightedCollectionContained<T>
extends AbstractWeightedCollection<WeightedCollectionContained<T>, Container<T>>
implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public WeightedCollectionContained(){
		
		super( WeightedCollectionContained::new,
				item -> item.weight,
				(item, newWeight) -> new Container<T>( item.contained, newWeight )
		);
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
