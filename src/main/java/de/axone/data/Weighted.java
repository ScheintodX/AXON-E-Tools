package de.axone.data;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public abstract class Weighted {
	
	
	public interface Item<T> {
	
		public double weight();
		public T item();
	}
	
	
	public static class WrappedItem<T> implements Item<T> {

		private final T item;
		private final double weight;
		
		public WrappedItem( T item, double weight ){
			
			this.item = item;
			this.weight = weight;
		}
		
		@Override
		public T item(){ return item; }
		
		@Override
		public double weight(){ return weight; }
		
		
		@Override
		public String toString() {
			return String.format( "%s (%2.1f)", item.toString(), weight );
		}
	}
	
	public interface ItemList {
		
		public double weight();
		public double maxWeight();
		public double avgWeight();
	}
	
	
	public static <T extends Item<?>> double listWeight( Collection<T> list ){
		
		double result = 0;
		for( Item<?> it : list ){
			result += it.weight();
		}
		return result;
	}
	
	public static <T extends Item<?>> double listMaxWeight( Collection<T> list ){
		
		double result = 0;
		for( Item<?> it : list ){
			if( it.weight() > result ) result = it.weight();
		}
		return result;
	}
	
	public static <T extends Item<?>> double listAvgWeight( Collection<T> list ){
		
		return listWeight( list ) / list.size();
	}
	
	
	public static class WrappedItemList<T>
			extends ListWrapper<Item<T>>
			implements ItemList {
		
		public WrappedItemList( List<Item<T>> wrapped ){
			
			super( wrapped );
		}
		
		@Override
		public double weight() { return listWeight( wrapped ); }
		
		@Override
		public double maxWeight(){ return listMaxWeight( wrapped ); }
		
		@Override
		public double avgWeight(){ return listAvgWeight( wrapped ); }

	}
	
	public static class LinkedList<T extends Item<?>>
			extends java.util.LinkedList<T>
			implements ItemList {
		
		public LinkedList() { }

		public LinkedList( Collection<? extends T> c ) {
			super( c );
		}

		@Override
		public double weight() { return listWeight( this ); }
		
		@Override
		public double maxWeight(){ return listMaxWeight( this ); }
		
		@Override
		public double avgWeight(){ return listAvgWeight( this ); }
		
	}
	
	public static class ArrayList<T extends Item<?>>
			extends java.util.ArrayList<T>
			implements ItemList {
		
		public ArrayList() { }

		public ArrayList( Collection<? extends T> c ) {
			super( c );
		}

		public ArrayList( int initialCapacity ) {
			super( initialCapacity );
		}

		@Override
		public double weight() { return listWeight( this ); }
		
		@Override
		public double maxWeight(){ return listMaxWeight( this ); }
		
		@Override
		public double avgWeight(){ return listAvgWeight( this ); }
		
	}
	
	
	@SuppressWarnings( "rawtypes" )
	public static final WeightComparator WEIGHT_COMPARATOR = new WeightComparator();
	
	@SuppressWarnings( "unchecked" )
	public static <X extends Item<?>> WeightComparator<X> WEIGHT_COMPARATOR(){ return WEIGHT_COMPARATOR; }
	
	public static class WeightComparator<X extends Item<?>>
			implements Comparator<X> {

		@Override
		public int compare( X o1, X o2 ) {
			return o2.weight() - o1.weight() > 0 ? 1 : -1;
		}
		
	};
	
	
	@SuppressWarnings( "rawtypes" )
	public static final ItemComparator ITEM_COMPARATOR = new ItemComparator();
	
	@SuppressWarnings( "unchecked" )
	public static <Y extends Comparable<Y>, X extends Item<Y>> ItemComparator<Y,X> ITEM_COMPARATOR(){ return ITEM_COMPARATOR; }
	
	public static class ItemComparator<Y extends Comparable<Y>, X extends Item<Y> >
			implements Comparator<X> {

		@Override
		public int compare( X o1, X o2 ) {
			return o1.item().compareTo( o2.item() );
		}
		
	};
	
	
}