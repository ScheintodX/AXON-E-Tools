package de.axone.web;

import java.util.zip.CRC32;

public abstract class Header {

	public static String makeETag( byte[] data ) {
		CRC32 crc32 = new CRC32();
		crc32.update( data, 0, data.length );

		String eTag = Long.toHexString( crc32.getValue() );

		return "\"" + eTag + "\"";
	}
}
