package de.axone.web.encoding;


/**
 * Encodes Text so it can be used in mails
 * 
 * Just in case a placeholder. Does nothing by now
 * 
 * @author flo
 *
 */
public class TextEncoder implements Encoder {
	
	private TextEncoder(){}
	private static TextEncoder instance = new TextEncoder();
	public static TextEncoder instance(){
		return instance;
	}
	
	public static String ENCODE( String value ) {
		
		return value;
	}

	@Override
	public String encode( String value ) {
		
		return ENCODE( value );
	}

}
