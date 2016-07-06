package de.axone.cache.ng;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.MultiValueAccessor;
import de.axone.cache.ng.CacheNG.SingleValueAccessor;
import de.axone.cache.ng.CacheNGTestHelpers.TestRealm;
import de.axone.tools.E;
import de.axone.tools.Mapper;

@Test( groups="cacheng.freshmulti" )
public class CacheNGTest_FetchFresh_MultiThreadMultiValue {
	
	private static final int NUM_THREADS = 1000;

	
	TestValueProvider<Integer,String> tvp = new TestValueProvider<Integer,String>( k -> "" + k )
			.threadsafe( false );
	
	CacheNG.Realm<Integer,String> realm = new TestRealm<>( "i2s" );
	
	CacheNG.Cache<Integer,String> cache = new CacheHashMap<>( realm, false );
	
	CacheNG.AutomaticClient<Integer,String> auto = new AutomaticClientImpl<>( cache );
	
	MultiValueAccessor<Integer,String> maccessor = keys -> tvp.get( keys );
	SingleValueAccessor<Integer,String> saccessor = key -> tvp.get( key );
		
		
	public void testSingleFetchFreshMultithreaded() throws Exception {
		
		tvp.resetCount();
		cache.invalidateAll( true );
		
		List<Thread> threads = new ArrayList<>( NUM_THREADS );
		
		for( int i=0; i<NUM_THREADS; i++ ) {
			
			threads.add( new Thread( this::testAccessSingle, "T"+i ) );
		}
		
		for( Thread t : threads ) t.start();
		
		for( Thread t : threads ) t.join();
		
		
		Map<Integer,Integer> act = IntStream.range( 1, 10 )
				.mapToObj( k->k )
				.collect( Collectors.toMap( k->k, tvp::getCount ) );
		
		Map<Integer,Integer> exp = Mapper.hashMap( 1, 1001, 2, 1001, 3, 1001, 4, 1001, 5, 1, 6, 1, 7, 1, 8, 1, 9, 1 );
		
		assertEquals( act, exp );
		
		
		/*
		for( int i=1; i<10; i++ ) {
			if( i < 5 ) assertEquals( tvp.getCount( i ), NUM_THREADS+1 );
			else assertEquals( tvp.getCount( i ), 1 );
		}
		*/
	}
	
	@Test( dependsOnMethods="testSingleFetchFreshMultithreaded" )
	public void testMultiFetchFreshMultithreaded() throws Exception {
		
		tvp.resetCount();
		cache.invalidateAll( true );
		
		List<Thread> threads = new ArrayList<>( NUM_THREADS );
		
		for( int i=0; i<NUM_THREADS; i++ ) {
			
			threads.add( new Thread( this::testAccessMulti, "T"+i ) );
		}
		
		for( Thread t : threads ) t.start();
		
		for( Thread t : threads ) t.join();
		
		
		Map<Integer,Integer> act = IntStream.range( 1, 10 )
				.mapToObj( k->k )
				.collect( Collectors.toMap( k->k, tvp::getCount ) );
		
		E.rr( act );
		
		Map<Integer,Integer> exp = Mapper.hashMap( 1, 1001, 2, 1001, 3, 1001, 4, 1001, 5, 1, 6, 1, 7, 1, 8, 1, 9, 1 );
		
		assertEquals( act, exp );
	}
	
	@Test( enabled=false )
	void testAccessSingle() {
		
		for( int i=1; i<10; i++ ) {
			
			String act = auto.fetchFresh( i, saccessor, CacheNGTest_FetchFresh_MultiThreadMultiValue::isOld );
			//String act = auto.fetch( i, saccessor );
			
			assertEquals( act, ""+i );
		}
		
	}
	
	@Test( enabled=false )
	void testAccessMulti() {
		
		List<Integer> keys = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9 );
		Map<Integer,String> exp = keys.stream().collect( Collectors.toMap( k->k, k->""+k ) );
		
		Map<Integer,String> act = auto.fetchFresh( keys, maccessor,
				CacheNGTest_FetchFresh_MultiThreadMultiValue::isOld );
		assertEquals( act, exp );
		
	}
	
	private static boolean isOld( String s ) {
		
		boolean result = "5".compareTo( s ) > 0;
		return result;
	}
	
}
