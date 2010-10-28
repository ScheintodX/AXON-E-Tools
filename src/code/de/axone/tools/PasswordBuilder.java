package de.axone.tools;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Set;
import java.util.TreeSet;

public class PasswordBuilder {

	private static final char [] allowedCharsLowerCase = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	};
	private static final char [] allowedCharsUpperCase = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	};
	private static final char [] allowedCharsNumbers = {
		'2', '3','4', '5', '6', '7', '8', '9',
	};
	private static final char [] allowedCharsSpecial = {
		'!', '$', '*', '#', '+', '-', '='
	};
	
	public static void main( String [] args ){
		Character [] ch = makeValues( true, false, true, true, false );
		char[] c = new char[ ch.length ];
		for( int i = 0; i < ch.length; i++ ) c[i] = ch[i];
		E.rr( new String(  c  ) );
		
		E.rr( makePasswd( 8, true ) );
	}
	
	private static void addAll( Set<Character> set, char[] toAdd ){
		
		for( char c : toAdd ) set.add( c );
	}
	
	private static Character[] makeValues( boolean humanize, boolean includeUpperCase, boolean includeLowerCase, boolean includeNumbers, boolean includeSpecial ){
		
		int c=0;
		if( includeUpperCase ) c++;
		if( includeLowerCase ) c++;
		if( includeNumbers ) c++;
		if( includeSpecial ) c++;
		
		if( c == 0 ) throw new IllegalArgumentException( "At least spzify one arg as true" );
		
		Set<Character> result = new TreeSet<Character>();
		if( includeLowerCase ){
			if( ! humanize || (! includeUpperCase && !includeNumbers ) ){
				result.add( 'l' );
			}
			addAll( result, allowedCharsLowerCase );
		}
		if( includeUpperCase ){
			if( ! humanize || (! includeLowerCase && !includeNumbers ) ){
				result.add( 'I' );
			}
			if( !includeNumbers ){
				result.add( 'O' );
			}
			addAll( result, allowedCharsUpperCase );
		}
		if( includeNumbers ){
			if( !humanize || !includeUpperCase ){
				result.add( '0' );
			}
			if( ! humanize || ( !includeUpperCase && !includeLowerCase ) ){
				result.add( '1' );
			}
			addAll( result, allowedCharsNumbers );
		}
		if( includeSpecial ){
			addAll( result, allowedCharsSpecial );
		}
		
		Character [] resultArray = new Character[ result.size() ];
		
		return result.toArray( resultArray );
	}
	
	/**
	 * Make a password.
	 * 
	 * humanized, including upper and lower case, numbers and special chars
	 * 
	 * @param length
	 * @return the generated password
	 */
	public static String makePasswd( int length ){
		return makePasswd( length, true, true, true, true, true );
	}
	
	/**
	 * 
	 * Make a password.
	 * 
	 * including upper and lower case, numbers and no special chars
	 * 
	 * @param length
	 * @param humanize use only unproblematic characters
	 * @return the generated password
	 */
	public static String makePasswd( int length, boolean humanize ){
		return makePasswd( length, humanize, true, true, true, false );
	}
	
	/**
	 * Make a password.
	 * 
	 * @param length length in chars
	 * @param humanize use only characters which are unproblematic for humans
	 * @param includeUpperCase include upper case letters
	 * @param includeLowerCase include lower case letters
	 * @param includeNumbers include numbers
	 * @param includeSpecial include special chars
	 * @return the generated password
	 */
	public static String makePasswd( int length, boolean humanize, boolean includeUpperCase, boolean includeLowerCase, boolean includeNumbers, boolean includeSpecial ){
		
		SecureRandom r=null;
		try {
			r = SecureRandom.getInstance( "SHA1PRNG", "SUN" );
		} catch( GeneralSecurityException e ) {
			e.printStackTrace();
		}
		
		StringBuilder result = new StringBuilder( length );
		
		Character [] allowedChars = makeValues( humanize, includeUpperCase, includeLowerCase, includeNumbers, includeSpecial );
		
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
