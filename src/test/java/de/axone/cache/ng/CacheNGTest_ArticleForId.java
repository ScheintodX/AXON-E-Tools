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
import de.axone.tools.E;

@Test( groups="helper.testng" )
public class CacheNGTest_ArticleForId {

	static class TestAccessor_ArticleForIdentifier
			extends AbstractSingleValueAccessor<Aid,TArticle>
			implements CacheNG.Accessor<Aid, TArticle>,
					CacheNG.CacheEventListener<Aid>{
	
		@Override
		public TArticle fetch( Aid identifier ) {
			
			if( identifier.name().startsWith( "-" ) ) return null;
			else return TArticle.build( identifier );
		}
	
		@Override
		public void invalidateEvent( Aid key ) {
			E.rr( "------" + key + "-----------" );
		}
	
	}

	public void cacheArticlesForIds(){
		
		TestAccessor_ArticleForIdentifier accessor =
				new TestAccessor_ArticleForIdentifier();
		
		CacheNG.AutomaticClient<Aid,TArticle> auto =
				new AutomaticClientImpl<>( RN.AID_ARTICLE.realm() );
		
		assertFalse( auto.isCached( A12345 ) );
		
		TArticle art = auto.fetch( A12345, accessor );
		assertThat( art ).isNotNull();
		assertThat( auto ).hasCached( A12345 );
		
		assertThat( art )
				.is( havingIdentifier( aid( "12345" ) ) )
				.is( havingTid( T123 ) )
				.is( havingTid( T234 ) )
		;
		
		auto.invalidate( A12345 );
		assertThat( auto ).hasNotCached( A12345 );
		
	}
	
}
