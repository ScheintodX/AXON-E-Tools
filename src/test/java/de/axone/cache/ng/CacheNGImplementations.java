package de.axone.cache.ng;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.axone.cache.ng.CacheNG.CacheEventListener;
import de.axone.cache.ng.CacheNG.CacheEventProvider;
import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNG.InvalidationManager;

public class CacheNGImplementations {
	
	public enum RN {
		
		AID_ARTICLE( "aid->article" ),
		TID_LARTICLE( "tid->L:article" ),
		TOP_LARTICLE( "top->L:article" ),
		
		SQTID_LARTICLE( "sqtid->L:article" ),
		
		;
		
		private final String realm;
		RN( String realm ){
			this.realm = realm;
		}
	
		public CacheNG.Realm realm() {
			return new TestRealm( realm );
		}
		
		public CacheNG.Realm unique(){
			return new TestRealm( realm, UUID.randomUUID().toString() );
		}
		
		public CacheNG.Realm timeout( int timeout ){
			return new TestRealm( realm, timeout );
		}
		
	}


	static class TestRealm implements CacheNG.Realm {
		
		private final String client;
		private final String name;
		private String suffix;
		private Integer timeout;
		
		TestRealm( String name ){
			this.client = "TestClient";
			this.name = name;
		}
		
		TestRealm( String name, String suffix ){
			this( name );
			this.suffix = suffix;
		}
		
		TestRealm( String name, int timeout ){
			this( name );
			this.timeout = timeout;
		}
	
		@Override
		public String realm() {
			String result = client + "/" + name;
			if( suffix != null ) result += "/" + suffix;
			if( timeout != null ) result += "[" + timeout;
			return result;
		}
		
	}

	static class TestClient<K,O> implements CacheNG.Client<K,O> {
		
		private HashMap<K,Entry<O>> map = new HashMap<>();
	
		@Override
		public boolean isCached( K key ) {
			return map.containsKey( key );
		}
	
		@Override
		public void invalidate( K key ){
			map.remove( key );
		}
		
		@Override
		public CacheNG.Client.Entry<O> fetchEntry( K key ) {
			return map.get( key );
		}

		@Override
		public O fetch( K key ) {
			CacheNG.Client.Entry<O> entry = fetchEntry( key );
			if( entry == null ) return null;
			return entry.data();
		}

		@Override
		public void put( K key, O object ) {
			putEntry( key, new TestEntry<>( object ) );
		}

		@Override
		public void putEntry( K key, CacheNG.Client.Entry<O> entry ) {
			map.put( key, entry );
		}
		
		public static class TestEntry<O> implements CacheNG.Client.Entry<O>, Serializable {
			
			private static final long serialVersionUID = 1L;
			
			private final long creation;
			private final O data;
			public TestEntry( O data ) {
				this.creation = System.currentTimeMillis();
				this.data = data;
			}
			@Override
			public O data() { return data;
			}
			@Override
			public long creation() { return creation; }
			
			@Override
			public int hashCode() {
				if( data == null ) return 0;
				return data.hashCode();
			}
			
			@Override
			public boolean equals( Object obj ) {
				if( this == obj ) return true;
				if( obj == null ) return false;
				if( !( obj instanceof TestEntry ) ) return false;
				
				TestEntry<?> other = (TestEntry<?>) obj;
				
				if( data == null ) {
					if( other.data != null ) return false;
				} else if( !data.equals( other.data ) ) return false;
				
				return true;
			}
			
		}

	}
	
	static class TestTimeoutClient<K,O> implements CacheNG.Client<K,O> {
		
		private final long maxLifeTime;
		
		private final CacheNG.Client<K,O> backend;
		
		TestTimeoutClient( CacheNG.Client<K,O> backend, long maxLifeTime ){
			this.backend = backend;
			this.maxLifeTime = maxLifeTime;
		}

		@Override
		public CacheNG.Client.Entry<O> fetchEntry( K key ) {
			CacheNG.Client.Entry<O> entry = backend.fetchEntry( key );
			if( entry == null ) return null;
			if( ! isAlive( key, entry ) ) return null;
			return entry;
		}
		
		@Override
		public O fetch( K key ) {
			CacheNG.Client.Entry<O> entry = fetchEntry( key );
			if( entry == null ) return null;
			return entry.data();
		}

		@Override
		public boolean isCached( K key ) {
			CacheNG.Client.Entry<O> entry = backend.fetchEntry( key );
			if( entry == null ) return false;
			return isAlive( key, entry );
		}
		
		protected boolean isAlive( K key, CacheNG.Client.Entry<O> entry ){
			
			if( maxLifeTime >= 0 ){
				
				long age = System.currentTimeMillis() - entry.creation();
					
				return age < maxLifeTime;
			}
			
			return true;
		}

		@Override
		public void invalidate( K key ) {
			backend.invalidate( key );
		}

		@Override
		public void putEntry( K key, CacheNG.Client.Entry<O> object ) {
			backend.putEntry( key, object );
		}

		@Override
		public void put( K key, O entry ) {
			backend.put( key, entry );
		}

	}

