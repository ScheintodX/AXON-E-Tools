package de.axone.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.axone.tools.Str;

public class Html {
	
	private static final Pattern TAGS = Pattern.compile( "<.*?>" );

	public static String removeTags( String value ){
		
		if( value == null ) return null;
		
		if( value.indexOf( '<' ) < 0 ) return value; // No html here
		
		Matcher matcher = TAGS.matcher( value );
		return matcher.replaceAll( "" );
	}
	
	public static String toHtml( String value ) {
		
			return Str.replaceFast( value, "\n", "<br/>\n" );
	}
}
