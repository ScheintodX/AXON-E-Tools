package de.axone.data;

import static org.testng.Assert.*;

import java.math.BigDecimal;

import org.testng.annotations.Test;

@Test( groups = "tools.numbers" )
public class NumbersTest {
	
	private BigDecimal ONE = new BigDecimal( "1" ),
	                   ONE_ = new BigDecimal( 1 ),
	                   ONE_O = new BigDecimal( "1.0" ),
	                   TWO = new BigDecimal( 2 )
	                   ;
	
	public void testEquals() {
		
		// Common cases
		assertTrue( Numbers.equals( ONE, ONE_ ) );
		assertFalse( Numbers.equals( ONE, ONE_O ) );
		
		// true equal
		assertTrue( Numbers.equals( ONE, ONE ) );
		assertTrue( Numbers.equals( null, null ) );
		
		// corner cases
		assertFalse( Numbers.equals( ONE, null ) );
		assertFalse( Numbers.equals( null, ONE ) );
	}
	
	public void testEqualsValue() {
		
		// Common cases
		assertTrue( Numbers.equalValues( ONE, ONE_ ) );
		assertTrue( Numbers.equalValues( ONE, ONE_O ) );
		
		// true equal
		assertTrue( Numbers.equalValues( ONE, ONE ) );
		assertTrue( Numbers.equalValues( null, null ) );
		
		// corner cases
		assertFalse( Numbers.equalValues( ONE, null ) );
		assertFalse( Numbers.equalValues( null, ONE ) );
	}
	
	public void testEqualsWithError() {
		
		// Common cases
		assertTrue( Numbers.equalsWithError( ONE, ONE, ONE_ ) );
		assertTrue( Numbers.equalsWithError( ONE, ONE, ONE_O ) );
		assertTrue( Numbers.equalsWithError( ONE, ONE, TWO ) );
		assertTrue( Numbers.equalsWithError( ONE, TWO, ONE ) );
		assertFalse( Numbers.equalsWithError( BigDecimal.ZERO, ONE, TWO ) );
		
		// true equal
		assertTrue( Numbers.equalValues( ONE, ONE ) );
		assertTrue( Numbers.equalValues( null, null ) );
		
		// corner cases
		assertFalse( Numbers.equalValues( ONE, null ) );
		assertFalse( Numbers.equalValues( null, ONE ) );
	}

}
