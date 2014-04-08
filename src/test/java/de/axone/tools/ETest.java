package de.axone.tools;

import static org.testng.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

@Test( groups="tools.e" )
public class ETest {
	
	private static final class StringOutputStream extends OutputStream {
		
		StringBuffer buf = new StringBuffer();

		@Override
		public void write( int b ) throws IOException {
			
			Character c = Character.valueOf( (char)b );
			buf.append( c );
		}
		
		@Override
		public String toString(){
			
			return buf.toString();
		}
		
		public void clear(){
			buf = new StringBuffer();
		}
		
	}
	
	StringOutputStream s = new StringOutputStream();
	PrintStream save=null, out = new PrintStream( s );
	
	public void testE() throws Exception {
		
		try {
			
			save = System.out;
			System.setOut( out );
			
			String string="text";
			
			E.cho( (Object)null );		assertOut( s, "(-null-)" );
			E.cho( (Object[])null );	assertOut( s, "(-null-)" );
			
			E.cho( string );	assertOut( s, "text" );
			
			// Primitives
			boolean booleanV=true;
			byte byteV=12;
			char charV='c';
			int intV=12345;
			double doubleV=123.45;
			
			E.cho( booleanV );	assertOut( s, "true" );
			E.cho( byteV );		assertOut( s, "12" );
			E.cho( charV );		assertOut( s, "c" );
			E.cho( intV );		assertOut( s, "12345" );
			E.cho( doubleV );	assertOut( s, "123.45" );
			E.cho( booleanV, byteV, charV, intV, doubleV );
			assertOut( s, "true, 12, c, 12345, 123.45" );
			
			// Wrapers
			Boolean booleanO=booleanV;
			Byte byteO=byteV;
			Character charO=charV;
			Integer intO=intV;
			Double doubleO=doubleV;
			
			E.cho( booleanO );	assertOut( s, "true" );
			E.cho( byteO );		assertOut( s, "12" );
			E.cho( charO );		assertOut( s, "c" );
			E.cho( intO );		assertOut( s, "12345" );
			E.cho( doubleO );	assertOut( s, "123.45" );
			
			// Primitive Arrays
			boolean [] booleanA=new boolean[]{ true, false };
			byte [] byteA=new byte[]{ (byte)1, (byte)2, (byte)3 };
			char [] charA=new char[]{ 'a', 'b', 'c' };
			int [] intA=new int[]{ 12345, 12346, 12347 };
			double [] doubleA = new double[]{ 123.45, 123.46, 123.47 };
			
			E.cho( booleanA );	assertOut( s, "['true', 'false']" );
			E.cho( byteA );		assertOut( s, "['1', '2', '3']" );
			E.cho( charA );		assertOut( s, "['a', 'b', 'c']" );
			E.cho( intA );		assertOut( s, "['12345', '12346', '12347']" );
			E.cho( doubleA );	assertOut( s, "['123.45', '123.46', '123.47']" ); 
			
			// Wrapper Arrays
			Boolean [] booleanOA=new Boolean[]{ true, false };
			Byte [] byteOA=new Byte[]{ (byte)1, (byte)2, (byte)3 };
			Character [] charOA=new Character[]{ 'a', 'b', 'c' };
			Integer [] intOA=new Integer[]{ 12345, 12346, 12347 };
			Double [] doubleOA = new Double[]{ 123.45, 123.46, 123.47 };
			
			E.cho( (Object[])booleanOA );	assertOut( s, "true, false" );
			E.cho( (Object[])byteOA );		assertOut( s, "1, 2, 3" );
			E.cho( (Object[])charOA );		assertOut( s, "a, b, c" );
			E.cho( (Object[])intOA );		assertOut( s, "12345, 12346, 12347" );
			E.cho( (Object[])doubleOA );	assertOut( s, "123.45, 123.46, 123.47" ); 
			E.cho( (Object)booleanOA );		assertOut( s, "['true', 'false']" );
			E.cho( (Object)byteOA );		assertOut( s, "['1', '2', '3']" );
			E.cho( (Object)charOA );		assertOut( s, "['a', 'b', 'c']" );
			E.cho( (Object)intOA );			assertOut( s, "['12345', '12346', '12347']" );
			E.cho( (Object)doubleOA );		assertOut( s, "['123.45', '123.46', '123.47']" ); 
			
			// List
			List<Boolean> booleanOL=Arrays.asList( true, false );
			List<Byte> byteOL=Arrays.asList( (byte)1, (byte)2, (byte)3 );
			List<Character> charOL=Arrays.asList( 'a', 'b', 'c' );
			List<Integer> intOL=Arrays.asList( 12345, 12346, 12347 );
			List<Double> doubleOL=Arrays.asList( 123.45, 123.46, 123.47 );
			
			E.cho( booleanOL );	assertOut( s, "('true', 'false')" );
			E.cho( byteOL );	assertOut( s, "('1', '2', '3')" );
			E.cho( charOL );	assertOut( s, "('a', 'b', 'c')" );
			E.cho( intOL );		assertOut( s, "('12345', '12346', '12347')" );
			E.cho( doubleOL );	assertOut( s, "('123.45', '123.46', '123.47')" ); 
			
			Map<Integer,Boolean> ibM = Mapper.linkedHashMap( new Integer[]{1, 2}, new Boolean[]{true, false} );
			E.cho( ibM );	assertOut( s, "{'1'=>'true', '2'=>'false'}" );
			
			Map<Character,String> csM = Mapper.linkedHashMap( new Character[]{'a', 'b'}, new String[]{"AAA", "BBB"} );
			E.cho( csM );	assertOut( s, "{'a'=>'AAA', 'b'=>'BBB'}" );
			
			List<Integer> l1 = Arrays.asList( 100, 101, 102 );
			List<Integer> l2 = Arrays.asList( 200, 201, 202, null );
			Map<Byte,List<?>> bslM = Mapper.hashMap(
					new Byte[]{ (byte)1, (byte)2 },
					new List<?>[]{ l1, l2 }
			);
			
			E.cho( bslM );	assertOut( s, "{'1'=>('100', '101', '102'), '2'=>('200', '201', '202', (-null-))}" );
			
		} finally {
			if( save != null ) try {
				System.setOut( save );
			} catch( Throwable t ){
				t.printStackTrace(); //<-- goes to err
			}
		}
	}
	
	private static final Pattern CLASS_PREFIX =
			Pattern.compile( "^>>> \\(ETest.java:[0-9]+\\) " );
	private static final Pattern NL = 
			Pattern.compile( "\n$" );
	private static final String EMPTY = "";

	private void assertOut( StringOutputStream s, String text ){
		
		String ref = s.toString();
		
		ref = CLASS_PREFIX.matcher( ref ).replaceAll( EMPTY );
		ref = NL.matcher( ref ).replaceAll( EMPTY );
		
		/*
		ref = ref
				.replaceAll( "^>>> \\(ETest.java:[0-9]+\\) ", "" )
				.replaceAll( "\n$", "" );
		;
		*/
		
		assertEquals( ref, text );
		
		s.clear();
	}
}