package de.axone.data;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AbstractObjectAssert;

public class AccessibleLineAssert<T extends Enum<T>> extends AbstractObjectAssert<AccessibleLineAssert<T>,AccessibleLine<T>> {
	
	private final Class<T> type;

	protected AccessibleLineAssert( AccessibleLine<T> actual, Class<T> type ) {
		super( actual, AccessibleLineAssert.class );
		this.type = type;
	}
	
	public AccessibleLineAssert<T> has( T field, String value ) {
		
		assertThat( actual.get( field ) )
				.isEqualTo( value )
				;
		
		return this;
	}
	
	public static <X extends Enum<X>> AccessibleLineAssert<X> assertThe( Class<X> type , AccessibleLine<X> actual  ) {
		return new AccessibleLineAssert<>( actual, type );
	}

	public AccessibleLineAssert<T> consistsOf( String ... values ) {
		
		T keys[] = type.getEnumConstants();
		
		assertThat( values.length )
				.as( "amount of given values" )
				.isEqualTo( keys.length )
				;
		
		for( int i=0; i<values.length; i++ ) {
			
			T key = keys[ i ];
			String val = values[ i ];
			
			assertThat( actual.get( key ) )
					.as( key.name() )
					.isEqualTo( val )
					;
		}
		
		return this;
	}

}
