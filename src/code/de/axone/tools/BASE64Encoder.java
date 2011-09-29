package de.axone.tools;

import org.apache.commons.codec.binary.Base64;

/**
 * Encoded unchunked urlsave
 * 
 * @author flo
 */
public class BASE64Encoder {
	
	public String encode( byte[] data ){
		//Base64 b64 = new Base64();
		return Base64.encodeBase64URLSafeString( data );
	}
	
}
