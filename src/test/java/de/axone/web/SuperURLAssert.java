package de.axone.web;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractIterableAssert;

import de.axone.web.SuperURL.Encode;

public class SuperURLAssert 
			extends AbstractAssert<SuperURLAssert, SuperURL> {

	protected SuperURLAssert( SuperURL actual ) {
		
		super( actual, SuperURLAssert.class );
	}
	
	public SuperURLAssert isEqualTo( SuperURL expected ){
		
		assertEquals( actual.getScheme(), expected.getScheme() );
		assertThat( actual.getHost() ).isEqualTo( expected.getHost() );
		assertEquals( actual.getPort(), expected.getPort() );
		assertThat( actual.getPath() ).isEqualTo( expected.getPath() );
		assertThat( actual.getQuery() ).isEqualTo( expected.getQuery() );
		assertEquals( actual.getPath(), expected.getPath() );
		
		for( Encode encode : Encode.values() ){
			assertThat( actual.toStringEncode( encode ) )
					.as( encode.toString() )
					.isEqualTo( expected.toStringEncode( encode ) );
		}
		
		assertEquals( actual, expected );
		
		return this;
	}
	
	public static class HostAssert extends AbstractIterableAssert<HostAssert, SuperURL.Host, String> {

		protected HostAssert( SuperURL.Host actual ) {
			
			super( actual, HostAssert.class );
		}
		
		public HostAssert isEqualTo( SuperURL.Host expected ){
			
			assertThat( actual.toList() )
					.isEqualTo( expected.toList() )
					;
			
			return myself;
		}

		public HostAssert hostEquals( String string ) {
			assertThat( actual.getHost() )
					.isEqualTo( string )
					;
			return this;
		}

		public HostAssert netContains( String string, int i ) {
			assertThat( actual.getNet() )
					.hasSize( i+1 )
					.contains( string, atIndex( i ) )
					;
			return this;
		}

		public HostAssert tldEquals( String string ) {
			assertThat( actual.getTld() )
					.isEqualTo( string )
					;
			return this;
		}

		public HostAssert netAsStringEquals( String string ) {
			assertThat( actual.getNetAsString() )
					.isEqualTo( string )
					;
			return this;
		}

		public HostAssert asStringEquals( SuperURL.Encode encode, String hostStr ) {
			assertThat( actual.toStringEncode( encode ) )
					.isEqualTo( hostStr )
					;
			return this;
		}
		
	}
	
	public static class PathAssert extends AbstractIterableAssert<PathAssert, SuperURL.Path, String> {
		
		protected PathAssert( SuperURL.Path actual ) {
			super( actual, PathAssert.class );
		}
		
		public PathAssert isEqualTo( SuperURL.Path other ){
			
			assertThat( actual.toList() )
					.isEqualTo( other.toList() );
					;
			
			return myself;
		}
	}
	
	public static class QueryAssert extends AbstractIterableAssert<QueryAssert, SuperURL.Query, SuperURL.Query.QueryPart> {
		
		protected QueryAssert( SuperURL.Query actual ) {
			super( actual, QueryAssert.class );
		}

		public QueryAssert contains( String string, String string2 ) {
			
			contains( new SuperURL.Query.QueryPart( string, string2 ) );
			return myself;
		}
		
		
	}
		
	public static class QueryPartAssert extends AbstractAssert<QueryPartAssert, SuperURL.Query.QueryPart> {
		
		protected QueryPartAssert( SuperURL.Query.QueryPart actual ) {
			super( actual, QueryAssert.class );
		}
		
		
	}
		
	
}
