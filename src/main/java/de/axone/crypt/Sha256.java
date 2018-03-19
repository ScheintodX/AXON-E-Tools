package de.axone.crypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.axone.tools.HEX;

public class Sha256 {

	public static byte [] digest( byte[] data ) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch( NoSuchAlgorithmException e ) {
			throw new Error( e );
		}
		return digest.digest( data );
	}
	
	public static String digestHex( String text ) {
		
		return HEX.encode( digest( text.getBytes( StandardCharsets.UTF_8 ) ) );
	}

}
