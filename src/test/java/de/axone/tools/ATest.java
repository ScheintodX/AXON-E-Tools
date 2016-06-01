package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.a" )
public class ATest {

	public void testIndexOf() {
		
		assertEquals( A.indexOf( (byte)1, new byte[]{ 0, 0, 0, 0 } ), -1 );
		assertEquals( A.indexOf( (byte)0, new byte[]{ 0, 0, 0, 0 } ), 0 );
		assertEquals( A.indexOf( (byte)1, new byte[]{ 1, 0, 0, 0 } ), 0 );
		assertEquals( A.indexOf( (byte)1, new byte[]{ 0, 0, 0, 1 } ), 3 );
		assertEquals( A.indexOf( (byte)1, new byte[]{ 1, 0, 1, 0 } ), 0 );
		
		assertEquals( A.indexOf( 0, (byte)1, new byte[]{ 1, 0, 1, 0 } ), 0 );
		assertEquals( A.indexOf( 1, (byte)1, new byte[]{ 1, 0, 1, 0 } ), 2 );
		assertEquals( A.indexOf( 2, (byte)1, new byte[]{ 1, 0, 1, 0 } ), 2 );
		assertEquals( A.indexOf( 3, (byte)1, new byte[]{ 1, 0, 1, 0 } ), -1 );
	}

	public void testTrim() {
		
		assertEquals( A.trim( new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0 } ), new byte[]{} );
		assertEquals( A.trim( new byte[]{ 1, 0, 0, 0, 0, 0, 0, 0 } ), new byte[]{ 1 } );
		assertEquals( A.trim( new byte[]{ 0, 0, 0, 0, 0, 0, 0, 1 } ), new byte[]{ 1 } );
		assertEquals( A.trim( new byte[]{ 1, 0, 0, 0, 0, 0, 0, 1 } ), new byte[]{ 1, 0, 0, 0, 0, 0, 0, 1 } );
		assertEquals( A.trim( new byte[]{ 0, 0, 3, 4, 5, 0, 0, 0 } ), new byte[]{ 3, 4, 5 } );
	}
}
