package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGImplementations.TestCacheBackend;
import de.axone.cache.ng.CacheNGImplementations.TestRealm;

@Test( groups="helper.cacheng" )
public class CacheNGTest_MultiValue {

	private CacheNG.Backend backend = new TestCacheBackend();
	
	public void accessSameCachesFromMultipleClients() {
		
		CacheNG.Realm commonRealm = new TestRealm( "S->S" );
		
		CacheNG.Client<String, String> client1 =
				backend.cache( commonRealm );
		
		CacheNG.Client<String, String> client2 =
				backend.cache( commonRealm );
		
		assertThat( client1 )
				.doesNotContain( "foo" ).doesNotContain( "bar" );
		assertThat( client2 )
				.doesNotContain( "foo" ).doesNotContain( "bar" );
		
		client1.put( "foo", ""+123 );
		
		assertThat( client1 )
				.contains( "foo" ).doesNotContain( "bar" );
		assertThat( client2 )
				.contains( "foo" ).doesNotContain( "bar" );
		
		client1.put( "bar", ""+123 );
		
		assertThat( client1 )
				.contains( "foo" ).contains( "bar" );
		assertThat( client2 )
				.contains( "foo" ).contains( "bar" );
		
		client1.remove( "foo" );
		
		assertThat( client1 )
				.doesNotContain( "foo" ).contains( "bar" );
		assertThat( client2 )
				.doesNotContain( "foo" ).contains( "bar" );
		
	}
	
	public void accessMultiValuesViaOneBackendCache() {
		
		CacheNG.Realm multivalueRealm = new TestRealm( "S->MultiValue" );
		
		CacheNG.Client<String, MultiValueData> backendMulti =
				backend.cache( multivalueRealm );
		
		CacheNG.AutomaticClient<String, String> frontendString =
				backend.automatic( multivalueRealm, null );
		
		CacheNG.AutomaticClient<String, Integer> frontendInteger =
				backend.automatic( multivalueRealm, null );
		
		
		// Problem hier ist, dass die Schnittstelle nur einen Type hat.
		// Eine get/set opetration dagegen hat probleme mit der
		// synchronisation
		
	}
	
	static class MultiValueData {
		String val1;
		Integer val2;
	}
	
	static class MultiValueAccessor<V> implements CacheNG.Accessor<String,V> {

		@Override
		public V get( String key ) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
}
