package de.axone.web.encoding;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="web.encoder" )
public class EncoderTest {
	
	@Test( enabled = false )
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
