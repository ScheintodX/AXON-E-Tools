package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.CacheBridge;
import de.axone.cache.ng.CacheNG.CacheEventListener;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;
import de.axone.cache.ng.CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid;
import de.axone.cache.ng.CacheNGTest_ArticleListForTid.TestMapTidToArticle;

@Test( groups="cacheng.query" )
public class CacheNGTest_ArticleListForSearchQuery {

	TestMapTidToArticle data = new TestMapTidToArticle();
	{
		data.addArticle( TArticle.build( A12345 ) );
		data.addArticle( TArticle.build( A12346 ) );
	}
	
	static int i=1;
	
	TestSearchQuery_Tid t1  = new TestSearchQuery_Tid( tid( "t1" ) ),
	                    t1_ = new TestSearchQuery_Tid( tid( "t"+i ) ),
					    t2 = new TestSearchQuery_Tid( tid( "t2" ) )
	                    ;
		
	public void euqalsAndHashCode(){
		
		assertThat( t1 )
				.isEqualTo( t1_ )
				.isNotEqualTo( t2 )
				.is( havingSameHashCodeAs( t1_ ) )
				.isNot( havingSameHashCodeAs( t2 ) )
				;
		
	}
	
	public void storeAndRetreiveQuery(){
		
		Set<TestSearchQuery_Tid> set = new HashSet<>();
		set.add( t1 );
		assertThat( set ).containsOnly( t1 ).hasSize( 1 );
		set.add( t2 );
		assertThat( set ).containsOnly( t1, t2 ).hasSize( 2 );
		set.add( t1_ );
		assertThat( set ).containsOnly( t1, t2 ).hasSize( 2 );
		set.remove( t1_ );
		assertThat( set ).containsOnly( t2 ).hasSize( 1 );
		set.remove( t2 );
		assertThat( set ).isEmpty();
	}
		
	public void cacheArticlesForQueries(){
		
		TestAccessor_ArticleForTid forTid =
				new TestAccessor_ArticleForTid( data );
		
		CacheNG.AutomaticClient<Tid, List<TArticle>> autoForTid =
				new AutomaticClientImpl<>( RN.TID_LARTICLE );
		
		TestAccessor_ArticleForQuery forQuery =
				new TestAccessor_ArticleForQuery( autoForTid, forTid );
		
		CacheNG.AutomaticClient<TestSearchQuery, List<TArticle>> autoForQuery =
				new AutomaticClientImpl<>( RN.SQTID_LARTICLE );
		
		TestSearchQuery_Tid q = new TestSearchQuery_Tid( T123 );
		
		assertThat( autoForQuery ).hasNotCached( q );
		
		List<TArticle> arts = autoForQuery.fetch( q, forQuery );
		assertThat( autoForQuery ).hasCached( q );
		
		assertThat( arts ).are( havingTid( T123 ) );
		
		// On import of articles: diff oldtids/newtids
		// Invalidation is done only on the article's tids
		autoForQuery.invalidate( q );
		
		// Invalidation must propagate to top
		assertThat( autoForQuery ).hasNotCached( q );
	}
	
	public void invalidateSearchQueryByInvalidatingTid(){
		
		TestAccessor_ArticleForTid forTid =
				new TestAccessor_ArticleForTid( data );
		
		CacheHashMap<Tid,List<TArticle>> cacheForTid =
				new CacheHashMap<>( RN.TID_LARTICLE );
		
		CacheNG.AutomaticClient<Tid, List<TArticle>> autoForTid =
				new AutomaticClientImpl<>( cacheForTid );
				
		TestAccessor_ArticleForQuery forQuery =
				new TestAccessor_ArticleForQuery( autoForTid, forTid );
		
		CacheHashMap<TestSearchQuery, List<TArticle>> cacheForQuery =
				new CacheHashMap<>( RN.SQTID_LARTICLE );
				
		CacheNG.AutomaticClient<TestSearchQuery, List<TArticle>> autoForQuery =
				new AutomaticClientImpl<>( cacheForQuery );
		
		cacheForTid.registerListener( new TidToQueryBridge( cacheForQuery ) );
		
		TestSearchQuery_Tid q1 = new TestSearchQuery_Tid( T123 ),
		                    q2 = new TestSearchQuery_Tid( T234 );
		
		assertThat( autoForQuery ).hasNotCached( q1 );
		
		List<TArticle> arts = autoForQuery.fetch( q1, forQuery );
		assertThat( arts ).are( havingTid( T123 ) );
		assertThat( autoForQuery ).hasCached( q1 );
		
		arts = autoForQuery.fetch( q2, forQuery );
		assertThat( arts ).are( havingTid( T234 ) );
		assertThat( autoForQuery ).hasCached( q2 );
		
		autoForQuery.invalidate( q2 );
		
		assertThat( autoForQuery )
				.hasNotCached( q2 ) // Now q2 is not cached anymore ...
				.hasCached( q1 ); // ... but q1 still is
		
		// Invalidating the Tid ...
		autoForTid.invalidate( T123 );
		//... invalidates the Query
		assertThat( autoForQuery )
				.hasNotCached( q1 );
		
	}
	
	interface TestSearchQuery {
		
		public List<TArticle> execute(
				CacheNG.AutomaticClient<Tid,List<TArticle>> data,
				CacheNG.Accessor<Tid,List<TArticle>> accessor );
	}
	
	static class TestSearchQuery_Tid implements TestSearchQuery {
		
		final Tid tid;
		
		public TestSearchQuery_Tid( Tid tid ) {
			this.tid = tid;
		}

		@Override
		public List<TArticle> execute(
				CacheNG.AutomaticClient<Tid,List<TArticle>> data,
				CacheNG.Accessor<Tid,List<TArticle>> accessor ){
			
			return data.fetch( tid, accessor );
		}

		@Override
		public int hashCode() {
			return tid.hashCode();
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof TestSearchQuery_Tid ) ) return false;
			
			TestSearchQuery_Tid other = (TestSearchQuery_Tid) obj;
			
			return tid.equals( other.tid );
		}
		
	}
	
	
	static class TidToQueryBridge extends CacheBridge<Tid,TestSearchQuery>{

		public TidToQueryBridge( CacheEventListener<TestSearchQuery> target ) {
			super( target );
		}

		@Override
		protected TestSearchQuery_Tid bridge( Tid tid ) {
			return new TestSearchQuery_Tid( tid );
		}
		
	}
	
	
	static class TestAccessor_ArticleForQuery 
			extends AbstractSingleValueAccessor<TestSearchQuery, List<TArticle>>
			implements CacheNG.Accessor<TestSearchQuery, List<TArticle>>{
		
		private final CacheNG.AutomaticClient<Tid,List<TArticle>> data;
		private final CacheNG.Accessor<Tid,List<TArticle>> accessor;

		public TestAccessor_ArticleForQuery( CacheNG.AutomaticClient<Tid,List<TArticle>> data,
				CacheNG.Accessor<Tid,List<TArticle>> accessor ) {
			
			this.data = data;
			this.accessor = accessor;
		}

		@Override
		public List<TArticle> fetch( TestSearchQuery identifier ) {
			
			return identifier.execute( data, accessor );
			
		}
		
	}
	
}
