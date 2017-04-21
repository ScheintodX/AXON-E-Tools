package de.axone.web;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.tools.E;
import de.axone.web.encoding.Encoder_AttributeId;

@Test( groups="web.encoder" )
public class Encoder_AttributeIdTest {
	
	private static final int RUNS = 10000, LOOPS = 100;
	
	public void testAttributeEncoding() {
		
		String s;
		
		assertEquals( (s="abc"), Encoder_AttributeId.Encode( s ) );
		assertEquals( "a_b", Encoder_AttributeId.Encode( "a b" ) );
		assertEquals( "a_b", Encoder_AttributeId.Encode( "a b" ) );
		assertEquals( "a", Encoder_AttributeId.Encode( "!a?" ) );
	}
	
	public void testAttributeEncodingSpeed() {
		
		long start, end;
		
		Encoder_AttributeId encoder = new Encoder_AttributeId();
		
		String plain = "thequickbrownfoxjumpsoverthelazydog";
		String notso = "the quick 123 jumps over the lazy !";
		
		// warmup
		for( long x = 0; x<LOOPS*RUNS; x++ ) encoder.encode( plain );
		
		start = System.currentTimeMillis();
		for( int loop = 0; loop < LOOPS; loop ++ ) {
			for( int i = 0; i < RUNS; i++ ) {
				
				encoder.encode( plain );
			}
		}
		end = System.currentTimeMillis();
		
		E.rr( "plain took: " + (end-start) + "ms" );
		
		
		// warmup
		for( long x = 0; x<LOOPS*RUNS; x++ ) encoder.encode( notso );
		
		start = System.currentTimeMillis();
		for( int loop = 0; loop < LOOPS; loop ++ ) {
			for( int i = 0; i < RUNS; i++ ) {
				
				encoder.encode( notso );
			}
		}
		end = System.currentTimeMillis();
		
		E.rr( "not so plain took: " + (end-start) + "ms" );
		
	}

}
