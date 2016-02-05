package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGTest_Implementations.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.RN;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTest_ArticleForId.TestAccessor_ArticleForAid;

@Test( groups="cacheng.accessor" )
public class CacheNGTest_Updater {
	
	public void testUpdate() throws Exception {
		
		CacheNG.AutomaticClient<Aid, TArticle> auto =
				new AutomaticClientImpl<>( new CacheHashMap<>( RN.AID_ARTICLE, false ) );
		
		TestAccessor_ArticleForAid acc = new TestAccessor_ArticleForAid();
		
		assertNull( auto.fetch( A12345, acc ) );
	}

}
