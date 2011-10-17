package de.axone.exception;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.assert" )
public class AssertTest {

	public void testAssertInRange(){
		
		Assert.inRange( 0, "lower bound", 0, 2 );
		Assert.inRange( 1, "in bound", 0, 2 );
		Assert.inRange( 1.5, "in bound float", 0, 2 );
		Assert.inRange( 2, "upper bound", 0, 2 );
		// This should not be used.
		Assert.inRange( 2.99, "assert casts to long so this becomes 2", 0, 2 );
		try {
			Assert.inRange( -1, "ir", 0, 2 );
			fail( "missing exception" );
		} catch( ArgumentRangeException e ){
		}
		try {
			Assert.inRange( 3, "ir", 0, 2 );
			fail( "missing exception" );
		} catch( ArgumentRangeException e ){
		}
	}
}
