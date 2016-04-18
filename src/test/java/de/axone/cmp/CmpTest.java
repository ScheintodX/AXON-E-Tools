package de.axone.cmp;

import static de.axone.cmp.Cmp.*;
import static org.testng.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

@Test( groups="tools.cmp" )
public class CmpTest {

	public void testCmp() throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat( "dd.MM.yyyy" );
		
		Date d1 = format.parse( "01.01.2000" ),
		     d2 = format.parse( "01.01.2001" ),
		     d3 = format.parse( "01.01.2001" )
		     ;
		
		assertTrue( The( d1 ).is( Cmp.LesserThan( d2 ) ) );
		assertTrue( The( d2 ).is( Cmp.GreaterThan( d1 ) ) );
		assertTrue( The( d2 ).is( Cmp.EqualTo( d3 ) ) );
		
		assertFalse( The( d1 ).is( Cmp.GreaterThan( d2 ) ) );
		assertFalse( The( d2 ).is( Cmp.LesserThan( d1 ) ) );
		assertFalse( The( d1 ).is( Cmp.EqualTo( d2 ) ) );
		
		assertFalse( The( d1 ).not( Cmp.LesserThan( d2 ) ) );
		assertFalse( The( d2 ).not( Cmp.GreaterThan( d1 ) ) );
		assertFalse( The( d2 ).not( Cmp.EqualTo( d3 ) ) );
		
		assertTrue( The( d1 ).not( Cmp.GreaterThan( d2 ) ) );
		assertTrue( The( d2 ).not( Cmp.LesserThan( d1 ) ) );
		assertTrue( The( d1 ).not( Cmp.EqualTo( d2 ) ) );
		
		assertFalse( The( 1 ).is( Cmp.GreaterThan( 2 ) ) );
		assertFalse( The( 1 ).is( Cmp.EqualTo( 2 ) ) );
		assertTrue( The( 1 ).is( Cmp.LesserThan( 2 ) ) );
	}
	
}
