package de.axone.tools;

public class Numbers {

	public static Long Longer( Object num ) {
		if( num.getClass() == Long.class ) return (Long)num;
		else if( num.getClass() == Integer.class ) return (long)(Integer)num;
		else throw new ClassCastException( "Cannot cast " + num.getClass() + " to long" );
	}
	public static long longer( Object num ) {
		return Longer( num );
	}
}
