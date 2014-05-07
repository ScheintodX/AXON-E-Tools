package de.axone.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class J {

	public static String son( Object object ) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString( object );
		} catch( JsonProcessingException e ) {
			throw new RuntimeException( e );
		}
	}
}
