package de.axone.data.weighted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import de.axone.data.weighted.WeightedCollectionContained.WeightedItem;

public class AbstractWeightedCollectionContained<W extends WeightedCollectionContained<W, T>, T>
		implements WeightedCollectionContained<W, T>, Iterable<WeightedItem<T>>, Serializable {
	
	private HashMap<T,WeightedItem<T>> map = new HashMap<>();
	
	@SuppressWarnings( "unchecked" )
	private final W myself = (W)this;
	
	private double sumWeight;
	private double maxWeight;
	
	private final Supplier<W> supplier;
	
	private static final int CONSTANT_SEED =
			(new Random()).nextInt();
	
	public AbstractWeightedCollectionContained( Supplier<W> supplier ){
		this.supplier = supplier;
	}
	
	
	@Override
	public W supply() { return supplier.get(); }
	
	
	@Override
	public W addAll( Iterable<WeightedItem<T>> items ) {
		
		for( WeightedItem<T> item : items ){
			
			_add( item );
		}
		return myself;
	}
	
	@Override
	public W addAll( Stream<WeightedItem<T>> items ) {
		
		items.forEach( item -> {
			
			_add( item );
		} );
		return myself;
	}
	
	
	@Override
	public W addAllIf( Iterable<WeightedItem<T>> items, Predicate<T> filter ) {
		
		for( WeightedItem<T> item : items ){
			
			if( filter.test( item.item() ) ) _add( item );
		}
		return myself;
	}
	
	
	
	protected void _add( WeightedItem<T> item ) {
		
		if( item == null ) return;
		
		WeightedItemImpl old = (WeightedItemImpl) map.get( item );
		
		double theWeight = item.weight();
		
		if( old != null ) {
			
			old.add( theWeight );
			
			count( theWeight, old.weight() );
			
		} else {
			
			T theItem = item.item();
		
			if( item instanceof AbstractWeightedCollectionContained.WeightedItemImpl ) {
				
				map.put( theItem, item );
			} else {
				map.put( theItem, new WeightedItemImpl( theItem, theWeight ) );
			}
			
			count( theWeight, theWeight );
		}
		
	}
	
	
	@Override
	public W add( T item, double weight ) {
		
		if( item == null ) return myself;
		
		WeightedItemImpl old = (WeightedItemImpl) map.get( item );
			
		if( old != null ){
			
			old.add( weight );
			
			count( weight, old.weight() );
			
		} else {
			
			map.put( item, new WeightedItemImpl( item, weight ) );
			
			count( weight, weight );
		}
		
		return myself;
	}
	
	@Override
	public boolean contains( T item ){ return map.containsKey( item ); }
	
	
	@Override
	public int size(){ return map.size(); }
	
	
	@Override
	public double weight() { return sumWeight; }

	@Override
	public double maxWeight() { return maxWeight; }

	@Override
	public double avgWeight() { return size() > 0 ? sumWeight / size() : 0; }

	
	@Override
	public Set<WeightedItem<T>> asSet() {
		return new HashSet<>( map.values() );
	}

	@Override
	public List<WeightedItem<T>> asList() {
		return new ArrayList<>( map.values() );
	}
	
	

	@Override
	public Stream<WeightedItem<T>> stream() {
		return map.values().stream();
	}

	@Override
	public Stream<WeightedItem<T>> sortedStream( Comparator<T> comparator ) {
		return map.values().stream()
				.sorted( new NestingComparator<>( comparator ) )
				;
	}

	@Override
	public Stream<WeightedItem<T>> bestStream() {
		return map.values().stream()
				.sorted( WEIGHT_COMPARATOR )
				;
	}
	
	@Override
	public W best( int amount ) {
		
		return supplier.get()
				.addAll( bestStream().limit( amount ) )
				;
	}
	
	
	@Override
	public W overrideBy( W other ) {
		
		W result = supplier.get();
		
		result.addAll( other );
		result.addAllIf( this, i -> ! result.contains( i ) );
		
		return result;
	}

	@Override
	public List<WeightedItem<T>> shuffled() {
		
		List<WeightedItem<T>> list = asList();
		Collections.shuffle( list );
		return list;
	}

	@Override
	public List<WeightedItem<T>> shuffledStable() {
		
		List<WeightedItem<T>> list = asList();
		Collections.shuffle( list, new Random( CONSTANT_SEED ) );
		return list;
	}
	
			
	@Override
	public Iterator<WeightedItem<T>> iterator() {
		
		return map.values().iterator();
	}

	private void count( double weight, double max ) {
		
		sumWeight += weight;
		
		if( max > maxWeight ) maxWeight = max;
	}
	
	@Override
	public String toString() {
		
		return String.format( "(S:%d/W:%.1f/M:%.1f/AVG:%.1f) %s",
				size(), weight(), maxWeight(), avgWeight(), asList().toString() );
	}
	
	
	@Override
	public int hashCode() {
		return map.hashCode();
	}


	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof AbstractWeightedCollectionContained ) ) return false;
		
		AbstractWeightedCollectionContained<?,?> other = (AbstractWeightedCollectionContained<?,?>) obj;
		
		if( map == null ) {
			if( other.map != null )
				return false;
		} else if( !map.equals( other.map ) )
			return false;
		
		return true;
	}






	class WeightedItemImpl implements WeightedItem<T> {
		
		private final T item;
		private double weight;
		
		WeightedItemImpl( T item, double weight ) {
			this.item = item;
			this.weight = weight;
		}

		@Override
		public T item() {
			return item;
		}

		@Override
		public double weight() {
			return weight;
		}

		@Override
		public double normalized() {
			return weight / maxWeight;
		}
		
		private void add( double weight ) {
			
			this.weight += weight;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( item == null ) ? 0 : item.hashCode() );
			long temp;
			temp = Double.doubleToLongBits( weight );
			result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof WeightedItem ) ) return false;
			
			WeightedItem<?> other = (WeightedItem<?>) obj;
			if( item == null ) {
				if( other.item() != null )
					return false;
			} else if( !item.equals( other.item() ) )
				return false;
			
			if( Double.doubleToLongBits( weight ) != Double
					.doubleToLongBits( other.weight() ) )
				return false;
			
			return true;
		}
		
		@Override
		public String toString() {
			
			return item.toString() +
					'(' + weight() + '/' + normalized() + ')';
		}

	}
	
	private static final class NestingComparator<T> implements Comparator<WeightedItem<T>> {
		
		private final Comparator<T> nestedComparator;
		
		public NestingComparator( Comparator<T> nestedComparator ) {
			
			this.nestedComparator = nestedComparator;
		}

		@Override
		public int compare( WeightedItem<T> o1, WeightedItem<T> o2 ) { 
			
			return nestedComparator.compare( o1.item(), o2.item() );
		}
		
	}

	public static final Comparator<WeightedItem<?>> WEIGHT_COMPARATOR = new Comparator<WeightedItem<?>>() {

		@Override
		public int compare( WeightedItem<?> o1, WeightedItem<?> o2 ) {
			
			return - Double.compare( o1.weight(), o2.weight() );
		}
	};
	
}
