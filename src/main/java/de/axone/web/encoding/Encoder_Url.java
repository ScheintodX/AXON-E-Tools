package de.axone.web.encoding;



/**
 * Users SuperURL to print the url.
 * 
 * Final encoding is for Attributes
 * 
 * @author flo
 */
public class Encoder_Url extends TranslatingEncoder {
	
	public static final char [] FROM = { '<','>','&', '"', '\'', ' ' };
	
	private static String [] buildTO(){
		
		String [] TO = new String[ FROM.length ];
		for( int i=0; i<FROM.length; i++ ){
			
			TO[ i ] = String.format( "%%%02x", (int)FROM[ i ] );
		}
		return TO;
	}
	
	protected Encoder_Url(){
		super( FROM, buildTO() );
	}
	private static final Encoder_Url instance = new Encoder_Url();
	public static Encoder_Url instance(){
		return instance;
	}

	public static String ENCODE( CharSequence value ) {
		
		return instance.encode( value );
		
	}
	
}
