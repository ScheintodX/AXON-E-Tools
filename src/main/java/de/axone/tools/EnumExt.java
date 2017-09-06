package de.axone.tools;

public interface EnumExt<T extends Enum<T>> {
	
	@SuppressWarnings( "unchecked" )
	public default boolean isOneOf(  T ... others ) {
		
		for( T other : others ) {
			if( this == other ) return true;
		}
		return false;
	}
	
	@SuppressWarnings( "unchecked" )
	public default boolean isNoneOf(  T ... others ) {
		
		for( T other : others ) {
			if( this == other ) return false;
		}
		return true;
	}
}
