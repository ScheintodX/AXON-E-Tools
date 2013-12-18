package de.axone.tools;

import static org.testng.Assert.*;

import java.util.Random;

import org.testng.annotations.Test;

@Test( groups="tools.base64" )
public class Base64Test {

	public void testApacheCommons(){
		
		Base64 b64 = new Base64ApacheCommons();
		
		Random rand = new Random();
		
		/* Encode */
		for( int i=0; i<1024; i++ ){
			
			byte [] plain = new byte[i];
			rand.nextBytes( plain );
			
			String encoded = b64.encode( plain );
			
			byte [] decoded = b64.decode( encoded );
			
			assertEquals( plain, decoded );
		}
		
		/* EncodeURLSafe */
		for( int i=0; i<128; i++ ){
			
			byte [] plain = new byte[i];
			rand.nextBytes( plain );
			
			String encoded = b64.encodeURLSafe( plain );
			
			byte [] decoded = b64.decode( encoded );
			
			assertEquals( plain, decoded );
		}
	}
	
	public void testJavaxDatabind(){
		
		Base64 b64 = new Base64JavaxDatabind();
		
		Random rand = new Random();
		
		/* Encode */
		for( int i=0; i<1024; i++ ){
			
			byte [] plain = new byte[i];
			rand.nextBytes( plain );
			
			String encoded = b64.encode( plain );
			
			byte [] decoded = b64.decode( encoded );
			
			assertEquals( plain, decoded );
		}
	}
	
	public void testXCompatibility(){
		
		byte [] plain = new byte[256];
		for( int i=0; i<plain.length; i++ ){
			plain[ i ] = (byte)i;
		}
		
		assertEquals( Base64ApacheCommons.Decode( Base64ApacheCommons.Encode( plain ) ), plain );
		assertEquals( Base64ApacheCommons.Decode( Base64ApacheCommons.EncodeURLSafe( plain ) ), plain );
		
		assertEquals( Base64ApacheCommons.Decode( Base64JavaxDatabind.Encode( plain ) ), plain );
		
		assertEquals( Base64JavaxDatabind.Decode( Base64ApacheCommons.Encode( plain ) ), plain );
		// ApacheCommons URL-Safe encoding is currently not compatible with Javax decoding
		assertNotEquals( Base64JavaxDatabind.Decode( Base64ApacheCommons.EncodeURLSafe( plain ) ), plain );
		
		assertEquals( Base64ApacheCommons.Decode( Base64JavaxDatabind.Encode( plain ) ), plain );
	}
	
}
