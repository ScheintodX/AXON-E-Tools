package de.axone.tools;

import org.apache.commons.codec.binary.Base64;

/**
 * Decodes unchunked urlsave
 * @author flo
 *
 */
public class BASE64Decoder {
	
	public byte [] decodeBuffer( String buffer ){
		//Base64 b64 = new Base64();
		return Base64.decodeBase64( buffer );
	}
	
}
