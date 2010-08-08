package de.axone.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides easier access to search/replace
 * 
 * @author flo
 *
 */
public class Regexer {
	
	private Pattern pattern;
	String patternStr;
	String replacementStr;
	
	/**
	 * Create a regexer instance with the given regex String.
	 * The Strings format is something like: <tt>/foo/bar/</tt>
	 * which means replace foo by bar.
	 * 
	 * The type of separator used is determined by the first char
	 * int the String so <tt>/foo/bar/</tt> is equivalent to 
	 * <tt>_foo_bar_</tt> or even "<tt> foo bar </tt>". (The last
	 * one shouldn't be used obviously.
	 * 
	 * The char used as separator must not be used within the
	 * expressions themselves. So for a valid expression it must
	 * occure exactly 3 times.
	 * 
	 * @param regex to initialize the Regexer with
	 */
	public Regexer( String regex ){
		
		if( regex == null || regex.length() < 3 )
			throw new RegexArgumentException( regex, "Wrong length or null" );
		
		char sep = regex.charAt( 0 );
		if( regex.charAt( regex.length()-1 ) != sep )
			throw new RegexArgumentException( regex, "First doesn't match with last char" );
		
		int idx2 = regex.indexOf( sep, 1 );
		
		if( idx2 == regex.length() -1 )
			throw new RegexArgumentException( regex, "Missing middle separator" );
		
		patternStr = regex.substring( 1, idx2 );
		
		if( patternStr.length() == 0 ) return; //Valid. Do nothing regex.
		
		pattern = Pattern.compile( patternStr );
		replacementStr = regex.substring( idx2+1, regex.length() -1 );
	}
	
	/**
	 * Transform the original String using the reges
	 * 
	 * @param original
	 * @return the transformed string
	 */
	public String transform( String original ){
		
		if( pattern == null ) return original;
		
		Matcher matcher = pattern.matcher( original );
		
		return matcher.replaceAll( replacementStr );
	}
	
	private class RegexArgumentException extends IllegalArgumentException {
		public RegexArgumentException( String regex, String message ){
			super( message + " in: '" + regex + "'" );
		}
	}
}
