package de.axone.tools;

import static org.testng.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

@Test( groups="nomt.tools.e", singleThreaded=true )
public class ETest {

	private static final class StringOutputStream extends OutputStream {

		ByteArrayOutputStream buf = new ByteArrayOutputStream();

		@Override
		public void write( int b ) throws IOException {

			Character c = Character.valueOf( (char)b );
			buf.write( c );
		}

		@Override
		public String toString(){

			System.err.print( buf.toString() );
			return buf.toString();
		}

		public void clear() throws IOException{
			if( buf != null ) buf.close();
			buf = new ByteArrayOutputStream();
		}

	}

	/**
	 * Note that these tests mail fail when another thread writes something
	 * to stdout. I have currently no idea how to fix this and it's no problem
	 * in production because gibberish is what you kind of expect in that case.
	 *
	 * @throws Exception
	 */
	public void testE() throws Exception {

		PrintStream saveOut = System.out;

		try (
				StringOutputStream s = new StringOutputStream();
				PrintStream tmpOut = new PrintStream( s, false, "UTF-8" )
		) {

			System.setOut( tmpOut );

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
			E.cho( byteV );		assertOut( s, "0c" );
			E.cho( charV );		assertOut( s, "c" );
			E.cho( intV );		assertOut( s, "12345" );
			E.cho( doubleV );	assertOut( s, "123.45" );
			E.cho( booleanV, byteV, charV, intV, doubleV );
			assertOut( s, "true, 0c, c, 12345, 123.45" );

			// Wrapers
			Boolean booleanO=booleanV;
			Byte byteO=byteV;
			Character charO=charV;
			Integer intO=intV;
			Double doubleO=doubleV;

			E.cho( booleanO );	assertOut( s, "true" );
			E.cho( byteO );		assertOut( s, "0c" );
			E.cho( charO );		assertOut( s, "c" );
			E.cho( intO );		assertOut( s, "12345" );
			E.cho( doubleO );	assertOut( s, "123.45" );

			// Primitive Arrays
			boolean [] booleanA=new boolean[]{ true, false };
			byte [] byteA=new byte[]{ (byte)1, (byte)2, (byte)3 };
			char [] charA=new char[]{ 'a', 'b', 'c' };
			int [] intA=new int[]{ 12345, 12346, 12347 };
			double [] doubleA = new double[]{ 123.45, 123.46, 123.47 };

			E.cho( booleanA );	assertOut( s, "[ 'true', 'false' ]" );
			E.cho( byteA );		assertOut( s, "[ 01, 02, 03 ]" );
			E.cho( charA );		assertOut( s, "[ 'a', 'b', 'c' ]" );
			E.cho( intA );		assertOut( s, "[ 12345, 12346, 12347 ]" );
			E.cho( doubleA );	assertOut( s, "[ 123.45, 123.46, 123.47 ]" );

			// Wrapper Arrays
			Boolean [] booleanOA=new Boolean[]{ true, false };
			Byte [] byteOA=new Byte[]{ (byte)1, (byte)2, (byte)3 };
			Character [] charOA=new Character[]{ 'a', 'b', 'c' };
			Integer [] intOA=new Integer[]{ 12345, 12346, 12347 };
			Double [] doubleOA = new Double[]{ 123.45, 123.46, 123.47 };

			E.cho( (Object[])booleanOA );	assertOut( s, "true, false" );
			E.cho( (Object[])byteOA );		assertOut( s, "01, 02, 03" );
			E.cho( (Object[])charOA );		assertOut( s, "a, b, c" );
			E.cho( (Object[])intOA );		assertOut( s, "12345, 12346, 12347" );
			E.cho( (Object[])doubleOA );	assertOut( s, "123.45, 123.46, 123.47" );
			E.cho( (Object)booleanOA );		assertOut( s, "[ 'true', 'false' ]" );
			E.cho( (Object)byteOA );		assertOut( s, "[ 01, 02, 03 ]" );
			E.cho( (Object)charOA );		assertOut( s, "[ 'a', 'b', 'c' ]" );
			E.cho( (Object)intOA );			assertOut( s, "[ 12345, 12346, 12347 ]" );
			E.cho( (Object)doubleOA );		assertOut( s, "[ 123.45, 123.46, 123.47 ]" );

			// List
			List<Boolean> booleanOL=Arrays.asList( true, false );
			List<Byte> byteOL=Arrays.asList( (byte)1, (byte)2, (byte)3 );
			List<Character> charOL=Arrays.asList( 'a', 'b', 'c' );
			List<Integer> intOL=Arrays.asList( 12345, 12346, 12347 );
			List<Double> doubleOL=Arrays.asList( 123.45, 123.46, 123.47 );

			E.cho( booleanOL );	assertOut( s, "( 'true', 'false' )" );
			E.cho( byteOL );	assertOut( s, "( 01, 02, 03 )" );
			E.cho( charOL );	assertOut( s, "( 'a', 'b', 'c' )" );
			E.cho( intOL );		assertOut( s, "( 12345, 12346, 12347 )" );
			E.cho( doubleOL );	assertOut( s, "( 123.45, 123.46, 123.47 )" );

			Map<Integer,Boolean> ibM = Mapper.linkedHashMap( new Integer[]{1, 2}, new Boolean[]{true, false} );
			E.cho( ibM );	assertOut( s, "{ 1=>'true', 2=>'false' }" );

			Map<Character,String> csM = Mapper.linkedHashMap( new Character[]{'a', 'b'}, new String[]{"AAA", "BBB"} );
			E.cho( csM );	assertOut( s, "{ 'a'=>'AAA', 'b'=>'BBB' }" );

			List<Integer> l1 = Arrays.asList( 100, 101, 102 );
			List<Integer> l2 = Arrays.asList( 200, 201, 202, null );
			Map<Byte,List<?>> bslM = Mapper.hashMap(
					new Byte[]{ (byte)1, (byte)2 },
					new List<?>[]{ l1, l2 }
			);

			E.cho( bslM );	assertOut( s, "{ 01=>( 100, 101, 102 ), 02=>( 200, 201, 202, (-null-) ) }" );

			// Keep line number stable or this will fail
			a(); assertOut( s, "[test] < (ETest.java:165) < (ETest.java:164) < (ETest.java:157)" );

		} finally {
			System.setOut( saveOut );
		}
	}

