package de.axone.web;

public class SuperURLAssertions {

	public static SuperURLAssert assertThis( SuperURL actual ){
		return new SuperURLAssert( actual );
	}
	
	public static SuperURLAssert.HostAssert assertThis( SuperURL.Host host ){
		return new SuperURLAssert.HostAssert( host );
	}
	
	public static SuperURLAssert.PathAssert assertThis( SuperURL.Path path ){
		return new SuperURLAssert.PathAssert( path );
	}
	
	public static SuperURLAssert.QueryAssert assertThis( SuperURL.Query query ){
		return new SuperURLAssert.QueryAssert( query );
	}
}
