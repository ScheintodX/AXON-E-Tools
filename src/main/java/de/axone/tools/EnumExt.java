package de.axone.tools;

import java.util.Collection;

import de.axone.exception.Assert;

public interface EnumExt<T extends Enum<T>> {
	
	@SuppressWarnings( "unchecked" )
	public default boolean isOneOf(  T ... others ) {
		
		if( others == null ) return false;
		
		for( T other : others ) {
			if( this == other ) return true;
		}
		return false;
	}
	
	public default boolean isOneOf(  Collection<T> others ) {
		
		if( others == null ) return false;
		
		for( T other : others ) {
			if( this == other ) return true;
		}
		return false;
	}
	
	@SuppressWarnings( "unchecked" )
	public default boolean isNoneOf(  T ... others ) {
		
		if( others == null ) return true;
		
		for( T other : others ) {
			if( this == other ) return false;
		}
		return true;
	}
	
	public default boolean isNoneOf(  Collection<T> others ) {
		
		if( others == null ) return true;
		
		for( T other : others ) {
			if( this == other ) return false;
		}
		return true;
	}
	
	public String name();
	
	public default boolean is( String value ) {
		if( value == null ) return false;
		return name().equals( value.toUpperCase() );
	}
	public default boolean isNot( String value ) {
		if( value == null ) return true;
		return ! name().equals( value.toUpperCase() );
	}
	
	public static <T extends Enum<T>> T valueOf( Class<T> clazz, String value ) {
		
		Assert.notNull( value, "value" );
		
		return Enum.valueOf( clazz, value.toUpperCase() );
	}
	
	public static <T extends Enum<T>> T valueOfDefault( Class<T> clazz, String value, T defaultValue ) {
		
		if( value == null ) return defaultValue;
		
		return Enum.valueOf( clazz, value.toUpperCase() );
	}
}
