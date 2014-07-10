package de.axone.cache;

import java.util.Random;

/**
 * Maps i -> random(i) 
 * so that it's a stable mapping.
 * 
 * @author flo
 */
public class RandomMapper {
	
	public static int integer( int value ){
		
		Random rng = new Random( value );
		rng.nextInt(); // shuffle
		rng.nextInt();
		
		return rng.nextInt();
	}
	
	public static int positiveInteger( int value ){
		return Math.abs( integer( value ) );
	}
	
}
