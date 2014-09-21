package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Tests for some java behaviour which should be remembered
 * 
 * These tests can be numbered and referenced in code
 * as [JFT x] where this special feature is used
 * 
 * And always remember: It's not a bug ...
 * 
 * @author flo
 * @param <V> value type
 */
@Test( groups="tools.java" )
public class JavaFeatureTest<V> {
	
	// Test #1
	/**
	 * This tests for diffrent runtime handlings of concrete subclasses
	 * and generic classes.
	 * Cast to an explicit class fails. Cast to a generic succeeds
	 */
	@SuppressWarnings( "unchecked" )
	@SuppressFBWarnings( "BC_IMPOSSIBLE_DOWNCAST" )
	public void testClassCastInGenerics(){
		
		String s = null;
		try {
			s = (String)new Object();
			fail( "Exception must be thrown here" );
		} catch( ClassCastException e ){
			assertNull( s );
		}
		
		V v = null;
		try {
			v = (V)new Object();
			assertNotNull( v );
			assertEquals( v.getClass(), Object.class );
		} catch( ClassCastException e ){
			fail( "Exception must not be thrown here" );
		}
		
	}
}
