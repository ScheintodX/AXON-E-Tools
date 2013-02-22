package de.axone.tools;

import de.axone.data.Charsets;


/**
 * Decodes unchunked urlsave
 * @author flo
 *
 */
public class BASE64Decoder {
	
	public byte [] decode( String buffer ){
		return org.apache.commons.codec.binary.Base64.decodeBase64( buffer );
	}
	
	public String decodeToString( String buffer ){
		return new String( decode( buffer ), Charsets.UTF8 );
	}
	
}
