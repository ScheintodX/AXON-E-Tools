package de.axone.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.StandardOpenOption;

/**
 * Slurps data from inputs until all data is read.
 * 
 * Will return byte arrays or ByteBuffers of exactly the size of the
 * available data. 
 * 
 * Don't use on unlimited sources (like /dev/random, /dev/null) because
 * it will create OutOfMemory errors.
 * 
 * This methods don't close the source. So encapsulate in try/finallyin
 * and do it manually by yourself.
 * 
 * TODO: Die Sache mit den Buffern lässt sich bestimmt weiter optimieren
 * und kürzen.
 * Und das Naminb vereinheitlichen. 
 * 
 * @author flo
 */
public class Slurper {
	
	private static final int DEFAULT_STARTSIZE = 4096,
	                         DEFAULT_EXTENDSIZE = 4096,
	                         DEFAULT_COPY_BUFFER = 4096;
	
	//private static final byte [] EMPTY_ARRAY = new byte[]{};
	private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
	
	private static final String DEFAULT_CHARSET = "utf8";
	
	public static String slurpString( File in ) throws IOException {
		return slurpString( in, DEFAULT_CHARSET );
	}
	public static String slurpString( File in, String charset ) throws IOException {
		try( FileInputStream fin = new FileInputStream( in ) ){
			return slurpString( fin, charset );
		}
	}
	public static String slurpString( InputStream in ) throws IOException {
		return slurpString( in, DEFAULT_CHARSET );
	}
	public static String slurpString( InputStream in, int startsize ) throws IOException {
		return slurpString( in, DEFAULT_CHARSET, startsize );
	}
	public static String slurpString( InputStream in, int startsize, int extendsize ) throws IOException {
		return slurpString( in, DEFAULT_CHARSET, startsize, extendsize );
	}
	
	public static String slurpString( InputStream in, String charsetName ) throws IOException {
		return new String( slurp( in ), charsetName );
	}
	public static String slurpString( InputStream in, String charsetName, int startsize ) throws IOException {
		return new String( slurp( in, startsize ), charsetName );
	}
	public static String slurpString( InputStream in, String charsetName, int startsize, int extendsize ) throws IOException {
		return new String( slurp( in, startsize, extendsize ), charsetName );
	}
	
	/*
	public static ByteBuffer slurpInBuffer( File in ) throws IOException {
		
		try( FileChannel fch = FileChannel.open( in.toPath(), StandardOpenOption.READ ) ){
			
			return slurp( fch );
		}
	}
	*/
	
	public static byte[] slurp( File in ) throws IOException {
		try( FileInputStream fin = new FileInputStream( in ) ){
			return slurp( fin );
		}
	}
	public static byte[] slurp( InputStream in ) throws IOException {
		return slurp( in, DEFAULT_STARTSIZE, DEFAULT_EXTENDSIZE );
	}
	public static byte[] slurp( InputStream in, int startsize ) throws IOException {
		return slurp( in, startsize, DEFAULT_EXTENDSIZE );
	}
	public static byte[] slurp( InputStream in, int startsize, int extendsize ) throws IOException {
		
		ByteBuffer result = slurp( Channels.newChannel( in ), startsize, extendsize );
		if( result.hasArray() ){
			return result.array();
		} else {
			byte [] r = new byte[ result.remaining() ];
			result.get( r );
			return r;
		}
	}
		
	/*
	public static byte[] slurp( InputStream in, int startsize, int extendsize ) throws IOException{
		
		if( startsize <= 0 ) startsize = DEFAULT_STARTSIZE;
		if( extendsize <= 1 ) extendsize = DEFAULT_EXTENDSIZE;
		
		byte [] buffer = new byte[ startsize ];
		
		int count = in.read( buffer );
		
		if( count >= 0 ){
			
			int b = in.read();
			
			// there is more in the buffer;
			if( b >= 0 ){
				
				// store one char
				buffer = addToBuffer( buffer, count, new byte[]{ (byte)b }, 1, extendsize+1 );
				count++;
				
				byte [] readBuffer = new byte[ extendsize ];
				
				int c;
				while( ( c = in.read( readBuffer ) ) >= 0 ){
					
					buffer = addToBuffer( buffer, count, readBuffer, c, extendsize );
					
					count += c;
				}
			}
			
			return trim( buffer, count );
		} else {
			return EMPTY_ARRAY;
		}
	}
	*/
	
