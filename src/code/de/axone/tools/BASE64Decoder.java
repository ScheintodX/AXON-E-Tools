package de.axone.tools;



/**
 * Decodes unchunked urlsave
 * @author flo
 *
 * @deprecated
 */
<<<<<<< HEAD
public class BASE64Decoder extends Base64ApacheCommons {}
=======
public class BASE64Decoder {
	
	public byte [] decode( String buffer ){
		return Base64ApacheCommons.Decode( buffer );
	}
	
	public String decodeToString( String buffer ){
		return new String( decode( buffer ), Charsets.UTF8 );
	}
	
}
>>>>>>> 457e3b271681ab21721ed66d420a3797d46c30e7
