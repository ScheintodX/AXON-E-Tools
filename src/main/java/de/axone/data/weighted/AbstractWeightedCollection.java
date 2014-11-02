package de.axone.data.weighted;

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

public abstract class AbstractWeightedCollection<W extends WeightedCollection<W, T>, T>
		implements WeightedCollection<W, T> {
	
	private final W self;
	
	private Map<T,T> map = new HashMap<>();
	
	private static final int CONSTANT_SEED =
			(new Random()).nextInt();
	
	private final Supplier<W> supplier;
	private final Weighter<T> weighter;
	private final Cloner<T> cloner;
	private final Merger<T> merger;
	private final ItemCollector<T,W> collector;
	
	@SuppressWarnings( "unchecked" )
	public AbstractWeightedCollection( Supplier<W> supplier, Weighter<T> weighter, Cloner<T> cloner, Merger<T> merger ){
		this.self = (W) this;
		this.supplier = supplier;
		this.weighter = weighter;
		this.cloner = cloner;
		this.collector = new ItemCollector<>( supplier );
		this.merger = merger;
	
	}
	
	public AbstractWeightedCollection( Supplier<W> supplier, Weighter<T> weighter, Cloner<T> cloner ){
		
		this( supplier, weighter, cloner,
			(t1,t2) -> cloner.clone( t1, weighter.weight( t1 ) + weighter.weight( t2 ) ) );
	}
	
	protected Supplier<W> supplier(){
		return supplier;
	}
	
	public Collector<T,W,W> collector(){
		return collector;
	}
	
	public Weighter<T> weighter(){
		return weighter;
	}
	
	public Cloner<T> cloner(){
		return cloner;
	}
	
	public Merger<T> merger(){
		return merger;
	}
	
	@Override
	public W add( T item ) {
		
		if( item == null ) return self;
		
		if( map.containsKey( item ) ){
			
			T old = map.get( item );
			
			map.remove( old );
			
			T merged = merger.merge( old, item );
			
			map.put( merged, merged );
			
		} else {
			
			map.put( item, item );
		}
		
		return self;
	}
	
	@Override
	public W copy(){
		
		W result = supplier.get();
		
		result.addAll( map.keySet() );
		
		return result;
	}
	
	@SuppressWarnings( "unchecked" )
	public W addAll( T ... items ) {
		if( items == null || items.length == 0 ) return self;
		for( T item : items ) add( item );
		return self;
	}
	
	@Override
	public W addAll( Collection<? extends T> items ){
		if( items == null || items.size() == 0 ) return self;
		for( T item : items ) add( item );
		return self;
	}
	
	@Override
	public W addAll( W items ){
		if( items == null || items.size() == 0 ) return self;
		for( T item : items ) add( item );
		return self;
	}
	
	@Override
	public W removeAll( W items ) {
		if( items == null || items.size() == 0 ) return self;
		for( Object item : items ) map.remove( item );
		return self;
	}
	
	@Override
	public W retainAll( W items ) {
		if( items == null || items.size() == 0 ) return supplier.get();
		for( Iterator<T> it = iterator(); it.hasNext(); ){
			T item = it.next();
			if( ! items.contains( item ) ) it.remove();
		}
		return self;
	}
	
	/**
	 * @return the stored version of the given item
	 * @param item
	 */
	public T get( T item ){
		
		return map.get( item );
	}

	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	public boolean contains( Object item ) {
		return map.containsKey( item );
	}
	
	@Override
	public Stream<T> stream(){
		return map.keySet().stream();
	}
	
	@Override
	public double maxWeight(){
		
		return stream()
				.mapToDouble( weighter::weight )
				.max()
				.getAsDouble()
				;
	}
	
	@Override
	public double weight(){ 
		
		return stream()
				.mapToDouble( weighter::weight )
				.sum()
				;
	}
		
	@Override
	public double avgWeight(){ 
		
		return stream()
				.mapToDouble( weighter::weight )
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
		if( !( obj instanceof AbstractWeightedCollection ) ) return false;
		
		@SuppressWarnings( "rawtypes" )
		AbstractWeightedCollection other = (AbstractWeightedCollection) obj;
		
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
	public List<T> sorted() {
		List<T> result = asList();
		Collections.sort( result, new WeightComparator<>( weighter ) );
		return result;
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
	public Stream<T> bestStream( int maxAmount, Predicate<T> filter ) {
		
		if( size() == 0 || maxAmount <= 0 ) return Stream.empty();
		
		return stream()
				.sorted( new WeightComparator<>( weighter::weight ) )
				.filter( filter )
				.limit( maxAmount )
				;
	}
	
	@Override
	@Deprecated
	public W best( int maxAmount, Predicate<T> filter ){
		
		return bestStream( maxAmount, filter )
				.collect( collector() )
				;
	}
	
	@Override
	public Stream<T> bestStream( int maxAmount ) {
		
		if( size() == 0 || maxAmount <= 0 ) return Stream.empty();
		
		return stream()
				.sorted( new WeightComparator<>( weighter::weight ) )
				.limit( maxAmount )
				;
	}
	
	@Override
	@Deprecated
	public W best( int maxAmount ){
		
		return bestStream( maxAmount )
				.collect( collector() )
				;
	}
	
	@Override
	public Stream<T> filteredStream( Predicate<T> filter ) {
		
		if( size() == 0 ) return Stream.empty();
		
		return stream()
				.filter( filter )
				;
	}
	
	@Override
	@Deprecated
	public W filter( Predicate<T> filter ) {
		
		return filteredStream( filter )
				.collect( collector() )
				;
	}

	
	@Override
	public Stream<T> normalizedStream() {
		
		if( size() == 0 ) return Stream.empty();
		
		double max = maxWeight();
		
		return stream()
				.map( (item) -> cloner.clone( item, weighter.weight( item ) / max ) )
				;
	}
	
	
	@Override
	@Deprecated
	public W normalized(){
		
		return normalizedStream()
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
	public static <T, W extends AbstractWeightedCollection<W,T>> W join( W a, W b ){
		
		if( a == null ) return b;
		if( b == null ) return a;
		
		W result = a.supplier().get();
		
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
	public static <T, W extends AbstractWeightedCollection<W,T>> W override( W a, W b ){
		
		if( a == null ) return b;
		if( b == null ) return a;
		
		W result = a.supplier().get();
		
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
	public static <T, W extends AbstractWeightedCollection<W,T>> W intersection( W a, W b ){
		
		if( a == null ) return null;
		if( b == null ) return null;
		
		W result = a.supplier().get();
		                           
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
	public static <T, W extends AbstractWeightedCollection<W,T>> W  complement( W a, W b ){
		
		if( a == null ) return null;
		if( b == null ) return a;
		
		W result = a.supplier().get();
		
		result.addAll( a );
		result.removeAll( b );
	
		return result;
	}
	
	@Override
	public String toString(){
		return map.keySet().toString();
	}
	
	public WeightComparator<T> weightComparator(){
		return new WeightComparator<>( this.weighter );
	}
	
	public static class WeightComparator<T>
			implements Comparator<T>, Serializable {
		
		private final Weighter<T> weighter;
		
		public WeightComparator( Weighter<T> weighter ){
			this.weighter = weighter;
		}

		@Override
		public int compare( T o1, T o2 ) {
			double diff = weighter.weight( o2 ) - weighter.weight( o1 );
			return (diff > 0) ? 1 : ((diff < 0) ? -1 : 0);
		}
		
	}

	public static class ItemCollector<T,W extends WeightedCollection<W,T>>
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
			return EnumSet.of(
					Characteristics.UNORDERED,
					Characteristics.IDENTITY_FINISH );
		}
		
	}
	
}