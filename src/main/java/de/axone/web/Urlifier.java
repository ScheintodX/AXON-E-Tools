package de.axone.web;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.web.encoding.AmpEncoder;
import de.axone.web.encoding.HtmlEncoder;

/**
 * Class to support encoding of Urls in any form.
 * 
 * Todo: Übersichtlicher gestalten
 * Todo: Fremdsprachen
 * 
 * @author flo
 *
 */
public abstract class Urlifier {
	
	public static final Logger log =
			LoggerFactory.getLogger( Urlifier.class );
	
	private static final String UNKNOWN = "";
	private static final String space = "-";
	private static final boolean collapseSpace = true;
	
	private static final String[] patterns		= { " ", "ä", "ö", "ü", "Ä", "Ö", "Ü", "ß" };
	private static final String[] replacements	= { "-", "ae", "oe", "ue", "Ae", "Oe", "Ue", "ss" };
	
	public enum Mode { ASCII, LATIN; }
	//private static final Mode mode = Mode.ASCII;
	public static volatile Mode mode = Mode.LATIN;

	//private static final String[] patterns		= { " " };
	//private static final String[] replacements	= { "-" };

	// Replace all but alphanum and _-
	private static final Pattern replacementPattern = Pattern.compile( "[^a-zA-Z0-9-]+" );
	
	private static final Pattern collapsePattern = Pattern.compile( space + "+" );
	
	/**
	 * Create a nice looking url of this string.
	 * 
	 * Note that this Process is irreversible
	 * 
	 * @param plain
	 * @return the urlified URL as String
	 */
	public static String urlify( String plain ){
		
		if( plain == null ) return null;
		
		String result = urlifyPlain( plain );
		
		try {
			result = java.net.URLEncoder.encode( result, "utf-8" );
		} catch( UnsupportedEncodingException e ) {
			
			log.error( "Cannot urlify: " + plain );
			return plain;
		}
		
		return result;
	}
	
	/**
	 * Does the same like <tt>urlify</tt> but doesn't url encode
	 * the result;
	 * 
	 * @param plain
	 * @return the urlified URL as String
	 */
	public static String urlifyPlain( String plain ){
		switch( mode ){
		case LATIN: return urlifyPlainLatin( plain );
		case ASCII:
		default: return urlifyPlainAscii( plain );
		}
	}
	
	public static String urlifyPlainAscii( String plain ){
		
		if( plain == null ) return null;
		
		String result = plain;
		
		// Replace common resriptions (ä->ae)
		for( int i = 0; i < patterns.length; i++ ){
			result = result.replace( patterns[ i ], replacements[ i ] );
		}
		
		// Normalize. this make é -> e´
		result = Normalizer.normalize( result, Normalizer.Form.NFKD );
		
		// Remove unknown chars (all but letters and digits)
		Matcher replacementMatcher = replacementPattern.matcher( result );
		result = replacementMatcher.replaceAll( UNKNOWN );
		
		// Remove/Replace spaces
		if( collapseSpace ){
			Matcher collapseMatcher = collapsePattern.matcher( result );
			result = collapseMatcher.replaceAll( space );
		}
		
		return result;
	}
	
	private static final String SPACE = "-";
	// chars which are diffrenent in iso8859-1/-15 and are not used
	private static final Set<Character> LATIN_DIFFS = new HashSet<Character>(
			Arrays.asList( new Character [] { 0xA4, 0xA6, 0xA8, 0xB4, 0xB8, 0xBC, 0xBC, 0xBE } ) );
	
	/**
	 * Translate unicode to latin-8859-15 in a way that is most compatible
	 * with browser urls.
	 * 
	 * This method tries to keep as much information as possible.
	 * 
	 * This doesn't work because of some strange fuck
	 * 
	 * @param plain The text in plain
	 * @return the encoded string
	 */
	public static String urlifyPlainLatin( String plain ){
		return urlifyPlainLatin( plain, true );
	}
	
