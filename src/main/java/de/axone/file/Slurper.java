package de.axone.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.function.Function;

import de.axone.data.Charsets;

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
	
	private static final Charset DEFAULT_CHARSET = Charsets.UTF8;
	
	public static String slurpString( File in ) throws IOException {
		return slurpString( in, DEFAULT_CHARSET );
	}
	public static String slurpString( File in, Charset charset ) throws IOException {
		try( FileInputStream fin = new FileInputStream( in ) ){
			return slurpString( fin, charset );
		}
	}
	public static String slurpString( Path in ) throws IOException {
		try( InputStream fin = Files.newInputStream( in ) ){
			return slurpString( fin );
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
	
	public static String slurpString( InputStream in, Charset charsetName ) throws IOException {
		return new String( slurp( in ), charsetName );
	}
	public static String slurpString( InputStream in, Charset charsetName, int startsize ) throws IOException {
		return new String( slurp( in, startsize ), charsetName );
	}
	public static String slurpString( InputStream in, Charset charsetName, int startsize, int extendsize ) throws IOException {
		return new String( slurp( in, startsize, extendsize ), charsetName );
	}
	
	public static byte[] slurp( File in ) throws IOException {
		try( FileInputStream fin = new FileInputStream( in ) ){
			return slurp( fin );
		}
	}
	public static byte[] slurp( Path in ) throws IOException {
		try( InputStream fin = Files.newInputStream( in ) ){
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
		return toArray( slurpToBuffer( Channels.newChannel( in ), startsize, extendsize ) );
	}
	
	public static char[] slurpChars( InputStream in, Charset cs ) throws IOException {
		return slurpChars( in, DEFAULT_STARTSIZE, DEFAULT_EXTENDSIZE, cs );
	}
	public static char[] slurpChars( InputStream in, int startsize, Charset cs ) throws IOException {
		return slurpChars( in, startsize, DEFAULT_EXTENDSIZE, cs );
	}
	public static char[] slurpChars( InputStream in, int startsize, int extendsize, Charset cs ) throws IOException {
		return toCharArray( slurpToBuffer( Channels.newChannel( in ), startsize, extendsize ), cs );
	}
	
	public static ByteBuffer slurpToBuffer( ReadableByteChannel in ) throws IOException{
		return slurpToBuffer( in, DEFAULT_STARTSIZE, DEFAULT_EXTENDSIZE );
	}
	public static ByteBuffer slurpToBuffer( ReadableByteChannel in, int startsize ) throws IOException{
		return slurpToBuffer( in, startsize, DEFAULT_EXTENDSIZE );
	}
	public static ByteBuffer slurpToBuffer( ReadableByteChannel in, int startsize, int extendsize ) throws IOException{
		
		if( startsize <= 0 ) startsize = DEFAULT_STARTSIZE;
		if( extendsize <= 1 ) extendsize = DEFAULT_EXTENDSIZE;
		
		ByteBuffer buffer = ByteBuffer.allocateDirect( startsize );
		
		int count = in.read( buffer );
		
		if( count == 0 ) return EMPTY_BUFFER;
			
		ByteBuffer bb = ByteBuffer.allocate( 1 );
		int b = in.read( bb );
		
		// there is more in the buffer;
		if( b >= 0 ){
			
			// store one char
			if( b == 1 ){
				bb.flip();
				buffer = addToBuffer( buffer, bb, extendsize+1 );
			}
			
			ByteBuffer extBuffer = ByteBuffer.allocateDirect( extendsize );
			
			while( in.read( extBuffer ) >= 0 ){
				
				extBuffer.flip();
				buffer = addToBuffer( buffer, extBuffer, extendsize );
				extBuffer.clear();
			}
		}
		
		return trim( buffer );
	}
	
	public static String slurpStringAtUntil( SeekableByteChannel in, long pos, byte until, Charset encoding, int startsize, int extendsize ) throws IOException { 
		
		ByteBuffer buf = slurpAtUntil( in, pos, until, startsize, extendsize );
		
		return new String( buf.array(), buf.position(), buf.limit(), encoding );
	}
	
	// NOTE: We don't use allocateDirect because we need .array() method in result and
	// benchmarks show no performance benefit from using it.
	public static ByteBuffer slurpAtUntil( SeekableByteChannel in, long pos, byte until, int startsize, int extendsize ) throws IOException {
		
		if( startsize <= 0 ) startsize = DEFAULT_STARTSIZE;
		if( extendsize <= 1 ) extendsize = DEFAULT_EXTENDSIZE;
		
		in.position( pos );
		
		ByteBuffer buffer = ByteBuffer.allocate( startsize );
		
		int count = in.read( buffer );
		
		if( count == 0 ) return EMPTY_BUFFER;
		
		int index = findInBufferZeroToLimit( buffer, until );
		
		// Not found read further
		if( index < 0 ) {
			
			ByteBuffer extBuffer = ByteBuffer.allocate( extendsize );
			 
			while( in.read( extBuffer ) >= 0 ){
	
				extBuffer.flip();
				 
				index = findInBufferZeroToLimit( extBuffer, until );
				 
				if( index >= 0 ) {
					
					ByteBuffer result = ByteBuffer.allocate( buffer.capacity() + index + 1 );
					buffer.flip();
					result.put( buffer );
					
					extBuffer.limit( index+1 );
					result.put( extBuffer );
					
					result.flip();
					return result;
					 
				} else {
					
					ByteBuffer plus = ByteBuffer.allocate( buffer.capacity() + extendsize );
					buffer.flip();
					plus.put( buffer );
					plus.put( extBuffer );
					buffer = plus;
				}
				 
				extBuffer.clear();
			}
			 
			return EMPTY_BUFFER;
			
		} else {
			
			buffer.flip();
			buffer.limit( index+1 );
			
			return buffer;
		}
	}
	
	// Copy to arrayable buffer if wanted
	/*
	private static ByteBuffer arrayableEventually( ByteBuffer buffer, boolean doit ) {
		
		if( !doit || buffer.hasArray() ) return buffer;
		
		ByteBuffer result = ByteBuffer.allocate( buffer.remaining() );
		
		result.put( buffer );
		result.flip();
		
		return result;
	}
	*/
	private static byte [] toArray( ByteBuffer buffer ) {
		
		if( buffer.hasArray() ){
			return buffer.array();
		} else {
			byte [] r = new byte[ buffer.remaining() ];
			buffer.get( r );
			return r;
		}
	}
	private static char [] toCharArray( ByteBuffer buffer, Charset cs ) {
		
		CharBuffer cb = cs.decode( buffer );
		
		if( cb.hasArray() ){
			return cb.array();
		} else {
			char [] r = new char[ cb.remaining() ];
			cb.get( r );
			return r;
		}
	}
	
	private static int findInBufferZeroToLimit( ByteBuffer haystack, byte needle ) {
		
		int i;
		for( i = 0; i < haystack.limit(); i++ ) {
			
			byte b = haystack.get( i );
			if( b == needle ) break;
		}
		if( i == haystack.limit() ) return -1;
		return i;
	}
	
	/*
	public static ByteBuffer slurpAt( RandomAccessFile in, long pos, int len ) throws IOException{
		
		ByteBuffer buffer = ByteBuffer.allocate( len );
		
		return slurpAt( buffer, in, pos );
		
	}
	public static ByteBuffer slurpAt( ByteBuffer buffer, RandomAccessFile in, long pos ) throws IOException {
		
		FileChannel ch = in.getChannel();
		ch.position( pos );
		
		int count = ch.read( buffer );
		
		if( count == -1 )
				throw new IOException( "Cannot read" );
		
		buffer.flip();
		
		return buffer;
	}
	*/
	
	/**
	 * Add 'bytes' to 'buffer'
	 * 
	 * If there is not enough space left a new Buffer is allocated
	 * 
	 * @param buffer where to add at position
	 * @param add where to add from position to limit
	 * @param extendsize
	 * @return the new buffer
	 */
	public static ByteBuffer addToBuffer( ByteBuffer buffer, ByteBuffer add, int extendsize ){
		
		int bLen = buffer.limit();
		int remaining = buffer.remaining();
		
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
	
	public static ByteBuffer trim( ByteBuffer buffer ){
		
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
		
		burp( outS.toPath(), buffer );
		
	}
	
	public static void burp( Path outS, ByteBuffer buffer ) throws IOException {
	
		try( WritableByteChannel ch = FileChannel.open( outS, StandardOpenOption.CREATE, StandardOpenOption.WRITE ) ){
		
			ch.write( buffer );
		}
	}
	
	public static void burp( Path outS, String value ) throws IOException {
		
		byte [] bytes = value.getBytes( "utf-8" );
		ByteBuffer buffer = ByteBuffer.wrap( bytes );
		
		burp( outS, buffer );
	}
	
	public static void print( File file ){
		
		try {
			System.out.println( slurpString( file ) );
		} catch( IOException e ){
			throw new IOError( e );
		}
	}
	
	public static <T> int burp( Path outS, Iterable<T> dataP, Function<T, byte[]> mapper ) throws IOException{
		
		try( WritableByteChannel ch = FileChannel.open( outS, StandardOpenOption.CREATE, StandardOpenOption.WRITE ) ){
			
			Iterator<T> it = dataP.iterator();
			
			int c = 0;
			while( it.hasNext() ) {
				
				ByteBuffer buf = ByteBuffer.wrap( mapper.apply( it.next() ) );
				
				c += buf.remaining();
				while( buf.remaining() > 0 ) {
					ch.write( buf );
				}
			}
			return c;
		}
	}
	
	public static int burp( Path outS, Iterable<byte[]> dataP ) throws IOException{
		
		return burp( outS, dataP, Function.identity() );
	}
	
}
