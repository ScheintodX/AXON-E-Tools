package de.axone.tools;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import com.google.common.base.Splitter;

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
		
		assertThat( "".split( ";" ) )
				.contains( "" )
				.hasSize( 1 )
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
	
	public void testSplitFastAtSpaces(){
		
		assertThat( Str.splitFastAtSpacesToList( "  a\t	b \n c" ) )
				.contains( "a", "b", "c" )
				.hasSize( 3 )
				;
		
		assertThat( Str.splitFastAtSpacesToList( "  a	b  c " ) )
				.contains( "a", "b", "c" )
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
	
	private static int NUM_RUNS = 1_000_000;
	
	public void timing() throws InterruptedException{
		
		long start, end;
		
		String test = "abc;def;hij";
		
		// Regex split
		Pattern pat = Pattern.compile( ";" );
		for( int i=0; i<NUM_RUNS; i++ ){
			pat.split( test );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			pat.split( test );
		}
		end = System.currentTimeMillis();
		E.rr( "Pattern.split Took " + (end-start) + " ms" );
		
		// String split
		for( int i=0; i<NUM_RUNS; i++ ){
			test.split( ";" );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			test.split( ";" );
		}
		end = System.currentTimeMillis();
		E.rr( "String.split Took " + (end-start) + " ms" );
		
		
		// splitFastLimited
		for( int i=0; i<NUM_RUNS; i++ ){
			Str.splitFastLimited( test, ';', 8 );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			Str.splitFastLimited( test, ';', 8 );
		}
		end = System.currentTimeMillis();
		E.rr( "Str Took " + (end-start) + " ms" );
		
		// splitFastOnce
		for( int i=0; i<NUM_RUNS; i++ ){
			Str.splitFastOnce( test, ';' );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			Str.splitFastOnce( test, ';' );
		}
		end = System.currentTimeMillis();
		E.rr( "Str Once Took " + (end-start) + " ms" );
		
		
		// String utils
		for( int i=0; i<NUM_RUNS; i++ ){
			StringUtils.split( test, ';' );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			StringUtils.split( test, ';' );
		}
		end = System.currentTimeMillis();
		E.rr( "StringUtils Took " + (end-start) + " ms" );
		
		
		// Test2
		
		// Pattern
		Pattern p = Pattern.compile( "\\s+" );
		String test2 = "   a \tb  c ";
		for( int i=0; i<NUM_RUNS; i++ ){
			p.split( test2 );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			p.split( test2 );
		}
		end = System.currentTimeMillis();
		E.rr( "Pattern Took " + (end-start) + " ms" );
		
		// Fast Spaces
		for( int i=0; i<NUM_RUNS; i++ ){
			Str.splitFastAtSpacesToList( test2 );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			Str.splitFastAtSpacesToList( test2 );
		}
		end = System.currentTimeMillis();
		E.rr( "FastSpaces Took " + (end-start) + " ms" );
		
		
		// Guava
		Splitter s = Splitter.on( ';' );
		for( int i=0; i<NUM_RUNS; i++ ){
			s.splitToList( test );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			s.splitToList( test );
		}
		end = System.currentTimeMillis();
		E.rr( "Guava Took " + (end-start) + " ms" );
		
		// Guava Once
		Splitter s2 = Splitter.on( ';' ).limit( 1 );
		for( int i=0; i<NUM_RUNS; i++ ){
			s2.splitToList( test );
		}
		start = System.currentTimeMillis();
		for( int i=0; i<NUM_RUNS; i++ ){
			s2.splitToList( test );
		}
		end = System.currentTimeMillis();
		E.rr( "Guava Once Took " + (end-start) + " ms" );
		
	}
	
	public void testGuavaSplitter() {
		
		Splitter s = Splitter.on( ';' );
		
		String test = "abc;def;hij";
		
		assertThat( s.splitToList( test ) )
				.hasSize( 3 )
				.contains( "abc", "def", "hij" )
				;
	}
	
	public void cleanup_Whitespace(){
		
		assertEquals( Str.collapseWhitespace( " a\tb\n c  	d\r" ), "a b c d" );
		
	}
	
	public void translateSomeCharactersToStrings(){
		
		char [] from = new char [] { 'b','c' };
		String [] to = new String [] { "BB", "CC" };
		
		assertNull( Str.translate( null, from, to ) );
		
		assertEquals( Str.translate( "", from, to ), "" );
		
		assertEquals( Str.translate( "abcd", from, to ), "aBBCCd" );
	}
	
	public void translateSomeCharactersToCharacters() {
		
		assertNull( Str.translate( null, 'a', 'A' ) );
		
		assertEquals( Str.translate( "abaca", 'a', 'A' ), "AbAcA" );
		
		String testNoA = "bc";
		
		assertEquals( Str.translate( testNoA, 'a', 'A' ), testNoA );
		assertTrue( Str.translate( testNoA, 'a', 'A' ) == testNoA );
	}
	
	public void testClean() {
		
		assertEquals( Str.clean( "aBc 123" ), "aBc_123" );
		assertEquals( Str.cleanToLowerCase( "aBc 123" ), "abc_123" );
		
	}
	
}
