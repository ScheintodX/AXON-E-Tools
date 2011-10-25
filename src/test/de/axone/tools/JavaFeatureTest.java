package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * Tests for some java behaviour which should be remembered
 * 
 * These tests can be numbered and referenced in code
 * as [JFT x] where this special feature is used
 * 
 * And always remember: It's not a bug ...
 * 
 * @author flo
 */
@Test( groups="tools.java" )
public class JavaFeatureTest<V> {

	
	// Test #1
	@SuppressWarnings( "unchecked" )
	public void testClassCastInGenerics(){
		
		String s = null;
		try {
			s = (String)new Object();
			fail( "Exception is thrown here" );
		} catch( ClassCastException e ){
			assertNull( s );
		}
		
		V v = null;
		try {
			v = (V)new Object();
			assertNotNull( v );
			assertEquals( v.getClass(), Object.class );
		} catch( ClassCastException e ){
			fail( "Exception is thrown here" );
		}
		
	}
}
