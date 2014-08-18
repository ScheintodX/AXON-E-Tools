package de.axone.web.encoding;

import static org.testng.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.testng.annotations.Test;

@Test( groups="web.encoder" )
public class EncodingAppenderTest {
	
	private static final char [] FROM = { '%', '&' };
	private static final String [] TO = { "%23", "%27" };
	
	public static void encodeSomeStuff() throws IOException{
		
		String test = "bläh&123%xyüß";
		
		StringWriter out = new StringWriter();
		
		TranslatingEncoder te = new TranslatingEncoder( FROM, TO );
		
		Appendable ap = te.filter( out );
		
		ap.append(  test  );
		
		assertEquals( out.toString(), "bläh%27123%23xyüß" );
		
	}
}
