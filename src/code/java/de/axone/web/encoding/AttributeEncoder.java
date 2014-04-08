package de.axone.web.encoding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributeEncoder implements Encoder{
	
	private static final Pattern pattern = Pattern.compile( "[&<>\"']" );
	
	protected AttributeEncoder(){}
	private static final AttributeEncoder instance = new AttributeEncoder();
	public static AttributeEncoder instance(){
		return instance;
	}

	public static String ENCODE( String value ) {
		
		if( value == null ) return null;
		
		Matcher matcher = pattern.matcher( value );
		StringBuffer result = new StringBuffer();
		
		while( matcher.find() ){
			
			int index = matcher.start();
			switch( value.charAt( index ) ){
			case '&':
				matcher.appendReplacement( result, "&amp;" );
				break;
			case '>':
				matcher.appendReplacement( result, "&gt;" );
				break;
			case '<':
				matcher.appendReplacement( result, "&lt;" );
				break;
			case '"':
				matcher.appendReplacement( result, "&quot;" );
				break;
			case '\'':
				matcher.appendReplacement( result, "&apos;" );
				break;
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
