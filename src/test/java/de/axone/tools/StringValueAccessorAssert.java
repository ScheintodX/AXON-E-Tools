package de.axone.tools;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AbstractAssert;

public class StringValueAccessorAssert<K,EX extends Exception>
extends AbstractAssert<StringValueAccessorAssert<K,EX>, StringValueAccessor<K,EX>>{

	public StringValueAccessorAssert( StringValueAccessor<K, EX> actual ) {

		super( actual, StringValueAccessorAssert.class );
	}

	public static <L,VI extends Exception> StringValueAccessorAssert<L,VI>
	assertThis( StringValueAccessor<L,VI> actual ){

		return new StringValueAccessorAssert<>( actual );
	}

	public StringValueAccessorAssert<K,EX> containsKey( K key ){

		if( ! actual.has( key ) )
				failWithMessage( "Doesn't contain key (%s)", key );

		return myself;
	}

	public StringValueAccessorAssert<K,EX> doesNotContainKey( K key ){

		if( actual.has( key ) )
				failWithMessage( "Does contain key (%s) but should not", key );

		return myself;
	}

	public StringValueAccessorAssert<K,EX> valueIs( K key, String value ){

		containsKey( key );
		assertThat( actual.get( key ) ).isEqualTo( value );

		return myself;
	}

	public StringValueAccessorAssert<K,EX> valueIsIgnoringCase( K key, String value ){

		containsKey( key );
		assertThat( actual.get( key ) ).isEqualToIgnoringCase( value );

		return myself;
	}

	public StringValueAccessorAssert<K,EX> valueAsIntegerIs( K key, Integer value ){

		containsKey( key );
		assertThat( actual.getInteger( key ) ).isEqualTo( value );

		return myself;
	}

	public StringValueAccessorAssert<K,EX> valueAsDoubleIs( K key, Double value ){

		containsKey( key );
		assertThat( actual.getDouble( key ) ).isEqualTo( value );

		return myself;
	}

	@SuppressWarnings( { "unchecked" } )
	public <EN extends Enum<EN>> StringValueAccessorAssert<K,EX> valueAsEnumIs( K key, EN value ){

		assertThat( actual.getEnum( value.getClass(), key ) ).isEqualTo( value );

		return myself;
	}

	public StringValueAccessorAssert<K,EX> valueAsBooleanIs( K key, Boolean value ){

		assertThat( actual.getBoolean( key ) ).isEqualTo( value );

		return myself;
	}


}
