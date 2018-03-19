package de.axone.web.rest;

import de.axone.tools.EnumExt;
import de.axone.web.HttpStatusCode;


public class LoginException extends RuntimeException {
	
	public enum Reason implements EnumExt<Reason>{
		
		WRONG_PASS( "Falsches Passwort", HttpStatusCode.UNAUTHORIZED ),
		UNKNOWN_USER( "Unbekannter Benutzer", HttpStatusCode.UNAUTHORIZED ),
		DISABLED( "Gesperrt", HttpStatusCode.UNAUTHORIZED ),
		NOT_LOGGED_IN( "Nicht angemeldet", HttpStatusCode.UNAUTHORIZED ),
		WRONG_REQUEST( "Fehler im Request", HttpStatusCode.METHOD_NOT_ALLOWED ),
		WRONG_RESPONSE( "Fehler im Response", HttpStatusCode.METHOD_NOT_ALLOWED ),
		NOT_ALLOWED( "Nicht erlaubt", HttpStatusCode.FORBIDDEN ),
		NO_PASSWORD( "Kein Passwort gesetzt", HttpStatusCode.UNAUTHORIZED )
		;
		
		private final String message;
		private final HttpStatusCode code;
		
		Reason( String message, HttpStatusCode code ){
			this.code = code;
			this.message = message;
		}
		
		public String message(){ return message; }
		
		public HttpStatusCode code() { return code; }
	}
	
	private final Reason reason;
	
	public LoginException( Reason reason ){
		super( reason.message() );
		this.reason = reason;
	}
	public LoginException( Reason reason, Throwable e ){
		super( reason.message(), e );
		this.reason = reason;
	}
	
	public Reason reason(){ return reason; }
	
	public static class NotLoggedInException extends LoginException {
	
		public NotLoggedInException() {
			super( Reason.NOT_LOGGED_IN );
		}
	}
	
}