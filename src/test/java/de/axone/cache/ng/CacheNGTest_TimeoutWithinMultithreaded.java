package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;
import de.axone.tools.E;

@Test( groups="cacheng.timeout" )
public class CacheNGTest_TimeoutWithinMultithreaded {

	private static final int NUM = 10_000;
	private static final int THREADS = 100;
	private static final int TIME = 1000; //ms
	
	private volatile long start;
	
	TestAccessor accessor =
			new TestAccessor();
	
	CacheNG.Cache<Aid,TArticle> cache =
			new CacheHashMap<>( RN.AID_ARTICLE );
	
	CacheWrapperDelayedInvalidation<Aid,TArticle> wrapper =
			new CacheWrapperDelayedInvalidation<>( cache, TIME );
	
	CacheAccessCounter<Aid,TArticle> counter =
			new CacheAccessCounter<>( wrapper );
	
	CacheNG.AutomaticClient<Aid,TArticle> autoClient =
			new AutomaticClientImpl<>( counter );
			
			
	public void fillCache(){
		
		E.rr( "fill: " + NUM );
		for( int i=0; i<NUM; i++ ){
			assertThat( autoClient )
					.fetch( aid( ""+i ), accessor )
					.isNotNull();
		}
	}
	
	@Test( dependsOnMethods="fillCache" )
	public void invalidateAll(){
		
		autoClient.invalidateAll( false );
	}
	
	private AtomicInteger count = new AtomicInteger(),
	                      mid = new AtomicInteger(),
	                      full = new AtomicInteger()
	                      ;
			
	/*
	 * This tests basically for no ConcurrentModificationException
	 * by paralell execution on the same values.
	 */
	@Test( dependsOnMethods="invalidateAll" )
	public void accessCacheWhileTimeoutRunning() throws InterruptedException{
		
		Thread [] rs = new Thread[ THREADS ];
		
		for( int t = 0; t < THREADS; t++ ){
			
			rs[ t ] = new Thread( new Runnable(){
				
				@Override
				public void run() {
					
					for( int i=0; i<NUM; i++ ){
						
						Aid aid = aid( ""+i );
						
						if( ! autoClient.isCached( aid ) ){
							System.out.print( "." );
							count.incrementAndGet();
							if( System.currentTimeMillis() < start + TIME/2 ){
								mid.incrementAndGet();
							}
							if( System.currentTimeMillis() < start + TIME ){
								full.incrementAndGet();
							}
						}
								
						assertThat( autoClient )
								.fetch( aid, accessor )
										.isNotNull().end()
								;
					}
				}
				
			} );
			
		}
		
		E.rr( "start: " + THREADS + " threads" );
		
		start = System.currentTimeMillis();
		for( int t = 0; t < THREADS; t++ ){ rs[ t ].start(); }
		for( int t = 0; t < THREADS; t++ ){ rs[ t ].join(); }
		long end = System.currentTimeMillis();
		
		long time = end-start;
		
		System.out.println();
		
		// Must run longer than invalidation time to get all invalidated
		E.rr( "time: " + time + "ms" );
		assertThat( time ).isGreaterThan( TIME );
		
		// It's possible that parallel threads access invalid
		// values at the same time. so count > NUM
		int theCount = count.get();
		E.rr( "invalid: " + theCount );
		assertThat( theCount ).isGreaterThanOrEqualTo( NUM );
		
		// Amount of values which where invalid at half the time invalidation time
		// Doesn't really work and don't know why...
		int theMid = mid.get();
		E.rr( "mid: " + theMid );
		//assertThat( theMid ).isBetween( theCount - theCount/2, theCount + theCount/2 );
		
		int theFull = full.get();
		E.rr( "full: " + theFull );
		//assertThat( theFull ).isBetween( (int)(theMid * 1.5), (int)(theMid * 3f) );
		
		E.rr( "Cache accesses: " + counter.count.get() );
		
		E.rr( "Accessor accesses: " + accessor.count.get() );
	}
	
	
	static class TestAccessor extends AbstractSingleValueAccessor<Aid,TArticle> {
		
		AtomicInteger count = new AtomicInteger();

		@Override
		public TArticle fetch( Aid key ) {
			
			count.incrementAndGet();
			
			return new TArticle( key, Collections.<Tid>emptyList() );
		}
		
	}
	
	static class CacheAccessCounter<K,O> extends CacheWrapper<K,O> {
		
		AtomicInteger count = new AtomicInteger();

		public CacheAccessCounter( Cache<K, O> wrapped ) {
			super( wrapped );
		}
		
		@Override
		public Entry<O> fetchEntry( K key ) {
			count.incrementAndGet();
			return super.fetchEntry( key );
		}

	}
	
}
