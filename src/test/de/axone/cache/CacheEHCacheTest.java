package de.axone.cache;

import static org.testng.Assert.*;

import java.util.HashMap;

import net.sf.ehcache.CacheManager;

import org.testng.annotations.Test;

@Test( groups="tools.cache.ehcache" )
public class CacheEHCacheTest {
	
	private static final String TESTKEY = "testkey";
	//private static final String TESTVALUE = "testvalue";
	private static final TestValue TESTVALUE = new TestValue( true );

	public void testEHCache() throws Exception {
		
		CacheEHCache<String,TestValue> cache = CacheEHCache.instance( "testcache", 10 );
		
		/*
		assertFalse( cache.containsKey( TESTKEY ) );
		assertEquals( cache.size(), 0 );
		assertEquals( cache.get( TESTKEY ), null );
		
		cache.put( TESTKEY, TESTVALUE );
		*/
		
		assertTrue( cache.containsKey( TESTKEY ) );
		assertEquals( cache.get( TESTKEY ), TESTVALUE );
		assertEquals( cache.size(), 1 );
		assertEquals( cache.capacity(), Integer.MAX_VALUE );
		
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
	
	private static final class TestValue extends HashMap<String,String>{
		TestValue( boolean init ){
			put( "A", "a" );
			put( "B", "b" );
		}
	}
}