	private void a(){ b(); }
	private void b(){ c(); }
	private void c(){
		E._x( 3, "test" );
	}

	private static final Pattern CLASS_PREFIX =
			Pattern.compile( "^>>> \\(ETest.java:[0-9]+\\) " );
	private static final Pattern NL =
			Pattern.compile( "\n$" );
	private static final String EMPTY = "";

	private void assertOut( StringOutputStream s, String text ) throws Exception {

		String ref = s.toString();

		ref = CLASS_PREFIX.matcher( ref ).replaceAll( EMPTY );
		ref = NL.matcher( ref ).replaceAll( EMPTY );

		ref = ref
				.replaceAll( "^>>> \\(ETest.java:[0-9]+\\) ", "" )
				.replaceAll( "\n$", "" );
		;

		System.err.println( "=" + ref + "=");

		assertEquals( ref, text );

		s.clear();
	}

	public void testFF() throws Exception {

		// make sure we dont get "test123"=="test123" because of
		// String buffering
		StringBuilder b1 = new StringBuilder();
		b1.append( "test" );
		b1.append( "123" );
		String test123 = b1.toString();

		StringBuilder b2 = new StringBuilder();
		b2.append( "1" );
		String one = b2.toString();

		assertEquals( Di.ff( "aBa", "a" ), "a[Ba|]" );
		assertEquals( Di.ff( test123, "test123" ), "test123" );
		assertEquals( Di.ff( test123, "test223" ), "test[1|2]23" );
		assertEquals( Di.ff( test123, "test" ), "test[123|]" );
		assertEquals( Di.ff( test123, test123+"-"+test123 ), "test123[|-test123]" );
		assertEquals( Di.ff( test123+"-"+test123, test123 ), "test123[-test123|]" );
		assertEquals( Di.ff( test123, test123+test123 ), "test123[|test123]" );
		assertEquals( Di.ff( test123+test123, test123 ), "test123[test123|]" );
		assertEquals( Di.ff( "test", test123 ), "test[|123]" );
		assertEquals( Di.ff( "", "" ), "" );
		assertEquals( Di.ff( test123, "" ), "[test123|]" );
		assertEquals( Di.ff( "", test123 ), "[|test123]" );
		assertEquals( Di.ff( null, null ), null );
		assertEquals( Di.ff( test123, null ), "[test123|null]" );
		assertEquals( Di.ff( null, test123 ), "[null|test123]" );

		assertEquals( Di.ff( "o", "O" ), "[o|O]" );
		assertEquals( Di.ff( "a", "aa" ), "a[|a]" );
		assertEquals( Di.ff( "aa", "a" ), "a[a|]" );

		assertEquals( Di.ff( one, "1" ), "1" );

		assertEquals( Di.ff( 1, 1 ), "1" );

	}

}
