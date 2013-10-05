package de.axone.web.rest;

import de.axone.web.rest.JsonResponseImpl.JsonResponseException;

public interface JsonResponse {

	public enum Status { OK, ERROR, INVALID; }
	
	public Status getStatus();
	public Error getError();
	public Message [] getMessages();
	
	public interface Error {
		
		public int getCode();
		public String getMessage();
		public StackTraceElement [] getStackTrace();
		public JsonResponseException throwable();
	}
	
	public interface Message {
		public String getField();
		public String getMessage();
	}
	
}
