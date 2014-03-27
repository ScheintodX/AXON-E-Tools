package de.axone.cache;

import static org.testng.Assert.*;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import net.sf.ehcache.CacheManager;

import org.testng.annotations.Test;

@Test( groups="tools.cache.ehcache" )
public class CacheEHCacheTest {
	
	public void testEHCache() throws Exception {
		
		CacheEHCache<String,TestValue> cache = CacheEHCache.instance(
				new File( "/tmp/ehcache" ), "testcache", 10 );
		
		String TESTKEY = "testkey";
		TestValue TESTVALUE = new TestValue( true );

		cache.put( TESTKEY, TESTVALUE );
		
		assertTrue( cache.containsKey( TESTKEY ) );
		assertEquals( cache.get( TESTKEY ), TESTVALUE );
		assertEquals( cache.size(), 1 );
		//assertEquals( cache.capacity(), Integer.MAX_VALUE );
		
		cache.remove( TESTKEY );
		
		assertFalse( cache.containsKey( TESTKEY ) );
		assertEquals( cache.size(), 0 );
		assertEquals( cache.get( TESTKEY ), null );
		
		cache.put( TESTKEY, TESTVALUE );
		cache.clear();
		assertFalse( cache.containsKey( TESTKEY ) );
		assertEquals( cache.size(), 0 );
		assertEquals( cache.get( TESTKEY ), null );
		
		cache.put( TESTKEY, TESTVALUE );
		
		CacheManager.getInstance().shutdown();
	}
	
	public void testIdentity() throws Exception {
		
		// A simple wrapper around EHCache which does the configuration
		// and provides a map like interface
		CacheEHCache<TestKey,String> cache = CacheEHCache.instance(
				new File( "/tmp/ehcache" ), "testcache", 10 );
		
		// May contain content from last run
		cache.clear();
		
		// equals and hash matches
		TestKey k11 = new TestKey( true, 1 );
		
		cache.put( k11, "Test11" );
		
		assertEquals( cache.size(), 1 );
		assertTrue( cache.containsKey( k11 ) );
		
		cache.remove( k11 );
		assertEquals( cache.size(), 0 );
		
		// doesn't equal but hash matches
		TestKey k21 = new TestKey( false, 1 );
		
		cache.put( k21, "Test21" );
		assertEquals( cache.size(), 1 );
		assertFalse( cache.containsKey( k21 ) );
		
		// This is interesting: Clear needs to find the objects to clear
		// So we must patch it.
		k21.equals = true; 
		cache.clear();
		assertEquals( cache.size(), 0 );
		
		// equals but hash missmatches
		TestKey k31 = new TestKey( true, 1 );
		TestKey k32 = new TestKey( true, 2 );
		cache.put( k31, "Test21" );
		assertEquals( cache.size(), 1 );
		assertTrue( cache.containsKey( k31 ) );
		assertFalse( cache.containsKey( k32 ) );
		cache.clear();
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
		public boolean equals( Object other ){ return equals; }
		
		@Override
		public int hashCode(){ return hash; }
	}
}
