package de.axone.stream;

import java.util.Iterator;
import java.util.stream.Stream;

public class Strapper<T> implements Iterable<T> {

	private final Stream<T> stream;

	public Strapper( Stream<T> stream ) {
		this.stream = stream;
	}

	@Override
	public Iterator<T> iterator(){

		return stream.iterator();
	}

	public static <T> Strapper<T> wrap( Stream<T> s ){

		return new Strapper<>( s );
	}
}
