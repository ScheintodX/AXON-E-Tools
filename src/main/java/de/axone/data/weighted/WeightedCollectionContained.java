package de.axone.data.weighted;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;


public interface WeightedCollectionContained<W extends WeightedCollectionContained<W, T>, T>
extends Iterable<WeightedCollectionContained.WeightedItem<T>>{
	
	public W supply();
	
	public W add( T item, double weight );
	public W addAll( Iterable<WeightedItem<T>> items );
	public W addAll( Stream<WeightedItem<T>> items );
	public W addAllIf( Iterable<WeightedItem<T>> items, Predicate<T> filter );
	
	public boolean contains( T item );
	
	public int size();
	
	public double weight();
	public double maxWeight();
	public double avgWeight();
	
	public Set<WeightedItem<T>> asSet();
	public List<WeightedItem<T>> asList();
	
	public Stream<WeightedItem<T>> stream();
	public Stream<WeightedItem<T>> sortedStream( Comparator<T> comparator );
	public Stream<WeightedItem<T>> bestStream();
	
	public W best( int amount );
	
	public List<WeightedItem<T>> shuffled();
	public List<WeightedItem<T>> shuffledStable();
	
	public W overrideBy( W other );
	
	public interface WeightedItem<I> {
		
		public I item();
		public double weight();
		public double normalized();
	}
	
	public static class Impl<T> extends AbstractWeightedCollectionContained<Impl<T>,T> {

		public Impl( Supplier<Impl<T>> supplier ) {
			super( supplier );
		}
	}
	
	public static class ItemCollector<T,X extends AbstractWeightedCollectionContained<X,T>>
	implements Collector<WeightedItem<T>,List<WeightedItem<T>>,X> {
		
		public ItemCollector( Supplier<X> supplier ) {
			this.supplier = supplier;
		}

		private final Supplier<X> supplier;

		@Override
		public Supplier<List<WeightedItem<T>>> supplier() {
			return LinkedList::new;
		}

		@Override
		public BiConsumer<List<WeightedItem<T>>, WeightedItem<T>> accumulator() {
			return Collection::add;
		}

		@Override
		public BinaryOperator<List<WeightedItem<T>>> combiner() {
			return (list1,list2) -> { list1.addAll( list2 ); return list1; };
		}

		@Override
		public Function<List<WeightedItem<T>>, X> finisher() {
			return list -> {
				X impl = supplier.get();
				impl.addAll( list );
				return impl;
			};
		}

		@Override
		public Set<Collector.Characteristics> characteristics() {
			return EnumSet.of( Collector.Characteristics.UNORDERED );
		}
	}
	
	/*
	@FunctionalInterface
	public interface WeightedItemSupplier<T> {
		WeightedItem<T> get( T item, double value );
	}
	*/
	
	@FunctionalInterface
	public interface Mapper<T,S> {
		
		public T map( S source );
	}
	
	public class ConvertingWrapper<T, S> implements WeightedItem<T> {
		
		private final WeightedItem<S> source;
		private final Mapper<T,S> mapper;
		
		public ConvertingWrapper( WeightedItem<S> source, Mapper<T,S> mapper ) {
			
			this.source = source;
			this.mapper = mapper;
		}

		@Override
		public T item() {
			return mapper.map( source.item() );
		}

		@Override
		public double weight() {
			return source.weight();
		}

		@Override
		public double normalized() {
			return source.normalized();
		}
	}
	
	public class TransportWrapper<T, S> implements WeightedItem<T> {
		
		private final S source;
		private final Mapper<T,S> mapper;
		
		public TransportWrapper( S source, Mapper<T,S> mapper ) {
			
			this.source = source;
			this.mapper = mapper;
		}

		@Override
		public T item() {
			return mapper.map( source );
		}

		@Override
		public double weight() {
			return 1;
		}

		@Override
		public double normalized() {
			return 1;
		}
	}

}
