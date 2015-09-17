package de.axone.tools;

/**
 * Usable for late evaluation of parameters for printing / logging
 * 
 * @author flo
 * @param <T> The type of object this printer is for
 */
public interface Printer<T> {
	
	static <T> Printer<T> of( T object ) {
		return new Printer<T>() {
			@Override
			public String toString(){
				return object.toString();
			}
		};
	}
}
