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
	
	private static final String X="x", Y="y", Z="z";
	private static final TestEntry x=new TestEntry( X ), y=new TestEntry( Y ), z=new TestEntry( Z );
	
	private static TestEntry[] testEntries = new TestEntry[]{ a, b, c, null };
	private static TestEntry[] testEntries2 = new TestEntry[]{ x, y, z, null };
	private static String [] testEntryKeys = new String[]{ A, B, C, D };
	private static String [] testEntryKeys2 = new String[]{ X, Y, Z, D };

	private static Cache<String,TestEntry> backend = new CacheLRUMap<String,TestEntry>("AutomaticCacheTest", 4);
	//private static Cache<String,TestEntry> backend = new CacheHashMap<String,TestEntry>();
	private static TestDataAccessor acc = new TestDataAccessor();
	private static AutomaticCache<String,TestEntry> auto = new AutomaticCacheImpl<String,TestEntry>( backend );
	
	// Second test for multiple frontend on one backend
	private static AutomaticCache<String,TestEntry> auto2 = new AutomaticCacheImpl<String,TestEntry>( backend );
	
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
					assertEquals( auto.get( k, acc ), e );
					assertEquals( auto2.get( k2, acc ), e2 );
					//System.err.printf( "% 4d:% 6d:%s\n", no, i, e );
					synchronized( this ){
						try {
							//Thread.sleep( 1 );
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
					
					result = auto.get( keysAsList, acc );
					assertEquals( result.get( A ), a );
					assertEquals( result.get( B ), b );
					assertEquals( result.get( C ), c );
					assertEquals( result.get( D ), null );
					assertFalse( result.containsKey( D ) );
					
					result2 = auto.get( keysAsList2, acc );
					assertEquals( result2.get( X ), x );
					assertEquals( result2.get( Y ), y );
					assertEquals( result2.get( Z ), z );
					assertEquals( result2.get( D ), null );
					assertFalse( result2.containsKey( D ) );
					
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
			else if( "x".equals( key ) ) result = x;
			else if( "y".equals( key ) ) result = y;
			else if( "z".equals( key ) ) result = z;
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
	
	static final class TestEntry2 {
		final String name;
		TestEntry2( String name ){ this.name = name; }
		@Override public String toString(){ return name; }
	}
}
