package de.axone.exception.codify;

public class CodifiedException extends Exception implements Codified {
	
	public CodifiedException( Exception cause ){
		super( cause );
	}

	@Override
	public String code() {
		return Codifier.codify( getCause() );
	}
	
	@Override
	public String getMessage() {
		return Codifier.message( getCause() );
	}
}
