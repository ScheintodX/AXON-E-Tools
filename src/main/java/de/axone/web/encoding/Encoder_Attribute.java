package de.axone.web.encoding;

import de.axone.tools.A;


public class Encoder_Attribute extends TranslatingEncoder{
	
	public static final char [] FROM = { '<','>','&', '"', '\'' };
	public static final String [] TO = { "&lt;", "&gt;", "&amp;", "&quot;", "&apos;" };
	
	protected Encoder_Attribute(){
		super( FROM, TO );
	}
	private static final Encoder_Attribute instance = new Encoder_Attribute();
	public static Encoder_Attribute instance(){
		return instance;
	}

	public static String ENCODE( CharSequence value ) {
		
		return instance.encode( value );
		
	}
	
	public static Encoder extended( char [] addChars, String [] addTargets ){
		
		return new TranslatingEncoder( A.union( FROM, addChars ), A.union( TO, addTargets ) );
	}
	
}
