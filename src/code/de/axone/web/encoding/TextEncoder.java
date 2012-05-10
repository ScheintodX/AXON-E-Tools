package de.axone.web.encoding;


/**
 * Encodes Text so only text is returned. All special chars are removed
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
		
		return value.replaceAll( "\\W", "" );
	}

	@Override
	public String encode( String value ) {
		
		return ENCODE( value );
	}

}
