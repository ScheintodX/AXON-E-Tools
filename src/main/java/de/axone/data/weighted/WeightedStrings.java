package de.axone.data.weighted;

import java.util.Comparator;
import java.util.function.Supplier;


public abstract class WeightedStrings<L extends WeightedStrings<L,S>, S extends WeightedString>
extends AbstractWeightedCollection<L, S> {

	public WeightedStrings( Supplier<L> supplier,
			WeightedCollection.Cloner<S> cloner ) {

		super( supplier, item -> item.weight, cloner );
	}

	public static Comparator<WeightedString> WeightComparator = new Comparator<WeightedString>() {

		@Override
		public int compare( WeightedString o1, WeightedString o2 ) {

			double diff = o2.weight - o1.weight;
			return (diff > 0) ? 1 : ((diff < 0) ? -1 : 0);
		}
	};

	public static Comparator<WeightedString> TextComparator = new Comparator<WeightedString>() {

		@Override
		public int compare( WeightedString o1, WeightedString o2 ) {

			return o1.text().compareTo( o2.text() );
		}

	};

}