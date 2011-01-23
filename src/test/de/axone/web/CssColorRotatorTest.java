package de.axone.web;

import static org.testng.Assert.*;

import java.awt.Color;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

@Test( groups="web.colorrotator" )
public class CssColorRotatorTest {

	public void testPattern(){
		
		Pattern pat = CssColorRotator.COLOR;
		
		String s1 = "#3cd";
		String s2 = "#ABCDEF";
		String e1 = "#3cd1";
		String e2 = "#ACDEF";
		
		assertTrue( pat.matcher( s1 ).matches() );
		assertTrue( pat.matcher( s2 ).matches() );
		
		assertFalse( pat.matcher( e1 ).matches() );
		assertFalse( pat.matcher( e2 ).matches() );
	}
	
	public void testRotate(){
		
		CssColorRotator rot;
		
		// No change: 0°
		rot = new CssColorRotator( 0.0, 1, 1 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		
		assertEquals( rot.rotate( c3( "800" ) ), c3( "800" ) );
		assertEquals( rot.rotate( c3( "080" ) ), c3( "080" ) );
		assertEquals( rot.rotate( c3( "008" ) ), c3( "008" ) );
		
		assertEquals( rot.rotate( c3( "088" ) ), c3( "088" ) );
		assertEquals( rot.rotate( c3( "808" ) ), c3( "808" ) );
		assertEquals( rot.rotate( c3( "880" ) ), c3( "880" ) );
		
		assertEquals( rot.rotate( c3( "888" ) ), c3( "888" ) );
		
		// No change: 360° -> 0°
		rot = new CssColorRotator( 1.0, 1, 1 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		
		assertEquals( rot.rotate( c3( "800" ) ), c3( "800" ) );
		assertEquals( rot.rotate( c3( "080" ) ), c3( "080" ) );
		assertEquals( rot.rotate( c3( "008" ) ), c3( "008" ) );
		
		assertEquals( rot.rotate( c3( "088" ) ), c3( "088" ) );
		assertEquals( rot.rotate( c3( "808" ) ), c3( "808" ) );
		assertEquals( rot.rotate( c3( "880" ) ), c3( "880" ) );
		
		assertEquals( rot.rotate( c3( "888" ) ), c3( "888" ) );
		
		// Rotate 120°
		rot = new CssColorRotator( (1.0/3), 1, 1 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		
		assertEquals( rot.rotate( c3( "800" ) ), c3( "080" ) );
		assertEquals( rot.rotate( c3( "080" ) ), c3( "008" ) );
		assertEquals( rot.rotate( c3( "008" ) ), c3( "800" ) );
		
		assertEquals( rot.rotate( c3( "088" ) ), c3( "808" ) );
		assertEquals( rot.rotate( c3( "808" ) ), c3( "880" ) );
		assertEquals( rot.rotate( c3( "880" ) ), c3( "088" ) );
		
		assertEquals( rot.rotate( c3( "888" ) ), c3( "888" ) );
		
		// Rotate 240°
		rot = new CssColorRotator( (2.0/3), 1, 1 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		
		assertEquals( rot.rotate( c3( "800" ) ), c3( "008" ) );
		assertEquals( rot.rotate( c3( "080" ) ), c3( "800" ) );
		assertEquals( rot.rotate( c3( "008" ) ), c3( "080" ) );
		
		assertEquals( rot.rotate( c3( "088" ) ), c3( "880" ) );
		assertEquals( rot.rotate( c3( "808" ) ), c3( "088" ) );
		assertEquals( rot.rotate( c3( "880" ) ), c3( "808" ) );
		
		assertEquals( rot.rotate( c3( "888" ) ), c3( "888" ) );
	}
	
	public void testGamma(){
		
		CssColorRotator rot;
		
		// Brightness
		// Don't change: Gamma 1.0
		rot = new CssColorRotator( 0, 1, 1.0 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		assertEquals( rot.rotate( c3( "400" ) ), c3( "400" ) );
		assertEquals( rot.rotate( c3( "800" ) ), c3( "800" ) );
		assertEquals( rot.rotate( c3( "c00" ) ), c3( "c00" ) );
		assertEquals( rot.rotate( c3( "f00" ) ), c3( "f00" ) );
		
		// Darken: Gamma 2.0
		rot = new CssColorRotator( 0, 1, 2.0 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		assertEquals( rot.rotate( c3( "400" ) ), new Color( 18, 0, 0 ) );
		assertEquals( rot.rotate( c3( "800" ) ), new Color( 73, 0, 0 ) );
		assertEquals( rot.rotate( c3( "c00" ) ), new Color( 163, 0, 0 ) );
		assertEquals( rot.rotate( c3( "f00" ) ), c3( "f00" ) );
		
		// Lighten: Gamma 0.5
		rot = new CssColorRotator( 0, 1, 0.5 );
		assertEquals( rot.rotate( c3( "000" ) ), c3( "000" ) );
		assertEquals( rot.rotate( c3( "400" ) ), new Color( 132, 0, 0 ) );
		assertEquals( rot.rotate( c3( "800" ) ), new Color( 186, 0, 0 ) );
		assertEquals( rot.rotate( c3( "c00" ) ), new Color( 228, 0, 0 ) );
		assertEquals( rot.rotate( c3( "f00" ) ), c3( "f00" ) );
		
	}
	
	Color c3( String code ){
		return CssColorRotator.c3( code );
	}
	Color c6( String code ){
		return CssColorRotator.c6( code );
	}
	
	public void testReplacement(){
		
		String testCss = 
			"main, body {\n" + 
			"	color: #800;\n" + 
			"}\n" +
			"h1\n" +
			"{\n" +
			"	background-color: #808080;\n" +
			"}\n" +
			"#abcd { font-color: #abc; }"
		;
		
		String refCss = 
			"main, body {\n" + 
			"	color: #080;\n" + 
			"}\n" +
			"h1\n" +
			"{\n" +
			"	background-color: #808080;\n" +
			"}\n" +
			"#abcd { font-color: #cab; }"
		;
		
		CssColorRotator rot = new CssColorRotator( 120, 1000, 1000 );
		
		String result = rot.rotate( testCss );
		
		assertEquals( result, refCss );
	}
	
}
