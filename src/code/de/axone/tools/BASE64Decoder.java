package de.axone.tools;

import org.apache.commons.codec.binary.Base64;

public class BASE64Decoder {
	
	public byte [] decodeBuffer( String buffer ){
		Base64 b64 = new Base64();
		return b64.decode( buffer );
	}
	
}
