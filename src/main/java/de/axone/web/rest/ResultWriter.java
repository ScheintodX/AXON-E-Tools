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

import de.axone.web.rest.Validator.ValidatorResult;

public class ResultWriter {
	
	private static final String ERROR_FIELD = "__all_errors__";
	private static final String HAS_ERROR_FIELD = "__has_error__";

	public static void writeValue( ObjectMapper mapper, PrintWriter out, Object data )
			throws JsonGenerationException, JsonMappingException, IOException{
		
		writeValue( mapper, out, data, null, (ValidatorResult)null, null );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out, Object data, Map<String,Object> overwrite )
			throws JsonGenerationException, JsonMappingException, IOException{
		
		writeValue( mapper, out, data, null, (ValidatorResult)null, overwrite );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out,
			Object data, List<?> errors )
			throws JsonGenerationException, JsonMappingException, IOException{
		writeValue( mapper, out, data, errors, null );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out,
			Object data, String prefix, ValidatorResult errors )
			throws JsonGenerationException, JsonMappingException, IOException{
		writeValue( mapper, out, data, prefix, errors, null );
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out,
			Object data, List<?> errors, Map<String,Object> overwrite )
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
			map.put( HAS_ERROR_FIELD, true );
			
			mapper.writeValue( out, map );
		} else {
			mapper.writeValue( out, data );
		}
	}
	
	public static void writeValue( ObjectMapper mapper, PrintWriter out,
			Object data, String prefix, ValidatorResult errors, Map<String,Object> overwrite )
			throws JsonGenerationException, JsonMappingException, IOException{
		
		if( errors != null && errors.hasError() || overwrite != null && overwrite.size() > 0 ){
			
			// First create a Map, then append __error__, then make json
			MapType mapType = mapper.getTypeFactory()
					.constructMapType( LinkedHashMap.class, String.class, Object.class );
			
			LinkedHashMap<String,Object> map = mapper.convertValue( data, mapType );
			
			if( overwrite != null ){
				for( String key : overwrite.keySet() ){
					map.put( key, overwrite.get( key ) );
				}
			}
			
			if( errors != null && errors.hasError() ){
				errors.mergeInto( map );
				
				map.put( ERROR_FIELD, errors.asList( prefix ) );
				map.put( HAS_ERROR_FIELD, true );
			}
			
			mapper.writeValue( out, map );
		} else {
			mapper.writeValue( out, data );
		}
	}
}
