package de.axone.web.encoding;

import java.util.regex.Pattern;


/**
 * Encodes Text so only text is returned. All special chars are removed
 * 
 * @author flo
 *
 */
public class Encoder_Text implements Encoder {
	
	private Encoder_Text(){}
	private static Encoder_Text instance = new Encoder_Text();
	public static Encoder_Text instance(){
		return instance;
	}
	
	private static final Pattern WS = Pattern.compile( "\\W" );
	
	public static String ENCODE( CharSequence value ) {
		
		return WS.matcher( value ).replaceAll( " " );
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