	static class TestAutomaticClient<K,O>
			implements CacheNG.AutomaticClient<K,O>,
					CacheEventProvider<K>, CacheEventListener<K> {
		
		private final Client<K,O> backend;
		
		//@SuppressWarnings( { "rawtypes", "unchecked" } )
		//private static final CacheNG.Client.Entry NORESULT = new TestEntry( null );
		
		private InvalidationManager<K,O> invalidationManager;
		
		private List<CacheEventListener<K>> listeners = null;
		
		TestAutomaticClient( Client<K,O> backend ){
			this.backend = backend;
		}
		
		TestAutomaticClient() {
			this( new TestClient<K,O>() );
		}
		
		TestAutomaticClient( long maxLifeTime ){
			this.backend = new TestTimeoutClient<K,O>( new TestClient<K,O>(), maxLifeTime );
		}
		
		CacheNG.Client<K,O> backend(){
			return backend;
		}
		
	
		@Override
		public void registerListener( CacheEventListener<K> listener ) {
			
			if( listeners == null ) listeners = new LinkedList<>();
			
			listeners.add( listener );
		}
		
		@Override
		public void notifyListeners( K key ){
			if( listeners != null ){
				for( CacheEventListener<K> listener : listeners ){
					listener.invalidateEvent( key );
				}
			}
		}
		
		@Override
		public O fetch( K key, CacheNG.Accessor<K,O> accessor ) {
			
			CacheNG.Client.Entry<O> entry = backend.fetchEntry( key );
			
			// Clear to refresh if timed out
			if( ! isAlive( key, entry ) ) entry = null;
			
			O result;
				
			if( entry != null ){
				
				result = entry.data();
				
			} else {
				
				result = accessor.fetch( key );
				
				backend.put( key, result );
			}
			
			return result;
		}
	
		@Override
		public boolean isCached( K key ) {
			
			CacheNG.Client.Entry<O> entry = backend.fetchEntry( key );
			
			if( entry == null ) return false;
			
			return isAlive( key, entry );
		}
		
		private boolean isAlive( K key, CacheNG.Client.Entry<O> entry ){
			
			if( entry == null ) return false;
			
			if( invalidationManager != null ){
				
				return invalidationManager.isValid( key, entry );
			}
			
			
			return true;
			
		}
	
		@Override
		public void invalidate( K key ) {
			
			notifyListeners( key );
			
			invalidateEvent( key );
		}
		
		@Override
		public void invalidateAllWithin( int milliSeconds ) {
			
			invalidationManager = new TimoutInvalidationManager<K,O>(
					System.currentTimeMillis(), milliSeconds );
		}
	
		@Override
		public void invalidateEvent( K key ) {
			
			backend.invalidate( key );
		}
		
	}

	static class TimoutInvalidationManager<K,O>
			implements CacheNG.InvalidationManager<K, O> {
		
		private final long timeoutStart,
		                   timeoutDuration;
		
		TimoutInvalidationManager( long timeoutStart, long timeoutDuration ){
			
			this.timeoutStart = timeoutStart;
			this.timeoutDuration = timeoutDuration;
		}
	
		@Override
		public boolean isValid( K key, CacheNG.Client.Entry<O> value ) {
			
			// Entry is newer than starting of timeout.
			// This happend regularily if the entry is re-fetched after invalidation
			if( value.creation() > timeoutStart ) return true;
			
			
			long elapsed = System.currentTimeMillis() - timeoutStart;
			
			// Invalid immediately if > duration
			if( elapsed >= timeoutDuration ) return false;
			
			long stretch = Integer.MAX_VALUE / timeoutDuration;
			
			int random = RandomMapper.positiveInteger( key.hashCode() );
			
			if( stretch * elapsed < random ){
				
				return false;
			}
			
			return true;
		}

		
	}

	static final class TArticle {
		
		private final Aid identifier;
		private final List<Tid> treeIdentifiers;
	
		public TArticle( Aid identifier, List<Tid> treeIdentifiers ) {
			assertNotNull( identifier );
			this.identifier = identifier;
			this.treeIdentifiers = treeIdentifiers;
		}
	
		public Aid getIdentifier() { return identifier; }
		
		public List<Tid> getTreeIdentifiers() { return treeIdentifiers; }
	
		public static TArticle build( Aid description ){
			
			String [] parts = description.name().split( ":" );
			assertThat( parts.length ).isGreaterThanOrEqualTo( 2 );
			
			List<Tid> treeIds = new ArrayList<>( parts.length-1 );
			for( int i=1; i<parts.length; i++ ){
				treeIds.add( tid( parts[i] ) );
			}
				
			return new TArticle( new Aid( parts[0].substring( 1 ) ), treeIds );
		}
		
		@Override
		public String toString(){ return identifier.name(); }
	}

	
	
	static class Identifiable {
		
		private final String identifier;
	
		public Identifiable( String identifier ) {
			this.identifier = identifier;
		}
	
		@Override
		public String toString() {
			return this.getClass().getSimpleName().replace( ".class", "" ) + ":" + identifier;
		}
	
		@Override
		public int hashCode() {
			return identifier.hashCode();
		}
		
		public String name(){
			return identifier;
		}
	
		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( getClass() != obj.getClass() ) return false;
			
			Identifiable other = (Identifiable) obj;
			
			return identifier.equals( other.identifier );
		}
		
	}

	
	
	static class Aid extends Identifiable {
		public Aid( String identifier ) { super( identifier ); }
	}

	static class Tid extends Identifiable {
		public Tid( String identifier ) { super( identifier ); }
	}

	
	
	static List<TArticle> union( List<TArticle> list1, List<TArticle> list2 ){
		
		LinkedHashSet<TArticle> asSet = new LinkedHashSet<>();
		
		asSet.addAll( list1 );
		asSet.addAll( list2 );
		
		return new ArrayList<>( asSet );
	}

	static Tid tid( String identifier ){ return new Tid( identifier ); }

	static Aid aid( String identifier ){ return new Aid( identifier ); }

}
