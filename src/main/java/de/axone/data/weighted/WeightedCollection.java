package de.axone.data.weighted;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface WeightedCollection<W extends WeightedCollection<W, T>, T> extends Iterable<T>{
	
	public double weight();
	public double maxWeight();
	public double avgWeight();
	public DoubleSummaryStatistics metrics();
		
	public Set<T> asSet();
	public List<T> asList();
	
	public List<T> sorted();
	public List<T> shuffled();
	public List<T> shuffledStable();
	
	public W copy();
	
	public W best( int amount, Predicate<T> filter );
	public W best( int amount );
	public W filter( Predicate<T> filter );
	public W normalized();
	
	public Stream<T> stream();
	
	public Stream<T> sortedStream();
	public Stream<T> bestStream( int amount, Predicate<T> filter );
	public Stream<T> bestStream( int amount );
	public Stream<T> filteredStream( Predicate<T> filter );
	public Stream<T> normalizedStream();
	
	public W add( T item );
	public W addAll( W items );
	public W addAll( Collection<? extends T> items );
	public W removeAll( W items );
	public W retainAll( W items );

	public boolean contains( T item );
	public int size();
	
	@FunctionalInterface
	public interface Weighter<T> {
		public double weight( T item );
	}

	@FunctionalInterface
	public interface Cloner<T> {
		public T clone( T item, double newWeight );
	}
	
	@FunctionalInterface
	public interface Merger<T> {
		public T merge( T item1, T item2 );
	}

	public interface Metrics {
		
		public double weight();
		public double max();
		public double avg();
	}
}