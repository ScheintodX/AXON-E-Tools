package de.axone.tools;

import static org.testng.Assert.*;

import java.util.Random;

import org.testng.annotations.Test;

@Test( groups="tools.base64" )
public class Base64Test {

	public void testBase64(){
		
		BASE64Encoder enc = new BASE64Encoder();
		BASE64Decoder dec = new BASE64Decoder();
		
		Random rand = new Random();
		
		for( int i=0; i<1024; i++ ){
			
			byte [] dataIn = new byte[i];
			rand.nextBytes( dataIn );
			
			String encS = enc.encodeURLSafe( dataIn );
			
			byte [] dataOut = dec.decode( encS );
			
			assertEquals( dataIn, dataOut );
		}
	}
	
}
