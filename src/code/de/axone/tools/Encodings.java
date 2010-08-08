package de.axone.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Encodings {
	
	public static boolean isWrongEncodedUtf8( String string ){
		
		boolean ok = true;
		// Ignore last char because it wouldn't start an utf-8 string
		for( int i = 0; i < string.length()-1; i++ ){
			
			int c = string.charAt( i );
			int c1 = string.charAt( i+1 );
			// Known pattern for utf-8 two byte string
			if( ((c & 0xE0) == 0xC0) && ((c1 & 0xC0) == 0x80 ) ){
				ok = false;
				break;
			}
		}
		
		return !ok;
	}
	
	/**
	 * Take one string wich my be wrongly encoded and convert
	 * it to an usable String.
	 * 
	 * @param string
	 * @return the converted string
	 * @throws IOException 
	 */
	public static String convertToUsableString( String string ) throws IOException{
		
		
		if( isWrongEncodedUtf8( string ) ){
			
			byte[] buffer = new byte[ string.length() ];
			for( int i = 0; i < string.length(); i++ ){
				buffer[ i ] = (byte)string.charAt( i );
			}
			
			ByteArrayInputStream bIn = new ByteArrayInputStream( buffer );
			Reader rIn = new InputStreamReader( bIn, "utf-8" );
			
			char [] cBuf = new char[ string.length() ];
			int l = rIn.read( cBuf );
			
			//throw new IllegalArgumentException( "result would be larger then 
			
			return new String( cBuf, 0, l );
			
		} else {
			return string;
		}
	}
}
