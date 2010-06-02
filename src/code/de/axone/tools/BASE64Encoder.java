package de.axone.tools;

import org.apache.commons.codec.binary.Base64;

public class BASE64Encoder {
	
	public String encode( byte[] data ){
		Base64 b64 = new Base64();
		return b64.encodeToString( data );
	}
	
}
