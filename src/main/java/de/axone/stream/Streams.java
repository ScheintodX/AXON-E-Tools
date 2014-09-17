package de.axone.stream;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Streams {

	public static final class ArrayCollector<T> implements Collector<T, ArrayList<T>, T[]> {
		
		private final Class<T> itemClass;
		
		ArrayCollector( Class<T> itemClass ){
			this.itemClass = itemClass;
		}
		
		@Override
		public Supplier<ArrayList<T>> supplier() {
			return () -> new ArrayList<T>();
		}

		@Override
		public BiConsumer<ArrayList<T>, T> accumulator() {
			return (list,item) -> list.add( item );
		}

		@Override
		public BinaryOperator<ArrayList<T>> combiner() {
			return (list1,list2) -> { list1.addAll( list2 ); return list1; };
		}

		@Override
		@SuppressWarnings( "unchecked" )
		public Function<ArrayList<T>, T[]> finisher() {
			return (list) -> list.toArray( (T[])Array.newInstance( itemClass, list.size() ));
		}

		@Override
		public Set<Collector.Characteristics> characteristics() {
			return EnumSet.noneOf( Collector.Characteristics.class );
		}
		
	}
	
}
