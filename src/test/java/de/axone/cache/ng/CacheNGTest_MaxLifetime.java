package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGImplementations.Aid;
import de.axone.cache.ng.CacheNGImplementations.RN;
import de.axone.cache.ng.CacheNGImplementations.TArticle;
import de.axone.cache.ng.CacheNGImplementations.TestCacheBackend;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier;

@Test( groups="helper.testng" )
public class CacheNGTest_MaxLifetime {

	private CacheNG.Backend backend = new TestCacheBackend();
	
	public void cacheArticlesForIdsAndTimeOut() throws Exception {
		
		TestAccessor_ArticleForIdentifier accessor = spy( new TestAccessor_ArticleForIdentifier() );
		
		// 100ms timeout
		CacheNG.AutomaticClient<Aid,TArticle> auto =
				backend.automatic( RN.AID_ARTICLE.timeout( 100 ), accessor );
		
		assertThat( auto ).hasNotCached( A12345 );
		
		verify( accessor, never() ).get( A12345 );
		assertThat( auto ).hasNotCached( A12345 );
		
		TArticle art = auto.fetch( A12345 );
		assertThat( art ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		verify( accessor, times( 1 ) ).get( A12345 );
		
		// This may fail on heavy CPU load
		synchronized( this ){ wait( 10 ); }
		assertThat( auto ).hasCached( A12345 );
		
		// Still cached so not accessor action
		art = auto.fetch( A12345 );
		verify( accessor, times( 1 ) ).get( A12345 );
		
		
		synchronized( this ){ wait( 100 ); }
		assertThat( auto ).hasNotCached( A12345 );
		
		// Fetch again
		art = auto.fetch( A12345 );
		assertThat( art ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		
		// Now we hat to get it again
		verify( accessor, times( 2 ) ).get( A12345 );
		
		synchronized( this ){ wait( 10 ); }
		
		assertThat( auto ).hasCached( A12345 );
		
		// And invalidate again
		synchronized( this ){ wait( 100 ); }
		assertThat( auto ).hasNotCached( A12345 );
	}
	
}
