package de.axone.exception.codify;

import java.nio.charset.Charset;

import de.axone.external.SipHash_2_4;

public class Hash {
	
	private static final Charset UTF8 = Charset.forName( "utf-8" );
	
	//private static final long k0=0x1234567890abcdefL;
	//private static final long k1=0xfedcba0987654321L;
	private static final byte [] k = new byte [] {
		(byte)0x01,
		(byte)0x23,
		(byte)0x45,
		(byte)0x67,
		(byte)0x89,
		(byte)0xab,
		(byte)0xcd,
		(byte)0xef,
		(byte)0x01,
		(byte)0x23,
		(byte)0x45,
		(byte)0x67,
		(byte)0x89,
		(byte)0xab,
		(byte)0xcd,
		(byte)0xef
	};

	public static int hash( int data ){
		return data;
	}
	public static int hash( long data ){
		return (int)(data&0xffffffff);
	}
	public static int hash( Integer data ){
		if( data == null ) return 0;
		return hash( data.intValue() );
	}
	public static int hash( Long data ){
		if( data == null ) return 0;
		return hash( data.longValue() );
	}
	public static int hash( String data ){
		
		SipHash_2_4 hash = new SipHash_2_4();
		
		if( data == null ) return 0;
		//return (int)(SipHashInline.hash24( k0, k1, data.getBytes( UTF8 ) )&0xffffffff);
		return (int) hash.hash( k, data.getBytes( UTF8 ) );
	}
	/*
	private static int hash( Object data ){
		
		if( data == null ) return 0;
		return hash( data.toString() );
	}
	*/
}
