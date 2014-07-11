package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGImplementations.*;
import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGImplementations.Aid;
import de.axone.cache.ng.CacheNGImplementations.TArticle;
import de.axone.cache.ng.CacheNGImplementations.TestClient;

@Test(groups="helper.cacheng" )
public class CacheNGAssertTest {

	public void checkAssertJForCache(){
		
		TestClient<Aid,TArticle> client = new TestClient<>();
		
		assertThat( client ).hasNotCached( aid( "12345" ) );
		try {
			assertThat( client ).hasCached( aid( "12345" ) );
			Assert.fail( "Should have failed" );
		} catch( Error e ){
			assertThat( e ).isInstanceOf( AssertionError.class )
					.hasMessageContaining( aid( "12345" ).toString() );
		}
		
		client.put( TART12345.getIdentifier(), TART12345 );
		
		assertThat( client ).hasCached( aid( "12345" ) )
				.hasNotCached( aid( "12346" ) );
		
		client.put( TART12346.getIdentifier(), TART12346 );
		
		assertThat( client ).hasCached( aid( "12345" ) );
		assertTrue( client.isCached( aid( "12345" ) ) ); // Yes. Direct access here for the test.
		assertThat( client ).hasCached( aid( "12346" ) );
	}
	
}
