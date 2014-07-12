package de.axone.cache.ng;


import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier;

@Test( groups="helper.testng" )
public class CacheNGTest_AccessorUsage {

	public void accessorIsOnlyUsedWhenNeeded(){
		
		CacheNG.AutomaticClient<Aid, TArticle> auto = new AutomaticClientImpl<>( RN.AID_ARTICLE );
		
		TestAccessor_ArticleForIdentifier acc = spy( new TestAccessor_ArticleForIdentifier() );
		
		verify( acc, never() ).fetch( A12345 );
		
		CacheNGTestHelpers.TArticle art;
		art = auto.fetch( A12345, acc );
		assertNotNull( art );
		
		// Now we hat one call to accessor
		verify( acc, times( 1 ) ).fetch( A12345 );
		
		art = auto.fetch( A12345, acc );
		assertNotNull( art );
		
		// this one was cached so no call
		verify( acc, times( 1 ) ).fetch( A12345 );
		
		auto.invalidate( A12345 );
		art = auto.fetch( A12345, acc );
		
		// Now we had to get it again, make it two calls
		verify( acc, times( 2 ) ).fetch( A12345 );
	}
	
}
