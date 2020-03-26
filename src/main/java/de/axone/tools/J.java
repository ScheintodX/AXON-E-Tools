package de.axone.tools;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class J {

	public static String son( Object o ) {

		try {

			ObjectMapper om = new ObjectMapper();

			om.configure( SerializationFeature.INDENT_OUTPUT, true );
			om.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true );

			return om.writeValueAsString( o );

		} catch( JsonProcessingException e ) {
			throw new RuntimeException( "Cannot serialize", e );
		}
	}

	/**
	 * Pretty print json
	 *
	 * This is done by deserialization of String value and then
	 * re-serialization with pretty printer
	 *
	 * @param value
	 */
	public static void ack( String value ) {

		if( value == null ) {
			E.rr( "-NULL-" );
			return;
		}

		try {

			ObjectMapper om = new ObjectMapper();

			Map<?,?> map = om.readValue( value, Map.class );

			E.rrup( 1, om.writerWithDefaultPrettyPrinter().writeValueAsString( map ) );

		} catch( JsonProcessingException e ) {
			throw new RuntimeException( "Cannot deserialize", e );
		} catch( IOException e ) {
			throw new RuntimeException( "Cannot read", e );
		}
	}
}
