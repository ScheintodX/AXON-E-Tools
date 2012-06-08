package de.axone.web;

import java.util.Map;

import de.axone.tools.Mapper;
import de.axone.web.encoding.AttributeEncoder;
import de.axone.web.encoding.XmlEncoder;

public abstract class InputBuilder {

	public static String hiddenFields( String ... fields ){

		return hiddenFields( Mapper.hashMap( fields ) );
	}

	public static String hiddenFields( Map<String,String> fields ){

		StringBuilder result = new StringBuilder();

		for( String key : fields.keySet() ){

			result.append( hiddenField( key, fields.get( key ) ) ).append( '\n' );
		}
		return result.toString();
	}

	public static StringBuilder hiddenField( String name, String value ){

		StringBuilder result = new StringBuilder();

		result
			.append( "<input type=\"hidden\"" )
			.append( " name=\"" )
			.append( AttributeEncoder.ENCODE( name ) )
			.append( "\" value=\"" )
			.append( AttributeEncoder.ENCODE( value ) )
			.append( "\"/>" )
		;
		return result;
	}
	
	public static StringBuilder option( String key, String value, boolean selected ){
		
		StringBuilder result = new StringBuilder();
		
		result
			.append( "<option value=\"" )
			.append( AttributeEncoder.ENCODE( key ) )
			.append( '"' )
		;
		if( selected ){
			result.append( " selected=\"selected\"" );
		}
		result
			.append( "\">" )
			.append( XmlEncoder.ENCODE( value ) )
			.append( "</option>" )
		;
		
		return result;
	}

	public static StringBuilder radio( String name, String key, String value, boolean selected ){
		
		StringBuilder result = new StringBuilder();
		
		String nameE = AttributeEncoder.ENCODE( name );
		String keyE = AttributeEncoder.ENCODE( key );
		String valueE = AttributeEncoder.ENCODE( value );
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
