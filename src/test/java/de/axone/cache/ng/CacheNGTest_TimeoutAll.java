package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier;
import de.axone.tools.E;

@Test( groups="helper.testng" )
public class CacheNGTest_TimeoutAll {

	public void cacheTimesoutEntriesAfterATimeoutPeriod() throws Exception {
		
		TestAccessor_ArticleForIdentifier accessor =
				new TestAccessor_ArticleForIdentifier();
		
		CacheNG.AutomaticClient<Aid,TArticle> auto =
				new AutomaticClientImpl<>( RN.AID_ARTICLE.realm() );
		
		assertThat( auto ).hasNotCached( A12345 )
				.hasNotCached( A12346 );
		
		assertThat( auto.fetch( A12345, accessor ) ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		
		assertThat( auto.fetch( A12346, accessor ) ).isNotNull();
		assertThat( auto )
				.hasCached( A12345 )
				.hasCached( A12346 );
		
		auto.invalidateAllWithin( 100 );
		
		synchronized( this ){ wait( 110 ); }
		
		assertThat( auto )
				.hasNotCached( A12345 )
				.hasNotCached( A12346 );
		
	}
	
	public void cacheDoesNotTimeoutEntriesCreatedAfterTimeoutEvent() throws Exception {
		
		TestAccessor_ArticleForIdentifier accessor =
				new TestAccessor_ArticleForIdentifier();
		
		CacheNG.AutomaticClient<Aid,TArticle> auto =
				new AutomaticClientImpl<>( RN.AID_ARTICLE.realm() );
		
		assertFalse( auto.isCached( A12345 ) );
		assertFalse( auto.isCached( A12346 ) );
		
		TArticle art = auto.fetch( A12345, accessor );
		assertThat( art ).isNotNull();
		assertTrue( auto.isCached( A12345 ) );
		
		auto.invalidateAllWithin( 100 );
		
		synchronized( this ){ wait( 100 ); }
		
		art = auto.fetch( A12346, accessor );
		assertThat( art ).isNotNull();
		assertTrue( auto.isCached( A12346 ) );
		
		synchronized( this ){ wait( 100 ); }
		
		assertFalse( auto.isCached( A12345 ) );
		assertTrue( auto.isCached( A12346 ) );
	}
	
	private static final int X = 12000;
	
	public void testInvalidationWithLargeRandomIds() throws Exception {
		
		TestAccessor_ArticleForIdentifier accessor =
				new TestAccessor_ArticleForIdentifier();
		
		CacheNG.AutomaticClient<Aid,TArticle> auto =
				new AutomaticClientImpl<>( RN.AID_ARTICLE.realm() );
		
		// Mimic real range article identifiers
		for( int i=0; i<X; i++ ){
			auto.fetch( aid( i+":123" ), accessor );
			assertThat( auto ).hasCached( aid( i+":123" ) );
		}
		
		auto.invalidateAllWithin( 1000 );
		
		synchronized( this ){ wait( 500 ); }
		
		long start = System.currentTimeMillis();
		int c=0;
		for( int i=0; i<X; i++ ){
			if( auto.isCached( aid( i+":123" ) ) ) c++;
		}
		long end = System.currentTimeMillis();
		E.cho( + X + " checked in " + (end-start) + "ms and " + (X-c) + " invalidated" );
		
		synchronized( this ){ wait( 500 ); }
		
		int d=0;
		for( int i=0; i<X; i++ ){
			if( auto.isCached( aid( i+":123" ) ) ) d++;
		}
		
		assertThat( d ).describedAs( "Amount of invalidated entries after full period." )
				.isEqualTo( 0 );
		
		assertThat( c ).describedAs( "Amount of invalidated entries after roughly half period. Can vary depending on timing." )
				.isBetween( X/2-X/10, X/2+X/10 );
	}
	
}