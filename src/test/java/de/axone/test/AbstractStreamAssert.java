package de.axone.test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.ListAssert;

public abstract class AbstractStreamAssert<
		X,
		S extends AbstractObjectAssert<S, Stream<X>>

> extends AbstractObjectAssert<S,Stream<X>> {

	protected AbstractStreamAssert( Stream<X> actual ) {
		super( actual, AbstractStreamAssert.class );
	}
	
	public S hasSize( int size ) {
		
		org.assertj.core.api.Assertions.assertThat( actual.count() )
				.as( descriptionText() )
				.isEqualTo( size );
		
		return myself;
	}
	
	public ListAssert<X> toListAssert() {
		
		return org.assertj.core.api.Assertions.assertThat(
				actual.collect( Collectors.toList() ) );
	}
	
	public List<X> theList() {
		
		return actual.collect( Collectors.toList() );
	}
	
}