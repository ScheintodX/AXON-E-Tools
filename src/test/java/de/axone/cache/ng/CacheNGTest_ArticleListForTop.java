package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.CacheBridge;
import de.axone.cache.ng.CacheNGTestHelpers.Identifiable;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;
import de.axone.cache.ng.CacheNGTest_ArticleListForTid.TestAccessor_ArticleForTid;
import de.axone.cache.ng.CacheNGTest_ArticleListForTid.TestMapTidToArticle;

@Test( groups="cacheng.events" )
public class CacheNGTest_ArticleListForTop {

	static final Top 
			T001 = top( "001" ),
			T002 = top( "002" )
			;
	
	private static final TestMapTidToArticle data = new TestMapTidToArticle();
	{
		data.addArticle( TArticle.build( A12345 ) );
		data.addArticle( TArticle.build( A12346 ) );
		data.addArticle( TArticle.build( A12347 ) );
	}
	
	TidForTop tidForTop = new TidForTop();
	
	
	public void cacheArticlesForTopAndInvalidateViaTid(){
		
		TestAccessor_ArticleForTid accessorForTid =
				new TestAccessor_ArticleForTid( data );
		
		CacheHashMap<Tid, List<TArticle>> cacheForTid =
				new CacheHashMap<>( RN.TID_LARTICLE );
				
		CacheNG.AutomaticClient<Tid, List<TArticle>> autoForTid =
				new AutomaticClientImpl<>( cacheForTid );
		
		TestAccessor_ArticleForTop accessorForTop =
				new TestAccessor_ArticleForTop( autoForTid, accessorForTid, tidForTop );
		
		CacheHashMap<Top, List<TArticle>> cacheForTop =
				new CacheHashMap<>( RN.TOP_LARTICLE );
		
		CacheNG.AutomaticClient<Top, List<TArticle>> autoForTop =
				new AutomaticClientImpl<>( cacheForTop );
		
		cacheForTid.registerListener( new TidToTopBridge( cacheForTop, tidForTop ) );
		
		
		assertThat( autoForTop )
				.hasNotCached( T001 )
				.hasNotCached( T002 );
		
		List<TArticle> arts = autoForTop.fetch( T001, accessorForTop );
		assertThat( autoForTop )
				.hasCached( T001 )
				.hasNotCached( T002 );
		
		assertThat( arts )
				.are( havingTid( T123 ) )      // T001 is top of T123
				.areNot( havingTid( T999 ) );  // But not stored in article
		
		arts = autoForTop.fetch( T002, accessorForTop );
		assertThat( arts ).hasSize( 2 )
				.are( havingTid( T345 ) )      // T002 is top of T345 and T456
				.areAtLeast( 1, havingTid( T234 ) )
				.areAtLeast( 1, havingTid( T456 ) )
				.areNot( havingTid( T123 ) )
				.areNot( havingTid( T999 ) )
		;
		
		// Invalidating T002 leeves us with cached T001 ...
		autoForTop.invalidate( T002 );
		assertThat( autoForTop )
				.hasNotCached( T002 )
				.hasCached( T001 );
		
		// Which we try to remove using the master Cache
		// On import of articles: diff oldtids/newtids
		// Invalidation is done only on the article's tids
		autoForTid.invalidate( T123 );
		
		// Invalidation must propagate to top
		assertThat( autoForTop )
				.hasNotCached( T002 )
				.hasNotCached( T001 );
	}
	
	
	static class Top extends Identifiable {
		public Top( String identifier ) {
			super( identifier );
		}
	}
	static Top top( String identifier ){
		return new Top( identifier );
	}
	
	
	private static class TidForTop {
		
		private static Map<Top,List<Tid>> tree = new HashMap<>();
		static {
			tree.put( T001, Arrays.asList( T123 ) );
			tree.put( T002, Arrays.asList( T345, T456 ) );
		}
		
		List<Tid> tidForTop( Top top ){
			
			return tree.get( top );
			
		}
		
		Top topForTid( Tid tid ){
			
			for( Map.Entry<Top, List<Tid>> entry : tree.entrySet() ){
				
				if( entry.getValue().contains( tid ) )
						return entry.getKey();
			}
			return null;
		}
	}
	
	
	public static class TestAccessor_ArticleForTop
			implements CacheNG.SingleValueAccessor<Top, List<TArticle>> {

		private final CacheNG.AutomaticClient<Tid, List<TArticle>> forTid;
		private final CacheNG.SingleValueAccessor<Tid,List<TArticle>> accessor;
		private final TidForTop tft;
		
		public TestAccessor_ArticleForTop(
				CacheNG.AutomaticClient<Tid, List<TArticle>> forTid,
				CacheNG.SingleValueAccessor<Tid,List<TArticle>> accessor, TidForTop tft) {
			this.forTid = forTid;
			this.accessor = accessor;
			this.tft = tft;
		}

		@Override
		public List<TArticle> fetch( Top top ) {
			
			List<Tid> path = tft.tidForTop( top );
			List<TArticle> result = Collections.emptyList();
			
			for( Tid tid : path ){
				result = union( result, forTid.fetch( tid, accessor ) );
			}
			
			return result;
		}

	}
	
	private static final class TidToTopBridge extends CacheBridge<Tid,Top> {
		
		private final TidForTop tft;
		
		public TidToTopBridge( CacheNG.CacheEventListener<Top> cacheForTop, TidForTop tft ) {
			
			super( cacheForTop );
			
			this.tft = tft;
		}

		@Override
		protected Top bridge( Tid tid ) {
			return tft.topForTid( tid );
		}

	}
	
}
