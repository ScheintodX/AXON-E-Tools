package de.axone.tools;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import com.google.common.base.Splitter;

import de.axone.test.Bench;

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
	
	private static int RUNS = 1_000_000;
	
	public void timing() throws InterruptedException{
		
		String test = "abc;def;hij";
		
		// Regex split
		Pattern pat = Pattern.compile( ";" );
		
		Bench.mark( "Pattern.split", RUNS, () -> pat.split( test ) )
				.print();
		
		// String split
		Bench.mark( "String.split", RUNS, () -> test.split( ";" ) )
				.print();
		
		// splitFastLimited
		Bench.mark( "Str.splitFastLimited", RUNS, () -> Str.splitFastLimited( test, ';', 8 ) )
				.print();
		
		// splitFastOnce
		Bench.mark( "Str.splitFastOnce", RUNS, () -> Str.splitFastOnce( test, ';' ) )
				.print();
		
		
		// String utils
		Bench.mark( "StringUtils.split", RUNS, () -> StringUtils.split( test, ';' ) )
				.print();
		
		
		// Test2
		
		// Pattern
		Pattern p = Pattern.compile( "\\s+" );
		String test2 = "   a \tb  c ";
		Bench.mark( "Pattern split", RUNS, ()->p.split( test2 ) )
				.print();
		
		// Fast Spaces
		Bench.mark( "Str.splitFastAtSpacesToList", RUNS, ()->Str.splitFastAtSpacesToList( test2 ) )
				.print();
		
		
		// Guava
		Splitter s = Splitter.on( ';' );
		Bench.mark( "Guava Splitter", RUNS, () -> s.splitToList( test ) )
				.print();
		
		// Guava Once
		Splitter s2 = Splitter.on( ';' ).limit( 1 );
		Bench.mark( "Guava Splitter Once", RUNS, () -> s2.splitToList( test ) )
				.print();
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
	
	public void testAlphanum() {
		
		assertEquals( Str.alphanum( "abcäöüß123" ), "abcäöüß123" );
		assertEquals( Str.alphanum( "a!§$%&/()=z" ), "a_z" );
		
	}
	
	public void testStrip() {
		
		assertEquals( Str.strip( "", '@' ), "" );
		assertEquals( Str.strip( "@", '@' ), "" );
		assertEquals( Str.strip( "@@", '@' ), "" );
		assertEquals( Str.strip( "@@@", '@' ), "" );
		assertEquals( Str.strip( "mummy", '@' ), "mummy" );
		assertEquals( Str.strip( "@mummy", '@' ), "mummy" );
		assertEquals( Str.strip( "mummy@@", '@' ), "mummy" );
		assertEquals( Str.strip( "@mummy@", '@' ), "mummy" );
		assertEquals( Str.strip( "@@@mummy@@@@", '@' ), "mummy" );
		
	}
	
	public void benchStrip() {
		
		Bench.mark( "Strip", 10_000_000, this::testStrip ).print();
	}
	
	public void testReplaceFast() {
		
		assertEquals( Str.replaceFast( "abcabcabc", "d", "B" ), "abcabcabc" );
		assertEquals( Str.replaceFast( "abcabcabc", "b", "B" ), "aBcaBcaBc" );
		assertEquals( Str.replaceFast( "abcabcabc", "ab", "B" ), "BcBcBc" );
		assertEquals( Str.replaceFast( "abcabcabc", "bc", "B" ), "aBaBaB" );
	}
	
	public void benchmarkReplaceFast() throws InterruptedException {
		
		String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit."
				+ " Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque"
				+ " penatibus et magnis dis parturient montes, nascetur ridiculus mus."
				+ " Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem."
				+ " Nulla consequat massa quis enim. Donec pede justo, fringilla vel,"
				+ " aliquet nec, vulputate eget, arcu."
				;
		
		// ReplaceFast
		Bench.mark( "Str.replaceFast", RUNS, () -> Str.replaceFast( text, "e", "E" ) )
				.print();
		
		//String.replace
		Bench.mark( "String.replace", RUNS, () -> text.replace( "e", "E" ) )
				.print();
		
	}
	
	public void testSplitEvery() {
		
		assertEquals( Str.splitEvery( "", 1 ).length, 0 );
		assertEquals( Str.splitEvery( "a", 1 ).length, 1 );
		assertEquals( Str.splitEvery( "a", 2 ).length, 1 );
		
		assertEquals( Str.splitEvery( "abc", 1 ).length, 3 );
		assertEquals( Str.splitEvery( "abc", 2 ).length, 2 );
		assertEquals( Str.splitEvery( "abc", 3 ).length, 1 );
		assertEquals( Str.splitEvery( "abc", 4 ).length, 1 );
		
		assertEquals( Str.splitEvery( "abc", 1 ), new String[]{ "a", "b", "c" } );
		assertEquals( Str.splitEvery( "abc", 2 ), new String[]{ "ab", "c" } );
		assertEquals( Str.splitEvery( "abc", 3 ), new String[]{ "abc" } );
		
		assertEquals( Str.splitEvery( "aabbcc", 1 ).length, 6 );
		assertEquals( Str.splitEvery( "aabbcc", 2 ).length, 3 );
		assertEquals( Str.splitEvery( "aabbcc", 3 ).length, 2 );
		assertEquals( Str.splitEvery( "aabbcc", 4 ).length, 2 );
		assertEquals( Str.splitEvery( "aabbcc", 5 ).length, 2 );
		assertEquals( Str.splitEvery( "aabbcc", 6 ).length, 1 );
		
		assertEquals( Str.splitEvery( "aabbcc", 2 ), new String[]{ "aa", "bb", "cc" } );
	}
	
	public void testSplitHtml() {
		
		assertEquals( splijo( "aaa bbb" ), "aaa:bbb" );
		assertEquals( splijo( "  aa bb  " ), "aa:bb" );
		assertEquals( splijo( "aa <a href=\"foo\"> bb" ), "aa:<a href=\"foo\">:bb" );
		assertEquals( splijo( " <a href=\"foo\"> bb" ), "<a href=\"foo\">:bb" );
		assertEquals( splijo( "aa <a href=\"foo\"> " ), "aa:<a href=\"foo\">" );
	}
	
	private static String splijo( String text ) {
		return Str.join( ":", Str.splitAtSpacesLeaveHtmlIntact( text ) );
	}
	
}
