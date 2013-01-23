package de.axone.exception.codify;

public class CodifiedError extends Error implements Codified {
	
	public CodifiedError( Error cause ){
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
