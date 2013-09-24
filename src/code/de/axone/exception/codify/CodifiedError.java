package de.axone.exception.codify;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CodifiedError extends Error implements Codified {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6995330463413359054L;

	public CodifiedError( Error cause ){
		super( cause );
	}

	@Override
	public String code() {
		return Codifier.codify( getWrapped() );
	}
	
	@Override
	public String getMessage() {
		return Codifier.message( getWrapped() );
	}
	
	@Override
	public String getLocalizedMessage() {
		return Codifier.localizedMessage( getWrapped() );
	}

	@Override
	public synchronized Throwable getCause() {
		return getWrapped().getCause();
	}
	
	@Override
	public synchronized Throwable getWrapped() {
		return super.getCause();
	}

	@Override
	public synchronized Throwable initCause( Throwable cause ) {
		return getWrapped().initCause( cause );
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}

	@Override
	public void printStackTrace() {
		getWrapped().printStackTrace();
	}

	@Override
	public void printStackTrace( PrintStream s ) {
		getWrapped().printStackTrace( s );
	}

	@Override
	public void printStackTrace( PrintWriter s ) {
		getWrapped().printStackTrace( s );
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return getWrapped().getStackTrace();
	}
	
}
