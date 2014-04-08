package de.axone.exception.codify.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import de.axone.exception.codify.Codified;
import de.axone.exception.codify.Codifier;

class CodifiedIOException extends IOException implements Codified {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7706891727387565336L;

	public CodifiedIOException( IOException cause ){
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