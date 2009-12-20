package de.axone.data.postalcode;

import java.util.Locale;


public abstract class PostalCode implements Comparable<PostalCode> {

	public abstract boolean isValid( String userInput );
	public abstract String tellProblem( String userInput );
	public abstract void parse( String userInput ) throws IllegalArgumentException ;

	public abstract String toString( boolean includeCountryCode );

	public abstract void setCode( String code );
	public abstract String getCode();

	public abstract void setCountryCode( String countryCode );
	public abstract String getCountryCode();

	public abstract int compareTo( PostalCode other );
	public abstract int compareToPrefix( String prefix );

	public abstract boolean inRange( String prefix1, String prefix2 );

	public static PostalCode instance( Locale locale ){

		if( locale == null ) throw new IllegalArgumentException( "Parameter is null" );

		String iso2 = locale.getCountry();
		if( iso2 == null ) throw new IllegalArgumentException( "Country is null" );

		if( "DE".equals( iso2 ) ) return new PostalCode_Numeric( 5 );
		else if( "AT".equals( iso2 ) ) return new PostalCode_Numeric( 4 );

		throw new IllegalArgumentException( "Cannot find PostalCode for " + locale );
	}
}
