package de.axone.tools;

import static org.testng.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;

import org.testng.annotations.Test;

import de.axone.data.Charsets;
import de.axone.file.Slurper;

@Test( groups="tools.slurper" )
public class SlurperTest {
	
	public void testSlurpArrayAtUntil() throws Exception {
		
		try( TestChannel in = new TestChannel( 10 ); ){
			
			assertEquals( in.size(), 10 );
			assertEquals( in.position(), 0 );
			
			ByteBuffer bOut;
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'0', 3, 3 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 1 );
			assertTrue( bOut.hasArray() );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "0" );
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'1', 3, 3 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 2 );
			assertTrue( bOut.hasArray() );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "01" );
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'2', 3, 3 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 3 );
			assertTrue( bOut.hasArray() );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "012" );
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'3', 3, 3 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 4 );
			assertTrue( bOut.hasArray() );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "0123" );
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'9', 3, 3 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 10 );
			assertTrue( bOut.hasArray() );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "0123456789" );
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'9', 11, 0 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 10 );
			assertTrue( bOut.hasArray() );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "0123456789" );
			
			in.reset();
			bOut = Slurper.slurpAtUntil( in, 0, (byte)'A', 3, 3 );
			assertEquals( bOut.position(), 0 );
			assertEquals( bOut.limit(), 0 );
			assertTrue( bOut.hasArray() );
			assertEquals( bOut.array(), new byte[]{} );
			assertEquals( new String( bOut.array(), bOut.position(), bOut.limit() ), "" );
			
			String s;
			in.reset();
			s = Slurper.slurpStringAtUntil( in, 0, (byte)'1', Charsets.UTF8, 3, 3 );
			assertEquals( s, "01" );
			
			in.reset();
			s = Slurper.slurpStringAtUntil( in, 0, (byte)'7', Charsets.UTF8, 1, 1 );
			assertEquals( s, "01234567" );
		}
		
	}

	public void testSlurpArray() throws Exception {
		
		byte [] bIn = "0123456789".getBytes( "ascii" );
		assertEquals( bIn.length, 10 );
		
		ByteArrayInputStream in = new ByteArrayInputStream( bIn );
		in.mark(0);
		
		byte [] bOut;
		
		bOut = Slurper.slurp( in );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 9 );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 10 );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 11 );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 100 );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 1 );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 1, 1 );
		assertEquals( bOut.length, 10 );
		
		in.reset();
		bOut = Slurper.slurp( in, 0, 0 );
		assertEquals( bOut.length, 10 );
		
		bIn = "".getBytes( "ascii" );
		in = new ByteArrayInputStream( bIn );
		
		bOut = Slurper.slurp( in );
		assertEquals( bOut.length, 0 );
	}

	private static class TestChannel implements ReadableByteChannel, SeekableByteChannel {
			
		int read = 0;
		final int max;
		
		TestChannel( int max ){
			this.max = max;
		}

		@Override
		public int read( ByteBuffer dst ) throws IOException {
			
			if( read == max ) return -1;
			
			int c=0;
			for( c=0; read < max; read++, c++ ){
				if( dst.remaining() > 0 ) dst.put( (byte)('0' + read) );
				else break;
			}
			
			return c;
		}
		
		void reset(){
			read = 0;
		}

		@Override public void close() throws IOException {}
		@Override public boolean isOpen() { return true; }

		@Override
		public long position() throws IOException {
			return read;
		}

		@Override
		public SeekableByteChannel position( long newPosition )
				throws IOException {
			
			if( newPosition >= max )
					throw new IllegalArgumentException( "Position out of bounds: " + newPosition );
					
			read = (int)newPosition;
			
			return this;
		}

		@Override
		public long size() throws IOException {
			return max;
		}

		@Override
		public int write( ByteBuffer src ) throws IOException {
			throw new UnsupportedOperationException();
		}
		@Override
		public SeekableByteChannel truncate( long size ) throws IOException {
			throw new UnsupportedOperationException();
		}
			
	}
	
	private static void assertBuffer( ByteBuffer buf, int capacity, int limit, int position ){
		
		assertEquals( buf.capacity(), capacity );
		assertEquals( buf.limit(), limit );
		assertEquals( buf.position(), position );
	}
	
	public void testSlurpByteBuffer() throws Exception {
		
		ByteBuffer bOut;
			
		try( TestChannel in = new TestChannel( 10 ); ){
		
			bOut = Slurper.slurpToBuffer( in );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 9 );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 10 );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 11 );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 100 );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 1 );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 1, 1 );
			assertBuffer( bOut, 10, 10, 0 );
			
			in.reset();
			bOut = Slurper.slurpToBuffer( in, 0, 0 );
			assertBuffer( bOut, 10, 10, 0 );
		}
		
		try( TestChannel in = new TestChannel( 0 ); ){
		
			bOut = Slurper.slurpToBuffer( in );
			assertBuffer( bOut, 0, 0, 0 );
		}
		
	}

}
