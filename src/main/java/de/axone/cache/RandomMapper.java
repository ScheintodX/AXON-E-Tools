package de.axone.cache;

import java.util.Random;

/**
 * Maps i -> random(i) 
 * so that it's a stable mapping.
 * 
 * @author flo
 */
public class RandomMapper {
	
    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;
    
    static int integerUsingRandom( int value ){
    	
		Random rng = new Random( value );
		rng.nextInt(); // shuffle
		rng.nextInt();
		
		return rng.nextInt();
    }
    
	public static int integer( int value ){
		
		// initial scramble
		long seed = (value ^ multiplier) & mask;
		
		// shuffle three times. This is like rng.nextInt()
		seed = (seed * multiplier + addend) & mask;
		seed = (seed * multiplier + addend) & mask;
		seed = (seed * multiplier + addend) & mask;
		
		// fit size
		return (int)(seed >>> 16);
	}
	
	public static int positiveInteger( int value ){
		return Math.abs( integer( value ) );
	}
	
}
