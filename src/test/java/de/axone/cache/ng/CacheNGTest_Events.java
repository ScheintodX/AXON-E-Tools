package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForAid;


// TODO: Test events for Automatic invalidation with Timeouts!!!

@Test( groups="cacheng.events" )
public class CacheNGTest_Events {

	public void listenersReceiveEvents() throws Exception {
		
		TestAccessor_ArticleForAid accMaster = spy( new TestAccessor_ArticleForAid() );
		TestAccessor_ArticleForAid accSlave = spy( new TestAccessor_ArticleForAid() );
		
		CacheHashMap<Aid,TArticle> cacheMaster =
				spy( new CacheHashMap<>( RN.AID_ARTICLE.unique(), false ) );
		
		CacheNG.AutomaticClient<Aid, TArticle> autoMaster =
				spy( new AutomaticClientImpl<Aid,TArticle>( cacheMaster ) );
		
		CacheHashMap<Aid,TArticle> cacheSlave =
				spy( new CacheHashMap<>( RN.AID_ARTICLE.unique(), false ) );
		
		CacheNG.AutomaticClient<Aid, TArticle> autoSlave =
				spy( new AutomaticClientImpl<Aid,TArticle>( cacheSlave ) );
		
		TArticle art;
		
		art = autoMaster.fetch( A12345, accMaster );
		
		assertNotNull( art );
		assertThat( autoMaster ).hasCached( A12345 );
		verify( accMaster ).fetch( A12345 );
		
		art = autoSlave.fetch( A12345, accSlave );
		
		assertNotNull( art );
		assertThat( autoMaster ).hasCached( A12345 );
		verify( accSlave ).fetch( A12345 );
		
		// Not connected so we keep the slave's content
		autoMaster.invalidate( A12345 );
		
		verify( autoMaster ).invalidate( A12345 ); // Yes. Same as line above
		verify( accSlave, never() ).invalidateEvent( A12345 ); // Does not happen right now
		assertThat( autoMaster ).hasNotCached( A12345 );
		assertThat( autoSlave ).hasCached( A12345 );
		
		// === Connect caches ================================
		cacheMaster.registerListener( cacheSlave );
		
		// Fetch again
		art = autoMaster.fetch( A12345, accMaster );
		
		assertNotNull( art );
		assertThat( autoMaster ).hasCached( A12345 );
		verify( accMaster, times( 2 ) ).fetch( A12345 );
		
		// Invalidate connected caches
		autoMaster.invalidate( A12345 );
		
		verify( autoMaster, atLeastOnce() ).invalidate( A12345 ); // Yes. Same as line above
		verify( cacheSlave ).invalidateEvent( A12345 ); // This is what we are looking for
		assertThat( autoMaster ).hasNotCached( A12345 );
		assertThat( autoSlave ).hasNotCached( A12345 );
		
		// So we need to fetch it again using the accessor
		art = autoSlave.fetch( A12345, accSlave );
		
		verify( accSlave, times( 2 ) ).fetch( A12345 );
		
	}
	
}
