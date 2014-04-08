package de.axone.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import de.axone.web.rest.Validator.FieldError;

public class ResultWriter {
	
	private static final String ERROR_FIELD = "__error__";

	public static void writeValue( ObjectMapper mapper, PrintWriter out, Object data )
			throws JsonGenerationException, JsonMappingException, IOException{
		
		writeValue( mapper, out, data, null, null );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out, Object data, Map<String,Object> overwrite )
			throws JsonGenerationException, JsonMappingException, IOException{
		
		writeValue( mapper, out, data, null, overwrite );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out,
			Object data, List<FieldError> errors )
			throws JsonGenerationException, JsonMappingException, IOException{
		writeValue( mapper, out, data, errors, null );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out,
			Object data, List<FieldError> errors, Map<String,Object> overwrite )
			throws JsonGenerationException, JsonMappingException, IOException{
		
		if( errors != null && errors.size() > 0 || overwrite != null && overwrite.size() > 0 ){
			
			// First create a Map, then append __error__, then make json
			MapType mapType = mapper.getTypeFactory()
					.constructMapType( LinkedHashMap.class, String.class, Object.class );
			
			LinkedHashMap<String,Object> map = mapper.convertValue( data, mapType );
			
			if( overwrite != null ){
				for( String key : overwrite.keySet() ){
					map.put( key, overwrite.get( key ) );
				}
			}
			
			map.put( ERROR_FIELD, errors );
			
			mapper.writeValue( out, map );
		} else {
			mapper.writeValue( out, data );
		}
	}
}
