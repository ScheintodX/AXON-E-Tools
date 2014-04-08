package de.axone.data;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.range" )
public class IntegerRangeTest {

	public void testRanges(){
		
		IntegerRange r00 = new IntegerRange( null, null );
		IntegerRange rx0 = new IntegerRange( null, 0 );
		IntegerRange r0x = new IntegerRange( 0, null );
		IntegerRange rx1 = new IntegerRange( null, 1 );
		IntegerRange r1x = new IntegerRange( 1, null );
		IntegerRange r01 = new IntegerRange( 0, 1 );
		IntegerRange r03 = new IntegerRange( 0, 3 );
		IntegerRange r12 = new IntegerRange( 0, 2 );
		IntegerRange r23 = new IntegerRange( 2, 3 );
		
		// -- Touches
		assertTrue( rx0.touches( r0x ) );
		assertFalse( rx0.overlaps( r0x ) );
		assertTrue( rx1.touches( r1x ) );
		assertFalse( rx1.overlaps( r1x ) );
		
		// -- Overlaps
		assertTrue( rx1.touches( r0x ) );
		assertTrue( rx1.overlaps( r0x ) );
		
		// -- Misses
		assertFalse( rx0.touches( r1x ) );
		assertFalse( rx0.overlaps( r1x ) );
		
		// -- Overlaps
		assertTrue( r12.touches( r03 ) );
		assertTrue( r12.overlaps( r03 ) );
		assertTrue( r01.touches( r03 ) );
		assertTrue( r01.overlaps( r03 ) );
		
		// -- Misses
		assertFalse( r01.touches( r23 ) );
		assertFalse( r01.overlaps( r23 ) );
		
		// -- No range matches all
		assertTrue( r00.touches( rx0 ) );
		assertTrue( r00.touches( r0x ) );
		assertTrue( r00.touches( rx1 ) );
		assertTrue( r00.touches( r1x ) );
		assertTrue( r00.touches( r01 ) );
		assertTrue( r00.touches( r03 ) );
		assertTrue( r00.touches( r12 ) );
		assertTrue( r00.touches( r23 ) );
		
		assertTrue( r00.overlaps( rx0 ) );
		assertTrue( r00.overlaps( r0x ) );
		assertTrue( r00.overlaps( rx1 ) );
		assertTrue( r00.overlaps( r1x ) );
		assertTrue( r00.overlaps( r01 ) );
		assertTrue( r00.overlaps( r03 ) );
		assertTrue( r00.overlaps( r12 ) );
		assertTrue( r00.overlaps( r23 ) );
	}
}
