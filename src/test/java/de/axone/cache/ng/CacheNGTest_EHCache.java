package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;

import java.io.File;

import org.testng.annotations.Test;

import de.axone.tools.Mapper;

@Test( groups="cacheng.timeoutall" )
public class CacheNGTest_EHCache {

	// Test what works as keys (and what not)
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public static void testCacheNGKeys() {
		
		CacheEHCache cache = CacheEHCache.instance( new File( "/tmp/ehcache" ), new RealmImpl( "blah" ), 10 );;
		
		cache.put( Mapper.hashSet( "a", "b" ), "set" );
		
		assertThat( cache )
				.hasSize( 1 )
				.hasCached( Mapper.hashSet( "a", "b" ) )
				.hasCached( Mapper.hashSet( "b", "a" ) )
				.hasCached( Mapper.treeSet( "b", "a" ) )
				.hasNotCached( Mapper.arrayList( "a", "b" ) )
				.fetch( Mapper.hashSet( "a", "b" ) )
						.isEqualTo( "set" )
						;
		
		cache.invalidateAll( true );
		
		assertThat( cache )
				.hasSize( 0 )
				.hasNotCached( Mapper.hashSet( "a", "b" ) )
				;
				
		
		cache.put( Mapper.arrayList( "a", "b" ), "list" );
		
		assertThat( cache )
				.hasSize( 1 )
				.hasCached( Mapper.arrayList( "a", "b" ) )
				.hasCached( Mapper.linkedList( "a", "b" ) )
				.hasNotCached( Mapper.arrayList( "b", "a" ) )
				.hasNotCached( Mapper.linkedList( "b", "a" ) )
				.hasNotCached( Mapper.hashSet( "b", "a" ) )
				;
		
		
	}
	
}