	private static String urlifyPlainLatin( String plain, boolean normalize ){
		
		if( plain == null ) return null;
		
		plain = plain.trim();
		
		StringBuilder sb = new StringBuilder();
		
		boolean wasSpace = false;
		for( char c : plain.toCharArray() ){
			
			String a=null;
			
			if( c == 0x20 || c == 0xa0 ){
				a = SPACE; // SP,NBSP
			} else {
				if( c == '+' ) a="&";
				if( c == '/' || c == '\\' ) a="-";
				else if( c > 0x20 && c < 0x7f ) a=Character.toString( c ); // ASCII
				else if( c >= 0xA0 && c <= 0xff ){
					if( ! LATIN_DIFFS.contains( c ) ) a=Character.toString( c );
				} else if( c > 0xff && normalize ){
					// Try decomposition
					String s = Character.toString( c );
					s = Normalizer.normalize( s, Normalizer.Form.NFKD );
					if( s.length() > 1 ){
						s = urlifyPlainLatin( s, false );
						a=s;
					}
				}
			}
			
			if( a != null ){
				if( collapseSpace && a.equals( SPACE ) ){
					if( !wasSpace ) sb.append( SPACE );
					wasSpace=true;
				} else {
					sb.append( a );
					wasSpace=false;
				}
			}
		}
		
		String result = sb.toString();
		return result;
	}
	
	/**
	 * Encode the given url with url encoding
	 * 
	 * @param plain
	 * @return the encoded URL as String
	 */
	public static String encode( String plain ){
		
		if( plain == null ) return null;
		
		try {
			String result = java.net.URLEncoder.encode( plain, "utf-8" );
			return result;
		} catch( UnsupportedEncodingException e ) {
			
			log.error( "Cannot encode: " + plain );
			return plain;
		}
	}
	
	/**
	 * Decode url to String
	 * 
	 * By now only replaces '+' with ' '
	 * 
	 * @param encoded
	 * @return the decoded URL as String
	 */
	public static String decode( String encoded ){
		
		if( encoded == null ) return null;
		
		String decoded = null;
		try {
			decoded = java.net.URLDecoder.decode( encoded, "utf-8" );
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}
		return decoded;
	}
	
	/**
	 * Replaces &amp; by &amp;amp;
	 * 
	 * This method is mainly for escaping Database Text which includes
	 * &amp; character but no html which will stay intact. (At least if
	 * it doesn't contain &amp;)
	 * 
	 * @param plain
	 * @return the escaped string
	 */
	public static String escapeAmp( String plain ){
		if( plain == null ) return null;
		
		return AmpEncoder.ENCODE( plain );
	}
	
	/**
	 * Replaces HTML characters &amp;, &lt;, &gt;
	 * 
	 * Removes dangerous characters
	 * This method should be used for any user inputable
	 * output.
	 * 
	 * @param plain
	 * @return the escaped string
	 */
	public static String escapeHtml( String plain ){
		if( plain == null ) return null;
		
		return HtmlEncoder.ENCODE( plain );
		
	}
	
	/**
	 * Replacement for response.encodeRedirectUrl. Neccessary in order
	 * to avoid appending JSESSIONID. Do *NEVER* call the other one.
	 * 
	 * @param url
	 * @return encoded url
	 */
	private static String encodeRedirectURL( String url ){
		return url;
	}

	/**
	 * Replacement for response.encodeRedirectUrl. Neccessary in order
	 * to avoid appending JSESSIONID. Do *NEVER* call the other one.
	 * 
	 * @param url
	 * @return encoded url
	 */
	private static String encodeRedirectURL( SuperURL url ){
		return url.toStringEncode( true );
	}

	/**
	 * Send a redirect
	 * 
	 * The url is encoded
	 * 
	 * @param response
	 * @param url
	 * @param status
	 */
	public static void redirectTo( HttpServletResponse response, String url, int status ){
		
		response.setStatus( status );
		response.setHeader( "Location", encodeRedirectURL( url ) );
	}
	
	/**
	 * Send a redirect
	 * 
	 * The url is encoded
	 * 
	 * @param response
	 * @param url
	 * @param status
	 */
	public static void redirectTo( HttpServletResponse response, SuperURL url, int status ){
		
		response.setStatus( status );
		response.setHeader( "Location", encodeRedirectURL( url ) );
	}
	
}
