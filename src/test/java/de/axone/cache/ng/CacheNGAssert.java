package de.axone.cache.ng;


import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.TArticle;
import de.axone.cache.ng.CacheNGTestHelpers.Tid;

public abstract class CacheNGAssert {
	
	
	public static class CacheObjectAssert<K,O,P>
			extends AbstractAssert<CacheObjectAssert<K,O,P>, O> {
		
		private final P parent;

		protected CacheObjectAssert( P parent, O actual ) {
			super( actual, CacheObjectAssert.class );
			this.parent = parent;
		}
		
		public P end(){
			return parent;
		}
	}
	
	
	public static class CacheAssert<K,O>
			extends AbstractAssert<CacheAssert<K,O>, CacheNG.Cache<K,O>> {

		protected CacheAssert( CacheNG.Cache<K,O> actual ) {
			super( actual, CacheAssert.class );
		}
		
		public CacheAssert<K,O> hasCached( K key ){
			
			if( ! actual.isCached( key ) )
					failWithMessage( "Is not cached but should be: " + key );
			
			return myself;
		}
		
		public CacheAssert<K,O> hasNotCached( K key ){
			
			if( actual.isCached( key ) )
					failWithMessage( "Is cached but shouldn't be: " + key );
			
			return myself;
		}
		
		public CacheAssert<K,O> hasSize( int size ){
			
			int actualSize = actual.size();
			
			if( actualSize != size )
					failWithMessage( "Should have size: " + size + " but has: " + actualSize );
			
			return myself;
		}
		
		public CacheAssert<K,O> hasCapacity( int capacity ){
			
			int actualCapacity = actual.capacity();
			
			if( actualCapacity != capacity )
					failWithMessage( "Should have capacity: " + capacity + " but has: " + actualCapacity );
			
			return myself;
		}
		
		public CacheObjectAssert<K,O,CacheAssert<K,O>> fetch( K key ){
			return new CacheObjectAssert<>( this, actual.fetch( key ) );
		}
	}
	
	
	public static <K,O> CacheAssert<K,O> assertThat( CacheNG.Cache<K,O> cache ){
		
		return new CacheAssert<K,O>( cache );
	}
	
	
	
	public static class AutoCacheAssert<K,O>
			extends AbstractAssert<AutoCacheAssert<K,O>, CacheNG.AutomaticClient<K,O>> {

		protected AutoCacheAssert( CacheNG.AutomaticClient<K,O> actual ) {
			super( actual, AutoCacheAssert.class );
		}
		
		public AutoCacheAssert<K,O> hasCached( K key ){
			
			if( ! actual.isCached( key ) )
					failWithMessage( "Is not cached but should be: " + key );
			
			return myself;
		}
		
		public AutoCacheAssert<K,O> hasNotCached( K key ){
			
			if( actual.isCached( key ) )
					failWithMessage( "Is cached but shouldn't be: " + key );
			
			return myself;
		}
		
		public CacheObjectAssert<K,O,AutoCacheAssert<K,O>> fetch( K key, CacheNG.SingleValueAccessor<K,O> accessor ){
			
			return new CacheObjectAssert<>( this, actual.fetch( key, accessor ) );
		}
		
		public CacheAssert<K,O> lookingInBackend(){
			
			isInstanceOf( AutomaticClientImpl.class );
			
			return new CacheAssert<>( ((AutomaticClientImpl<K,O>)actual).backend );
		}
	}
	
	
	static final class HavingTid extends Condition<TArticle> {
		
		private final Tid tid;
	
		public HavingTid( Tid tid ) { this.tid = tid; }
	
		@Override
		public boolean matches( TArticle value ) {
			return value.getTreeIdentifiers().contains( tid );
		}
	}

	
	static final class HavingIdentifier extends Condition<TArticle> {
		
		private final Aid aid;
	
		public HavingIdentifier( Aid aid ) { this.aid = aid; }
	
		@Override
		public boolean matches( TArticle value ) {
			return aid.equals( value.getIdentifier() );
		}
	
		@Override
		public Description description() {
			return new TextDescription( "Having Aid", aid.name() );
		}
		
	}
	
	
	static final class HavingCached<K> extends Condition<CacheNG.Cache<K,?>> {
		
		private final K key;
		
		public HavingCached( K key ){ this.key = key; }

		@Override
		public boolean matches( Cache<K,?> client ) {
			return client.isCached( key );
		}
	}
	
	public static final <K> HavingCached<K> cached( K key ){
		return new HavingCached<K>( key );
	}
	
	static final class HavingAutoCached<K> extends Condition<CacheNG.AutomaticClient<K,?>> {
		
		private final K key;
		
		public HavingAutoCached( K key ){ this.key = key; }

		@Override
		public boolean matches( CacheNG.AutomaticClient<K,?> autoClient ) {
			return autoClient.isCached( key );
		}
	}
	
	public static final <K> HavingAutoCached<K> havingAutoCached( K key ){
		return new HavingAutoCached<K>( key );
	}

	static final class HavingSameHashCodeAs extends Condition<Object> {
	
		private final Object expected;
	
		public HavingSameHashCodeAs( Object expected ) { this.expected = expected; }
		
		@Override
		public boolean matches( Object value ) {
			return value.hashCode() == expected.hashCode();
		}
		
	}

	public static <K,O> AutoCacheAssert<K,O> assertThat( CacheNG.AutomaticClient<K, O> cache ){
		return new AutoCacheAssert<>( cache );
	}

	static CacheNGAssert.HavingTid havingTid( Tid tid ){
		return new CacheNGAssert.HavingTid( tid );
	}

	static CacheNGAssert.HavingIdentifier havingIdentifier( Aid aid ){
		
		return new CacheNGAssert.HavingIdentifier( aid );
	}

	static CacheNGAssert.HavingSameHashCodeAs havingSameHashCodeAs( Object expected ){
		return new CacheNGAssert.HavingSameHashCodeAs( expected );
	}
}
