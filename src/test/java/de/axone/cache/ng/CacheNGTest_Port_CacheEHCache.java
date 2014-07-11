package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static org.testng.Assert.*;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import net.sf.ehcache.CacheManager;

import org.testng.annotations.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Test( groups="tools.cache.ehcache" )
public class CacheNGTest_Port_CacheEHCache {
	
	public void testEHCache() throws Exception {
		
		CacheEHCache<String,TestValue> cache = CacheEHCache.instance(
				new File( "/tmp/ehcache" ), "testcache", 10 );
		
		String TESTKEY = "testkey";
		TestValue TESTVALUE = new TestValue( true );

		cache.put( TESTKEY, TESTVALUE );
		
		assertThat( cache )
				.hasCapacity( -1 )
				.has( cached( TESTKEY ) )
				.hasSize( 1 )
				.fetch( TESTKEY ).isEqualTo( TESTVALUE )
				;
		//assertEquals( cache.capacity(), Integer.MAX_VALUE );
		
		cache.invalidate( TESTKEY );
		
		assertThat( cache )
				.doesNotHave( cached( TESTKEY ) )
				.hasSize( 0 )
				.fetch( TESTKEY ).isNull()
				;
		
		cache.put( TESTKEY, TESTVALUE );
		cache.invalidateAll();
		assertThat( cache )
				.doesNotHave( cached( TESTKEY ) )
				.hasSize( 0 )
				.fetch( TESTKEY ).isNull()
				;
		assertEquals( cache.size(), 0 );
		
		cache.put( TESTKEY, TESTVALUE );
		
		CacheManager.getInstance().shutdown();
	}
	
	public void testIdentity() throws Exception {
		
		// A simple wrapper around EHCache which does the configuration
		// and provides a map like interface
		CacheNG.Cache<TestKey,String> cache = CacheEHCache.instance(
				new File( "/tmp/ehcache" ), "testcache", 10 );
		
		// May contain content from last run
		cache.invalidateAll();
		
		// equals and hash matches
		TestKey k11 = new TestKey( true, 1 );
		
		cache.put( k11, "Test11" );
		
		assertThat( cache )
				.hasSize( 1 )
				.hasCached( k11 )
				;
		
		cache.invalidate( k11 );
		assertThat( cache )
				.hasSize( 0 )
				.hasNotCached( k11 )
				;
		
		// doesn't equal but hash matches
		TestKey k21 = new TestKey( false, 1 );
		
		cache.put( k21, "Test21" );
		assertThat( cache )
				.hasSize( 1 )
				.hasNotCached( k21 )
				;
		
		// This is interesting: Clear needs to find the objects to clear
		// So we must patch it.
		k21.equals = true; 
		cache.invalidateAll();
		assertEquals( cache.size(), 0 );
		
		// equals but hash missmatches
		TestKey k31 = new TestKey( true, 1 );
		TestKey k32 = new TestKey( true, 2 );
		cache.put( k31, "Test21" );
		assertThat( cache )
				.hasSize( 1 )
				.hasCached( k31 )
				.hasNotCached( k32 )
				;
		cache.invalidateAll();
	}
	
	private static final class TestValue extends HashMap<String,String>{
		
		private static final long serialVersionUID = 433153539345496387L;

		TestValue( boolean init ){
			put( "A", "a" );
			put( "B", "b" );
		}
	}
	
	private static final class TestKey implements Serializable {
		
		boolean equals;
		int hash;
		
		TestKey( boolean equals, int hash ){
			this.equals = equals;
			this.hash = hash;
		}
		
		@Override
		@SuppressFBWarnings("EQ_UNUSUAL")
		public boolean equals( Object other ){ return equals; }
		
		@Override
		public int hashCode(){ return hash; }
	}
}
