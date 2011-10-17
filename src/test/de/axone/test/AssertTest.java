package de.axone.test;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.test" )
public class AssertTest {

	public void testAssertEqualsIgnoreOrder(){
		
		Throwable e=null;
		
		String [] a = new String[]{ "1", "2", "3", "4" };
		String [] b = new String[]{ "1", "3", "2", "4" };
		
		Assert.assertEqualsIgnoreOrder( a, b );
		
		b = new String[]{ "1", "2", "3", "5" };
		try{
			Assert.assertEqualsIgnoreOrder( a, b );
			fail( "Should throw exception" );
		} catch( Throwable t ){ e=t; }
		
		assertNotNull( e );
	}
	
}
