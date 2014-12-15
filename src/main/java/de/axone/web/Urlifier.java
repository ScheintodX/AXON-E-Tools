package de.axone.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.data.Charsets;
import de.axone.refactor.Refactor;

/**
 * Class to support encoding of Urls in any form.
 * 
 * Todo: Übersichtlicher gestalten
 * Todo: Fremdsprachen
 * 
 * @author flo
 */
@Refactor( action="remove", reason="replaced by SuperUrlPrinter" )
public abstract class Urlifier {
	
	public static final Logger log =
			LoggerFactory.getLogger( Urlifier.class );
	
	public enum Mode { ASCII, LATIN; }
	public static volatile Mode mode = Mode.LATIN;

	/**
	 * Encode the given url with url encoding
	 * 
	 * @param plain
	 * @return the encoded URL as String
	 */
	public static String encode( String plain ){
		
		if( plain == null ) return null;
		
		try {
			String result = java.net.URLEncoder.encode( plain, Charsets.utf8 );
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
			decoded = java.net.URLDecoder.decode( encoded, Charsets.utf8 );
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}
		return decoded;
	}
	
	/**
	 * Replacement for response.encodeRedirectUrl. Neccessary in order
	 * to avoid appending JSESSIONID. Do *NEVER* call the other one.
	 * 
	 * @param url
	 * @return encoded url
	 */
	private static String encodeRedirectURL( SuperURL url ){
		return url.toRedirect();
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
