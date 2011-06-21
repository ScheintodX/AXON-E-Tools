package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.regexer" )
public class RegexerTest {
	
	public void testRegexes() throws Exception {
		
		Regexer regexer = new Regexer( "/foo/bar/" );
		
		assertEquals( regexer.patternStr, "foo" );
		assertEquals( regexer.replacementStr, "bar" );
		
		assertEquals( regexer.transform( "foo" ), "bar" );
		
		regexer = new Regexer( "///" );
		assertEquals( regexer.transform( "foo" ), "foo" );
		
		regexer = new Regexer( "//stupid/" );
		assertEquals( regexer.transform( "foo" ), "foo" );
		
		regexer = new Regexer( "/delete//" );
		assertEquals( regexer.transform( "deleteme" ), "me" );
	}
	
	@SuppressWarnings( "unused" )
	public void testExceptions() throws Exception {
	
		try{
			new Regexer( null );
			fail( "Should throw exception" );
		} catch( Exception e ){
		}
		
		try{
			new Regexer( "" );
			fail( "Should throw exception" );
		} catch( Exception e ){
		}
		
		try{
			new Regexer( "/" );
			fail( "Should throw exception" );
		} catch( Exception e ){
		}
		
		try{
			new Regexer( "abc" );
			fail( "Should throw exception" );
		} catch( Exception e ){
		}
		
		try{
			new Regexer( "aaa" );
		} catch( Exception e ){
			fail( "Should not throw exception (Empty regex)" );
		}
		
		try{
			new Regexer( "//blah/" );
		} catch( Exception e ){
			fail( "Should not throw exception (Stupid but valid)" );
		}
		
	}
}
