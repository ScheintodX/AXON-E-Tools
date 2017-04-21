package de.axone.i18n;

import org.assertj.core.api.AbstractAssert;

public class CountryAssert extends AbstractAssert<CountryAssert, Country> {

	protected CountryAssert( Country actual ) {
		super( actual, CountryAssert.class );
	}
	
	public static CountryAssert assertThis( Country country ) {
		return new CountryAssert( country );
	}

}
