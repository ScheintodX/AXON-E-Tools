package de.axone.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
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
	
	public static <T> ItemList<T> arrayList(){
		return new WrappedWeightedItemList<T>( new ArrayList<Item<T>>() );
	}
	
	
	public static <T> ItemList<T> linkedList(){
		return new WrappedWeightedItemList<T>( new LinkedList<Item<T>>() );
	}
	
	public interface ItemList<T> {
		
		public double weight();
		public double maxWeight();
		public double avgWeight();
	}
	
	public static class WrappedWeightedItemList<T>
			extends ListWrapper<Item<T>>
			implements ItemList<T> {
		
		
		public WrappedWeightedItemList( List<Item<T>> wrapped ){
			
			super( wrapped );
		}
		
		@Override
		public double weight() {
			double result = 0;
			for( Item<T> it : wrapped ){
				result += it.weight();
			}
			return result;
		}
		
		
		@Override
		public double maxWeight(){
			double result = 0;
			for( Item<T> it : wrapped ){
				if( it.weight() > result ) result = it.weight();
			}
			return result;
		}
		
		@Override
		public double avgWeight(){
			
			double result = 0;
			for( Item<T> it : wrapped ){
				result += it.weight();
			}
			return result / size();
		}

	}
	
	
	@SuppressWarnings( "rawtypes" )
	public static final WeightComparator WEIGHT_COMPARATOR = new WeightComparator();
	
	@SuppressWarnings( "unchecked" )
	public static <X> WeightComparator<X> WEIGHT_COMPARATOR(){ return WEIGHT_COMPARATOR; }
	
	public static class WeightComparator<X>
			implements Comparator<Item<X>> {

		@Override
		public int compare( Item<X> o1, Item<X> o2 ) {
			return o2.weight() - o1.weight() > 0 ? 1 : -1;
		}
		
	};
	
	@SuppressWarnings( "rawtypes" )
	public static final WeightComparator ITEM_COMPARATOR = new WeightComparator();
	
	@SuppressWarnings( "unchecked" )
	public static <X> WeightComparator<X> ITEM_COMPARATOR(){ return ITEM_COMPARATOR; }
	
	public static class ItemComparator<X extends Comparable<X>>
			implements Comparator<Item<X>> {

		@Override
		public int compare( Item<X> o1, Item<X> o2 ) {
			return o1.item().compareTo( o2.item() );
		}
		
	};
	
}