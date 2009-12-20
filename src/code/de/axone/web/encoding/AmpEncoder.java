package de.axone.web.encoding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encodes single &amp; characters to &amp;amp;
 * 
 * @author flo
 *
 */
public class AmpEncoder implements Encoder {
	
	// Note: ?= is a lookahead matcher. (Meaning: is expected to be there but
	// isnt't included in match
	private static final Pattern pattern = Pattern.compile( "&(?=\\s+)" );
	
	public static String ENCODE( String value ) {
		
		if( value == null ) return null;
		
		Matcher matcher = pattern.matcher( value );
		
		return matcher.replaceAll( "&amp;" );
	}

	@Override
	public String encode( String value ) {
		
		return ENCODE( value );
	}

}
