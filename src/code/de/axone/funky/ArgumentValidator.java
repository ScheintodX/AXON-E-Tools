package de.axone.funky;

public interface ArgumentValidator<T> {

	/**
	 * Return a description of the valid ranges of this argument.
	 * 
	 *  E.g. "Range 1..5"
	 *  
	 *  TODO: Context rein ums dynamisch zu gestalten
	 *  
	 * @return
	 */
	public String description();
	
	/**
	 * Test the given value if it matches the description
	 * 
	 * @param value
	 * @return an error message or null if ok
	 */
	public String validate( T value );
	
}
