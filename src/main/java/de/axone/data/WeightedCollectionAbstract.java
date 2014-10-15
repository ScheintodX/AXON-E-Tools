package de.axone.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public abstract class WeightedCollectionAbstract<W extends WeightedCollectionAbstract<W, T>, T>
		implements WeightedCollection<W, T> {
	
	private final W self;
	
	private Map<T,T> map = new HashMap<>();
	
	private static final int CONSTANT_SEED =
			(new Random()).nextInt();
	
	private final ItemCollector<T,W> collector
			= new ItemCollector<>( () -> create() );
	
	@SuppressWarnings( "unchecked" )
	public WeightedCollectionAbstract(){
		self = (W) this;
	}
	public WeightedCollectionAbstract( Collection<T> items ){
		this();
		addAll( items );
	}
	
	protected abstract double weight( T item );
	protected abstract T clone( T item, double newWeight );
	protected abstract W create();
	
	public Supplier<W> supplier(){
		return () -> create();
	}
	
	public Collector<T,W,W> collector(){
		return collector;
	}
	
	public Weighter<T> weighter(){
		return this::weight;
	}
	
	protected T merge( T t1, T t2 ) {
		
		return clone( t1, weight( t1 ) + weight( t2 ) );
	}
	
	@Override
	public W copy(){
		
		W result = create();
		
		result.addAll( map.keySet() );
		
		return result;
	}

	public W add( T item ) {
		
		if( item == null ) return self;
		
		if( map.containsKey( item ) ){
			
			T old = map.get( item );
			
			map.remove( old );
			
			T merged = merge( old, item );
			
			map.put( merged, merged );
			
		} else {
			
			map.put( item, item );
		}
		
		return self;
	}
	
	
	@SuppressWarnings( "unchecked" )
	public W addAll( T ... items ) {
		if( items == null || items.length == 0 ) return self;
		for( T item : items ) add( item );
		return self;
	}
	
	public W addAll( Collection<? extends T> items ){
		if( items == null || ! items.iterator().hasNext() ) return self;
		for( T item : items ) add( item );
		return self;
	}
	
	public W addAll( W items ){
		if( items == null || ! items.iterator().hasNext() ) return self;
		for( T item : items ) add( item );
		return self;
	}
	
	public W remove( Object item ){
		if( item == null ) return self;
		map.remove( item );
		return self;
	}
	
	public W removeAll( Collection<?> items ) {
		if( items == null || ! items.iterator().hasNext() ) return self;
		for( Object item : items ) map.remove( item );
		return self;
	}
	
	public W removeAll( W items ) {
		if( items == null || ! items.iterator().hasNext() ) return self;
		for( Object item : items ) map.remove( item );
		return self;
	}
	
	public W retainAll( Collection<?> items ) {
		for( Iterator<T> it = iterator(); it.hasNext(); ){
			T item = it.next();
			if( ! items.contains( item ) ){
				it.remove();
			}
		}
		return self;
	}
	
	public W retainAll( W items ) {
		for( Iterator<T> it = iterator(); it.hasNext(); ){
			T item = it.next();
			if( ! items.contains( item ) ) it.remove();
		}
		return self;
	}

	public int size() {
		return map.size();
	}
	
	public boolean contains( Object item ) {
		return map.containsKey( item );
	}
	
	public Stream<T> stream(){
		return map.keySet().stream();
	}
	
	@Override
	public double maxWeight(){
		
		return stream()
				.mapToDouble( this::weight )
				.max()
				.getAsDouble()
				;
	}
	
	@Override
	public double weight(){ 
		
		return stream()
				.mapToDouble( this::weight )
				.sum()
				;
	}
		
	@Override
	public double avgWeight(){ 
		
		return stream()
				.mapToDouble( this::weight )
				.average()
				.getAsDouble()
				;
	}
	
	
	@Override
	public int hashCode() {
		return map.hashCode();
	}
	
	@Override
	public boolean equals( Object obj ) {
		
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof WeightedCollectionAbstract ) ) return false;
		
		@SuppressWarnings( "rawtypes" )
		WeightedCollectionAbstract other = (WeightedCollectionAbstract) obj;
		
		return map.equals( other.map );
	}
	
	@Override
	public Iterator<T> iterator() {
		return map.keySet().iterator();
	}
	
	@Override
	public List<T> asList() {
		return new ArrayList<T>( map.keySet() );
	}
	
	@Override
	public List<T> shuffled() {
		List<T> result = asList();
		Collections.shuffle( result );
		return result;
	}
	
	@Override
	public List<T> shuffledStable() {
		List<T> result = asList();
		Collections.shuffle( result, new Random( CONSTANT_SEED ) );
		return result;
	}
	
	@Override
	public Set<T> asSet() {
		return map.keySet();
	}
	
	@Override
	public W filter( Predicate<T> filter ) {
		
		if( size() == 0 ) return self;
		
		return stream()
				.filter( filter )
				.collect( collector() )
				;
		
	}

	@Override
	public W best( int maxAmount, Predicate<T> filter ){
		
		if( size() == 0 ) return self;
		
		if( maxAmount == 0 ) return create();
		
		return stream()
				.sorted( new WeightComparator() )
				.filter( filter )
				.limit( maxAmount )
				.collect( collector() )
				;
		
	}
	
	@Override
	public W best( int maxAmount ){
		
		if( size() == 0 ) return self;
		
		if( maxAmount == 0 ) return create();
		
		return stream()
				.sorted( new WeightComparator() )
				.limit( maxAmount )
				.collect( collector() )
				;
		
	}
	
	@Override
	public W normalized(){
		
		if( size() == 0 ) return self;
		
		double max = maxWeight();
		
		return stream()
				.map( (item) -> clone( item, weight( item ) / max ) )
				.collect( collector() )
				;
						
	}
	
	/**
	 * Return a Set containing all elements combined from A AND B
	 * 
	 * @param a First list to combine
	 * @param b Second list to combine
	 * @return A ∪ B
	 */
	public static <T, W extends WeightedCollectionAbstract<W,T>> W join( W a, W b ){
		
		if( a == null ) return b;
		if( b == null ) return a;
		
		W result = a.create();
		
		result.addAll( a );
		result.addAll( b );
		
		return result;
	}
	
	/**
	 * Return a Set containing all elements combined from A AND B
	 * 
	 * @param a First list to combine
	 * @param b Second list to combine
	 * @return A ∪ B
	 */
	public static <T, W extends WeightedCollectionAbstract<W,T>> W override( W a, W b ){
		
		if( a == null ) return b;
		if( b == null ) return a;
		
		W result = a.create();
		
		result.addAll( a );
		result.removeAll( b );
		result.addAll( b );
		
		return result;
	}
	
	/**
	 * Return a Set containing the elements which are in both A and B
	 * 
	 * @see #join
	 * 
	 * @param a First list to combine
	 * @param b Second list to combine
	 * @return A ∩ B
	 */
	public static <T, W extends WeightedCollectionAbstract<W,T>> W intersection( W a, W b ){
		
		if( a == null ) return null;
		if( b == null ) return null;
		
		W result = a.create();
		                           
		result.addAll( a );
		result.retainAll( b );
	
		return result;
	}
	
	/**
	 * Return a Set containing elements from A which are not in B
	 * 
	 * @see #join
	 * 
	 * @param a First list to combine
	 * @param b Second list to combine
	 * @return A \ B
	 */
	public static <T, W extends WeightedCollectionAbstract<W,T>> W  complement( W a, W b ){
		
		if( a == null ) return null;
		if( b == null ) return a;
		
		W result = a.create();
		
		result.addAll( a );
		result.removeAll( b );
	
		return result;
	}
	
	public class WeightComparator 
			implements Comparator<T>, Serializable {

		@Override
		public int compare( T o1, T o2 ) {
			double diff = weight( o2 ) - weight( o1 );
			return (diff > 0) ? 1 : ((diff < 0) ? -1 : 0);
		}
		
	}

	public static class ItemCollector<T,W extends WeightedCollectionAbstract<W,T>>
	implements Collector<T, W, W>, Serializable {
		
		private final Supplier<W> supplier;
		
		public ItemCollector( Supplier<W> supplier ){
			this.supplier = supplier;
		}

		@Override
		public Supplier<W> supplier() {
			return supplier;
		}

		@Override
		public BiConsumer<W, T> accumulator() {
			return ( items, it ) -> items.add( it );
		}

		@Override
		public BinaryOperator<W> combiner() {
			return ( items1, items2 ) -> items1.addAll( items2 );
		}

		@Override
		public Function<W, W> finisher() {
			return ( items ) -> items;
		}

		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			return EnumSet.of( Characteristics.IDENTITY_FINISH );
		}
		
	}
	
}