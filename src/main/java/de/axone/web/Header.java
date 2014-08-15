package de.axone.web;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public abstract class Header {

	public static String makeETag( byte[] data ) {
		CRC32 crc32 = new CRC32();
		crc32.update( data, 0, data.length );

		String eTag = Long.toHexString( crc32.getValue() );

		return "\"" + eTag + "\"";
	}
	
	public static String makeETag( ByteBuffer data ){
		
		byte [] x = new byte[ data.remaining() ];
		
		data.get( x );
		
		return makeETag( x );
	}
}
