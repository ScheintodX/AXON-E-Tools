package de.axone.test;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assert;
import org.assertj.core.api.ObjectAssert;

	@SuppressWarnings( "unchecked" )
public interface AssertHelper<SELF extends Assert<SELF,ACTUAL>, ACTUAL> extends Assert<SELF, ACTUAL>{
	
	public default SELF assertEquals( String v1, String v2, String description ) {
		
		describedAsMyself( assertThat( v1 ), description )
				.isEqualTo( v2 )
				;
		
		return (SELF)( this );
	}
	
	public default SELF assertEquals( long v1, long v2, String description ) {
		
		describedAsMyself( assertThat( v1 ), description )
				.isEqualTo( v2 )
				;
		
		return (SELF)( this );
	}
	
	public default <T> SELF assertEquals( T v1, T v2, String description ) {
		
		describedAsMyself( new ObjectAssert<T>( v1 ), description )
				.isEqualTo( v2 )
				;
		
		return (SELF)( this );
	}
	
	
	public default SELF assertNotEqual( String v1, String v2, String description ) {
		
		describedAsMyself( assertThat( v1 ), description )
				.isNotEqualTo( v2 )
				;
		
		return (SELF)( this );
	}
	
	public default SELF assertNotEqual( long v1, long v2, String description ) {
		
		describedAsMyself( assertThat( v1 ), description )
				.isNotEqualTo( v2 )
				;
		
		return (SELF)( this );
	}
	
	public default <T> SELF assertNotEqual( T v1, T v2, String description ) {
		
		describedAsMyself( new ObjectAssert<T>( v1 ), description )
				.isNotEqualTo( v2 )
				;
		
		return (SELF)( this );
	}
	
	public default SELF assertNotNull( Object actual, String description ) {
		
		describedAsMyself( new ObjectAssert<Object>( actual ), description )
				.isNotNull()
				;
		
		return (SELF)( this );
	}
	
	public default SELF assertTrue( boolean value, String description ) {
		
		describedAsMyself( assertThat( value ) )
				.isTrue()
				;
		
		return (SELF)( this );
	}
	
	public default SELF assertFalse( boolean value, String description ) {
		
		describedAsMyself( assertThat( value ) )
				.isTrue()
				;
		
		return (SELF)( this );
	}
	
	public default <S extends Assert<?, A>, A> S describedAsMyself( S ass ) {
		
		if( this instanceof AbstractAssert<?,?> ) {
			ass.describedAs( ((AbstractAssert<?,?>)this).descriptionText() );
		}
		return ass;
	}

	public default <S extends Assert<?, A>, A> S describedAsMyself( S ass, String description ) {
		
		if( this instanceof AbstractAssert<?,?> ) {
			String text = ((AbstractAssert<?,?>)this).descriptionText();
			if( text == null || text.length() == 0 ) text = description;
			else text = text + " / " + description;
			ass.describedAs( text );
		}
		return ass;
	}

}
