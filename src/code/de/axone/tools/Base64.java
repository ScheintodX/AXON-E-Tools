package de.axone.tools;

import de.axone.data.Charsets;

public abstract class Base64 {
	
	/* Same as intance methods */
	public abstract byte [] decode( String buffer );
	
	public String decodeToString( String buffer ){
		return new String( decode( buffer ), Charsets.UTF8 );
	}
	
	public abstract String encode( byte[] data );
	
	public String encode( String data ){
		return encode( data.getBytes( Charsets.UTF8 ) );
	}
	
	public abstract String encodeURLSafe( byte[] data );
	
	public String encodeURLSafe( String data ){
		return encodeURLSafe( data.getBytes( Charsets.UTF8 ) );
	}
	
}
