package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForAid;


@Test(groups="cacheng.helper" )
public class CacheNGTest_Implementations {
	
	static final Tid  T123 = tid( "123" ),
	                     T234 = tid( "234" ),
	                     T345 = tid( "345" ),
	                     T456 = tid( "456" ),
	                     T999 = tid( "999" );
	
	static final Aid A12345 = aid( "+12345:123:234" ),
	                    A12346 = aid( "+12346:234:345" ),
	                    A12347 = aid( "+12347:345:456" ),
	                    A_FAIL = aid( "-12345:123:234" )
	                    ;
	
	static final TArticle TART12345 = TArticle.build( A12345 ),
	                      TART12346 = TArticle.build( A12346 ),
	                      TART12347 = TArticle.build( A12347 );
	
	int i=123;
	
	public void identifiableEqualsHashCodeEctWorking() {
		
		Aid aid = aid( "123" ),
		    aid2 = aid( ""+i ),
		    aid3 = aid( "999" );
		
		Tid tid = tid( "123" );
		
		assertThat( aid )
				.isEqualTo( aid2 )
				.isNotEqualTo( aid3 )
				.isNotEqualTo( tid );
				;
		
		assertThat( Arrays.asList( aid, aid2 ) )
				.isEqualTo( Arrays.asList( aid, aid ) )
				.isNotEqualTo( Arrays.asList( aid, tid ) )
				;
		
	}
	
	public void buildTArticleUsingBuilder(){
		
		TArticle tart = TArticle.build( A12345 );
		
		assertThat( tart.getIdentifier() )
				.isEqualTo( aid( "12345" ) )
				;
		
		assertThat( tart.getTreeIdentifiers() )
				.contains( T123, T234 )
				.hasSize( 2 )
				;
		
		assertThat( tart )
				.is( havingIdentifier( aid( "12345" ) ) )
				.isNot( havingIdentifier( aid( "00001" ) ) )
				.is( havingTid( T123 ) )
				.is( havingTid( T234 ) )
				.isNot( havingTid( T999 ) )
				;
		
	}
	
	public void buildArticleUsingAccessor() {
		
		TestAccessor_ArticleForAid acc = new TestAccessor_ArticleForAid();
		
		TArticle tart = acc.fetch( A12345 );
		assertThat( tart )
				.is( havingIdentifier( aid( "12345" ) ) )
				.is( havingTid( T123 ) )
				.is( havingTid( T234 ) )
				;
				
	}
	
	public void useUnion(){
		
		
		List<TArticle> list1 = Arrays.asList( TART12345, TART12346 ),
		               list2 = Arrays.asList( TART12346, TART12347 );
		
		List<TArticle> union = union( list1, list2 );
		
		List<TArticle> expected = Arrays.asList( TART12345, TART12346, TART12347 );
		
		assertThat( union ).isEqualTo( expected );
		
	}
	
	public void storeAndRetrievArticleFromTestMap() {
		
		CacheNGTest_ArticleListForTid.TestMapTidToArticle data = new CacheNGTest_ArticleListForTid.TestMapTidToArticle();
		
		assertThat( data ).isEmpty();
		
		data.addArticle( TART12345 );
		
		assertThat( data ).hasSize( 2 )
				.containsKey( T123 )
				.containsKey( T234 )
				.containsValue( Arrays.asList( TART12345 ) )
				;
		
		assertThat( data.get( T123 ) ).containsOnly( TART12345 );
		assertThat( data.get( T234 ) ).containsOnly( TART12345 );
		
		data.addArticle( TART12346 );
		
		assertThat( data ).hasSize( 3 )
				.containsKey( T123 )
				.containsKey( T234 )
				.containsKey( T345 )
				.containsValue( Arrays.asList( TART12345 ) )
				.containsValue( Arrays.asList( TART12346 ) )
				;
		
		assertThat( data.get( T123 ) ).containsOnly( TART12345 );
		assertThat( data.get( T234 ) ).contains( TART12345, TART12346 );
		assertThat( data.get( T345 ) ).containsOnly( TART12346 );
	}
	
