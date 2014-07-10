package de.axone.cache.ng;


import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="helper.testng" )
public class CacheNGTest_AccessorUsage {

	public void accessorIsOnlyUsedWhenNeeded(){
		
		CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier accessor = spy( new CacheNGTest_ArticleForId.TestAccessor_ArticleForIdentifier() );
		
		CacheNGImplementations.TestAutomaticClient<CacheNGImplementations.Aid, CacheNGImplementations.TArticle> auto = new CacheNGImplementations.TestAutomaticClient<>( accessor );
		
		verify( accessor, never() ).get( A12345 );
		
		CacheNGImplementations.TArticle art;
		art = auto.fetch( A12345 );
		assertNotNull( art );
		
		// Now we hat one call to accessor
		verify( accessor, times( 1 ) ).get( A12345 );
		
		art = auto.fetch( A12345 );
		assertNotNull( art );
		
		// this one was cached so no call
		verify( accessor, times( 1 ) ).get( A12345 );
		
		auto.invalidate( A12345 );
		art = auto.fetch( A12345 );
		
		// Now we had to get it again, make it two calls
		verify( accessor, times( 2 ) ).get( A12345 );
	}
	
}
