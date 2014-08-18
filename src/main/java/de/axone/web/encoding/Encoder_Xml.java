package de.axone.web.encoding;



/**
 * Encoding escapes html entities: &lt; &gt; &amp;
 * 
 * @author flo
 */
public class Encoder_Xml extends TranslatingEncoder {
	
	public static final char [] FROM = { '<','>','&' };
	public static final String [] TO = { "&lt;", "&gt;", "&amp;" };
	
	protected Encoder_Xml(){
		super( FROM, TO );
	}
	private static final Encoder_Xml instance = new Encoder_Xml();
	public static Encoder_Xml instance(){
		return instance;
	}
	
	public static String ENCODE( String value ){
		return instance.encode( value );
	}
	
}
