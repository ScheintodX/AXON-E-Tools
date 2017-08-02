package de.axone.web;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.test.Bench;
import de.axone.web.encoding.Encoder_AttributeId;

@Test( groups="web.encoder" )
public class Encoder_AttributeIdTest {
	
	private static final int RUNS = 1000000;
	
	public void testAttributeEncoding() {
		
		String s;
		
		assertEquals( (s="abc"), Encoder_AttributeId.Encode( s ) );
		assertEquals( "a_b", Encoder_AttributeId.Encode( "a b" ) );
		assertEquals( "a_b", Encoder_AttributeId.Encode( "a b" ) );
		assertEquals( "a", Encoder_AttributeId.Encode( "!a?" ) );
	}
	
	public void testAttributeEncodingSpeed() {
		
		Encoder_AttributeId encoder = new Encoder_AttributeId();
		
		String plain = "thequickbrownfoxjumpsoverthelazydog";
		String notso = "the quick 123 jumps over the lazy !";
		
		Bench.mark( "plain", RUNS, () -> encoder.encode( plain ) )
				.print();
		
		Bench.mark( "not so plain", RUNS, () -> encoder.encode( notso ) )
				.print();
		
	}

}
