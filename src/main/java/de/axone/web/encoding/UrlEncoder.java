package de.axone.web.encoding;

public class UrlEncoder extends AttributeEncoder {

	private static final UrlEncoder instance = new UrlEncoder();
	public static UrlEncoder instance(){
		return instance;
	}
	private UrlEncoder(){}
	
	public static String ENCODE( String value ) {
		String encoded = AttributeEncoder.ENCODE( value );
		encoded = encoded.replace( "%", "%25" );
		return encoded;
	}
	
	@Override
	public String encode( String value ) {
		return ENCODE( value );
	}
}
