package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNGImplementations.TestAutomaticClient;
import de.axone.cache.ng.CacheNGImplementations.TestCacheBackend;
import de.axone.cache.ng.CacheNGImplementations.TestRealm;
import de.axone.cache.ng.CacheNGImplementations.TimedCacheEntry;

@Test( groups="helper.cacheng" )
public class CacheNGTest_MultiValue {

	private static final String NOT_HERE = "NOT_HERE";
	private CacheNG.Backend backend = new TestCacheBackend();
	
	public void accessSameCachesFromMultipleClients() {
		
		CacheNG.Realm commonRealm = new TestRealm( "S->S" );
		
		CacheNG.Client<String, String> client1 =
				backend.cache( commonRealm );
		
		CacheNG.Client<String, String> client2 =
				backend.cache( commonRealm );
		
		assertThat( client1 )
				.hasNotCached( "foo" ).hasNotCached( "bar" );
		assertThat( client2 )
				.hasNotCached( "foo" ).hasNotCached( "bar" );
		
		client1.put( "foo", ""+123 );
		
		assertThat( client1 )
				.hasCached( "foo" ).hasNotCached( "bar" );
		assertThat( client2 )
				.hasCached( "foo" ).hasNotCached( "bar" );
		
		client1.put( "bar", ""+123 );
		
		assertThat( client1 )
				.hasCached( "foo" ).hasCached( "bar" );
		assertThat( client2 )
				.hasCached( "foo" ).hasCached( "bar" );
		
		client1.invalidate( "foo" );
		
		assertThat( client1 )
				.hasNotCached( "foo" ).hasCached( "bar" );
		assertThat( client2 )
				.hasNotCached( "foo" ).hasCached( "bar" );
		
	}
	
	
	private static final String TEST123 = "test123";
	
	public void accessMultiValuesViaOneBackendCache() {
		
		CacheNG.Client<String, MultiValueData> backendMulti =
				backend.cache( new TestRealm( "S->MultiValue" ) );
		
		TestStringAccessor strToStrAcc = new TestStringAccessor();
		
		TestAutomaticClient<String,String> frontendString =
				new TestAutomaticClient<String,String>(
						new MultiStringAccessor( backendMulti ) );
		
		TestIntegerAccessor strToIntAcc = new TestIntegerAccessor();
		
		TestAutomaticClient<String,Integer> frontendInteger =
				new TestAutomaticClient<String,Integer>(
						new MultiIntegerAccessor( backendMulti ) );
		
		assertThat( backendMulti ).hasNotCached( TEST123 );
		assertThat( frontendString ).hasNotCached( TEST123 );
		assertThat( frontendInteger ).hasNotCached( TEST123 );
		
		String valString = frontendString.fetch( TEST123, strToStrAcc );
		assertEquals( valString, TEST123 );
		
		assertThat( backendMulti ).hasCached( TEST123 );
		assertThat( frontendString ).hasCached( TEST123 );
		assertThat( frontendInteger ).hasNotCached( TEST123 );
		
		Integer valInteger = frontendInteger.fetch( TEST123, strToIntAcc );
		assertThat( valInteger ).isEqualTo( 7 );
		
		assertThat( backendMulti ).hasCached( TEST123 );
		assertThat( frontendString ).hasCached( TEST123 );
		assertThat( frontendInteger ).hasCached( TEST123 );
		
		frontendString.invalidate( TEST123 );
		
		assertThat( backendMulti ).hasCached( TEST123 );
		assertThat( frontendString ).hasNotCached( TEST123 );
		assertThat( frontendInteger ).hasCached( TEST123 );
		
		frontendInteger.invalidate( TEST123 );
		
		assertThat( backendMulti ).hasCached( TEST123 );
		assertThat( frontendString ).hasNotCached( TEST123 );
		assertThat( frontendInteger ).hasNotCached( TEST123 );
		
		backendMulti.invalidate( TEST123 );
		
		assertThat( backendMulti ).hasNotCached( TEST123 );
		assertThat( frontendString ).hasNotCached( TEST123 );
		assertThat( frontendInteger ).hasNotCached( TEST123 );
		
		// ---------------
		
		valString = frontendString.fetch( TEST123, strToStrAcc );
		assertEquals( valString, TEST123 );
		
		assertThat( backendMulti ).hasCached( TEST123 );
		assertThat( frontendString ).hasCached( TEST123 );
		assertThat( frontendInteger ).hasNotCached( TEST123 );
		
		backendMulti.invalidate( TEST123 );
		
		assertThat( backendMulti ).hasNotCached( TEST123 );
		assertThat( frontendString ).hasNotCached( TEST123 );
		assertThat( frontendInteger ).hasNotCached( TEST123 );
		
	}
	
