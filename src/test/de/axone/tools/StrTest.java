package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.str" )
public class StrTest {

	public void testStr() throws Exception {
		
		// Direct via ...
		assertEquals( Str.join( ",", "A", "B", "C" ), "A,B,C" );
		
		// As explizit array
		assertEquals( Str.join( ",", new String[]{ "A", "B", "C" } ), "A,B,C" );
		
		// Ints
		assertEquals( Str.join( ",", 1L, 2, "3" ), "1,2,3" );
		
		// As Array
		assertEquals( Str.join( ",", new int[]{ 1, 2, 3 } ), "1,2,3" );
		
		// Mixed
		assertEquals( Str.join( ",", 1L, 2, "3" ), "1,2,3" );
	}
}
