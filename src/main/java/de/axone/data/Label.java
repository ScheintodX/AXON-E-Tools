package de.axone.data;

/**
 * Mainly a marker class for things which can be used as label for something.
 * 
 * This is used in class which support Label as parameters to use {@link Label#label()}
 * instead of {@link Object#toString()}
 * 
 * E.g. SuperURL uses this for creation of links or DataHolder uses this
 * for ease of use.
 * 
 * @author flo
 */
public interface Label {
	
	public String label();
}