	public void invalidationDoesTheRightThing() {
		
		CacheNG.Client<String, MultiValueData> backendMulti =
				backend.cache( new TestRealm( "S->MultiValue" ) );
		
		TestStringAccessor strToStrAcc = new TestStringAccessor();
		
		TestAutomaticClient<String,String> frontendString =
				new TestAutomaticClient<String,String>(
						new MultiStringAccessor( backendMulti ) );
		
		assertThat( frontendString ).hasNotCached( NOT_HERE );
		assertThat( backendMulti ).hasNotCached( NOT_HERE );
		String value = frontendString.fetch( NOT_HERE, strToStrAcc );
		assertEquals( value, null );
		assertThat( frontendString ).hasCached( NOT_HERE );
		assertThat( backendMulti ).hasCached( NOT_HERE );
		
		frontendString.invalidate( NOT_HERE );
		assertThat( frontendString ).hasNotCached( NOT_HERE );
		assertThat( backendMulti ).hasCached( NOT_HERE );
		
		backendMulti.invalidate( NOT_HERE );
		assertThat( frontendString ).hasNotCached( NOT_HERE );
		assertThat( backendMulti ).hasNotCached( NOT_HERE );
		
	}
	
	
	static class MultiValueData {
		TimedCacheEntry<String> stringValue;
		TimedCacheEntry<Integer> integerValue;
	}
	
	static abstract class AbstractMultiDataAccessor<MV,O> implements CacheNG.Client<String,TimedCacheEntry<O>> {
		
		private static final TimedCacheEntry<String> NOVAL = new TimedCacheEntry<>( "NOVAL" );
		
		private final CacheNG.Client<String,MV> wrapped;

		public AbstractMultiDataAccessor( Client<String, MV> wrapped ) {
			this.wrapped = wrapped;
		}
		
		protected abstract TimedCacheEntry<O> get( MV data );
		protected abstract void put( MV data, TimedCacheEntry<O> value );
		protected abstract MV create();

		@Override
		public synchronized TimedCacheEntry<O> fetch( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return null;
			TimedCacheEntry<O> result = get( data );
			if( result == NOVAL ) return null;
			return result;
		}

		@Override
		public synchronized boolean isCached( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return false;
			TimedCacheEntry<O> value = get( data );
			return value != null;
		}

		@Override
		public synchronized void invalidate( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return;
			put( data, null );
			wrapped.put( key, data );
		}

		@Override
		public synchronized void put( String key, TimedCacheEntry<O> value ) {
			MV data = wrapped.fetch( key );
			if( data == null ){
				data = create();
			}
			put( data, value );
			
			// store again (probably needed for distributed caches)
			wrapped.put( key, data );
		}
		
	}
	
	static class MultiStringAccessor extends AbstractMultiDataAccessor<MultiValueData,String> {

		public MultiStringAccessor( Client<String, MultiValueData> wrapped ) {
			super( wrapped );
		}

		@Override
		protected TimedCacheEntry<String> get( MultiValueData data ) {
			return data.stringValue;
		}

		@Override
		protected void put( MultiValueData data, TimedCacheEntry<String> value ) {
			data.stringValue = value;
		}

		@Override
		protected MultiValueData create() {
			return new MultiValueData();
		}
		
	}
	
	
	static class MultiIntegerAccessor extends AbstractMultiDataAccessor<MultiValueData,Integer> {

		public MultiIntegerAccessor( Client<String, MultiValueData> wrapped ) {
			super( wrapped );
		}

		@Override
		protected TimedCacheEntry<Integer> get( MultiValueData data ) {
			return data.integerValue;
		}

		@Override
		protected void put( MultiValueData data, TimedCacheEntry<Integer> value ) {
			data.integerValue = value;
		}

		@Override
		protected MultiValueData create() {
			return new MultiValueData();
		}
		
	}
	
	
	static class TestStringAccessor implements CacheNG.Accessor<String,String> {
		@Override
		public String fetch( String key ) {
			if( key.equals( NOT_HERE ) ) return null;
			return key;
		}
	}
	
	static class TestIntegerAccessor implements CacheNG.Accessor<String,Integer> {
		@Override
		public Integer fetch( String key ) {
			if( key.equals( NOT_HERE ) ) return null;
			return key.length();
		}
	}
	
	
}
