package de.axone.tools;

import de.axone.data.Charsets;


/**
 * Decodes unchunked urlsave
 * @author flo
 *
 */
public class BASE64Decoder {
	
	public byte [] decode( String buffer ){
		return Base64ApacheCommons.Decode( buffer );
	}
	
	public String decodeToString( String buffer ){
		return new String( decode( buffer ), Charsets.UTF8 );
	}
	
}
