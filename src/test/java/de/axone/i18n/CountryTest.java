package de.axone.i18n;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.Locale;

import org.testng.annotations.Test;

@Test( groups="tools.country" )
public class CountryTest {
	
	public void testDe() throws Exception {
		
		Country c = Country.DE;
		assertEquals( c.commonName(), "Germany" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.GERMAN ) );
	}
	
	public void testUs() throws Exception {
		
		Country c = Country.US;
		assertEquals( c.commonName(), "United States" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
	}
	
	public void testUk() throws Exception {
		
		Country c = Country.GB;
		assertEquals( c.commonName(), "United Kingdom" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
	}
	
	public void testCa() throws Exception {
		
		Country c = Country.CA;
		assertEquals( c.commonName(), "Canada" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 2 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.FRENCH ) );
	}
}
