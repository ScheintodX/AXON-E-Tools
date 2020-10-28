package de.axone.data.weighted;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

import de.axone.data.weighted.WeightedBag.WeightedEntry;

/**
 * A more simple class for weighted Lists
 *
 * @author flo
 *
 * @param <I>
 */
public class WeightedBag<I> implements Iterable<WeightedEntry<I>>{

	private long accu = 0;

	protected final HashMap<I,Integer> entries
			= new HashMap<>();

	public WeightedBag(){}

	public WeightedBag<I> add( I item, int weight ) {

		accu += weight;

		int sum;
		if( !entries.containsKey( item ) ) {
			sum = weight;
		} else {
			sum = entries.get( item ) + weight;
		}
		entries.put( item, sum );

		return this;
	}

	public WeightedBag<I> add( I item ) {

		return add( item, 1 );
	}

	public WeightedBag<I> addAll( WeightedBag<I> other ){

		for( Map.Entry<I,Integer> entry : other.entries.entrySet() ) {

			add( entry.getKey(), entry.getValue() );
		}
		return this;
	}

	public <X extends WeightedBag<I>> X addTo( X other ){

		other.addAll( this );

		return other;
	}

	public Stream<WeightedEntry<I>> stream() {

		return entries.entrySet().stream()
				.map( WeightedEntryImpl::new )
				;
	}

	@Override
	public Iterator<WeightedEntry<I>> iterator() {

		Iterator<Map.Entry<I, Integer>> mi = entries.entrySet().iterator();

		return new Iterator<WeightedEntry<I>>() {

			@Override
			public boolean hasNext() {
				return mi.hasNext();
			}

			@Override
			public WeightedEntry<I> next() {
				return new WeightedEntryImpl( mi.next() );
			}

		};

	}

	public static <J,K extends WeightedBag<J>> Collector<J,?,WeightedBag<J>> collector() {

			return Collector.of(
					WeightedBag::new,
					WeightedBag::add,
					WeightedBag::addAll );
	}

	@Override
	public String toString() {

		return entries.toString();
	}

	public interface WeightedEntry<I> {

		public I getItem();
		public int getWeight();
		public float getRelativeWeight();

	}


	private class WeightedEntryImpl implements WeightedEntry<I> {

		private final I item;
		final int weight;

		WeightedEntryImpl( Map.Entry<I,Integer> e ){
			this.item = e.getKey();
			this.weight = e.getValue();
		}

		@Override
		public I getItem() {
			return item;
		}

		@Override
		public int getWeight() {
			return weight;
		}

		@Override
		public float getRelativeWeight() {
			return (float)weight / accu;
		}


	}

	public static final class BagOfStrings extends WeightedBag<String>{}
}
