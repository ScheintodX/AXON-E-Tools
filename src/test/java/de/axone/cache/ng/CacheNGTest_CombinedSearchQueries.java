package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;
import de.axone.cache.ng.CacheNGTest_ArticleListForSearchQuery.TestAccessor_ArticleForQuery;
import de.axone.cache.ng.CacheNGTest_ArticleListForSearchQuery.TestSearchQuery;
import de.axone.cache.ng.CacheNGTest_ArticleListForSearchQuery.TestSearchQuery_Tid;
import de.axone.cache.ng.CacheNGTest_ArticleListForSearchQuery.TidToQueryBridge;
import de.axone.cache.ng.CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid;
import de.axone.cache.ng.CacheNGTest_ArticleListForTid.TestMapTidToArticle;

//TODO: Notiz: Die echten Search-Queries sollen von ArticleQuery auf irgendwas
// wie "ExecutorService" umgestellt werden. Die Exekutoren könnten von den jew.
// Backends order von einer Factory erzeugt werden.

@Test( groups="helper.testng" )
public class CacheNGTest_CombinedSearchQueries {
	
	TestMapTidToArticle data = new TestMapTidToArticle();
	{
		data.addArticle( TArticle.build( A12345 ) );
		data.addArticle( TArticle.build( A12346 ) );
		data.addArticle( TArticle.build( A12347 ) );
	}
	
	static int i=1;
	
	TestSearchQuery_Tid t123  = new TestSearchQuery_Tid( T123 ),
	                    t123_ = new TestSearchQuery_Tid( tid( i+"23" ) ),
			    		t234  = new TestSearchQuery_Tid( T234 )
	                    ;
	
	CombinedSearchQuery qq1234  = new CombinedSearchQuery( t123, t234 ),
	                    qq1234_ = new CombinedSearchQuery( t123_, t234 ),
	                    qq2345  = new CombinedSearchQuery( t234, t234 )
	                    ;
	
	public void hashCodeAndEquals(){
		
		assertThat( qq1234 )
				.isEqualTo( qq1234_ )
				.isNotEqualTo( qq2345 )
				.is( havingSameHashCodeAs( qq1234_ ) )
				.isNot( havingSameHashCodeAs( qq2345 ) )
		        ;
	}
	
	public void basicQueryOperations() {
		
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
		
		assertFalse( autoForQuery.isCached( qq1234 ) );
		List<TArticle> arts = autoForQuery.fetch( qq1234, forQuery );
		assertThat( arts )
				.areExactly( 1, havingIdentifier( aid( "12345" ) ) )
				.areExactly( 1, havingIdentifier( aid( "12346" ) ) )
				.hasSize( 2 )
				;
		assertTrue( autoForQuery.isCached( qq1234 ) );
		
	}

	static class CombinedSearchQuery implements TestSearchQuery {
		
		final List<TestSearchQuery> subQueries;
		public CombinedSearchQuery( TestSearchQuery ... subQueries ) {
			this.subQueries = Arrays.asList( subQueries );
		}
		
		@Override
		public List<TArticle> execute( CacheNG.AutomaticClient<Tid, List<TArticle>> data,
				CacheNG.Accessor<Tid, List<TArticle>> accessor ) {
			
			Set<TArticle> result = new LinkedHashSet<TArticle>();
					
			for( TestSearchQuery sub : subQueries ){
				
				result.addAll( sub.execute( data, accessor ) );
			}
					
			return new ArrayList<>( result );
		}

		@Override
		public int hashCode() {
			return subQueries.hashCode();
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof CombinedSearchQuery ) ) return false;
			
			CombinedSearchQuery other = (CombinedSearchQuery) obj;
			
			return subQueries.equals( other.subQueries );
		}
		
	}
	
}
