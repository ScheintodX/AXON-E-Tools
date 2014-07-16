package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForAid;
import de.axone.data.Duration;

@Test( groups="cacheng.lifetime" )
public class CacheNGTest_MaxLifetime {

	public void cacheArticlesForIdsAndTimeOut() throws Exception {
		
		TestAccessor_ArticleForAid accessor = spy( new TestAccessor_ArticleForAid() );
		
		CacheNG.Cache<Aid,TArticle> client = 
				new CacheHashMap<>( RN.AID_ARTICLE );
		
		// 100ms timeout
		CacheNG.Cache<Aid,TArticle> timeoutClient =
				new CacheWrapperTimeout<>( client, Duration.milliseconds( 100 ) );
		
		CacheNG.AutomaticClient<Aid,TArticle> auto =
				new AutomaticClientImpl<>( timeoutClient );
		
		assertThat( auto ).hasNotCached( A12345 );
		
		verify( accessor, never() ).fetch( A12345 );
		assertThat( auto ).hasNotCached( A12345 );
		
		TArticle art = auto.fetch( A12345, accessor );
		assertThat( art ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		verify( accessor, times( 1 ) ).fetch( A12345 );
		
		// This may fail on heavy CPU load
		synchronized( this ){ wait( 10 ); }
		assertThat( auto ).hasCached( A12345 );
		
		// Still cached so not accessor action
		art = auto.fetch( A12345, accessor );
		verify( accessor, times( 1 ) ).fetch( A12345 );
		
		
		synchronized( this ){ wait( 100 ); }
		assertThat( auto ).hasNotCached( A12345 );
		
		// Fetch again
		art = auto.fetch( A12345, accessor );
		assertThat( art ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		
		// Now we hat to get it again
		verify( accessor, times( 2 ) ).fetch( A12345 );
		
		synchronized( this ){ wait( 10 ); }
		
		assertThat( auto ).hasCached( A12345 );
		
		// And invalidate again
		synchronized( this ){ wait( 100 ); }
		assertThat( auto ).hasNotCached( A12345 );
	}
	
}
