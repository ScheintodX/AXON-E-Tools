package de.axone.web.rest;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.databind.ObjectMapper;


public class RestFunctionException extends Exception {

	public RestFunctionException(String message) { super( message ); }

	public RestFunctionException(Throwable cause) { super( cause ); }

	public RestFunctionException(String message, Throwable cause) { super( message, cause ); }

	public void write( ObjectMapper mapper, Writer out ) throws IOException{
		
		mapper.writeValue( out, new Json() );
	}
	
	@SuppressWarnings( "unused" )
	private class Json {
		
		public boolean getError(){ return true; }
		public String getErrorMessage(){ return getMessage(); }
		
		public StackTraceElement [] getStackTrace(){
			
			return RestFunctionException.this.getStackTrace();
		}
	}
}
