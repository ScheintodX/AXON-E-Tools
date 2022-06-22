package de.axone.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

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

	private JsonMapper.Builder jmb;

	/* (non-Javadoc)
	 * @see de.axone.web.rest.RFSRI#mapper()
	 */
	@Override
	public JsonMapper.Builder jmb(){

		if( jmb == null ){

			jmb = JsonMapper.builder();
			jmb.configure( SerializationFeature.INDENT_OUTPUT, true );
			jmb.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
			// JS uses unix timestamps!!!
			//mapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
		}

		return jmb;
	}




}