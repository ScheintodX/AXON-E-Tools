package de.axone.tools;

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
}
