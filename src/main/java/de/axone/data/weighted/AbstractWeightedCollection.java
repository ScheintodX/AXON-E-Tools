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
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public abstract class AbstractWeightedCollection<W extends WeightedCollection<W, T>, T>
		implements WeightedCollection<W, T>, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings( "unchecked" )
	protected final W self = (W) this;

	protected Map<T,T> map = new HashMap<>();

	private Map<String,T> forName;

	private static final int CONSTANT_SEED =
			(new Random()).nextInt();

	private final Supplier<W> supplier;
	private final Weighter<T> weighter;
	private final Cloner<T> cloner;
	private final Combiner<T> combiner;
	private final ItemCollector<T,W> collector;
	private final Namer<T> namer;

	public AbstractWeightedCollection( Supplier<W> supplier, Weighter<T> weighter, Cloner<T> cloner, Combiner<T> merger, Namer<T> namer ){
		this.supplier = supplier;
		this.weighter = weighter;
		this.cloner = cloner;
		this.collector = new ItemCollector<>( supplier );
		this.combiner = merger;
		this.namer = namer;
		if( namer != null ) {
			forName = new HashMap<>();
		}
	}

	public AbstractWeightedCollection( Supplier<W> supplier, Weighter<T> weighter, Cloner<T> cloner ){

		this( supplier, weighter, cloner,
			(t1,t2) -> cloner.clone( t1, weighter.weight( t1 ) + weighter.weight( t2 ) ), null );
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

	public Combiner<T> combiner(){
		return combiner;
	}

	@Override
	public W add( T item ) {

		if( item == null ) return self;

		if( map.containsKey( item ) ){

			T old = map.get( item );

			map.remove( old );

			T merged = combiner.combine( old, item );

			map.put( merged, merged );

		} else {

			map.put( item, item );
		}

		if( namer != null ) {

			String name = namer.name( item );

			forName.put( name, item );
		}

		return self;
	}

	@Override
	public W addIf( T item, Predicate<T> condition ) {

		if( condition.test( item ) ) add( item );

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

	@Override
	public W distinct( W items ) {
		for( Iterator<T> it = iterator(); it.hasNext(); ){
			T item = it.next();
			if( items.contains( item ) ) it.remove();
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
	public boolean contains( T item ) {
		return map.containsKey( item );
	}

	@Override
	public Stream<T> stream(){
		return map.keySet().stream();
	}

	@Override
	public Optional<T> findFirst( Predicate<? super T> predicate ){
		return stream().filter( predicate )
				.findFirst();
	}
	@Override
	public Optional<T> findFirst( String name, Function<T, String> mapper ){
		return stream()
				.filter( t -> name.equals( mapper.apply( t ) ) )
				.findFirst()
				;
	}

	@Override
	public double weight(){

		return weightStream()
				.sum();
	}

	@Override
	public double maxWeight(){

		OptionalDouble max = weightStream().max();

		return max.isPresent() ? max.getAsDouble() : 0;
	}

	@Override
	public double avgWeight(){

		OptionalDouble avg = weightStream().average();

		return avg.isPresent() ? avg.getAsDouble() : 0;
	}

	private DoubleStream weightStream() {

		return stream()
				.mapToDouble( weighter::weight )
				;
	}

	public T find( String name ) {

		if( namer == null )
				throw new UnsupportedOperationException( "No 'namer' given" );

		return forName.get( name );
	}

	/*
	private DoubleSummaryStatistics metrics() {

		return weightStream()
				.summaryStatistics()
				;
	}
	*/

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
		return new ArrayList<>( map.keySet() );
	}

	@Override
	public List<T> sorted() {
		return sorted( new WeightComparator<>( weighter ) );
	}
	@Override
	public List<T> sorted( Comparator<T> comparator ) {
		List<T> result = asList();
		Collections.sort( result, comparator );
		return result;
	}

	protected static void shuffle( List<?> list ){
		Collections.shuffle( list );
	}

	protected static void shuffleStable( List<?> list ){
		Collections.shuffle( list, new Random( CONSTANT_SEED ) );
	}

	@Override
	public List<T> shuffled() {
		List<T> result = asList();
		shuffle( result );
		return result;
	}

	@Override
	public List<T> shuffledStable() {
		List<T> result = asList();
		shuffleStable( result );
		return result;
	}

	@Override
	public Set<T> asSet() {
		return map.keySet();
	}

	@Override
	public Stream<T> sortedStream() {

		return stream()
				.sorted( new WeightComparator<>( weighter ) )
				;
	}

	public Stream<T> normalizedBestStream( int maxAmount ){

		if( size() == 0 || maxAmount <= 0 ) return Stream.empty();

		return normalizedStream()
				.sorted( new WeightComparator<>( weighter ) )
				.limit( maxAmount )
				;
	}

	public List<T> normalizedBest( int maxAmount ){

		return normalizedBestStream( maxAmount )
				.collect( Collectors.toList() )
				;
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

	// Note, that this doesn't imply "sorted" results.
	@Override
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
	public W filter( Predicate<T> filter ) {

		return filteredStream( filter )
				.collect( collector() )
				;
	}


	@Override
	public Stream<T> normalizedStream() {

		if( size() == 0 ) return Stream.empty();

		double max = maxWeight();
		double avg = avgWeight();

		return stream()
				.map( (item) -> cloner.clone( item, normalize( item, max, avg ) ) )
				;
	}

	/**
	 * Normalise so that values near the average value end up
	 * with a 0.5 value.
	 *
	 * This gives a "visually more pleasing" distribution of the values.
	 *
	 * This prevents that for values like 1,2,5,7,100 (avg: 23)
	 * we and up with a distribution like: .01,.02,.05,.07,1
	 * but we get with: 0.02, 0.04, 0.11, 0.15, 1
	 *
	 * @param item
	 * @param max
	 * @param avg
	 * @return
	 */
	private double normalize( T item, double max, double avg ){

		double weight = weighter.weight( item ),
		       result;

		if( weight <= avg ){
			result = (weight/avg) / 2.0;
		} else {
			result = weight/max;
		}

		return result;
	}


	@Override
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