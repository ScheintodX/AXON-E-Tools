package de.axone.tools;




/**
 * Encoded unchunked urlsave
 * 
 * @author flo
 * 
 * @deprecated
 */
<<<<<<< HEAD
public class BASE64Encoder extends Base64ApacheCommons {}
=======
public class BASE64Encoder {
	
	public String encodeURLSafe( byte[] data ){
		return Base64ApacheCommons.EncodeURLSafe( data );
	}
	
	public String encodeURLSafe( String data ){
		return encodeURLSafe( data.getBytes( Charsets.UTF8 ) );
	}
	
	
	
}
>>>>>>> 457e3b271681ab21721ed66d420a3797d46c30e7
