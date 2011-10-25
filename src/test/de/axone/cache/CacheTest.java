package de.axone.cache;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * Test Cache implementors
 * 
 * @author flo
 *
 */
@Test( groups="tools.cache" )
public class CacheTest {
	
	static final String A="a", B="b", C="c", D="d";
	
	static final TestEntry a = new TestEntry( A );
	static final TestEntry b = new TestEntry( B );
	static final TestEntry c = new TestEntry( C );

	// Not much to test here. CacheHashMap is a simple extension of HashMap
	// Note that caches are not synchronised so there is no need for parallel tests.
	public void testHashCache(){
		
		Cache<String,TestEntry> cache = new CacheHashMap<String,TestEntry>();
		assertPutGet( cache, A, a );
		assertPutGet( cache, B, b );
		assertPutGet( cache, C, c );
		cache.put( A, a );
		cache.put( B, b );
		cache.put( C, c );
		assertEquals( cache.info(), "HashMap (3)" );
	}
	
	public void testLRUCache(){
		
		Cache<String,TestEntry> cache = new CacheLRUMap<String,TestEntry>(2);
		assertPutGet( cache, A, a );
		assertPutGet( cache, B, b );
		assertPutGet( cache, C, c );
		cache.put( A, a );
		cache.put( B, b );
		cache.put( C, c );
		assertFalse( cache.containsKey( A ) );
		assertTrue( cache.containsKey( B ) );
		assertTrue( cache.containsKey( C ) );
		assertEquals( cache.info().substring( 0, 18 ), "LRU (Size: 2 of 2," );
	}
	
	public void testNoCache(){
		
		Cache<String,TestEntry> cache = new CacheNoCache<String,TestEntry>();
		cache.put( A, a );
		cache.put( B, b );
		cache.put( C, c );
		assertFalse( cache.containsKey( A ) );
		assertFalse( cache.containsKey( B ) );
		assertFalse( cache.containsKey( C ) );
		assertEquals( cache.info(), "no caching" );
	}
	
	private void assertPutGet( Cache<String,TestEntry> cache, String key, TestEntry value ){
		
		cache.put( key, value );
		assertTrue( cache.containsKey( key ) );
		assertEquals( cache.get( key ), value );
		cache.put( key, null );
		assertTrue( cache.containsKey( key ) );
		assertEquals( cache.get( key ), null );
		cache.remove( key );
		assertFalse( cache.containsKey( key ) );
	}
			
	static final class TestEntry {
		final String name;
		TestEntry( String name ){ this.name = name; }
		@Override public String toString(){ return name; }
	}
}
