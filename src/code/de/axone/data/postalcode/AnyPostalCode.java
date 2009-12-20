package de.axone.data.postalcode;

import de.axone.data.Pair;


public class AnyPostalCode extends AbstractPostalCode {

	public AnyPostalCode() {}

	@Override
	public String tellProblem( String userInput ) {

		if( userInput == null ) return null;

		Pair<String, String> codes = parseCode( userInput );

		if( codes.getRight() == null || codes.getRight().length() == 0 ) return "Wrong code: "
				+ userInput;

		return null;
	}

	@Override
	public void parse( String userInput ) throws IllegalArgumentException {

		if( ! isValid( userInput ) ){
    		throw new IllegalArgumentException( tellProblem( userInput ) );
		}

		if( userInput == null ) {
			setCode( null );
			return;
		}

		Pair<String, String> codes = parseCode( userInput );

		setCode( codes.getRight() );

	}

}
