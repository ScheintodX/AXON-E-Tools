package de.axone.function;

public interface Argument<T>{
	public void parse( String value ) throws ShellException;
	public T value();
	@Override public String toString();
}
	