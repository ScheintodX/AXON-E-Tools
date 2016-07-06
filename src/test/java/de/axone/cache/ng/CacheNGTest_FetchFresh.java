package de.axone.cache.ng;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.UniversalAccessor;
import de.axone.tools.A;
import de.axone.tools.Mapper;

@Test( groups="cacheng.fetchfresh" )
public class CacheNGTest_FetchFresh {
	
	public void fetchFreshSingleValues() {
		
		TestAccessor_StringForString accessor = new TestAccessor_StringForString();
		CacheNG.Cache<String,String> cache = spy( new CacheHashMap<>( new RealmImpl<String,String>( "blah" ), false ) );
		AutomaticClientImpl<String,String> auto = new AutomaticClientImpl<>( cache );
		
		assertFalse( auto.isCached( "abc" ) );
		assertEquals( auto.fetch( "abc", accessor ), "abc" );
		assertTrue( auto.isCached( "abc" ) );
		
		accessor.setPrefix( "X" );
		
		// Stays the same because is already cached
		assertEquals( auto.fetch( "abc", accessor ), "abc" );
		
		// refetch on predicate
		assertEquals( auto.fetchFresh( "abc", accessor, x->true ), "Xabc" );
		// and is cached in that way
		assertEquals( auto.fetch( "abc", accessor ), "Xabc" );
	}
	
	public void fetchFreshMultiValues() {
		
		TestAccessor_StringForString accessor = new TestAccessor_StringForString();
		CacheNG.Cache<String,String> cache = spy( new CacheHashMap<>( new RealmImpl<String,String>( "blah" ), false ) );
		AutomaticClientImpl<String,String> auto = new AutomaticClientImpl<>( cache );
		
		assertFalse( auto.isCached( "a1" ) );
		assertFalse( auto.isCached( "a2" ) );
		assertFalse( auto.isCached( "b1" ) );
		
		assertEquals( auto.fetch( A.List( "a1", "a2", "b1" ), accessor ),
				Mapper.hashMap( "a1", "a1", "a2", "a2", "b1", "b1" ) );
		
		accessor.setPrefix( "X" );
		
		// Stays the same because is already cached
		assertEquals( auto.fetch( A.List( "a1", "a2", "b1" ), accessor ),
				Mapper.hashMap( "a1", "a1", "a2", "a2", "b1", "b1" ) );
		
		assertEquals( auto.fetchFresh( A.List( "a1", "a2", "b1" ), accessor, x -> x.startsWith( "b" ) ),
				Mapper.hashMap( "a1", "a1", "a2", "a2", "b1", "Xb1" ) );
		
		assertEquals( auto.fetchFresh( A.List( "a1", "a2", "b1" ), accessor, x -> x.startsWith( "a" ) ),
				Mapper.hashMap( "a1", "Xa1", "a2", "Xa2", "b1", "Xb1" ) );
		
		// And stays so
		assertEquals( auto.fetch( A.List( "a1", "a2", "b1" ), accessor ),
				Mapper.hashMap( "a1", "Xa1", "a2", "Xa2", "b1", "Xb1" ) );
	}
	
	private static final int ACCESSORS = 100,
	                         ACCESSES = 10_000;
	
	public void testFetchFreshMultiThreadedMulti() {
		
		//CacheNG.Cache<String,String> cache = spy( new CacheHashMap<>( new RealmImpl<String,String>( "blah" ), false ) );
		//AutomaticClientImpl<String,String> auto = new AutomaticClientImpl<>( cache );
		TestAccessor_StringForString accessor = new TestAccessor_StringForString();
		
		ExecutorService accessors = Executors.newFixedThreadPool( ACCESSORS );
		for( int t = 0; t < ACCESSORS; t++ ) {
			
			accessors.execute( () -> {
				for( int i=0; i<ACCESSES; i++ ) {
					assertEquals( accessor.fetch( "abc" ), "abc" );
				}
			} );
		}
		
		accessors.shutdown();
	}
	
	public static final class TestAccessor_StringForString implements UniversalAccessor<String,String> {
		
		private String prefix = "";
		
		public void setPrefix( String prefix ) {
			this.prefix = prefix;
		}
		public String getPrefix( String prefix ) {
			return this.prefix;
		}

		@Override
		public String fetch( String key ) {
			if( key.startsWith( "-" ) ) return null;
			return prefix + key;
		}
	}
}
