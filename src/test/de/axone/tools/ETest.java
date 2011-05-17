package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.e" )
public class ETest {

	public void testE(){
		
		String s = "String";
		Object o = s;
		
		assertEquals( E.f( s ), s );
		assertEquals( E.f( o ), s );
	}
}
