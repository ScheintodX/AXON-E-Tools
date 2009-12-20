package de.axone.web.encoding;

import org.testng.annotations.Test;

import de.axone.web.encoding.AmpEncoder;
import de.axone.web.encoding.AttributeEncoder;
import de.axone.web.encoding.HtmlEncoder;

import static org.testng.Assert.*;

@Test( groups="web.encoder" )
public class EncoderTest {
	
	public static void main( String [] args ){
		
		new EncoderTest().testAmpEncoder();
		new EncoderTest().testHtmlEncoder();
		new EncoderTest().testAttributeEncoder();
	}

	public void testAmpEncoder(){
		
		String plain = "&lt;blah &  blub  & blob&gt;";
    	String ref = "&lt;blah &amp;  blub  &amp; blob&gt;";
		
    	assertNull( AmpEncoder.ENCODE( null ) );
		assertEquals( AmpEncoder.ENCODE( plain ), ref );
	}
	
	public void testHtmlEncoder(){
		
		String plain = "<blah &  blub  & blob>";
    	String ref = "&lt;blah &amp;  blub  &amp; blob&gt;";
		
    	assertNull( HtmlEncoder.ENCODE( null ) );
		assertEquals( HtmlEncoder.ENCODE( plain ), ref );
	}
	
	public void testAttributeEncoder(){
		
		String plain = "'<bla>\"test\"</bla>'";
		String ref = "&apos;&lt;bla&gt;&quot;test&quot;&lt;/bla&gt;&apos;";
		
    	assertNull( AttributeEncoder.ENCODE( null ) );
		assertEquals( AttributeEncoder.ENCODE( plain ), ref );
	}
}
