package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGImplementations.Aid;
import de.axone.cache.ng.CacheNGImplementations.RN;
import de.axone.cache.ng.CacheNGImplementations.TArticle;
import de.axone.cache.ng.CacheNGImplementations.TestCacheBackend;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier;


// TODO: Test events for Automatic invalidation with Timeouts!!!

@Test( groups="helper.cacheng" )
public class CacheNGTest_Events {

	private CacheNG.Backend backend = new TestCacheBackend();
	
	public void listenersReceiveEvents() throws Exception {
		
		TestAccessor_ArticleForIdentifier accMaster = spy( new TestAccessor_ArticleForIdentifier() );
		TestAccessor_ArticleForIdentifier accSlave = spy( new TestAccessor_ArticleForIdentifier() );
		
		CacheNG.AutomaticClient<Aid, TArticle> autoMaster =
				spy( backend.<Aid,TArticle>automatic( RN.TOP_LARTICLE.unique() ) );
		
		CacheNG.AutomaticClient<Aid, TArticle> autoSlave =
				spy( backend.<Aid,TArticle>automatic( RN.TOP_LARTICLE.unique() ) );
		
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
		autoMaster.registerListener( autoSlave );
		
		// Fetch again
		art = autoMaster.fetch( A12345, accMaster );
		
		assertNotNull( art );
		assertThat( autoMaster ).hasCached( A12345 );
		verify( accMaster, times( 2 ) ).fetch( A12345 );
		
		// Invalidate connected caches
		autoMaster.invalidate( A12345 );
		
		verify( autoMaster, atLeastOnce() ).invalidate( A12345 ); // Yes. Same as line above
		verify( autoSlave ).invalidateEvent( A12345 ); // This is what we are looking for
		assertThat( autoMaster ).hasNotCached( A12345 );
		assertThat( autoSlave ).hasNotCached( A12345 );
		
		// So we need to fetch it again using the accessor
		art = autoSlave.fetch( A12345, accSlave );
		
		verify( accSlave, times( 2 ) ).fetch( A12345 );
		
	}
	
}
