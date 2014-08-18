package de.axone.web.encoding;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="web.encoder" )
public class EncoderTest {
	
	public void testAmpEncoder(){
		
		String plain = "&lt;blah &  blub  & blob&gt;";
    	String ref = "&lt;blah &amp;  blub  &amp; blob&gt;";
		
    	assertNull( Encoder_Amp.ENCODE( null ) );
		assertEquals( Encoder_Amp.ENCODE( plain ), ref );
	}
	
	public void testHtmlEncoder(){
		
		String plain = "<blah &  blub  & blob>";
    	String ref = "&lt;blah &amp;  blub  &amp; blob&gt;";
		
    	assertNull( Encoder_Html.instance().encode( null ) );
		assertEquals( Encoder_Html.instance().encode( plain ), ref );
	}
	
	public void testAttributeEncoder(){
		
		String plain = "'<bla>\"test\"</bla>'";
		String ref = "&apos;&lt;bla&gt;&quot;test&quot;&lt;/bla&gt;&apos;";
		
    	assertNull( Encoder_Attribute.ENCODE( null ) );
		assertEquals( Encoder_Attribute.ENCODE( plain ), ref );
	}
}
