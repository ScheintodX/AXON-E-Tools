package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.Set;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNGTestHelpers.TestRealm;

@Test( groups="cacheng.multivalue" )
public class CacheNGTest_MultiValue {

	private static final String NOT_HERE = "NOT_HERE";
	
	private static final String TEST123 = "test123";
	
	public void accessMultiValuesViaOneBackendCache() {
		
		CacheNG.Cache<String, MultiValueData> backendMulti =
				new CacheHashMap<>( new TestRealm<String,MultiValueData>( "S->MV" ) );
		
		TestStringAccessor strToStrAcc = new TestStringAccessor();
		
		CacheNG.AutomaticClient<String,String> frontendString = new AutomaticClientImpl<>(
				new MultiStringAccessor( backendMulti ) );
		
		TestIntegerAccessor strToIntAcc = new TestIntegerAccessor();
		
		CacheNG.AutomaticClient<String,Integer> frontendInteger = new AutomaticClientImpl<>(
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
		
		CacheNG.Cache<String, MultiValueData> backendMulti =
				new CacheHashMap<>( new TestRealm<String,MultiValueData>( "S->MV" ) );
		
		TestStringAccessor strToStrAcc = new TestStringAccessor();
		
		CacheNG.AutomaticClient<String,String> frontendString =
				new AutomaticClientImpl<String,String>(
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
		CacheNG.Cache.Entry<String> stringValue;
		CacheNG.Cache.Entry<Integer> integerValue;
	}
	
	
	static abstract class AbstractMultiDataAccessor<MV,O>
			implements CacheNG.Cache<String,O> {
		
		private final CacheNG.Cache<String,MV> wrapped;

		public AbstractMultiDataAccessor( Cache<String, MV> wrapped ) {
			this.wrapped = wrapped;
		}
		
		protected abstract CacheNG.Cache.Entry<O> getFromMultivalue( MV data );
		protected abstract void putInMultivalue( MV data, CacheNG.Cache.Entry<O> value );
		protected abstract MV create();

		@Override
		public synchronized CacheNG.Cache.Entry<O> fetchEntry( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return null;
			CacheNG.Cache.Entry<O> result = getFromMultivalue( data );
			return result;
		}
		
		@Override
		public synchronized O fetch( String key ) {
			CacheNG.Cache.Entry<O> entry = fetchEntry( key );
			if( entry == null ) return null;
			return entry.data();
		}

		@Override
		public synchronized boolean isCached( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return false;
			CacheNG.Cache.Entry<O> value = getFromMultivalue( data );
			return value != null;
		}

		@Override
		public synchronized void invalidate( String key ) {
			MV data = wrapped.fetch( key );
			if( data == null ) return;
			putInMultivalue( data, null );
			wrapped.put( key, data );
		}

		@Override
		public void put( String key, O value ) {
			
			MV data = wrapped.fetch( key );
			if( data == null ){
				data = create();
			}
			putInMultivalue( data, new DefaultEntry<>( value ) );
			
			// store again (probably needed for distributed caches)
			wrapped.put( key, data );
		}

		@Override
		public void invalidateAll( boolean force ) {
			wrapped.invalidateAll( force );
		}

		@Override
		public int size() {
			return wrapped.size();
		}

		@Override
		public int capacity() {
			return wrapped.capacity();
		}

		@Override
		public String info() {
			return wrapped.info();
		}
		
		@Override
		public double ratio(){
			return wrapped.ratio();
		}

		@Override
		public Set<String> keySet() {
			return wrapped.keySet();
		}

		@Override
		public Iterable<O> values() {
			throw new UnsupportedOperationException( "Cannot iterate over values" );
		}

	}
	
	static class MultiStringAccessor extends AbstractMultiDataAccessor<MultiValueData,String> {

		public MultiStringAccessor( Cache<String, MultiValueData> wrapped ) {
			super( wrapped );
		}

		@Override
		protected CacheNG.Cache.Entry<String> getFromMultivalue( MultiValueData data ) {
			return data.stringValue;
		}

		@Override
		protected void putInMultivalue( MultiValueData data, CacheNG.Cache.Entry<String> value ) {
			data.stringValue = value;
		}

		@Override
		protected MultiValueData create() {
			return new MultiValueData();
		}
		
	}
	
	
	static class MultiIntegerAccessor extends AbstractMultiDataAccessor<MultiValueData,Integer> {

		public MultiIntegerAccessor( Cache<String, MultiValueData> wrapped ) {
			super( wrapped );
		}

		@Override
		protected CacheNG.Cache.Entry<Integer> getFromMultivalue( MultiValueData data ) {
			return data.integerValue;
		}

		@Override
		protected void putInMultivalue( MultiValueData data, CacheNG.Cache.Entry<Integer> value ) {
			data.integerValue = value;
		}

		@Override
		protected MultiValueData create() {
			return new MultiValueData();
		}

	}
	
	
	static class TestStringAccessor
			implements CacheNG.SingleValueAccessor<String,String> {
		
		@Override
		public String fetch( String key ) {
			if( key.equals( NOT_HERE ) ) return null;
			return key;
		}
	}
	
	static class TestIntegerAccessor
			implements CacheNG.SingleValueAccessor<String,Integer> {
		
		@Override
		public Integer fetch( String key ) {
			if( key.equals( NOT_HERE ) ) return null;
			return key.length();
		}
	}
	
	
}
