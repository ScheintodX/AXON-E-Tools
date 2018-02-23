package de.axone.login;

@FunctionalInterface
public interface LoginUserProvider {

	public LoginUser getUser( String identifier );
	
}
