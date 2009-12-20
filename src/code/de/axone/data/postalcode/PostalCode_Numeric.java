package de.axone.data.postalcode;

import de.axone.data.Pair;


public class PostalCode_Numeric extends AbstractPostalCode {

	private int length;


	public PostalCode_Numeric( int length ){
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setLength( int length ) {
		this.length = length;
	}

	@Override
	public String tellProblem( String userInput ){

		if( userInput == null ) return null;

		Pair<String,String> codes = parseCode( userInput );

		String code = codes.getRight();
		if( code.length() != length ){
			return "Wrong length: " + code + "(" + code.length() + ") should be: " + length;
		}

		for( int i = 0; i < code.length(); i++ ){
			char c = code.charAt( i );
			if( c < '0' || c > '9' ) return "Invalid char (" + c + ") in: " + code;
		}

		return null;
	}

	@Override
	public void parse( String userInput ) throws IllegalArgumentException {

		if( ! isValid( userInput ) ){
    		throw new IllegalArgumentException( tellProblem( userInput ) );
		}

		if( userInput == null ){
			setCode( null );
			return;
		}

		Pair<String,String> codes = parseCode( userInput );

		setCode( codes.getRight() );

	}

}
