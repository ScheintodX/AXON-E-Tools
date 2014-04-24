package de.axone.web.rest;

public enum LoginError {
	
	WRONG_PASS( "Falsches Passwort" ),
	UNKNOWN_USER( "Unbekannter Benutzer" ),
	DISABLED( "Gesperrt" ),
	NOT_LOGGED_IN( "Nicht angemeldet" ),
	WRONG_REQUEST( "Fehler im Request" ),
	NOT_ALLOWED( "Nicht erlaubt" );
	
	private String message;
	
	LoginError( String message ){
		this.message = message;
	}
	
	public String message(){
		return message;
	}
}