	public static ByteBuffer slurp( ReadableByteChannel in ) throws IOException{
		return slurp( in, DEFAULT_STARTSIZE, DEFAULT_EXTENDSIZE );
	}
	public static ByteBuffer slurp( ReadableByteChannel in, int startsize ) throws IOException{
		return slurp( in, startsize, DEFAULT_EXTENDSIZE );
	}
	public static ByteBuffer slurp( ReadableByteChannel in, int startsize, int extendsize ) throws IOException{
		
		if( startsize <= 0 ) startsize = DEFAULT_STARTSIZE;
		if( extendsize <= 1 ) extendsize = DEFAULT_EXTENDSIZE;
		
		ByteBuffer buffer = ByteBuffer.allocateDirect( startsize );
		
		int count = in.read( buffer );
		
		if( count >= 0 ){
			
			ByteBuffer bb = ByteBuffer.allocate( 1 );
			int b = in.read( bb );
			
			// there is more in the buffer;
			if( b >= 0 ){
				
				// store one char
				if( b == 1 ){
					bb.flip();
					buffer = addToBuffer( buffer, bb, extendsize+1 );
				}
				
				ByteBuffer readBuffer = ByteBuffer.allocateDirect( extendsize );
				
				while( in.read( readBuffer ) >= 0 ){
					
					readBuffer.flip();
					buffer = addToBuffer( buffer, readBuffer, extendsize );
					readBuffer.clear();
				}
			}
			
			return trim( buffer );
		} else {
			return EMPTY_BUFFER;
		}
	}
	
	/*
	private static byte [] addToBuffer( byte [] buffer, int pos, byte [] add, int len, int extendsize ){
		
		int bLen = buffer.length;
		int remaining = bLen-pos;
		
		if( len > remaining ){
			
			int missing = len-remaining;
			missing = missing > extendsize ? missing : extendsize;
			byte [] newBuffer = new byte[ bLen + missing ];
			System.arraycopy( buffer, 0, newBuffer, 0, buffer.length );
			buffer = newBuffer;
		}
		System.arraycopy( add, 0, buffer, pos, len );
		return buffer;
	}
	
	private static byte [] trim( byte [] buffer, int size ){
		
		if( buffer.length <= size ) return buffer;
		
		byte [] result =  Arrays.copyOf( buffer, size );
		
		return result;
	}
	*/
	
	/**
	 * Add 'add' to 'buffer'
	 * 
	 * @param buffer where to add at position
	 * @param add where to add from position to limit
	 * @param extendsize
	 * @return
	 */
	private static ByteBuffer addToBuffer( ByteBuffer buffer, ByteBuffer add, int extendsize ){
		
		int pos = buffer.position();
		int bLen = buffer.limit();
		int remaining = bLen-pos;
		
		int len = add.remaining();
		
		if( len > remaining ){
			
			int missing = len-remaining;
			missing = missing > extendsize ? missing : extendsize;
			ByteBuffer newBuffer = ByteBuffer.allocateDirect( bLen+missing );
			buffer.flip();
			newBuffer.put( buffer );
			buffer = newBuffer;
		}
		buffer.put( add );
		return buffer;
	}
	
	private static ByteBuffer trim( ByteBuffer buffer ){
		
		if( buffer.capacity() != buffer.position() ){
		
			ByteBuffer result = ByteBuffer.allocateDirect( buffer.position() );
			
			buffer.flip();
			
			result.put( buffer );
			
			buffer = result;
		}
		
		buffer.rewind();
		
		return buffer;
	}
	
	public static void copy( OutputStream outS, InputStream inS ) throws IOException{
		
		try( 
			WritableByteChannel dst = Channels.newChannel( outS );
			ReadableByteChannel src = Channels.newChannel( inS );
		){
		
			ByteBuffer buffer = ByteBuffer.allocateDirect( DEFAULT_COPY_BUFFER );
			
			while( ( src.read( buffer ) ) > -1 ){
				
				buffer.flip();
				
				dst.write( buffer );
				
				buffer.compact();
			}
			
			buffer.flip();
			
			while( buffer.hasRemaining() ){
				
				dst.write( buffer );
			}
		}
	}
	
	public static void burp( OutputStream outS, byte [] data ) throws IOException {
		
		outS.write( data, 0, data.length );
		outS.flush();
	}
	
	public static void burp( File outS, ByteBuffer buffer ) throws IOException {
		
		try( WritableByteChannel ch = FileChannel.open( outS.toPath(), StandardOpenOption.WRITE ) ){
		
			ch.write( buffer );
		}
		
	}
}
