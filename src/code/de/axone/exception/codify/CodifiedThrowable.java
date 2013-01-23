package de.axone.exception.codify;

public class CodifiedThrowable extends Throwable implements Codified {
	
	public CodifiedThrowable( Throwable cause ){
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
