package de.axone.tools;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Set;
import java.util.TreeSet;

import de.axone.exception.Assert;

public class PasswordBuilder {
	
	public static final int DEFAULT_LENGTH = 12;
	public static final int DEFAULT_ROUNDS_EXP = 10; // <1024 rounds
	public static final String DEFAULT_ALGO = "SHA-256";
	public static final String DEFAULT_PROVIDER = "SUN";
	
	public static final String HASH_SPLIT = "\\$";

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
		'!', '$', '*', '#', '+', '-', '=', '%', '(', ')', '?', '/', '&', '@'
	};
	
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
	
	public static void main( String [] args ){
		
		for( int i=0; i<100; i++ ){
			
			System.out.println( makeSimplaPasswd() );
		}
	}
	
	/**
	 * Make a simple to type password
	 * 
	 * The password is in the form of aaaa1111
	 * 
	 * @return
	 */
	public static String makeSimplaPasswd(){
		
		return makePasswd( 4, true, false, true, false, false ) +
		       makePasswd( 4, true, false, false, true, false );
		
	}
	
	/**
	 * Make a password with usefull settings
	 * 
	 * uses DEFAULT_LENGTH as length. Currently 12 chars.
	 * humanized, including upper and lower case, numbers but no special chars
	 */
	public static String makePasswd(){
		return makePasswd( DEFAULT_LENGTH );
	}
	
	
	/**
	 * Make a password.
	 * 
	 * humanized, including upper and lower case, numbers but no special chars
	 * 
	 * @param length
	 * @return the generated password
	 */
	public static String makePasswd( int length ){
		return makePasswd( length, true, true, true, true, false );
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
			
			if( r != null ){
			
				idx = (int)( r.nextFloat() * chars );
			} else {
				// Fallback to simple random
    			idx = (int) (Math.random() * chars);
			}
			
			result.append( allowedChars[ idx ] );
		}
		
		return result.toString();
	}
	
	private static String makeSalt( int chars ){
		
		return makePasswd( chars, false, true, true, true, false );
		
	}
	
	public static boolean checkPassword( String plain, String hashed ) {
		
		Assert.notNull( hashed, "hashed" );
		
		String parts[] = hashed.split( HASH_SPLIT );
		
		if( parts.length == 1 ){
			
			return hashed.equals( plain );
			
		} else {
			
			Assert.inRange( parts.length, "parts", 4, 4 );
		
			String algo = parts[0];
			int rounds = Integer.parseInt( parts[1] );
			String salt = parts[2];
		
			try {
				String ref = hashPassword( plain, algo, salt, rounds );
				
				return hashed.equals( ref );
				
			} catch( NoSuchAlgorithmException e ) {
				throw new IllegalArgumentException( "Algo: '" + algo + "' is unknown for hashing" );
			}
		}
	}
	
	public static String hashPassword( String plain, String algo, String salt, int roundsExp )
	throws NoSuchAlgorithmException {
		
		try {
			MessageDigest digest = MessageDigest.getInstance( algo, DEFAULT_PROVIDER );
			digest.update( salt.getBytes() );
			digest.update( plain.getBytes() );
			byte [] hashed = digest.digest();
			
			int rounds = 1<<roundsExp;
			for( int i=0; i < rounds; i++ ){
				hashed = digest.digest( hashed );
			}
			
			String readable = (new BASE64Encoder()).encode( hashed );
			
			return algo + "$" + roundsExp + "$" + salt + "$" + readable.trim();
		} catch( NoSuchProviderException e ) {
			throw new Error( "Unknown Provider: " + DEFAULT_PROVIDER );
		}
		
		
	}
	
	public static String hashPassword( String plain, String algo )
	throws NoSuchAlgorithmException {
		return hashPassword( plain, algo, makeSalt( 24 ), DEFAULT_ROUNDS_EXP );
	}
	public static String hashPassword( String plain ){
		
		String algo = DEFAULT_ALGO;
		try {
			return hashPassword( plain, algo );
		} catch( NoSuchAlgorithmException e ) {
			throw new Error( "Error in installation: " + algo + " is missing" );
		}
	}
}
