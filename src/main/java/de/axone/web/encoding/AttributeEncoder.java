package de.axone.web.encoding;

import de.axone.tools.Str;

public class AttributeEncoder implements Encoder{
	
	private static final char [] FROM = { '<','>','&', '"', '\'' };
	private static final String [] TO = { "&lt;", "&gt;", "&amp;", "&quot;", "&apos;" };
	
	protected AttributeEncoder(){}
	private static final AttributeEncoder instance = new AttributeEncoder();
	public static AttributeEncoder instance(){
		return instance;
	}

	public static String ENCODE( String value ) {
		
		if( value == null ) return null;
		
		return Str.translate( value, FROM, TO );
		
	}

	@Override
	public String encode( String value ) {
		return ENCODE( value );
	}
	
}
