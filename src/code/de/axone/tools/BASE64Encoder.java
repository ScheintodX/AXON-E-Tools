package de.axone.tools;

import de.axone.data.Charsets;



/**
 * Encoded unchunked urlsave
 * 
 * @author flo
 */
public class BASE64Encoder {
	
	public String encodeURLSafe( byte[] data ){
		return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( data );
	}
	
	public String encodeURLSafe( String data ){
		return encodeURLSafe( data.getBytes( Charsets.UTF8 ) );
	}
	
	
	
}
