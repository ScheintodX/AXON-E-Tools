package de.axone.function;

public interface ArgumentDescription<T>{
	public String name();
	public String description();
	public String defaultValue();
	public boolean isMandatory();
	public boolean isNamed();
	public Argument<T> argumentInstance();
}

	