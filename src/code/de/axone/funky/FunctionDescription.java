package de.axone.funky;

import java.util.List;

public interface FunctionDescription {
	
	public String name();
	public String description();
	
	public List<Argument<?,?>> arguments();
}
