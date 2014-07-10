package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGImplementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGImplementations.Aid;
import de.axone.cache.ng.CacheNGImplementations.TArticle;
import de.axone.cache.ng.CacheNGImplementations.TestAutomaticClient;
import de.axone.cache.ng.CacheNGImplementations.TestClient;
import de.axone.cache.ng.CacheNGImplementations.Tid;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier;


@Test(groups="helper.cacheng" )
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
		
		TestAccessor_ArticleForIdentifier acc = new TestAccessor_ArticleForIdentifier();
		
		TArticle tart = acc.get( A12345 );
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
		
		TestAccessor_ArticleForIdentifier acc = new TestAccessor_ArticleForIdentifier();
		
		TArticle tart = acc.get( A_FAIL );
		
		assertNull( tart );
	}
	
	public void storeAndRestoreFromTestCache() {
		
		TestClient<Aid,TArticle> client = new TestClient<>();
		
		TArticle tart = TArticle.build( A12345 );
		
		assertThat( client ).doesNotContain( aid( "12345" ) );
		client.put( tart.getIdentifier(), tart );
		assertThat( client ).contains( aid( "12345" ) );
		
		TArticle restored = client.get( aid( "12345" ) );
		
		assertThat( restored ).isEqualTo( tart );
		client.remove( aid( "12345" ) );
		assertThat( client ).doesNotContain( aid( "12345" ) );
		
		TArticle removed = client.get( aid( "12345" ) );
		
		assertNull( removed );
	}
	
	public void buildByTestAutoCache() {
		
		TestAutomaticClient<Aid, TArticle> auto =
				new TestAutomaticClient<>( new TestAccessor_ArticleForIdentifier() );
		
		assertThat( auto ).hasNotCached( A12345 )
				.lookingInBackend().doesNotContain( A12345 );
			
		TArticle art = auto.fetch( A12345 );
		assertThat( auto ).hasCached( A12345 )
				.lookingInBackend().contains( A12345 );
		
		assertThat( art ).is( havingIdentifier( aid( "12345" ) ) )
				.is( havingTid( T123 ) )
				.is( havingTid( T234 ) )
				;
		
		assertThat( auto ).hasNotCached( A_FAIL )
				.lookingInBackend().doesNotContain( A_FAIL );
		
		TArticle failed = auto.fetch( A_FAIL );
		assertThat( failed ).isNull();
		
		assertThat( auto ).hasCached( A_FAIL )
				.lookingInBackend().contains( A_FAIL );
	}
	
	public void buildByListAccessor(){
		
		TArticle a12345 = TArticle.build( A12345 );
		TArticle a12346 = TArticle.build( A12346 );
		
		CacheNGTest_ArticleListForTid.TestMapTidToArticle data = new CacheNGTest_ArticleListForTid.TestMapTidToArticle();
		data.addArticle( a12345 );
		data.addArticle( a12346 );
		
		CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid acc = new CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid( data );
		
		assertThat( acc.get( T123 ) ).hasSize( 1 ).isNotNull();
		assertThat( acc.get( T234 ) ).hasSize( 2 ).isNotNull();
		assertThat( acc.get( T345 ) ).hasSize( 1 ).isNotNull();
		
		TestAutomaticClient<Tid, List<TArticle>> auto =
				new TestAutomaticClient<>( acc );
		
		assertThat( auto ).hasNotCached( T123 )
				.lookingInBackend().doesNotContain( T123 );
		
		List<TArticle> forList123 = auto.fetch( T123 );
		assertThat( forList123 )
				.contains( a12345 )
				.hasSize( 1 )
				;
		
		assertThat( auto ).hasCached( T123 )
				.lookingInBackend().contains( T123 );
		
		
		assertThat( auto ).hasNotCached( T234 )
				.lookingInBackend().doesNotContain( T234 );
		
		List<TArticle> forList234 = auto.fetch( T234 );
		assertThat( forList234 )
				.contains( a12345 )
				.contains( a12346 )
				.hasSize( 2 )
				;
		
		assertThat( auto ).hasCached( T234 )
				.lookingInBackend().contains( T234 );
		
		
		assertThat( auto ).hasNotCached( T345 )
				.lookingInBackend().doesNotContain( T345 );
		
		List<TArticle> forList345 = auto.fetch( T345 );
		assertThat( forList345 )
				.contains( a12346 )
				.hasSize( 1 )
				;
		
		assertThat( auto ).hasCached( T345 )
				.lookingInBackend().contains( T345 );
		
	}
	
}
