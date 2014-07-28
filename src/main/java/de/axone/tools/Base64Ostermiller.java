package de.axone.tools;

public class Base64Ostermiller extends Base64 {

	/* Static Methods */
	public static byte [] Decode( String data ){
		return com.Ostermiller.util.Base64.decodeToBytes( data );
	}
	
	public static String Encode( byte[] data ){
		return com.Ostermiller.util.Base64.encodeToString( data );
	}
	
	public static String EncodeURLSafe( byte[] data ){
		throw new UnsupportedOperationException( "Cannot encode URL-Safe" );
	}
	
	/* Same as intance methods */
	@Override
	public byte [] decode( String buffer ){
		return Decode( buffer );
	}
	
	@Override
	public String encode( byte[] data ){
		return Encode( data );
	}

	@Override
	public String encodeURLSafe( byte[] data ) {
		return EncodeURLSafe( data );
	}

}
