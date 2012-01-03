package de.axone.tools;


/**
 * Encoded unchunked urlsave
 * 
 * @author flo
 */
public class BASE64Encoder {
	
	public String encode( byte[] data ){
		//return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( data );
		return org.apache.commons.codec.binary.Base64.encodeBase64String( data );
	}
	
}
