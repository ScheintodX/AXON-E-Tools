package de.axone.web.encoding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encoding escapes html entities: &lt; &gt; &amp;
 * 
 * @author flo
 */
public class XmlEncoder implements Encoder {
	
	private static final Pattern pattern = Pattern.compile( "[<>&]" );
	
	protected XmlEncoder(){}
	private static final XmlEncoder instance = new XmlEncoder();
	public static XmlEncoder instance(){
		return instance;
	}
	
	public static String ENCODE( String value ){
		
		if( value == null ) return null;
		
		Matcher matcher = pattern.matcher( value );
		StringBuffer result = new StringBuffer();
		
		while( matcher.find() ){
			
			int index = matcher.start();
			char c = value.charAt( index );
			switch( c ){
			case '<':
				matcher.appendReplacement( result, "&lt;" );
				break;
			case '>':
				matcher.appendReplacement( result, "&gt;" );
				break;
			case '&':
				matcher.appendReplacement( result, "&amp;" );
				break;
			default:
				throw new IllegalArgumentException( "" + c + " (at index: " + index + ")" );
			}
		}
		matcher.appendTail( result );
		
		return result.toString();
	}

	@Override
	public String encode( String value ) {
		
		return ENCODE( value );
	}

}
