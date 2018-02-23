package de.axone.login;

import de.axone.web.HttpStatusCode;

public class LoginException extends Exception {
	
	public enum Cause {
		
		UNAUTORIZED( HttpStatusCode.UNAUTHORIZED ), FORBIDDEN( HttpStatusCode.FORBIDDEN );
		
		private final HttpStatusCode code;
		
		Cause( HttpStatusCode code ){
			this.code = code;
		}
		public HttpStatusCode code(){ return code; }
	}
	
	private final Cause cause;

	public LoginException( Cause cause ) {
		super( cause.code().text() );
		this.cause = cause;
	}
	public Cause cause(){ return cause; }
}
