package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.text" )
public class TextTest {

	public void testDiff(){
		
		String aabcc = "aabcc";
		String aaBcc = "aaBcc";
		
		String d1 = Text.diff( "aabcc", "aaBcc" );
		assertEquals( d1, "aa--->[:b:|:B:]<---cc" );
		
		String d1__1= Text.diff( aabcc, aaBcc, -1 );
		String d1_3 = Text.diff( aabcc, aaBcc, 3 );
		String d1_2 = Text.diff( aabcc, aaBcc, 2 );
		String d1_1 = Text.diff( aabcc, aaBcc, 1 );
		String d1_0 = Text.diff( aabcc, aaBcc, 0 );
		assertEquals(d1__1, "aa--->[:b:|:B:]<---cc" );
		assertEquals( d1_3, "aa--->[:b:|:B:]<---cc" );
		assertEquals( d1_2, "aa--->[:b:|:B:]<---cc" );
		assertEquals( d1_1, "a--->[:b:|:B:]<---c" );
		assertEquals( d1_0, "--->[:b:|:B:]<---" );
		
		String d2 = Text.diff( "I am a diff", "I was a diff" );
		assertEquals( d2, "I --->[:am:|:was:]<--- a diff" );
		
		String d2_2 = Text.diff( "I am a diff", "I was a diff", 2 );
		assertEquals( d2_2, "I --->[:am:|:was:]<--- a" );
	}
}
