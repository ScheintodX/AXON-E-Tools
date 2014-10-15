package de.axone.data;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface WeightedCollection<W extends WeightedCollection<W, T>, T> extends Iterable<T>{
	
	public double weight();
	public double maxWeight();
	public double avgWeight();
		
	public Set<T> asSet();
	public List<T> asList();
	
	public List<T> shuffled();
	public List<T> shuffledStable();
	
	public W copy();
	public W best( int amount, Predicate<T> filter );
	public W best( int amount );
	public W filter( Predicate<T> filter );
	public W normalized();
	
	public interface Weighter<T> {
		public double weight( T item );
	}

}