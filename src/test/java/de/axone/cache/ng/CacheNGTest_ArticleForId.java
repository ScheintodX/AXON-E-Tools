package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGImplementations.RN;
import de.axone.tools.E;

@Test( groups="helper.testng" )
public class CacheNGTest_ArticleForId {

	static class TestAccessor_ArticleForIdentifier
			implements CacheNG.Accessor<CacheNGImplementations.Aid, CacheNGImplementations.TArticle>,
					CacheNG.CacheEventListener<CacheNGImplementations.Aid>{
	
		@Override
		public CacheNGImplementations.TArticle get( CacheNGImplementations.Aid identifier ) {
			
			if( identifier.name().startsWith( "-" ) ) return null;
			else return CacheNGImplementations.TArticle.build( identifier );
		}
	
		@Override
		public void invalidateEvent( CacheNGImplementations.Aid key ) {
			E.rr( "------" + key + "-----------" );
		}
	
	}

	private CacheNG.Backend backend = new CacheNGImplementations.TestCacheBackend();
	
	public void cacheArticlesForIds(){
		
		CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier accessor = new CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier();
		
		CacheNG.AutomaticClient<CacheNGImplementations.Aid,CacheNGImplementations.TArticle> auto =
				backend.automatic( RN.AID_ARTICLE.realm(), accessor );
		
		assertFalse( auto.isCached( A12345 ) );
		
		CacheNGImplementations.TArticle art = auto.fetch( A12345 );
		assertThat( art ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		
		assertThat( art )
				.is( CacheNGAssert.havingIdentifier( CacheNGImplementations.aid( "12345" ) ) )
				.is( CacheNGAssert.havingTid( T123 ) )
				.is( CacheNGAssert.havingTid( T234 ) )
		;
		
		auto.invalidate( A12345 );
		assertThat( auto ).hasNotCached( A12345 );
		
	}
	
}
