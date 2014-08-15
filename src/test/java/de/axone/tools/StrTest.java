package de.axone.tools;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

@Test( groups="tools.str" )
public class StrTest {

	public void testStr() throws Exception {
		
		// Direct via ...
		assertEquals( Str.join( ",", "A", "B", "C" ), "A,B,C" );
		
		// As explizit array
		assertEquals( Str.join( ",", new String[]{ "A", "B", "C" } ), "A,B,C" );
		
		// Ints
		assertEquals( Str.join( ",", 1L, 2, "3" ), "1,2,3" );
		
		// As Array
		assertEquals( Str.join( ",", new int[]{ 1, 2, 3 } ), "1,2,3" );
		
		// Mixed
		assertEquals( Str.join( ",", 1L, 2, "3" ), "1,2,3" );
	}
	
	public void testSplitOnce() throws Exception {
		
		assertThat( Str.splitFastOnce( "a", ';' ) )
				.contains( "a" )
				.hasSize( 1 )
				;
		
		assertThat( Str.splitFastOnce( "a;b", ';' ) )
				.contains( "a" )
				.contains( "b" )
				.hasSize( 2 )
				;
		
		assertThat( Str.splitFastOnce( "abc;def;hij", ';' ) )
				.contains( "abc" )
				.contains( "def;hij" )
				.hasSize( 2 )
				;
		
		assertThat( Str.splitFastOnce( "", ';' ) )
				.contains( "" )
				.hasSize( 1 )
				;
		
		assertThat( Str.splitFastOnce( ";", ';' ) )
				.contains( "" )
				.hasSize( 2 )
				;
		
		assertThat( Str.splitFastOnce( ";;", ';' ) )
				.contains( "" )
				.contains( ";" )
				.hasSize( 2 )
				;
		
	}
	
	public void testSplitFastUnsafe() throws Exception {
		
		assertThat( Str.splitFastLimited( "a", ';', 8 ) )
				.contains( "a" )
				.hasSize( 1 )
				;
		
		assertThat( Str.splitFastLimited( "a;b", ';', 8 ) )
				.contains( "a" )
				.contains( "b" )
				.hasSize( 2 )
				;
		
		assertThat( Str.splitFastLimited( "abc;def;hij", ';', 8 ) )
				.contains( "abc" )
				.contains( "def" )
				.contains( "hij" )
				.hasSize( 3 )
				;
		
		assertThat( Str.splitFastLimited( "abc;def;hij", ';', 2 ) )
				.contains( "abc" )
				.contains( "def;hij" )
				.hasSize( 2 )
				;
		assertThat( Str.splitFastLimited( "abc;;", ';', 2 ) )
				.contains( "abc" )
				.contains( ";" )
				.hasSize( 2 )
				;
		
		assertThat( Str.splitFastLimited( "", ';', 8 ) )
				.contains( "" )
				.hasSize( 1 )
				;
		
		assertThat( Str.splitFastLimited( ";", ';', 8 ) )
				.contains( "" )
				.hasSize( 2 )
				;
		
		assertThat( Str.splitFastLimited( ";;", ';', 8 ) )
				.contains( "" )
				.hasSize( 3 )
				;
	}
	
	public void testGeneralSplit(){
		
		String [] parts = Str.splitFast( "1;2;3;4;5;6;7;8;9;10", ';' );
		
		assertThat( parts )
				.hasSize( 10 )
				;
		
		parts = Str.splitFast( "/", '/' );
		
		assertThat( parts )
				.hasSize( 2 )
				.contains( "" )
				;
		
	}
	
	public void timing(){
		
		long start, end;
		
		String test = "abc;def;hij";
		
		// Warmup
		for( int i=0; i<10_000_000; i++ ){
			Str.splitFastLimited( test, ';', 8 );
		}
		for( int i=0; i<10_000_000; i++ ){
			StringUtils.split( test, ';' );
		}
		
		start = System.currentTimeMillis();
		for( int i=0; i<10_000_000; i++ ){
			Str.splitFastLimited( test, ';', 8 );
		}
		end = System.currentTimeMillis();
		E.rr( "Str Took " + (end-start) + " ms" );
		
		start = System.currentTimeMillis();
		for( int i=0; i<10_000_000; i++ ){
			Str.splitFastOnce( test, ';' );
		}
		end = System.currentTimeMillis();
		E.rr( "Str Once Took " + (end-start) + " ms" );
		
		start = System.currentTimeMillis();
		for( int i=0; i<10_000_000; i++ ){
			StringUtils.split( test, ';' );
		}
		end = System.currentTimeMillis();
		E.rr( "StringUtils Took " + (end-start) + " ms" );
		
	}
	
	public void cleanup_Whitespace(){
		
		assertEquals( Str.collapseWhitespace( " a\tb\n c  	d\r" ), "a b c d" );
		
	}
	
	public void translateSomeCharacters(){
		
		char [] from = new char [] { 'b','c' };
		String [] to = new String [] { "BB", "CC" };
		
		assertNull( Str.translate( null, from, to ), "aBBCCd" );
		
		assertEquals( Str.translate( "", from, to ), "" );
		
		assertEquals( Str.translate( "abcd", from, to ), "aBBCCd" );
	}
	
}
