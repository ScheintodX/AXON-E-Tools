package de.axone.exception.codify;

public class CodifiedRuntimeException extends RuntimeException implements Codified {
	
	public CodifiedRuntimeException( RuntimeException cause ){
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
