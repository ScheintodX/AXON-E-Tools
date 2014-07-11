package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Port_Client.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.Accessor;
import de.axone.cache.ng.CacheNGTestHelpers.TestRealm;
import de.axone.cache.ng.CacheNGTest_Port_Client.TestEntry;

@Test( groups="tools.autocache" )
public class CacheNGTest_Port_AutomaticClient {
	
	//private static final Object mutex = new Object();
	
	private static final int NUM_THREADS = 1000;
	private static final int NUM_RUNS = 100000;
	
	private static final String X="x", Y="y", Z="z";
	private static final TestEntry x=new TestEntry( X ), y=new TestEntry( Y ), z=new TestEntry( Z );
	
	private static TestEntry[] testEntries = new TestEntry[]{ a, b, c, null };
	private static TestEntry[] testEntries2 = new TestEntry[]{ x, y, z, null };
	private static String [] testEntryKeys = new String[]{ A, B, C, D };
	private static String [] testEntryKeys2 = new String[]{ X, Y, Z, D };

	private static CacheNG.Client<String,TestEntry> backend =
			new ClientLRUMap<String,TestEntry>( new TestRealm( "AutomaticCacheTest" ), 4);
	
	//private static Cache<String,TestEntry> backend = new CacheHashMap<String,TestEntry>();
	private static TestDataAccessor acc = new TestDataAccessor();
	private static CacheNG.AutomaticClient<String,TestEntry> auto =
			new AutomaticClientImpl<String,TestEntry>( backend );
	
	// Second test for multiple frontend on one backend
	private static CacheNG.AutomaticClient<String,TestEntry> auto2 =
			new AutomaticClientImpl<String,TestEntry>( backend );
	
	//@Test( enabled=false )
	public void testAutomaticCache(){
		
		SecureRandom srand = new SecureRandom();
		
		assertEquals( auto.fetch( A, acc ), a );
		assertEquals( auto.fetch( B, acc ), b );
		assertEquals( auto.fetch( C, acc ), c );
		assertEquals( auto.fetch( A, acc ), a );
		assertEquals( auto.fetch( B, acc ), b );
		assertEquals( auto.fetch( C, acc ), c );
		assertEquals( auto.fetch( A, acc ), a );
		assertEquals( auto.fetch( B, acc ), b );
		assertEquals( auto.fetch( C, acc ), c );
		
		for( int i=0; i<NUM_RUNS; i++ ){
			int r = srand.nextInt( 4 );
			String k = testEntryKeys[r];
			TestEntry e = testEntries[r];
			assertThat( auto ).fetch( k, acc ).isEqualTo( e );
		}
		
		/*
		E.rr( "Auto: " + auto.stats() );
		E.rr( backend.info() );
		E.rr( acc );
		*/
	}
	//@Test( enabled=false )
	public void testAutomaticCacheMulti(){
		
		// Encapsulate so that we don't reverse order of underlying array!
		List<String> keysAsList = new ArrayList<String>( Arrays.asList( testEntryKeys ) );
		Map<String,TestEntry> result;
		
		result = auto.fetch( keysAsList, acc );
		assertThat( result )
				.containsEntry( A, a )
				.containsEntry( B, b )
				.containsEntry( C, c )
				.doesNotContainKey( D )
				;
		
		Collections.reverse( keysAsList );
		
		result = auto.fetch( keysAsList, acc );
		assertThat( result )
				.containsEntry( A, a )
				.containsEntry( B, b )
				.containsEntry( C, c )
				.doesNotContainKey( D )
				;
	}
	
	//@Test( enabled=false )
	public void testThreaded() throws Exception {
		
		TestThread [] threads = new TestThread[NUM_THREADS];
		
		for( int i=0; i<threads.length; i++ ){
			
			threads[i] = new TestThread(i, i%4);
			threads[i].start();
		}
		// Wait for threads to finish
		for( TestThread t : threads ){
			t.join();
		}
		for( TestThread t : threads ){
			assertEquals( t.getState(), Thread.State.TERMINATED );
			assertNull( t.error );
		}
		
		assertEquals( backend.size(), 4, "Test for some rare synchronization problem" );
		
		/*
		E.rr( "Auto: " + auto.stats() );
		E.rr( backend.info() );
		E.rr( acc );
		*/
	}
	
