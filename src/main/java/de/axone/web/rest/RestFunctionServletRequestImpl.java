package de.axone.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.axone.web.Method;

public class RestFunctionServletRequestImpl extends HttpServletRequestWrapper implements RestRequest {

	public RestFunctionServletRequestImpl( HttpServletRequest request ) {
		super( request );
	}
	
	/* (non-Javadoc)
	 * @see de.axone.web.rest.RFSRI#getRestMethod()
	 */
	@Override
	public Method getRestMethod(){
		return Method.from( this );
	}
	
	private ObjectMapper mapper;
	
	/* (non-Javadoc)
	 * @see de.axone.web.rest.RFSRI#mapper()
	 */
	@Override
	public ObjectMapper mapper(){
		
		if( mapper == null ){
			
			mapper = new ObjectMapper();
			mapper.configure( SerializationFeature.INDENT_OUTPUT, true );
			mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			// JS uses unix timestamps!!!
			//mapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
		}
		
		return mapper;
	}
	
	
	
	
}