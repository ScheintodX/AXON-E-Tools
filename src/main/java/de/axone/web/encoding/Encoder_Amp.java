package de.axone.web.encoding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encodes single &amp; characters to &amp;amp;
 * 
 * @author flo
 */
public class Encoder_Amp implements Encoder {
	
	// Note: ?= is a lookahead matcher. (Meaning: is expected to be there but
	// isnt't included in match
	private static final Pattern pattern = Pattern.compile( "&(?=\\s+)" );
	
	private Encoder_Amp(){}
	private static Encoder_Amp instance = new Encoder_Amp();
	public static Encoder_Amp instance(){
		return instance;
	}
	
	public static String ENCODE( CharSequence value ) {
		
		if( value == null ) return null;
		
		Matcher matcher = pattern.matcher( value );
		
		return matcher.replaceAll( "&amp;" );
	}

	@Override
	public String encode( CharSequence value ) {
		
		return ENCODE( value );
	}

	@Override
	public Appendable filter( Appendable out ) {
		return new EncodingAppender( this, out );
	}

}
