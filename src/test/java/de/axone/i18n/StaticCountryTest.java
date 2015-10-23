package de.axone.i18n;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.Locale;

import org.testng.annotations.Test;

@Test( groups="tools.country" )
public class StaticCountryTest {
	
	public void testDe() throws Exception {
		
		StaticCountries c = StaticCountries.DE;
		assertEquals( c.getCommonName(), "Germany" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.GERMAN ) );
	}
	
	public void testUs() throws Exception {
		
		StaticCountries c = StaticCountries.US;
		assertEquals( c.getCommonName(), "United States" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
	}
	
	public void testUk() throws Exception {
		
		StaticCountries c = StaticCountries.GB;
		assertEquals( c.getCommonName(), "United Kingdom" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
	}
	
	// Canada
	// (Note that ca as a language is catalan. Canadian english is en_CA)
	public void testCa() throws Exception {
		
		StaticCountries c = StaticCountries.CA;
		assertEquals( c.getCommonName(), "Canada" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 2 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.FRENCH ) );
	}
	
	// Isle of man
	public void testIm() throws Exception {
		
		StaticCountries c = StaticCountries.IM;
		assertEquals( c.getCommonName(), "Isle of Man" );
		assertNotNull( c.locales() );
		assertEquals( c.locales().length, 1 );
		assertTrue( Arrays.asList( c.locales() ).contains( Locale.ENGLISH ) );
	}
}
