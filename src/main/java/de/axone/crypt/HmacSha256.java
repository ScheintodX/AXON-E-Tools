package de.axone.crypt;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import de.axone.tools.HEX;

public abstract class HmacSha256 {

	public static byte[] sign( byte [] message, byte [] key ) {
		
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec( key, "HmacSHA256" );
		  
			sha256_HMAC.init( secret_key );
			
			return sha256_HMAC.doFinal( message );
		
		} catch( NoSuchAlgorithmException | InvalidKeyException e ) {
			throw new Error( e );
		}

	}
	
	public static String sign( String message, String key ) {
		
		return HEX.encode( sign( 
				message.getBytes( StandardCharsets.UTF_8 ),
				key.getBytes( StandardCharsets.US_ASCII )
		) );
		
	}
}
