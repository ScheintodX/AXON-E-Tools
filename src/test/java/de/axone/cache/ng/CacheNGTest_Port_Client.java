package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.TestRealm;

/**
 * Test Cache implementors
 * 
 * @author flo
 *
 */
@Test( groups="tools.cache" )
public class CacheNGTest_Port_Client {
	
	static final String A="a", B="b", C="c", D="d";
	
	static final TestEntry a = new TestEntry( A );
	static final TestEntry b = new TestEntry( B );
	static final TestEntry c = new TestEntry( C );

	// Not much to test here. CacheHashMap is a simple extension of HashMap
	// TODO: There is room here for parallel tests.
	public void testHashCache(){
		
		CacheNG.Cache<String,TestEntry> cache = new CacheHashMap<>(
				new TestRealm<String,TestEntry>( "HashTest" ));
		
		assertPutGet( cache, A, a );
		assertPutGet( cache, B, b );
		assertPutGet( cache, C, c );
		
		cache.put( A, a );
		cache.put( B, b );
		cache.put( C, c );
		
		assertThat( cache.info() ).contains( "(3)" );
	}
	
	public void testLRUCache(){
		
		CacheNG.Cache<String,TestEntry> cache = new CacheLRUMap<>(
				new TestRealm<String,TestEntry>( "LRUCache" ), 2);
		
		assertPutGet( cache, A, a );
		assertPutGet( cache, B, b );
		assertPutGet( cache, C, c );
		
		cache.put( A, a );
		cache.put( B, b );
		cache.put( C, c );
		
		assertThat( cache )
				.doesNotHave( cached( A ) )
				.has( cached( B ) )
				.has( cached( C ) )
				;
		
		assertThat( cache.info() ).contains( "(Size: 2 of 2," );
	}
	
	public void testNoCache(){
		
		CacheNG.Cache<String,TestEntry> cache
				= new CacheNoCache<String,TestEntry>( new TestRealm<>( "A cache" ) );
		
		cache.put( A, a );
		cache.put( B, b );
		cache.put( C, c );
		
		assertThat( cache )
				.doesNotHave( cached( A ) )
				.doesNotHave( cached( B ) )
				.doesNotHave( cached( C ) )
				;
		assertEquals( cache.info(), "no caching: TestClient/A cache" );
	}
	
	private void assertPutGet( CacheNG.Cache<String,TestEntry> cache, String key, TestEntry value ){
		
		cache.put( key, value );
		
		assertThat( cache )
				.has( cached( key ) )
				.fetch( key ).isEqualTo( value )
				;
		
		cache.put( key, null );
		
		assertThat( cache )
				.has( cached( key ) )
				.fetch( key ).isNull()
				;
		
		cache.invalidate( key );
		
		assertThat( cache )
				.doesNotHave( cached( key ) )
				;
		
	}
			
	static final class TestEntry {
		final String name;
		TestEntry( String name ){ this.name = name; }
		@Override public String toString(){ return "E("+name+")"; }
	}
}
