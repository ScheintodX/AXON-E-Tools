package de.axone.data.postalcode;

import de.axone.data.Pair;


public abstract class AbstractPostalCode extends PostalCode {

	private String code;
	private String countryCode;

	@Override
	public boolean isValid( String userInput ){
		return tellProblem( userInput ) == null;
	}

	@Override
	public void setCode( String code ){
		this.code = code.toUpperCase();
	}

	@Override
	public void setCountryCode( String countryCode ){
		this.countryCode = countryCode.toUpperCase();
	}

	@Override
	public String getCode() {
		return code;
	}
	@Override
	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public int compareTo( PostalCode o ) {

		return code.compareTo( o.getCode() );
	}

	@Override
	public int compareToPrefix( String prefix ){

		prefix = prefix.toUpperCase();
		if( code.equals( prefix ) ) return 0;
		else if( code.startsWith( prefix ) ) return 0;
		else return code.substring( 0, prefix.length() ).compareTo( prefix );
	}

	@Override
	public boolean inRange( String prefix1, String prefix2 ){

		return compareToPrefix( prefix1 ) >= 0 && compareToPrefix( prefix2 ) <= 0;
	}

	@Override
	public String toString( boolean includeCountryCode ){
		if( includeCountryCode ) return getCountryCode() + "-" + getCode();
		else return getCode();
	}

	@Override
	public String toString(){
		return toString( true );
	}

	/* -- HELPER -- */

	private char [] ccDelimiters = new char[]{ '-' };
	protected Pair<String,String> parseCode( String userInput ) throws IllegalArgumentException {

		if( userInput == null ) throw new IllegalArgumentException( "No Input" );

		for( char c : ccDelimiters ){

			int idx = userInput.indexOf( c );
			if( idx > 0 ){
				String countryCode = userInput.substring( 0, idx );
				String code = userInput.substring( idx+1 );
				return new Pair<String,String>( countryCode.trim(), code.trim() );
			}
		}
		return new Pair<String,String>( null, userInput.trim() );
	}

	protected boolean parseHasCountryCode( String userInput ) {

		if( userInput == null ) return false;

		for( char c : ccDelimiters ){
			if( userInput.indexOf( c ) > 0 ) return true;
		}
		return false;
	}
}
