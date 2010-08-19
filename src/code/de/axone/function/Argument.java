package de.axone.function;

public interface Argument<T>{
	public void parse( String value ) throws ShellException;
	public T value();
	public String toString();
}
	