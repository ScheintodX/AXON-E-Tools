package de.axone.web;

import java.util.Map;

import de.axone.tools.Mapper;
import de.axone.web.encoding.AttributeEncoder;

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

}
