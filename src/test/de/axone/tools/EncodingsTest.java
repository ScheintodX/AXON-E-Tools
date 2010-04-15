package de.axone.tools;

import static org.testng.Assert.*;

import java.io.IOException;

import org.testng.annotations.Test;

@Test
public class EncodingsTest {

	public void testCorrectEncoding() throws IOException{
		
		String wrong = "blÃ¶d";
		String ok = "blöd";
		
		assertTrue( Encodings.isWrongEncodedUtf8( wrong ) );
		assertEquals( Encodings.convertToUsableString( wrong ), ok );
		
		assertFalse( Encodings.isWrongEncodedUtf8( ok ) );
		
	}
}
