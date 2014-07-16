package de.axone.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WeightedItem<T> {

	private final T item;
	private final double weight;
	
	public WeightedItem( T item, double weight ){
		
		this.item = item;
		this.weight = weight;
	}
	
	public T item(){ return item; }
	public double weight(){ return weight; }
	
	@Override
	public String toString() {
		return String.format( "%s (%2.1f)", item.toString(), weight );
	}

	public interface WeightedItemList<T> extends List<WeightedItem<T>> {
		
		public double maxWeight();
		public double avgWeight();
		
	}
	
	public static <T> WeightedItemList<T> arrayList(){
		return new WeightedItemListImpl<T>( new ArrayList<WeightedItem<T>>() );
	}
	
	public static <T> WeightedItemList<T> linkedList(){
		return new WeightedItemListImpl<T>( new LinkedList<WeightedItem<T>>() );
	}
	
	private static class WeightedItemListImpl<T>
			extends ListWrapper<WeightedItem<T>>
			implements WeightedItemList<T> {
		
		public WeightedItemListImpl( List<WeightedItem<T>> wrapped ){
			super( wrapped );
		}
		
		@Override
		public double maxWeight(){
			double result = 0;
			for( WeightedItem<T> it : wrapped ){
				if( it.weight > result ) result = it.weight;
			}
			return result;
		}
		
		@Override
		public double avgWeight(){
			
			double result = 0;
			for( WeightedItem<T> it : wrapped ){
				result += it.weight;
			}
			return result / size();
		}
		
		
	}
	
}