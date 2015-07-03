package de.axone.cache.ng;

import static org.testng.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.testng.annotations.Test;

import com.google.common.io.Files;

import de.axone.cache.ng.CacheNG.SingleValueAccessor;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.tools.E;

/**
 * Try to simulate Article Backend in order to find some deadlocks which occure there
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

	public void testMultithreadedBackendLikeAccess() throws InterruptedException {
		
		long start = System.currentTimeMillis();
		
		File tmp = Files.createTempDir();
		
		E.chof( "Running in tmp dir: %s", tmp.getAbsolutePath() );
		
		CacheNG.Cache<String,Collection<String>> cache =
				CacheEHCache.instance( tmp, RN.S_SS, (long)(NUM_ACCESSES * RATIO ) );
		
		CacheNG.AutomaticClient<String,Collection<String>> client
			= new AutomaticClientImpl<>( cache );
		
		List<Thread> threads = new ArrayList<>( NUM_THREADS );
		
		for( int ti = 0; ti < NUM_THREADS; ti++ ) {
			
			threads.add( 
			
				new Thread( new Runnable() {
		
					@Override
					public void run() {
						
						for( int i=0; i<NUM_ACCESSES; i++ ){
							
							String key = "" + (int)(Math.random() * NUM_ACCESSES );
							
							Collection<String> result = client.fetch( key, SIMULATE_ACCESS_BACKEND );
							
							assertEquals( result.iterator().next(), key );
						}
					}
				} ) 
			);
		}
			
		for( Thread t : threads ) t.start();
		for( Thread t : threads ) t.join();
		
		long end = System.currentTimeMillis();
		
		E.cho( client.stats() );
		E.chof( "Run in %dms", end-start );
	}
	
	
	private final SimulateAccessBackend SIMULATE_ACCESS_BACKEND = new SimulateAccessBackend();
	
	private final class SimulateAccessBackend implements SingleValueAccessor<String,Collection<String>> {

		@Override
		public Collection<String> fetch( String key ) {
			
			try {
				Thread.sleep( ACCESS_DELAY_MS );
			} catch( InterruptedException e ) {
				throw new Error( "Interrupted but shouldn't" );
			}
			
			return Arrays.asList( key );
		}
		
	}
	
}
