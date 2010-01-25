package de.axone.web;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class PasswordBuilder {

	private static final char [] allowedChars = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z',
		'2', '3','4', '5', '6', '7', '8', '9',
		'!', '$', '*', '#', '+', '-', '='
	};
	
	public static String makePasswd( int length ){
		
		SecureRandom r=null;
		try {
			r = SecureRandom.getInstance( "SHA1PRNG", "SUN" );
		} catch( GeneralSecurityException e ) {
			e.printStackTrace();
		}
		
		StringBuilder result = new StringBuilder( length );
		
		int chars = allowedChars.length;
		
		for( int i = 0; i < length; i++ ){
			
			int idx;
			
			int range;
			if( i == 0 || i == length-1 ){
				range = chars-15;
			} else {
				range = chars;
			}
			
			if( r != null ){
			
				idx = (int)( r.nextFloat() * range );
			} else {
				// Fallback to simple random
    			idx = (int) (Math.random() * range);
			}
			
			result.append( allowedChars[ idx ] );
		}
		
		return result.toString();
	}
	
}
