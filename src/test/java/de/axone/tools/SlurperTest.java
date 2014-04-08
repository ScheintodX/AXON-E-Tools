package de.axone.tools;

import static org.testng.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import org.testng.annotations.Test;

@Test( groups="tools.slurper" )
public class SlurperTest {

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

	private static class TestChannel implements ReadableByteChannel {
			
		int read = 0;
		int max = 10;
		
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
			
	}
	
	private static void assertBuffer( ByteBuffer buf, int capacity, int limit, int position ){
		
		assertEquals( buf.capacity(), capacity );
		assertEquals( buf.limit(), limit );
		assertEquals( buf.position(), position );
	}
	
	public void testSlurpByteBuffer() throws Exception {
		
		TestChannel in = new TestChannel( 10 );
		
		ByteBuffer bOut;
		
		bOut = Slurper.slurp( in );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 9 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 10 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 11 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 100 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 1 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 1, 1 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in.reset();
		bOut = Slurper.slurp( in, 0, 0 );
		assertBuffer( bOut, 10, 10, 0 );
		
		in = new TestChannel( 0 );
		
		bOut = Slurper.slurp( in );
		assertBuffer( bOut, 0, 0, 0 );
	}

}
