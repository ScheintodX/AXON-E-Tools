package de.axone.exception.codify;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.codify" )
public class CodifyTest {

	public void testCodify() throws Exception {
		
		assertFalse( false );
		
		Exception e = new IllegalArgumentException( "No Arg" );
		
		Codifier.Description desc = Codifier.description( e );
		
		assertEquals( desc.file(), "CodifyTest.java" );
		assertEquals( desc.line(), 14 );
		assertEquals( desc.method(), "testCodify" );
		assertEquals( desc.exception(), "IllegalArgumentException" );
		assertEquals( desc.message(), "No Arg" );
		
		CodifiedException ce = new CodifiedException( e );
		String code = ce.code();
		
		assertEquals( code, "bedc13ba/4b60fdb6/00000014/5142c15c/8b45bf77" );		
		
	}
	
	public void testReport() throws Exception {
		
		Exception e = new Exception( new IllegalArgumentException( "No Arg" ) );
		
		Codifier.report( e );
		
	}
	
}
