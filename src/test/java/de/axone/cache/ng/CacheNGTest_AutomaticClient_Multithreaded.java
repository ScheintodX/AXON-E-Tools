package de.axone.cache.ng;

import static org.testng.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.AutomaticClient;
import de.axone.cache.ng.CacheNG.UniversalAccessor;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.tools.E;
import de.axone.tools.Mapper;

@SuppressWarnings( "unused" )
@Test( groups="cacheng.multithreaded" )
public class CacheNGTest_AutomaticClient_Multithreaded {
	
	private static final int NUM_THREADS = 100,
	                         NUM_ENTRIES = 100, // Must be multiple of 2
	                         THREAD_DELAY_MS = 10
	                         ;

	public void testAutomaticClientParallelSingleOneByOneAccessor() throws InterruptedException {
		
		//final AtomicInteger index = new AtomicInteger( 0 );
		final DelayedIndexProvider index = new DelayedIndexProvider();
		
		TestAccessor_Single accessor =
				new TestAccessor_Single( index::getAndIncrement );
		
		CacheNG.AutomaticClient<String,String> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.S_S, false ) );
		
		
		List<Thread> ts = new LinkedList<>();
		
		for( int i=0; i<NUM_THREADS; i++ ){
			
			Thread t = new Thread( new FetchRunnerOneByOne( accessor, auto ), "T-" + i );
			
			ts.add( t );
		}
		
		for( Thread t : ts ) t.start();
		for( Thread t : ts ) t.join();
		
		assertEquals( index.getAndIncrement(), NUM_ENTRIES, "Amount of fetches" );
		
	}
	
	public void testAutomaticClientParallelSingleManyAtOnceAccessor() throws InterruptedException {
		
		//final AtomicInteger index = new AtomicInteger( 0 );
		final DelayedIndexProvider index = new DelayedIndexProvider();
		
		TestAccessor_Single accessor =
				//new TestAccessor_Single( new PrintingIndexProvider( index ) );
				new TestAccessor_Single( index::getAndIncrement );
		
		CacheNG.AutomaticClient<String,String> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.S_S, false ) );
		
		
		List<Thread> ts = new LinkedList<>();
		
		for( int i=0; i<NUM_THREADS; i++ ){
			
			Thread t = new Thread( new FetchRunnerManyAtOnce( accessor, auto ), "T-" + i );
			
			ts.add( t );
			
		}
		
		for( Thread t : ts ) t.start();
		for( Thread t : ts ) t.join();
		
		assertEquals( index.getAndIncrement(), NUM_ENTRIES, "Amount of fetches" );
		
	}
	
	public void testAutomaticClientParallelMultiOneByOneAccessor() throws InterruptedException {
		
		//final AtomicInteger index = new AtomicInteger( 0 );
		final DelayedIndexProvider index = new DelayedIndexProvider();
		
		TestAccessor_Multi accessor =
				new TestAccessor_Multi( index::getAndIncrement );
		
		CacheNG.AutomaticClient<String,String> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.S_S, false ) );
		
		List<Thread> ts = new LinkedList<>();
		
		for( int i=0; i<NUM_THREADS; i++ ){
			
			Thread t = new Thread( new FetchRunnerOneByOne( accessor, auto ), "T-" + i );
			
			ts.add( t );
			
		}
		
		for( Thread t : ts ) t.start();
		for( Thread t : ts ) t.join();
		
		assertEquals( index.getAndIncrement(), NUM_ENTRIES, "Amount of fetches" );
		
	}
	
	public void testAutomaticClientParallelMultiManyAtOnceAccessor() throws InterruptedException {
		
		//final AtomicInteger index = new AtomicInteger( 0 );
		final DelayedIndexProvider index = new DelayedIndexProvider();
		
		TestAccessor_Multi accessor =
				new TestAccessor_Multi( index::getAndIncrement );
		
		CacheNG.AutomaticClient<String,String> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.S_S, false ) );
		
		List<Thread> ts = new LinkedList<>();
		
		for( int i=0; i<NUM_THREADS; i++ ){
			
			Thread t = new Thread( new FetchRunnerManyAtOnce( accessor, auto ), "T-" + i );
			
			ts.add( t );
			
		}
		
		for( Thread t : ts ) t.start();
		for( Thread t : ts ) t.join();
		
		assertEquals( index.getAndIncrement(), NUM_ENTRIES, "Amount of fetches" );
		
	}
	
	private class FetchRunnerOneByOne implements Runnable {
		
		final CacheNG.UniversalAccessor<String, String> accessor;
		final CacheNG.AutomaticClient<String, String> autoClient;
		
		public FetchRunnerOneByOne( UniversalAccessor<String, String> accessor,
				AutomaticClient<String, String> autoClient ) {
			this.accessor = accessor;
			this.autoClient = autoClient;
		}

		@Override
		public void run() {
			
			for( int i=0; i<NUM_ENTRIES; i++ ) {
				
				char c = (char)('A' + i);
				
				String expected = ""+c+i;
				String result = autoClient.fetch( ""+c, accessor );
				
				assertEquals( result, expected );
			}
		}
	}
	
	private class FetchRunnerManyAtOnce implements Runnable {
		
		final CacheNG.UniversalAccessor<String, String> accessor;
		final CacheNG.AutomaticClient<String, String> autoClient;
		
		public FetchRunnerManyAtOnce( UniversalAccessor<String, String> accessor,
				AutomaticClient<String, String> autoClient ) {
			this.accessor = accessor;
			this.autoClient = autoClient;
		}

		@Override
		public void run() {
			
			for( int i=0; i<NUM_ENTRIES; i+=2 ) {
				
				int j = i+1;
				
				char c = (char)('A'+i),
				     d = (char)('A'+j);
				
				List<String> request = Mapper.asLinkedList( ""+c, ""+d );
				
				Map<String,String> expected = Mapper.hashMap( ""+c, ""+c+i, ""+d, ""+d+j );
				Map<String,String> result = autoClient.fetch( request, accessor );
				
				//print( c + "/" + d + "->" + expected + "///" + result );
				
				assertEquals( result, expected );
			}
			
		}
		
	}
	
	private static synchronized void print( String x ){
		E._rrt( x );
	}
	
	
	@FunctionalInterface
	interface IndexProvider {
		public int getAndIncrement();
	}
	
	private static final class PrintingIndexProvider implements IndexProvider {
		
		private final AtomicInteger index;
		
		public PrintingIndexProvider( AtomicInteger index ) {
			this.index = index;
		}

		@Override
		public int getAndIncrement() {
			int result = index.getAndIncrement();
			print( "-->" + result );
			return result;
		}
	}
	
	private static final class DelayedIndexProvider implements IndexProvider {
		
		private final AtomicInteger index = new AtomicInteger();
		
		@Override
		public int getAndIncrement() {
			
			synchronized( this ){
				try {
					this.wait( THREAD_DELAY_MS );
				} catch( InterruptedException e ) {}
			}
			
			return index.getAndIncrement();
		}
	}
	
	class TestAccessor_Single
			implements CacheNG.UniversalAccessor<String, String> {
		
		final IndexProvider index;
		
		public TestAccessor_Single( IndexProvider index ) {
			this.index = index;
		}
		
		@Override
		public String fetch( String key ) {
			
			return key + index.getAndIncrement();
		}
	}
	
	class TestAccessor_Multi
			implements CacheNG.UniversalAccessor<String, String> {
		
		final IndexProvider index;
		
		
		public TestAccessor_Multi( IndexProvider index ) {
			this.index = index;
		}
		
		@Override
		public Map<String,String> fetch( Collection<String> keys ) {
			
			Map<String,String> result = new HashMap<>();
			
			for( String key : keys ){
				
				result.put( key, key + index.getAndIncrement() );
			}
			
			return result;
		}
	}
}
