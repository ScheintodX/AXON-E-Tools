package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;

@Test( groups="helper.testng" )
public class CacheNGTest_ArticleListForTid {

	static class TestAccessor_ArticleForTid
			extends AbstractSingleValueAccessor<Tid, List<TArticle>>
			implements CacheNG.Accessor<Tid, List<TArticle>>{
		
		private final TestMapTidToArticle data;
		
		public TestAccessor_ArticleForTid( TestMapTidToArticle data ) {
			this.data = data;
		}
	
		@Override
		public List<TArticle> fetch( Tid tid ) {
			return data.get( tid );
		}
		
		void addArticle( TArticle art ){
			
			data.addArticle( art );
			
		}
	
	}

	static class TestMapTidToArticle extends HashMap<Tid, List<TArticle>>{
		
		void addArticle( TArticle art ){
			
			for( Tid tid : art.getTreeIdentifiers() ){
				
				List<TArticle> arts = get( tid );
				if( arts == null ){
					arts = new LinkedList<>();
					put( tid, arts );
				}
				arts.add( art );
			}
		}
	}

	public void cacheArticlesForTids(){
		
		TestMapTidToArticle data = new TestMapTidToArticle();
		data.addArticle( TArticle.build( A12345 ) );
		data.addArticle( TArticle.build( A12346 ) );
		
		TestAccessor_ArticleForTid accessor = new TestAccessor_ArticleForTid( data );
		
		CacheNG.AutomaticClient<Tid, List<TArticle>> auto =
				new AutomaticClientImpl<>( RN.TID_LARTICLE.realm() );
		
		assertThat( auto ).hasNotCached( T123 );
		
		List<TArticle> arts = auto.fetch( T123, accessor );
		assertThat( auto ).hasCached( T123 );
		
		assertThat( arts ).are( havingTid( T123 ) );
		
		// On import of articles: diff oldtids/newtids
		auto.invalidate( T123 );
		assertThat( auto ).hasNotCached( T123 );
	}
	
	
}
