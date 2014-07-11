package de.axone.cache.ng;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public class CacheNGTestHelpers {
	
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
