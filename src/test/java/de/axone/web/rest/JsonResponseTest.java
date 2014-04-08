package de.axone.web.rest;

import static org.testng.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Test
public class JsonResponseTest {

	public void testResponse() throws Exception {
		
		/* OK */
		
		JsonResponse resp = JsonResponseImpl.OK();
		assertEquals( resp.getStatus(), JsonResponse.Status.OK );
		
		JsonResponse resp2 = reWrite( resp );
		
		assertEquals( resp2.getStatus(), resp.getStatus() );
		assertNull( resp2.getError() );
		
		
		/* ERROR */
		
		resp = JsonResponseImpl.ERROR( 304, "You are wrong here" );
		assertEquals( resp.getStatus(), JsonResponse.Status.ERROR );
		JsonResponse.Error error = resp.getError();
		assertEquals( error.getCode(), 304 );
		assertEquals( error.getMessage(), "You are wrong here" );
		assertNull( error.getStackTrace() );
		
		resp2 = reWrite( resp );
		
		assertEquals( resp2.getStatus(), resp.getStatus() );
		assertNotNull( resp2.getError() );
		JsonResponse.Error error2 = resp2.getError();
		assertEquals( error2.getCode(), error.getCode() );
		assertEquals( error2.getMessage(), error.getMessage() );
		assertEquals( error2.getStackTrace(), error.getStackTrace() );
		
		
		/* EXCEPTION */
		
		Exception e = new Exception( "Test Exception" );
		Exception e2 = new Exception( e );
		Exception e3 = new Exception( e2 );
		
		resp = JsonResponseImpl.ERROR( e3 );
		assertEquals( resp.getStatus(), JsonResponse.Status.ERROR );
		error = resp.getError();
		assertEquals( error.getCode(), 500 );
		assertEquals( error.getMessage(), "java.lang.Exception: java.lang.Exception: Test Exception" );
		assertNotNull( error.getStackTrace() );
		
		resp2 = reWrite( resp );
		
		assertEquals( resp2.getStatus(), resp.getStatus() );
		assertNotNull( resp2.getError() );
		error2 = resp2.getError();
		assertEquals( error2.getCode(), error.getCode() );
		assertEquals( error2.getMessage(), error.getMessage() );
		assertEquals( error2.getStackTrace(), error.getStackTrace() );
		
	}
	
	private JsonResponse reWrite( JsonResponse resp )
			throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		StringWriter s = new StringWriter();
		mapper.writeValue( s, resp );
		String asString = s.toString();
		
		StringReader r = new StringReader( asString );
		
		JsonResponse resp2 = mapper.readValue( r, JsonResponseImpl.class );
		
		return resp2;
	}
}
