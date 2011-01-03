package de.axone.web.formatter;

import de.axone.tools.Str;

public class HtmlFormatter {

	public static String format( String text ){
		
		text = text.replace( "\n", "<br/>\n" );
		
		return text;
	}
	
	public static String formatAsTable( String text, String sep ){
		
		StringBuilder result = new StringBuilder();
		
		String [] lines = text.split( "\n" );
		
		for( int i=0; i < lines.length; i++ ){
			lines[ i ] = lines[ i ].replaceFirst("\\s*"+sep+"\\s*", "</th><td>" );
			lines[ i ] += "</td></tr><tr><th>";
		}
		
		result
			.append( "<table><tr><th>" )
			.append( Str.join( "\n", lines ) )
			.append( "</td></tr></table>" )
		;
		
		return result.toString();
	}
}