	public void testThreadedMulti() throws Exception {
		
		TestThreadMulti [] threads = new TestThreadMulti[NUM_THREADS];
		
		for( int i=0; i<threads.length; i++ ){
			
			threads[i] = new TestThreadMulti(i);
			threads[i].start();
		}
		// Wait for threads to finish
		for( TestThreadMulti t : threads ){
			t.join();
		}
		for( TestThreadMulti t : threads ){
			assertEquals( t.getState(), Thread.State.TERMINATED );
			assertNull( t.error );
		}
		
		assertEquals( backend.size(), 4, "Test for some rare synchronization problem" );
		
		/*
		E.rr( "Auto: " + auto.stats() );
		E.rr( backend.info() );
		E.rr( acc );
		*/
	}
	
	private static class TestThread extends Thread {
		
		final int no;
		final int index;
		Throwable error;
		TestThread( int no, int index ){
			this.no=no;
			this.index=index;
		}
		@Override
		public void run() {
			
			try {
				for( int i=0; i<NUM_RUNS/NUM_THREADS; i++ ){
					String k = testEntryKeys[index];
					String k2 = testEntryKeys2[index];
					TestEntry e = testEntries[index];
					TestEntry e2 = testEntries2[index];
					assertThat( auto ).fetch( k, acc ).isEqualTo( e );
					assertThat( auto2 ).fetch( k2, acc ).isEqualTo( e2 );
					synchronized( this ){
						try {
							this.wait( 1 );
						} catch( InterruptedException e1 ) {
							e1.printStackTrace();
						}
					}
				}
			} catch( Error t ){
				
				this.error = t;
				throw t;
			}
		}
		@Override public String toString(){
			return "TestThread no. " + no;
		}
	};
	
	private static class TestThreadMulti extends Thread {
		
		final int no;
		final List<String> keysAsList, keysAsList2;
		Throwable error;
		TestThreadMulti( int no ){
			this.no=no;
			keysAsList = new ArrayList<String>( Arrays.asList( testEntryKeys ) );
			keysAsList2 = new ArrayList<String>( Arrays.asList( testEntryKeys2 ) );
			Collections.shuffle( keysAsList );
			Collections.shuffle( keysAsList2 );
		}
		
		@Override
		public void run() {
			
			try {
				for( int i=0; i<NUM_RUNS/NUM_THREADS; i++ ){
					
					Map<String,TestEntry> result, result2;
					
					result = auto.fetch( keysAsList, acc );
					assertThat( result )
							.containsEntry( A, a )
							.containsEntry( B, b )
							.containsEntry( C, c )
							.doesNotContainKey( D );
					
					result2 = auto.fetch( keysAsList2, acc );
					assertThat( result2 )
							.containsEntry( X, x )
							.containsEntry( Y, y )
							.containsEntry( Z, z )
							.doesNotContainKey( D );
					
					synchronized( this ){
						try {
							this.wait( 1 );
							//Thread.sleep( 1 );
						} catch( InterruptedException e1 ) {
							e1.printStackTrace();
						}
					}
				}
			} catch( Error t ){
				this.error = t;
				throw t;
			}
		
		}
		
		@Override public String toString(){
			return "TestThreadMulti no. " + no;
		}
	};
	
	private static final class TestDataAccessor implements Accessor<String,TestEntry> {
		
		private int hitcount;
		private int acccount;

		@Override
		public synchronized TestEntry fetch( String key ) {
			hitcount++;
			acccount++;
			TestEntry result = null;
			if( "a".equals( key ) ) result = a;
			else if( "b".equals( key ) ) result = b;
			else if( "c".equals( key ) ) result = c;
			else if( "x".equals( key ) ) result = x;
			else if( "y".equals( key ) ) result = y;
			else if( "z".equals( key ) ) result = z;
			else hitcount--;
			return result;
		}

		@Override
		public synchronized Map<String, TestEntry> fetch( Collection<String> keys ) {
			Map<String, TestEntry> result = new TreeMap<String, TestEntry>();
			for( String key : keys ){
				result.put( key, fetch( key ) );
			}
			return result;
		}
		
		@Override
		public String toString(){
			
			return "Accessor Hitcount: " + hitcount + "/" + acccount + "=" + ((int)(Math.round( 100.0*hitcount/acccount )) + "%" );
			
		}
	}
	
}
