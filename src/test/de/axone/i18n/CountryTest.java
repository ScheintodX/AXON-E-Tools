package de.axone.i18n;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test
public class CountryTest {

	@Test( enabled=false )
	public static void main( String [] args ) throws Exception {
		
		new CountryTest().testDe();
		new CountryTest().testUs();
		new CountryTest().testUk();
		new CountryTest().testCa();
	}
	
	public void testDe() throws Exception {
		
		Country c = Country.DE;
		assertEquals( c.commonName(), "Germany" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		//assertTrue( c.locales().contains( Locale.GERMANY ) );
	}
	
	public void testUs() throws Exception {
		
		Country c = Country.US;
		assertEquals( c.commonName(), "United States" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		//assertTrue( c.locales().contains( Locale.US ) );
	}
	
	public void testUk() throws Exception {
		
		Country c = Country.GB;
		assertEquals( c.commonName(), "Great Britain" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		//assertTrue( c.locales().contains( Locale.UK ) );
	}
	
	public void testCa() throws Exception {
		
		Country c = Country.CA;
		assertEquals( c.commonName(), "Canada" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 2 );
		//assertTrue( c.locales().contains( Locale.CANADA ) );
		//assertTrue( c.locales().contains( Locale.CANADA_FRENCH ) );
	}
}
