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
		
		// This needs to stay stable
		//assertEquals( code, "df3f0659/4605efe4/00000014/132835a5/7d28c319" );		
		// So. Changing siphash implementation to a not buggy one we have this:
		assertEquals( code, "3379449a/78971ad5/00000014/f496acbc/8fbad4cc" );
		
	}
	
	public void testReport() throws Exception {
		
		Exception e = new Exception( new IllegalArgumentException( "No Arg" ) );
		
		Codifier.report( e );
		
	}
	
}
