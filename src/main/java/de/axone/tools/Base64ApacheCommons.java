package de.axone.tools;

public class Base64ApacheCommons extends Base64 {
	
	/* Static Methods */
	public static byte [] Decode( String buffer ){
		return org.apache.commons.codec.binary.Base64.decodeBase64( buffer );
	}
	
	public static String Encode( byte[] data ){
		return org.apache.commons.codec.binary.Base64.encodeBase64String( data );
	}
	
	public static String EncodeURLSafe( byte[] data ){
		return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( data );
	}
	
	/* Same as intance methods */
	@Override
	public byte [] decode( String buffer ){
		return Decode( buffer );
	}
	
	@Override
	public String encodeURLSafe( byte[] data ){
		return Encode( data );
	}

	@Override
	public String encode( byte[] data ) {
		return EncodeURLSafe( data );
	}
	
}
