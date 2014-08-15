package de.axone.web.encoding;

import de.axone.tools.Str;


/**
 * Encoding escapes html entities: &lt; &gt; &amp;
 * 
 * TODO: Das geht schneller
 * 
 * @author flo
 */
public class XmlEncoder implements Encoder {
	
	public static final char [] FROM = { '<','>','&' };
	public static final String [] TO = { "&lt;", "&gt;", "&amp;" };
	
	protected XmlEncoder(){}
	private static final XmlEncoder instance = new XmlEncoder();
	public static XmlEncoder instance(){
		return instance;
	}
	
	public static String ENCODE( String value ){
		
		if( value == null ) return null;
		
		return Str.translate( value, FROM, TO );
	}
	
	@Override
	public String encode( String value ) {
		
		return ENCODE( value );
	}

}
