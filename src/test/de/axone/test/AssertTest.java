package de.axone.test;

import org.testng.annotations.Test;

import de.axone.tools.E;

//import static org.testng.Assert.*;

@Test( groups="tools.test" )
public class AssertTest {

	public void testAssert(){
		
		Throwable e = null;
		
		String [] a = new String[]{ "1", "2", "3", "4" };
		String [] b = new String[]{ "1", "3", "2", "4" };
		
		Assert.assertEqualsIgnoreOrder( a, b );
		
		b = new String[]{ "1", "2", "3", "5" };
		try{
			Assert.assertEqualsIgnoreOrder( a, b );
		} catch( Throwable t ){ e=t; }
		
		E.rr( e );
	}
}
