package de.axone.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import de.axone.exception.Assert;

public class PasswordBuilder {

	public static final int DEFAULT_LENGTH = 12;
	public static final int DEFAULT_ROUNDS_EXP = 12; // <4096 rounds
	public static final String DEFAULT_ALGO = "SHA-256";
	public static final String DEFAULT_PROVIDER = "SUN";

	public static final String HASH_SPLIT = "\\$";

	/*
	private static final char [] allowedCharsDENIC =
			"aAbBcCdDeEfFgGhHijJkKLmMnNpPqQrRsStTuUvVwWxXyYzZ23456789+-/".toCharArray();
	*/

	private static final char [] allowedCharsLowerCase =
			"abcdefghijkmnopqrstuvwxyz".toCharArray();

	private static final char [] allowedCharsUpperCase =
			"ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();

	private static final char [] allowedCharsNumbers =
			"23456789".toCharArray();

	private static final char [] allowedCharsSpecial =
			"!$*#+-=%()?/&@".toCharArray();

	private static void addAll( Set<Character> set, char[] toAdd ){

		for( char c : toAdd ) set.add( c );
	}

	private static char[] makeValues( boolean humanize, boolean includeUpperCase, boolean includeLowerCase, boolean includeNumbers, boolean includeSpecial ){

		int c=0;
		if( includeUpperCase ) c++;
		if( includeLowerCase ) c++;
		if( includeNumbers ) c++;
		if( includeSpecial ) c++;

		if( c == 0 ) throw new IllegalArgumentException( "At least spzify one arg as true" );

		Set<Character> result = new TreeSet<>();
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

		//Character [] resultArray = new Character[ result.size() ];
		char [] resultArray = new char[ result.size() ];
		int i=0;
		for( Character rc : result ){
			resultArray[ i++ ] = rc.charValue();
		}

		return resultArray;
	}

	public static void main( String [] args ) throws Exception {

		int i=0;
		try( BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) ) ){

			String line;
			while( ( line = in.readLine() ) != null ){

				i++;

				String [] parts = line.split( "\\s+", 2 );
				Assert.equal( parts.length, "parts", 2 );

				String id = parts[0];
				String pass = parts[1];

				System.out.printf( "UPDATE user SET password='%s' where id=%s;\n",
						PasswordBuilder.hashPassword( pass ), id );
			}
		} catch( Exception e ){
			E.rr( "In line: " + i );
			throw e;
		}

		/*
		int len = DEFAULT_LENGTH;
		if( args.length == 1 ){
			len = Integer.parseInt( args[0] );
		}

		String plain = makePasswd( len );
		String salt = makeSalt( DEFAULT_LENGTH );

		String hashed = hashPassword( plain, "SHA-1", salt, DEFAULT_ROUNDS_EXP );

		boolean ok = checkPassword( plain, hashed );

		System.out.printf( "%s: %s -> %s\n", ok?"OK":"ERR", plain, hashed );
		*/

		/*
		for( int i=0; i<10; i++ )
				E.rr( makeSimplaPasswd() );
		*/


	}

	/**
	 * Make a simple to type password
	 *
	 * The password is in the form of aaaa1111
	 *
	 * @return the generated password
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
	 *
	 * @return the generated password
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

		char [] allowedChars = makeValues( humanize, includeUpperCase, includeLowerCase, includeNumbers, includeSpecial );

		return makePasswd( allowedChars, length );
	}

	public static String makePasswd( char [] vocabulary, int length ){

		Random r=null;
		try {
			r = SecureRandom.getInstance( "SHA1PRNG", "SUN" );
		} catch( GeneralSecurityException e ) {
			e.printStackTrace();
		}
		// Fallback if something is wrong with Java sec. Should never happen
		if( r == null ){
			r = new Random();
		}

		StringBuilder result = new StringBuilder( length );

		int chars = vocabulary.length;

		int idx;
		for( int i = 0; i < length; i++ ){

			//idx = (int)( r.nextFloat() * chars );
			idx = r.nextInt( chars );

			result.append( vocabulary[ idx ] );
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

			String readable = Base64ApacheCommons.EncodeURLSafe( hashed );

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
