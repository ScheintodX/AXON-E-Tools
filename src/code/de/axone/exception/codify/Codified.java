package de.axone.exception.codify;

public interface Codified {

	public String code();
	public Throwable getWrapped();
	
}
