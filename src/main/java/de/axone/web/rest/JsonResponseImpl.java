package de.axone.web.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.axone.exception.IllegalNamedArgumentException;

public class JsonResponseImpl implements JsonResponse {
	
	public static class OK extends JsonResponseImpl {
		public OK(){
			super( Status.OK );
		}
		
		@JsonIgnore
		@Override
		public Error getError(){
			return super.getError();
		}
	}
	
	public static class ERROR extends JsonResponseImpl {
		public ERROR(){
			super( Status.ERROR );
		}
	}
	
	public static class INVALID extends JsonResponseImpl {
		public INVALID(){
			super( Status.INVALID );
		}
	}
	
	
	public static JsonResponse OK(){
		return new JsonResponseImpl.OK();
	}
	
	public static JsonResponse ERROR( int code, String message ){
		
		JsonResponseImpl result = new JsonResponseImpl.ERROR();
		result.error = new ErrorImpl( code, message );
		return result;
	}
	
	public static JsonResponse ERROR( Throwable t ){
		
		JsonResponseImpl result = new JsonResponseImpl.ERROR();
		result.error = new ErrorImpl( 500, t );
		return result;
	}
	
	public static JsonResponse INVALID( IllegalNamedArgumentException e ){
		
		JsonResponseImpl result = new JsonResponseImpl.INVALID();
		result.messages = new JsonResponse.Message[1];
		result.messages[ 0 ] = new MessageImpl( e.getArgumentName(), e.getMessage() );
		return result;
	}
	
	JsonResponseImpl(){
	}
	JsonResponseImpl( Status status ){
		setStatus( status );
	}
	JsonResponseImpl( Status status, Error error ){
		setStatus( status );
		setError( error );
	}
	
	private Status status;
	private Error error;
	private Message [] messages;
	
	@Override
	public Status getStatus() {
		return status;
	}
	public void setStatus( Status status ){
		this.status = status;
	}

	@Override
	@JsonDeserialize( as=ErrorImpl.class )
	public Error getError() {
		return error;
	}
	public void setError( Error error ){
		this.error = error;
	}
	
	@Override
	@JsonDeserialize( contentAs=MessageImpl.class )
	public Message[] getMessages() {
		return messages;
	}
	public void setMessages( Message [] messages ){
		this.messages = messages;
	}
	
	
	public static class ErrorImpl implements JsonResponse.Error {
		
		private int code;
		private String message;
		private StackTraceElement [] stackTrace;
		
		public ErrorImpl(){}
		
		public ErrorImpl( int code, String message ){
			this.code = code;
			this.message = message;
		}
		public ErrorImpl( int code, Throwable t ){
			
			this( code, findTopCause( t ).getMessage() );
			this.stackTrace = t.getStackTrace();
		}
		private static Throwable findTopCause( Throwable t ){
			
			while( t.getCause() != null ){
				t = t.getCause();
			}
			return t;
		}
		
		@Override
		public int getCode() { return code; }
		public void setCode( int code ){ this.code = code; }

		@Override
		public String getMessage() { return message; }
		public void setMessage( String message ){ this.message = message; }
		
		@Override
		public StackTraceElement [] getStackTrace() {
			return stackTrace;
		}
		public void setStackTrace( StackTraceElement [] stackTrace ){
			this.stackTrace = stackTrace;
		}
		
		@Override
		public JsonResponseException throwable(){
			
			return new WrappedThrowable();
		}
		
		private class WrappedThrowable extends JsonResponseException {
			
			private static final long serialVersionUID = -4372453687500907898L;
			@Override
			
			public String getMessage(){
				return message;
			}
			
			@Override
			public StackTraceElement [] getStackTrace(){
				return stackTrace;
			}
		}
	}
	
	public static class MessageImpl implements Message {
		
		private String field, message;
		
		public MessageImpl(){}
		
		public MessageImpl( String field, String message ){
			this.field = field;
			this.message = message;
		}

		@Override
		public String getField() { return field; }
		public void setField( String field ){ this.field = field; }

		@Override
		public String getMessage() { return message; }
		public void setMessage( String message ){ this.message = message; }
		
	}
	
	public static class JsonResponseException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8862829876634590375L;

		public JsonResponseException() { super(); }

		public JsonResponseException( String message, Throwable cause ) {
			super( message, cause );
		}

		public JsonResponseException( String message ) {
			super( message );
		}

		public JsonResponseException( Throwable cause ) {
			super( cause );
		}
		
	}


}
