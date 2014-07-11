package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNGImplementations.TestAutomaticClient;
import de.axone.cache.ng.CacheNGImplementations.TestClient;
import de.axone.cache.ng.CacheNGImplementations.TestClient.TestEntry;

@Test( groups="helper.cacheng" )
public class CacheNGTest_MultiValue {

	private static final String NOT_HERE = "NOT_HERE";
	
	private static final String TEST123 = "test123";
	
	public void accessMultiValuesViaOneBackendCache() {
		
		CacheNG.Client<String, MultiValueData> backendMulti =
				new TestClient<>();
		
		TestStringAccessor strToStrAcc = new TestStringAccessor();
		
		TestAutomaticClient<String,String> frontendString = new TestAutomaticClient<>(
				new MultiStringAccessor( backendMulti ) );
		
		TestIntegerAccessor strToIntAcc = new TestIntegerAccessor();
		
		TestAutomaticClient<String,Integer> frontendInteger = new TestAutomaticClient<>(
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
				new TestClient<>();
		
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
		CacheNG.Client.Entry<String> stringValue;
		CacheNG.Client.Entry<Integer> integerValue;
	}
	
	
	static abstract class AbstractMultiDataAccessor<MV,O> implements CacheNG.Client<String,O> {
		
		private final CacheNG.Client<String,MV> wrapped;

		public AbstractMultiDataAccessor( Client<String, MV> wrapped ) {
			this.wrapped = wrapped;
		}
		
		protected abstract CacheNG.Client.Entry<O> get( MV data );
		protected abstract void put( MV data, CacheNG.Client.Entry<O> value );
		protected abstract MV create();

		@Override
		public synchronized CacheNG.Client.Entry<O> fetchEntry( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return null;
			CacheNG.Client.Entry<O> result = get( data );
			return result;
		}
		
		@Override
		public synchronized O fetch( String key ) {
			CacheNG.Client.Entry<O> entry = fetchEntry( key );
			if( entry == null ) return null;
			return entry.data();
		}

		@Override
		public synchronized boolean isCached( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return false;
			CacheNG.Client.Entry<O> value = get( data );
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
		public synchronized void putEntry( String key, CacheNG.Client.Entry<O> value ) {
			MV data = wrapped.fetch( key );
			if( data == null ){
				data = create();
			}
			put( data, value );
			
			// store again (probably needed for distributed caches)
			wrapped.put( key, data );
		}
		
		@Override
		public void put( String key, O value ) {
			
			putEntry( key, new TestEntry<>( value ) );
		}

	}
	
	static class MultiStringAccessor extends AbstractMultiDataAccessor<MultiValueData,String> {

		public MultiStringAccessor( Client<String, MultiValueData> wrapped ) {
			super( wrapped );
		}

		@Override
		protected CacheNG.Client.Entry<String> get( MultiValueData data ) {
			return data.stringValue;
		}

		@Override
		protected void put( MultiValueData data, CacheNG.Client.Entry<String> value ) {
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
		protected CacheNG.Client.Entry<Integer> get( MultiValueData data ) {
			return data.integerValue;
		}

		@Override
		protected void put( MultiValueData data, CacheNG.Client.Entry<Integer> value ) {
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
