package de.axone.cache.ng;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.axone.cache.ng.CacheNG.AutomaticClient;
import de.axone.cache.ng.CacheNG.CacheEventListener;
import de.axone.cache.ng.CacheNG.CacheEventProvider;
import de.axone.cache.ng.CacheNG.Client;
import de.axone.cache.ng.CacheNG.InvalidationManager;
import de.axone.cache.ng.CacheNG.Realm;

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

	static final class TestCacheBackend implements CacheNG.Backend {
		
		private Map<String,Client<?,?>> clients = new HashMap<>();
		private Map<String,AutomaticClient<?,?>> autoClients = new HashMap<>();
	
		@Override
		public <K,O> Client<K,O> cache( Realm realm ) {
			
			String key = realm.realm();
			
			if( ! clients.containsKey( key ) ){
				
				clients.put( key, new TestClient<K,O>() );
			}
			
			@SuppressWarnings( "unchecked" )
			Client<K,O> result = (Client<K,O>) clients.get( key );
			
			return result;
		}
	
		@Override
		public <K,O> AutomaticClient<K,O> automatic( Realm realm ) {
			
			String key = realm.realm();
			
			if( ! autoClients.containsKey( key ) ){
				
				TestAutomaticClient<K,O> result;
			
				if( key.indexOf( '[' ) > 0 ){
					
					String [] parts = key.split( "\\[" );
					long timeout = Long.parseLong( parts[ 1 ] );
					
					result = new TestAutomaticClient<K,O>( timeout );
				} else {
					result = new TestAutomaticClient<K,O>();
				}
				
				autoClients.put( key, result );
			}
			
			@SuppressWarnings( "unchecked" )
			AutomaticClient<K,O> result = (AutomaticClient<K,O>) autoClients.get( key );
			
			return result;
		}

		/*
		@Override
		public <K, MV, O> AutomaticClient<K, O> automatic(
				Client<K, TimedCacheEntry<MV>> backend, Accessor<K, O> accessor ) {
			
			return new TestAutomaticClient<K,O>( backend, accessor );
		}
		*/
	
	}

	static class TestClient<K,O> implements CacheNG.Client<K,O> {
		
		private HashMap<K,O> map = new HashMap<>();
	
		@Override
		public boolean isCached( K key ) {
			return map.containsKey( key );
		}
	
		@Override
		public O fetch( K key ) {
			return map.get( key );
		}
		
		@Override
		public void invalidate( K key ){
			map.remove( key );
		}
		
		@Override
		public void put( K key, O value ){
			map.put( key, value );
		}
	}

	static class TestAutomaticClient<K,O>
			implements CacheNG.AutomaticClient<K,O>,
					CacheEventProvider<K>, CacheEventListener<K> {
		
		private final Client<K,TimedCacheEntry<O>> backend;
		
		private static final TimedCacheEntry<?> NORESULT = new TimedCacheEntry<>( null );
		
		private long maxLifeTime = -1;
		
		private InvalidationManager<K,TimedCacheEntry<O>> invalidationManager;
		
		private List<CacheEventListener<K>> listeners = null;
		
		TestAutomaticClient() {
			this.backend = new TestClient<K,TimedCacheEntry<O>>();
		}
		
		TestAutomaticClient( Client<K,TimedCacheEntry<O>> backend ){
			this.backend = backend;
		}
		
		TestAutomaticClient( long maxLifeTime ){
			this.maxLifeTime = maxLifeTime;
			this.backend = new TestClient<K,TimedCacheEntry<O>>();
		}
		
		CacheNG.Client<K,TimedCacheEntry<O>> backend(){
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
		
		@SuppressWarnings( "unchecked" )
		@Override
		public O fetch( K key, CacheNG.Accessor<K,O> accessor ) {
			
			TimedCacheEntry<O> entry = backend.fetch( key );
			O result;
			
			// Refresh if timed out
			if( ! isAlive( key, entry ) ) entry = null;
				
			if( entry == null ){
				
				result = accessor.fetch( key );
				
				if( result == null ){
					entry = (TimedCacheEntry<O>)NORESULT;
				} else {
					entry = new TimedCacheEntry<O>( result );
				}
				backend.put( key, entry );
			}
			
			result = entry.data;
			
			if( result == NORESULT ) return null;
			
			return result;
		}
	
		@Override
		public boolean isCached( K key ) {
			
			TimedCacheEntry<O> entry = backend.fetch( key );
			
			if( entry == null ) return false;
			
			return isAlive( key, entry );
		}
		
		private boolean isAlive( K key, TimedCacheEntry<O> entry ){
			
			if( entry == null ) return false;
			
			if( maxLifeTime >= 0 ){
				
				long age = System.currentTimeMillis() - entry.creation;
					
				return age < maxLifeTime;
			}
			
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

	
	
	static class TimedCacheEntry<O> {
		
		final O data;
		final long creation;
		
		TimedCacheEntry( O data ){
			this.data = data;
			this.creation = System.currentTimeMillis();
		}
	}
	
	static class TimoutInvalidationManager<K,O>
			implements CacheNG.InvalidationManager<K, TimedCacheEntry<O>> {
		
		private final long timeoutStart,
		                   timeoutDuration;
		
		TimoutInvalidationManager( long timeoutStart, long timeoutDuration ){
			
			this.timeoutStart = timeoutStart;
			this.timeoutDuration = timeoutDuration;
		}
	
		@Override
		public boolean isValid( K key, TimedCacheEntry<O> value ) {
			
			if( timeoutStart >= value.creation ){
				
				long elapsed = System.currentTimeMillis() - timeoutStart;
				
				// Invalid immediately if > duration
				if( elapsed >= timeoutDuration ) return false;
				
				long stretch = Integer.MAX_VALUE / timeoutDuration;
				
				int random = RandomMapper.positiveInteger( key.hashCode() );
				
				if( stretch * elapsed < random ){
					
					return false;
				}
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
