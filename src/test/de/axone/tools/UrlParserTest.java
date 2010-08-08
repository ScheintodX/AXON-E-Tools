package de.axone.tools;

import static org.testng.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.Test;

@Test( groups="tools.urlparser" )
public class UrlParserTest {

	public void testUrlParser() throws MalformedURLException{
		
		URL url1 = new URL( "http://headshop-de_localhost:8080/article/00801/Some_stuff.html" );
		URL url2 = new URL( "http://headshop-de_localhost:8080/tree/101/page/2/Some_dir/" );
		URL url3 = new URL( "http://headshop-de_localhost:8080/tree/101/page/2/Some_dir" );
		URL url4 = new URL( "http://headshop-de_localhost:8080/" );
		URL url5 = new URL( "http://headshop-de_localhost:8080" );
		
		UrlParser urlParser1 = new UrlParser( url1 );
		UrlParser urlParser2 = new UrlParser( url2 );
		UrlParser urlParser3 = new UrlParser( url3 );
		UrlParser urlParser4 = new UrlParser( url4 );
		UrlParser urlParser5 = new UrlParser( url5 );
		
		assertEquals( urlParser1.length(), 3 );
		assertEquals( urlParser1.get( 0 ), "article" );
		assertEquals( urlParser1.get( 1 ), "00801" );
		assertEquals( urlParser1.get( 2 ), "Some_stuff.html" );
		assertEquals( urlParser1.fileType(), "text/html" );
		assertTrue( urlParser1.startsWithSlash() );
		assertFalse( urlParser1.endsWithSlash() );
		
		assertEquals( urlParser2.length(), 5 );
		assertEquals( urlParser2.get( 0 ), "tree" );
		assertEquals( urlParser2.get( 1 ), "101" );
		assertEquals( urlParser2.get( 2 ), "page" );
		assertEquals( urlParser2.get( 3 ), "2" );
		assertEquals( urlParser2.get( 4 ), "Some_dir" );
		assertNull( urlParser2.fileType() );
		assertTrue( urlParser2.startsWithSlash() );
		assertTrue( urlParser2.endsWithSlash() );
		
		assertEquals( urlParser3.length(), 5 );
		assertEquals( urlParser3.get( 0 ), "tree" );
		assertEquals( urlParser3.get( 1 ), "101" );
		assertEquals( urlParser3.get( 2 ), "page" );
		assertEquals( urlParser3.get( 3 ), "2" );
		assertEquals( urlParser3.get( 4 ), "Some_dir" );
		assertNull( urlParser3.fileType() );
		assertTrue( urlParser3.startsWithSlash() );
		assertFalse( urlParser3.endsWithSlash() );
		
		assertEquals( urlParser4.length(), 0 );
		assertNull( urlParser4.fileType() );
		assertTrue( urlParser4.startsWithSlash() );
		assertTrue( urlParser4.endsWithSlash() );
		
		assertEquals( urlParser5.length(), 0 );
		assertNull( urlParser5.fileType() );
		assertFalse( urlParser5.startsWithSlash() );
		assertFalse( urlParser5.endsWithSlash() );
	}
}
