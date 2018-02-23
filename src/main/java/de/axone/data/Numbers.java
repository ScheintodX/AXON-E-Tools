package de.axone.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

import de.axone.exception.Assert;

public abstract class Numbers {

	public static boolean equals( Number first, Number second ) {
		
		// covers null==null
		if( first == second ) return true;
		
		if( first == null || second == null ) return false;
		
		return first.equals( second );
	}
	
	public static boolean equalValues( BigDecimal first, BigDecimal second ) {
		
		if( first == second ) return true;
		
		if( first == null || second == null ) return false;
		
		return first.compareTo( second ) == 0;
	}
	
	public static boolean equalsWithError( BigDecimal error, BigDecimal first, BigDecimal second ) {
		
		Assert.notNull( error, "error" );
		
		if( first == second ) return true;
		
		if( first == null || second == null ) return false;
		
		return first.add( error ).compareTo( second ) >= 0 && first.subtract( error ).compareTo( second ) <= 0;
	}
	
	public static int compare( BigDecimal o1, BigDecimal o2 ) {
		
			if( o1 == o2 ) return 0;
			if( o1 == null ) return -1;
			if( o2 == null ) return 1;
			
			return o1.compareTo( o2 );
	}
	
	public static final Comparator<BigDecimal> COMPARATOR = Numbers::compare;
	
	
	public static boolean isZero( BigDecimal value ) {
		
		if( value == null ) return true;
		
		return BigDecimal.ZERO.compareTo( value ) == 0;
	}
	
	public static boolean isZero( BigInteger value ) {
		
		if( value == null ) return true;
		
		return BigInteger.ZERO.compareTo( value ) == 0;
	}

}
