package de.axone.web;

import java.util.Map;

import de.axone.tools.Mapper;
import de.axone.web.encoding.Encoder_Attribute;
import de.axone.web.encoding.Encoder_Xml;

public abstract class InputBuilder {

	public static String hiddenFields( String ... fields ){

		return hiddenFields( Mapper.hashMap( fields ) );
	}

	public static String hiddenFields( Map<String,String> fields ){

		StringBuilder result = new StringBuilder();

		for( Map.Entry<String,String> entry : fields.entrySet() ){
			
			result
					.append( hiddenField( entry.getKey(), entry.getValue() ) )
					.append( '\n' )
					;
		}
		return result.toString();
	}

	public static StringBuilder hiddenField( String name, String value ){

		StringBuilder result = new StringBuilder();

		result
			.append( "<input type=\"hidden\"" )
			.append( " name=\"" )
			.append( Encoder_Attribute.ENCODE( name ) )
			.append( "\" value=\"" )
			.append( Encoder_Attribute.ENCODE( value ) )
			.append( "\"/>" )
		;
		return result;
	}
	
	public static StringBuilder option( String key, String value, boolean selected ){
		
		StringBuilder result = new StringBuilder();
		
		result
			.append( "<option value=\"" )
			.append( Encoder_Attribute.ENCODE( key ) )
			.append( '"' )
		;
		if( selected ){
			result.append( " selected=\"selected\"" );
		}
		result
			.append( "\">" )
			.append( Encoder_Xml.ENCODE( value ) )
			.append( "</option>" )
		;
		
		return result;
	}

	public static StringBuilder radio( String name, String key, String value, boolean selected ){
		
		StringBuilder result = new StringBuilder();
		
		String nameE = Encoder_Attribute.ENCODE( name );
		String keyE = Encoder_Attribute.ENCODE( key );
		String valueE = Encoder_Attribute.ENCODE( value );
		String idE = nameE + "_" + keyE;
		
		result
			.append( "<span class=\"radio\">" )
			.append( "<input type=\"radio\" name=\"" )
			.append( nameE )
			.append( "\" value=\"" )
			.append( keyE )
			.append( "\" id=\"" )
			.append( idE )
			.append( '"' )
		;
		if( selected ){
			result.append( " checked=\"checked\"" );
		}
		result
			.append( "/>" )
			.append( "<label for=\"" )
			.append( idE )
			.append( "\">" )
			.append( valueE )
			.append( "</label>" )
			.append( "</span>" )
		;
		
		return result;
	}
}
