package de.axone.tools.data.postalcode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.Locale;

import org.testng.annotations.Test;

import de.axone.data.postalcode.PostalCode;
import de.axone.data.postalcode.PostalCode_Numeric;

@Test( groups="tools.postalcode" )
public class PostalCodeTest {

	public static void testPostalCodesDe(){

		PostalCode code = PostalCode.instance( Locale.GERMANY );

		assertTrue( code instanceof PostalCode_Numeric );
		assertEquals( ((PostalCode_Numeric)code).getLength(), 5 );

		assertNull( code.isValid( "12345" ) );
		assertNull( code.isValid( "D-12345" ) );
		assertNotNull( code.isValid( "1234" ) );
		assertNotNull( code.isValid( "123456" ) );
		assertNotNull( code.isValid( "1234A" ) );

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
