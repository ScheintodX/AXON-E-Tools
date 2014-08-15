package de.axone.web;

public class SuperURLAssertions {

	public static SuperURLAssert assertThat( SuperURL actual ){
		return new SuperURLAssert( actual );
	}
	
	public static SuperURLAssert.HostAssert assertThat( SuperURL.Host host ){
		return new SuperURLAssert.HostAssert( host );
	}
	
	public static SuperURLAssert.PathAssert assertThat( SuperURL.Path path ){
		return new SuperURLAssert.PathAssert( path );
	}
	
	public static SuperURLAssert.QueryAssert assertThat( SuperURL.Query query ){
		return new SuperURLAssert.QueryAssert( query );
	}
}
