package de.axone.cache;

import static de.axone.cache.CacheTest.*;
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

import de.axone.cache.CacheTest.TestEntry;

@Test( groups="tools.autocache" )
public class AutomaticCacheTest {
	
	//private static final Object mutex = new Object();
	
	private static final int NUM_THREADS = 1000;
	private static final int NUM_RUNS = 100000;
	
	private static TestEntry[] testEntries = new TestEntry[]{ a, b, c, null };
	private static String [] testEntryKeys = new String[]{ A, B, C, D };

	private static Cache<String,TestEntry> backend = new CacheLRUMap<String,TestEntry>(2);
	private static TestDataAccessor acc = new TestDataAccessor();
	private static AutomaticCache<String,TestEntry> auto = new AutomaticCacheImpl<String,TestEntry>( backend );
	
	//@Test( enabled=false )
	public void testAutomaticCache(){
		
		SecureRandom srand = new SecureRandom();
		
		assertEquals( auto.get( A, acc ), a );
		assertEquals( auto.get( B, acc ), b );
		assertEquals( auto.get( C, acc ), c );
		assertEquals( auto.get( A, acc ), a );
		assertEquals( auto.get( B, acc ), b );
		assertEquals( auto.get( C, acc ), c );
		assertEquals( auto.get( A, acc ), a );
		assertEquals( auto.get( B, acc ), b );
		assertEquals( auto.get( C, acc ), c );
		
		for( int i=0; i<NUM_RUNS; i++ ){
			int r = srand.nextInt( 4 );
			String k = testEntryKeys[r];
			TestEntry e = testEntries[r];
			assertEquals( auto.get( k, acc ), e );
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
		
		result = auto.get( keysAsList, acc );
		assertEquals( result.get( A ), a );
		assertEquals( result.get( B ), b );
		assertEquals( result.get( C ), c );
		assertEquals( result.get( D ), null );
		assertFalse( result.containsKey( D ) );
		
		Collections.reverse( keysAsList );
		
		result = auto.get( keysAsList, acc );
		assertEquals( result.get( A ), a );
		assertEquals( result.get( B ), b );
		assertEquals( result.get( C ), c );
		assertEquals( result.get( D ), null );
		assertFalse( result.containsKey( D ) );
	}
	
	//@Test( enabled=false )
	public void testThreaded() throws Exception {
		
		Thread [] threads = new Thread[NUM_THREADS];
		
		for( int i=0; i<threads.length; i++ ){
			
			threads[i] = new TestThread(i, i%4);
			threads[i].start();
		}
		// Wait for threads to finish
		for( Thread t : threads ){
			t.join();
		}
		for( Thread t : threads ){
			assertEquals( t.getState(), Thread.State.TERMINATED );
		}
		
		assertEquals( backend.size(), 2, "Test for some rare synchronization problem" );
		
		/*
		E.rr( "Auto: " + auto.stats() );
		E.rr( backend.info() );
		E.rr( acc );
		*/
	}
	
	public void testThreadedMulti() throws Exception {
		
		Thread [] threads = new Thread[NUM_THREADS];
		
		for( int i=0; i<threads.length; i++ ){
			
			threads[i] = new TestThreadMulti(i);
			threads[i].start();
		}
		// Wait for threads to finish
		for( Thread t : threads ){
			t.join();
		}
		for( Thread t : threads ){
			assertEquals( t.getState(), Thread.State.TERMINATED );
		}
		
		assertEquals( backend.size(), 2, "Test for some rare synchronization problem" );
		
		/*
		E.rr( "Auto: " + auto.stats() );
		E.rr( backend.info() );
		E.rr( acc );
		*/
	}
	
	private static class TestThread extends Thread {
		
		final int no;
		final int x;
		TestThread( int no, int x ){
			this.no=no;
			this.x=x;
		}
		@Override
		public void run() {
			
			for( int i=0; i<NUM_RUNS/NUM_THREADS; i++ ){
				String k = testEntryKeys[x];
				TestEntry e = testEntries[x];
				assertEquals( auto.get( k, acc ), e );
				//System.err.printf( "% 4d:% 6d:%s\n", no, i, e );
				synchronized( this ){
					try {
						Thread.sleep( 1 );
					} catch( InterruptedException e1 ) {
						e1.printStackTrace();
					}
				}
			}
		}
		@Override public String toString(){
			return "TestThread no. " + no;
		}
	};
	
	private static class TestThreadMulti extends Thread {
		
		final int no;
		final List<String> keysAsList;
		TestThreadMulti( int no ){
			this.no=no;
			keysAsList = new ArrayList<String>( Arrays.asList( testEntryKeys ) );
			Collections.shuffle( keysAsList );
		}
		//static volatile int cnt=0;
		@Override
		public void run() {
			
			for( int i=0; i<NUM_RUNS/NUM_THREADS; i++ ){
				
				Map<String,TestEntry> result;
				
				result = auto.get( keysAsList, acc );
				assertEquals( result.get( A ), a );
				assertEquals( result.get( B ), b );
				assertEquals( result.get( C ), c );
				assertEquals( result.get( D ), null );
				assertFalse( result.containsKey( D ) );
				
				synchronized( this ){
					try {
						Thread.sleep( 1 );
					} catch( InterruptedException e1 ) {
						e1.printStackTrace();
					}
				}
			}
		
		}
		
		@Override public String toString(){
			return "TestThreadMulti no. " + no;
		}
	};
	
	private static final class TestDataAccessor implements DataAccessor<String,TestEntry> {
		
		private int hitcount;
		private int acccount;

		@Override
		public synchronized TestEntry get( String key ) {
			hitcount++;
			acccount++;
			TestEntry result = null;
			if( "a".equals( key ) ) result = a;
			else if( "b".equals( key ) ) result = b;
			else if( "c".equals( key ) ) result = c;
			else hitcount--;
			return result;
		}

		@Override
		public synchronized Map<String, TestEntry> get( Collection<String> keys ) {
			Map<String, TestEntry> result = new TreeMap<String, TestEntry>();
			for( String key : keys ){
				result.put( key, get( key ) );
			}
			return result;
		}
		
		@Override
		public String toString(){
			
			return "Accessor Hitcount: " + hitcount + "/" + acccount + "=" + ((int)(Math.round( 100.0*hitcount/acccount )) + "%" );
			
		}
	}
}