	public void accessorFailsToFetchArticleWithMinus() {
		
		TestAccessor_ArticleForAid acc = new TestAccessor_ArticleForAid();
		
		TArticle tart = acc.fetch( A_FAIL );
		
		assertNull( tart );
	}
	
	public void storeAndRestoreFromTestCache() {
		
		CacheNG.Cache<Aid,TArticle> client = new CacheHashMap<>( RN.AID_ARTICLE );
		
		TArticle tart = TArticle.build( A12345 );
		
		assertThat( client ).hasNotCached( aid( "12345" ) );
		client.put( tart.getIdentifier(), tart );
		assertThat( client ).hasCached( aid( "12345" ) );
		
		TArticle restored = client.fetch( aid( "12345" ) );
		
		assertThat( restored ).isEqualTo( tart );
		client.invalidate( aid( "12345" ) );
		assertThat( client ).hasNotCached( aid( "12345" ) );
		
		TArticle removed = client.fetch( aid( "12345" ) );
		
		assertNull( removed );
	}
	
	public void buildByTestAutoCache() {
		
		CacheNG.AutomaticClient<Aid, TArticle> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.AID_ARTICLE ) );
				
		TestAccessor_ArticleForAid accessor = new TestAccessor_ArticleForAid();
		
		assertThat( auto ).hasNotCached( A12345 )
				.lookingInBackend().hasNotCached( A12345 );
			
		TArticle art = auto.fetch( A12345, accessor );
		assertThat( auto ).hasCached( A12345 )
				.lookingInBackend().hasCached( A12345 );
		
		assertThat( art ).is( havingIdentifier( aid( "12345" ) ) )
				.is( havingTid( T123 ) )
				.is( havingTid( T234 ) )
				;
		
		assertThat( auto ).hasNotCached( A_FAIL )
				.lookingInBackend().hasNotCached( A_FAIL );
		
		TArticle failed = auto.fetch( A_FAIL, accessor );
		assertThat( failed ).isNull();
		
		assertThat( auto ).hasCached( A_FAIL )
				.lookingInBackend().hasCached( A_FAIL );
	}
	
	public void buildByListAccessor(){
		
		TArticle a12345 = TArticle.build( A12345 );
		TArticle a12346 = TArticle.build( A12346 );
		
		CacheNGTest_ArticleListForTid.TestMapTidToArticle data = new CacheNGTest_ArticleListForTid.TestMapTidToArticle();
		data.addArticle( a12345 );
		data.addArticle( a12346 );
		
		CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid acc = new CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid( data );
		
		assertThat( acc.fetch( T123 ) ).hasSize( 1 ).isNotNull();
		assertThat( acc.fetch( T234 ) ).hasSize( 2 ).isNotNull();
		assertThat( acc.fetch( T345 ) ).hasSize( 1 ).isNotNull();
		
		CacheNG.AutomaticClient<Tid, List<TArticle>> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.TID_LARTICLE ) );
		
		assertThat( auto ).hasNotCached( T123 )
				.lookingInBackend().hasNotCached( T123 );
		
		assertThat( auto.fetch( T123, acc ) )
				.contains( a12345 )
				.hasSize( 1 )
				;
		
		assertThat( auto ).hasCached( T123 )
				.lookingInBackend().hasCached( T123 );
		
		
		assertThat( auto ).hasNotCached( T234 )
				.lookingInBackend().hasNotCached( T234 );
		
		assertThat( auto.fetch( T234, acc ) )
				.contains( a12345 )
				.contains( a12346 )
				.hasSize( 2 )
				;
		
		assertThat( auto ).hasCached( T234 )
				.lookingInBackend().hasCached( T234 );
		
		
		assertThat( auto ).hasNotCached( T345 )
				.lookingInBackend().hasNotCached( T345 );
		
		assertThat( auto.fetch( T345, acc ) )
				.contains( a12346 )
				.hasSize( 1 )
				;
		
		assertThat( auto ).hasCached( T345 )
				.lookingInBackend().hasCached( T345 );
		
	}
	
}
