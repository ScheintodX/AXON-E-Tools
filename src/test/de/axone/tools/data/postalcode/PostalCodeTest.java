package de.axone.tools.data.postalcode;

import static org.testng.Assert.*;

import java.util.Locale;

import org.testng.annotations.Test;

import de.axone.data.postalcode.PostalCode;
import de.axone.data.postalcode.PostalCode_Numeric;

@Test( groups="tools.postalcode" )
public class PostalCodeTest {
	
	@Test( enabled=false )
	public static void main( String [] args ){
		
		new PostalCodeTest().testPostalCodesDe();
	}

	public void testPostalCodesDe(){

		PostalCode codeGeneric = PostalCode.instance( Locale.GERMANY );

		assertTrue( codeGeneric instanceof PostalCode_Numeric );
		assertEquals( ((PostalCode_Numeric)codeGeneric).getLength(), 5 );
		
		PostalCode_Numeric code = (PostalCode_Numeric)codeGeneric;

		assertTrue( code.isValid( "12345" ) );
		assertTrue( code.isValid( "D-12345" ) );
		assertFalse( code.isValid( "1234" ) );
		assertFalse( code.isValid( "123456" ) );
		assertFalse( code.isValid( "1234A" ) );
		
		code.parse( "D-12345" );
		assertEquals( code.getCode(), "12345" );

		assertTrue( code.inRange( "0", "9" ) );
		assertTrue( code.inRange( "1", "1" ) );
		assertTrue( code.inRange( "0", "1" ) );
		assertTrue( code.inRange( "1", "2" ) );
		assertTrue( code.inRange( "12345", "12345" ) );
		assertTrue( code.inRange( "0", "12345" ) );
		assertTrue( code.inRange( "12345", "9" ) );

		assertFalse( code.inRange( "0", "12344" ) );
		assertFalse( code.inRange( "12346", "9" ) );

		assertFalse( code.inRange( "0", "0" ) );
		assertFalse( code.inRange( "2", "9" ) );

		assertEquals( 0, code.compareToPrefix( "1" ) );

	}

}
