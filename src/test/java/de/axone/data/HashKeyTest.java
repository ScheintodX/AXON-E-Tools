package de.axone.data;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Test( groups="tools.hashkey" )
public class HashKeyTest {

	@SuppressFBWarnings( value="ES_COMPARING_STRINGS_WITH_EQ",
			justification="We exactly want to test if this is another object" )
	public void testHashKey(){
		
		// First test normal java functions just to be sure.
		String a="A";
		String b="B";
		
		String a1 = a+a;
		String a2 = a+a;
		String a3 = a+a+a;
		
		String b1 = b+b;
		String b2 = b+b;
		String b3 = b+b+b;
		
		assertFalse( a1==a2 );
		assertFalse( a1==a3 );
		assertFalse( b1==b2 );
		assertFalse( b1==b3 );
		
		assertEquals( a1, a2 );
		assertEquals( b1, b2 );
		assertFalse( a1.equals( a3 ) );
		assertFalse( b1.equals( b3 ) );
		
		// Now for the real thing
		HashKey K1 = new HashKey( a1, b1 );
		HashKey K2 = new HashKey( a2, b2 );
		HashKey K3 = new HashKey( a3, b3 );
		HashKey K4 = new HashKey( a1, b1, b3 );
		// This is reduces in the inner set to AA,BB:
		HashKey K5 = new HashKey( a1, b1, b2 );
		
		assertTrue( K1.hashCode() == K2.hashCode() );
		assertTrue( K1.hashCode() == K5.hashCode() );
		assertFalse( K1.hashCode() == K3.hashCode() );
		assertFalse( K1.hashCode() == K4.hashCode() );
		
		assertTrue( K1.equals( K2 ) );
		assertTrue( K1.equals( K5 ) );
		assertFalse( K1.equals( K3 ) );
		assertFalse( K1.equals( K4 ) );
		
		
	}
}
