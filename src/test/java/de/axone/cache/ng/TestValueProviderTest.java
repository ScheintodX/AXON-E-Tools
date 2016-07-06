package de.axone.cache.ng;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.testng.annotations.Test;

import de.axone.cache.ng.TestValueProvider.Range;

@Test( groups="testng.testvalueprovider" )
public class TestValueProviderTest {
	
	private static final int NUM_THREADS = 1000;

	public void testValueProvider() {
		
		TestValueProvider<Integer,String> t1 = new TestValueProvider<Integer,String>( k -> "value_" + k );
		
		assertEquals( t1.get( 0 ), "value_0" );
		assertEquals( t1.get( 10 ), "value_10" );
		assertEquals( t1.get( 11 ), "value_11" );
		
		
		TestValueProvider<Integer,String> t2 = t1.ranged( Range.of( 0, 10 ) );
		
		assertEquals( t2.get( 0 ), "value_0" );
		assertEquals( t2.get( 10 ), "value_10" );
		assertNull( t2.get( 11 ) );
		
		
		TestValueProvider<Integer,String> t3 = t2.delayed(  10  );
		
		long start = System.currentTimeMillis();
		
		assertEquals( t3.get( 0 ), "value_0" );
		assertEquals( t3.get( 10 ), "value_10" );
		assertNull( t3.get( 11 ) );
		
		long end = System.currentTimeMillis();
		
		assertTrue( (end-start) >= 20, "Takes some Time: " + (end-start) + " >= 20" );
		
	}
	
	public void testMultithreaded() throws Exception {
		
		TestValueProvider<Integer,String> t1 = new TestValueProvider<Integer,String>( k -> "value_" + k )
				.threadsafe( false );
		
		List<Thread> threads = new ArrayList<>( NUM_THREADS );
		
		for( int i = 0; i < NUM_THREADS; i++ ) {
			
			threads.add( new Thread( new ValueChecker( t1, ConcurrentModificationException.class, i ) ) );
		}
		
		for( Thread t : threads ) t.start();
			
		for( Thread t : threads ) t.join();
			
	}
	
	static class ValueChecker implements Runnable {
		
		final int i;
		final TestValueProvider<Integer,String> tvp;
		final Class<? extends Throwable> expectException;
		
		ValueChecker( TestValueProvider<Integer,String> tvp, Class<? extends Throwable> expectException, int i ) {
			this.tvp = tvp;
			this.i = i;
			this.expectException = expectException;
		}
		
		@Override
		public void run() {
			
			if( expectException != null ) {
				
				try {
					assertEquals( tvp.get( i ), "value_" + i );
				} catch( Throwable t ) {
					assertEquals( t.getClass(), expectException );
				}
				
			} else {
				assertEquals( tvp.get( i ), "value_" + i );
			}
			
		}
	}
	
}
