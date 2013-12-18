package de.axone.tools;


public class Base64JavaxDatabind extends Base64 {
	
	/* Static Methods */
	public static byte [] Decode( String lexicalXSDBase64Binary ){
		return javax.xml.bind.DatatypeConverter.parseBase64Binary( lexicalXSDBase64Binary );
	}
	
	public static String Encode( byte[] data ){
		return javax.xml.bind.DatatypeConverter.printBase64Binary( data );
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
