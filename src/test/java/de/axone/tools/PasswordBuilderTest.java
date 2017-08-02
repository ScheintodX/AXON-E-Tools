package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.test.Bench;

@Test( groups="tools.passwordbuilder" )
public class PasswordBuilderTest {
	
	
	@Test( enabled=false )
	public static void main( String [] args ) throws Exception {
		
		new PasswordBuilderTest().testHashing();
		new PasswordBuilderTest().testTiming();

		/*
		Provider [] providers = Security.getProviders();
		for( Provider p : providers ){
			for( Object o : p.keySet() ){
				
				String key = (String)o;
				key = key.split( " " )[0];
				
				if( key.contains( "SHA" ) ) E.rr( key );
				
				if( key.startsWith( "MessageDigest") ){
				//if( key.startsWith( "Cipher") ){
					E.rr( key );
				}
			}
		}
	*/
	}	
	
	public void testHashing() throws Exception {
		
		for( int i=0; i<100; i++ ){
			String plain = PasswordBuilder.makePasswd();
			// Plain should work, too
			assertTrue( PasswordBuilder.checkPassword( plain, plain ) );
			String hashed = PasswordBuilder.hashPassword( plain, "SHA1" );
			assertTrue( PasswordBuilder.checkPassword( plain, hashed ) );
		}
	}
	
	private static final long TIMING_ROUNDS = 100;
	private static final long TIMING_LIMIT_LOWER = 100;
	private static final long TIMING_LIMIT_UPPER = 1000;
	
	public void testTiming() throws Exception {

		String plain = "testPw123!";
		
		Bench.mark( "hashing", TIMING_ROUNDS, () -> PasswordBuilder.hashPassword( plain ) )
				.print()
				.time()
						.isGreaterThan( TIMING_LIMIT_LOWER )
						.isLessThan( TIMING_LIMIT_UPPER )
						;
						
				
	}
}
