package de.axone.exception.codify;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CodifiedError extends Error implements Codified {
	
	public CodifiedError( Error cause ){
		super( cause );
	}

	@Override
	public String code() {
		return Codifier.codify( super.getCause() );
	}
	
	@Override
	public String getMessage() {
		return Codifier.message( super.getCause() );
	}
	
	@Override
	public String getLocalizedMessage() {
		return Codifier.localizedMessage( super.getCause() );
	}

	@Override
	public synchronized Throwable getCause() {
		return super.getCause().getCause();
	}
	
	@Override
	public synchronized Throwable getRealCause() {
		return super.getCause();
	}

	@Override
	public synchronized Throwable initCause( Throwable cause ) {
		return super.getCause().initCause( cause );
	}

	@Override
	public String toString() {
		return super.getCause().toString();
	}

	@Override
	public void printStackTrace() {
		super.getCause().printStackTrace();
	}

	@Override
	public void printStackTrace( PrintStream s ) {
		super.getCause().printStackTrace( s );
	}

	@Override
	public void printStackTrace( PrintWriter s ) {
		super.getCause().printStackTrace( s );
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return super.getCause().getStackTrace();
	}
	
}
