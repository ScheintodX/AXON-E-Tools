package de.axone.cache.ng;

import static org.testng.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.testng.annotations.Test;

import com.google.common.io.Files;

import de.axone.cache.ng.CacheNG.Realm;
import de.axone.cache.ng.CacheNG.SingleValueAccessor;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.tools.E;

/**
 * Try to simulate Article Backend in order to find some deadlocks which might occur there
 * 
 * Simulating is done using a simple delay in accessor before returning something.
 * 
 * @author flo
 */
@Test( groups="cacheng.multithreaded.articlebackend" )
public class CacheNGTest_SimulateArticleBackend {
	
	private final int ACCESS_DELAY_MS = 100,
	                  NUM_THREADS = 500,
	                  NUM_ACCESSES = 1000
	                  ;
	private final double RATIO = .5; 

	public void testMultithreadedBackendLikeAccess() throws Exception {
		
		long start = System.currentTimeMillis();
		
		File tmp = Files.createTempDir();
		
		E.chof( "Running in tmp dir: %s", tmp.getAbsolutePath() );
		
		CacheNG.Cache<String,Collection<String>> cache =
				CacheEHCache.instance( tmp, RN.S_SS2, (long)(NUM_ACCESSES * RATIO ) );
		
		CacheNG.AutomaticClient<String,Collection<String>> client
			= new AutomaticClientImpl<>( cache, Realm.Hint.STRICT );
		
		ExecutorService service = Executors.newFixedThreadPool( NUM_THREADS );
		List<Future<?>> tasks = new ArrayList<>( NUM_THREADS );
		
		for( int ti = 0; ti < NUM_THREADS; ti++ ) {
			
			tasks.add( service.submit( new Runnable() {
		
					@Override
					public void run() {
						
						for( int i=0; i<NUM_ACCESSES; i++ ){
							
							String key = "" + (int)(Math.random() * NUM_ACCESSES );
							
							Collection<String> result = client.fetch( key, BackendAccessorSimulator );
							
							assertEquals( result.iterator().next(), key );
						}
					}
				}
			) );
		}
		
		service.shutdown();
		for( Future<?> f : tasks ) {
			
			// This may fail if running paralell with other tests because EHCache may have been shut down.
			assertNull( f.get() );
		}
		
		long end = System.currentTimeMillis();
		
		E.cho( client.stats() );
		E.chof( "Run in %dms", end-start );
	}
	
	
	private final SingleValueAccessor<String,Collection<String>> BackendAccessorSimulator =
			new SingleValueAccessor<String,Collection<String>>() {

		@Override
		public Collection<String> fetch( String key ) {
			
			try {
				Thread.sleep( ACCESS_DELAY_MS );
			} catch( InterruptedException e ) {
				throw new Error( "Interrupted but shouldn't" );
			}
			
			return Arrays.asList( key );
		}
		
	};
	
}
