package de.axone.data;

import java.math.BigDecimal;

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
		
}
