package de.axone.tools;


/**
 * Decodes unchunked urlsave
 * @author flo
 *
 */
public class BASE64Decoder {
	
	public byte [] decode( String buffer ){
		return org.apache.commons.codec.binary.Base64.decodeBase64( buffer );
	}
	
}
