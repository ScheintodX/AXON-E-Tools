package de.axone.web.rest;


public class LoginException extends RuntimeException {
	
	private final LoginError error;
	
	public LoginException( LoginError error ){
		super( error.message() );
		this.error = error;
	}
	
	public LoginError getError(){ return error; }
	
	public static class NotLoggedInException extends LoginException {
	
		public NotLoggedInException() {
			super( LoginError.NOT_LOGGED_IN );
		}
	}
	
}