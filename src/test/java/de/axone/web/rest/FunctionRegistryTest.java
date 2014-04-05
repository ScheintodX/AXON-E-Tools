package de.axone.web.rest;

import static org.testng.Assert.*;

import java.util.Map;

import org.testng.annotations.Test;

@Test( groups="web.functionregistry" )
public class FunctionRegistryTest {
	
	public void testRoute(){
		
		RestFunctionRoute.Simple route = new RestFunctionRoute.Simple( "/a" );
		
		assertNull( route.match( "/b" ) );
		
		Map<String,String> parameters;
		
		parameters = route.match( "/a" );
		assertNotNull( parameters );
		assertEquals( parameters.size(), 0 );
		
		route = new RestFunctionRoute.Simple( "/a/:v1" );
		assertNull( route.match( "/a" ) );
		assertNull( route.match( "/a/" ) );
		parameters = route.match( "/a/123" );
		assertNotNull( parameters );
		assertEquals( parameters.size(), 1 );
		assertTrue( parameters.containsKey( "v1" ) );
		assertEquals( parameters.get( "v1" ), "123" );
		
		assertNull( route.match( "/a/123/b" ) );
		
		route = new RestFunctionRoute.Simple( "/a/:v1/b/:v2" );
		assertNull( route.match( "/a" ) );
		assertNull( route.match( "/a/123/b/" ) );
		parameters = route.match( "/a/123/b/456" );
		assertNotNull( parameters );
		assertEquals( parameters.size(), 2 );
		assertEquals( parameters.get( "v1" ), "123" );
		assertEquals( parameters.get( "v2" ), "456" );
		
		assertNull( route.match( "/a/123/b/456/c" ) );
	}
		
}
