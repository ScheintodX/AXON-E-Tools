package de.axone.web;

import static de.axone.web.SuperURLAssertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.testng.annotations.Test;

import de.axone.web.SuperURL.Host;
import de.axone.web.SuperURL.Path;

@Test( groups="web.superurl" )
public class SuperURLTest {
	
	private static final String UTF8 = "utf-8";
	
	private static final String AE, UE, EQ, AMP;
	static {
		try {
			AE = URLEncoder.encode( "Ä", UTF8 );
			UE = URLEncoder.encode( "Ü", UTF8 );
			EQ = URLEncoder.encode( "=", UTF8 );
			AMP = URLEncoder.encode( "&", UTF8 );
		} catch( UnsupportedEncodingException e ) {
			throw new RuntimeException( "Cannot encode", e );
		}
	}
	
	private static final String URL_UMLAUT_IN_PATH_AND_QUERY = 
			"http://www.axon-e.de/äbü/?füü=bär";
	
	private static final String URL_UMLAUT_IN_HOST = 
			"http://www.äxon-e.de/äbü/?füü=bär";
	
	public void firstTestTheURLEncoderItself() throws Exception {
		
		assertEquals( ec8("Abc"), "Abc" );
		assertEquals( ec1("Abc"), "Abc" );
		
		assertEquals( ec8("Äbc"), "%C3%84bc" ); // 2 bytes for utf-8
		assertEquals( ec1("Äbc"), "%C4bc" );  // only one byte for latin-1
	}
	
	private String ec8( String value ) throws Exception {
		return URLEncoder.encode( value, "utf8" );
	}
	
	private String ec1( String value ) throws Exception {
		return URLEncoder.encode( value, "latin1" );
	}
	
	
	public void checkWhatURICanDo() throws Exception {
		
		URI uri = new URI( URL_UMLAUT_IN_HOST );
		
		assertEquals( uri.getScheme(), "http" );
		assertEquals( uri.getHost(), null ); // cannot parse umlaut in host but doesn't throw exception
		assertEquals( uri.getPath(), "/äbü/" ); // note: includes first slash because it's an absolute path
		assertEquals( uri.getQuery(), "füü=bär" );
		
		 uri = new URI( URL_UMLAUT_IN_PATH_AND_QUERY );
		
		assertEquals( uri.getScheme(), "http" );
		assertEquals( uri.getHost(), "www.axon-e.de" );
		assertEquals( uri.getPath(), "/äbü/" );
		assertEquals( uri.getQuery(), "füü=bär" );
		
		uri = new URI( "blah.txt" );
		
		assertNull( uri.getScheme() );
		assertNull( uri.getHost() );
		assertEquals( uri.getPath(), "blah.txt" );
		assertNull( uri.getQuery() );
		
		// Special chars
		
		String reserved = 
			" \"%<>[]\\^`{}|";
		
		for( char c=0x20; c<127; c++ ){
			
			if( reserved.indexOf( c ) >= 0 ) continue;
			
			uri = new URI( "http://www.axon-e.de/"+c );
		}
		
		for( int i=0; i<reserved.length(); i++ ){
			
			try {
				uri = new URI( "http://www.axon-e.de/"+reserved.charAt( i ) );
				org.testng.Assert.fail( "Should throw exception for: '" + reserved.charAt( i ) + "'" );
			} catch( URISyntaxException e ){}
		}
		
		try {
			uri = new URI( "[foo]" );
			org.testng.Assert.fail( "Should throw exception" );
		} catch( URISyntaxException e ){}
		
		assertEquals( new URI( URLEncoder.encode( "[foo]", "utf-8" ) ).getPath(), "[foo]" );
		
	}
	
	public void checkWhatURLCanDo() throws Exception {
		
		String test = "http://flo:bar@äbä.[com]/füü[test]/?foo=bär#fragme";
		
		URL url = new URL( test );
		
		assertEquals( url.toString(), test );
		assertEquals( url.getProtocol(), "http" );
		assertEquals( url.getAuthority(), "flo:bar@äbä.[com]" );
		assertEquals( url.getUserInfo(), "flo:bar" );
		assertEquals( url.getFile(), "/füü[test]/?foo=bär" );
		assertEquals( url.getPath(), "/füü[test]/" );
		assertEquals( url.getQuery(), "foo=bär" );
	}
	
	@Test( dependsOnMethods={ "firstTestTheURLEncoderItself", "checkWhatURICanDo", "checkWhatURLCanDo" } )
	public void prerequesits(){}
	
	public void testEquals() throws Exception {
		
		SuperURL url = SuperURLBuilders.fromString().build( "http://www.axon-e.de:8080/foo/bar/?par=val" );
		
		SuperURL url2 = new SuperURL();
		url2.setScheme( "http" );
		url2.setHost( Host.parse( "www.axon-e.de" ) );
		url2.setPort( 8080 );
		url2.setPath( Path.parse( "/foo/bar/" ) );
		url2.setQuery( SuperURL.Query.parse( "par=val" ) );
		
		assertThat( url ).isEqualTo( url2 );
		
	}

	public void testHost() throws Exception {
		
		SuperURL.Host host;
		String hostStr;
		
		/* ----------- */
		hostStr = "www.axon-e.de";
		host = SuperURL.Host.parse( hostStr );
		
		assertThat( host )
				.hostEquals( "www" )
				.netContains( "axon-e", 0 )
				.netAsStringEquals( "axon-e" )
				.tldEquals( "de" )
				.asStringEquals( false, hostStr )
				;
		
		host.getParts().remove( 0 );
		assertThat( host ).asStringEquals( false, "axon-e.de" );
		
		/* ----------- */
		hostStr = "test.webs.axon-e.de";
		host = SuperURL.Host.parse( hostStr );
		
		assertThat( host.getNet() )
				.contains( "webs", atIndex( 0 ) )
				.contains( "axon-e", atIndex( 1 ) )
				.hasSize( 2 )
				;
		assertThat( host )
				.hostEquals( "test" )
				.netAsStringEquals( "webs.axon-e" )
				.tldEquals( "de" )
				.asStringEquals( false, hostStr )
				;
		
	}
	
	public void testPath() throws Exception {
		
		SuperURL.Path path;
		String pathStr;
		
		pathStr = "/dir1/dir2/file.ext";
		path = SuperURL.Path.parse( pathStr );
		
		assertEquals( path.length(), 3 );
		assertEquals( path.getFirst(), "dir1" );
		assertEquals( path.getLast(), "file.ext" );
		assertEquals( path.getExtension(), "ext" );
		assertEquals( path.getLastWithoutExtension(), "file" );
		assertEquals( path.get( 0 ), "dir1" );
		assertEquals( path.get( 1 ), "dir2" );
		assertEquals( path.get( 2 ), "file.ext" );
		assertFalse( path.isEndsWithSlash() );
		assertEquals( path.toString(), pathStr );
		
		pathStr = "/newdir1/dir2/newfile.ext2";
			
		path.replaceFirst( "newdir1" );
		assertEquals( path.getFirst(), "newdir1" );
		path.replaceLast( "newfile.ext2" );
		assertEquals( path.getLast(), "newfile.ext2" );
		assertEquals( path.getExtension(), "ext2" );
		assertEquals( path.getLastWithoutExtension(), "newfile" );
		assertEquals( path.toString(), pathStr );
		
		path.removeFirst();
		path.removeLast();
		assertEquals( path.toString(), "/dir2" );
		
	}
	
	public void testPathAndSlashes() throws Exception {
		
		SuperURL.Path path;
		
		path = SuperURL.Path.parse( null );
		assertEquals( path.length(), 0 );
		assertFalse( path.isStartsWithSlash() );
		assertFalse( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "" );
		
		path = SuperURL.Path.parse( "" );
		assertEquals( path.length(), 0 );
		assertFalse( path.isStartsWithSlash() );
		assertFalse( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "" );
		
		path = SuperURL.Path.parse( "/" );
		assertEquals( path.length(), 0 );
		assertTrue( path.isStartsWithSlash() );
		assertFalse( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "/" );
		
		path = SuperURL.Path.parse( "//" );
		assertEquals( path.length(), 1 );
		assertTrue( path.isStartsWithSlash() );
		assertTrue( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "//" );
		
		path = SuperURL.Path.parse( "///" );
		assertEquals( path.length(), 2 );
		assertTrue( path.isStartsWithSlash() );
		assertTrue( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "///" );
		
		path = SuperURL.Path.parse( "/a" );
		assertEquals( path.length(), 1 );
		assertTrue( path.isStartsWithSlash() );
		assertFalse( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "/a" );
		
		path = SuperURL.Path.parse( "a/" );
		assertEquals( path.length(), 1 );
		assertFalse( path.isStartsWithSlash() );
		assertTrue( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "a/" );
		
		path = SuperURL.Path.parse( "/a/" );
		assertEquals( path.length(), 1 );
		assertTrue( path.isStartsWithSlash() );
		assertTrue( path.isEndsWithSlash() );
		assertEquals( path.toString(false), "/a/" );
	}
	
	// Tests only important types
	public void testMimeTypes() throws Exception {
		
		assertEquals( SuperURL.Path.parse( "index.Xhtml" ).getMimeType(), "text/html" );
		assertEquals( SuperURL.Path.parse( "index.hTml" ).getMimeType(), "text/html" );
		assertEquals( SuperURL.Path.parse( "img.jpg" ).getMimeType(), "image/jpeg" );
		assertEquals( SuperURL.Path.parse( "img.jpEg" ).getMimeType(), "image/jpeg" );
		assertEquals( SuperURL.Path.parse( "img.giF" ).getMimeType(), "image/gif" );
		assertEquals( SuperURL.Path.parse( "file.CSS" ).getMimeType(), "text/css" );
	}
	
	// Compare results to those of the URLParser in order to
	// be able to interchange them seamlessly
	public void testCompareToURLParser() throws Exception {
	}
	
	// Test slicing
	public void testSlice() throws Exception {
	}
	
	public void testQuery() throws Exception {
		
		SuperURL.Query query;
		String queryStr;
		
		queryStr = "key1=val1&key2=val2&key2=val22&key3";
		query = SuperURL.Query.parse( queryStr );
		
		assertEquals( query.size(), 4 );
		assertTrue( query.has( "key1" ) );
		assertEquals( query.getPart( "key1").getValue(), "val1" );
		assertEquals( query.getValue( "key1" ), "val1" );
		assertTrue( query.has( "key2" ) );
		assertEquals( query.getPart( "key2").getValue(), "val22" );
		assertEquals( query.getValue( "key2" ), "val22" );
		assertEquals( query.getParts( "key2" ).size(), 2 );
		assertEquals( query.getValues( "key2" ).size(), 2 );
		assertEquals( query.getParts( "key2" ).get( 0 ).getValue(), "val2" );
		assertEquals( query.getValues( "key2" ).get( 0 ), "val2" );
		assertEquals( query.getParts( "key2" ).get( 1 ).getValue(), "val22" );
		assertEquals( query.getValues( "key2" ).get( 1 ), "val22" );
		assertNotNull( query.getPart( "key3" ) );
		assertTrue( query.has( "key3" ) );
		assertNull( query.getPart( "key3" ).getValue() );
		assertNull( query.getValue( "key3" ) );
		
		assertEquals( query.toString(), queryStr );
		
		query.setValue( "key1", "newval1" );
		assertEquals( query.size(), 4 );
		assertEquals( query.getParts( "key1" ).size(), 1 );
		assertEquals( query.getValue( "key1" ), "newval1" );
		
		query.setValue( "key2", "newval2" );
		assertEquals( query.size(), 3 );
		assertEquals( query.getParts( "key2" ).size(), 1 );
		assertEquals( query.getValue( "key2" ), "newval2" );
		
		query.addValue( "key2", "newval22" );
		assertEquals( query.size(), 4 );
		assertEquals( query.getParts( "key2" ).size(), 2 );
		assertEquals( query.getValue( "key2" ), "newval22" );
		
		query.addValue( "key2", "newval23" );
		assertEquals( query.size(), 5 );
		assertEquals( query.getParts( "key2" ).size(), 3 );
		assertEquals( query.getValue( "key2" ), "newval23" );
		
		SuperURL.Query query2 = SuperURL.Query.parse( query.toString() );
		
		assertTrue( query2.equals( query ) );
		assertEquals( (Object)query2, query );
		assertThat( query2 ).isEqualTo( query );
		
	}
	
	public void testQueryparsing() throws Exception {
		
		String test = "a=b&ä=ü&"+AE+"="+UE+"&"+"x=äü"+AE+UE+EQ+AMP;
		
		SuperURL.Query query = SuperURL.Query.parse( test, true );
		
		assertThat( query )
				.contains( "a", "b" )
				.contains( "ä", "ü" )
				.contains( "Ä", "Ü" )
				.contains( "x", "äüÄÜ=&" )
				.hasSize( 4 )
				;
		
	}
	
	public void testUserInfo() throws Exception {
		
		SuperURL.UserInfo userInfo;
		
		userInfo = SuperURL.UserInfo.parse( "myuser:mypass" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertEquals( userInfo.getPass(), "mypass" );
		assertEquals( userInfo.toString(), "myuser:mypass" );
		
		userInfo = SuperURL.UserInfo.parse( "myuser" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertNull( userInfo.getPass() );
		assertEquals( userInfo.toString(), "myuser" );
		
		userInfo = SuperURL.UserInfo.parse( "myuser:" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertEquals( userInfo.getPass(), "" );
		assertEquals( userInfo.toString(), "myuser:" );
		
		userInfo = SuperURL.UserInfo.parse( ":mypass" );
		assertEquals( userInfo.getUser(), "" );
		assertEquals( userInfo.getPass(), "mypass" );
		assertEquals( userInfo.toString(), ":mypass" );
		
		userInfo = SuperURL.UserInfo.parse( ":" );
		assertEquals( userInfo.getUser(), "" );
		assertEquals( userInfo.getPass(), "" );
		assertEquals( userInfo.toString(), ":" );
		
		userInfo = new SuperURL.UserInfo();
		assertEquals( userInfo.getUser(), null );
		assertEquals( userInfo.getPass(), null );
		assertEquals( userInfo.toString(), "" );
		
		userInfo = new SuperURL.UserInfo( "myuser", "mypass" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertEquals( userInfo.getPass(), "mypass" );
		assertEquals( userInfo.toString(), "myuser:mypass" );
		
	}
	
    @Test( dependsOnMethods = { "testHost", "testPath", "testQuery", "testUserInfo" } )
    public void testSuperURL() throws Exception {
    	
    	// Empty constructor
    	SuperURL empty = new SuperURL();
    	assertNull( empty.getScheme() );
    	assertNull( empty.getUserInfo() );
    	assertNull( empty.getHost() );
    	assertNotNull( empty.getPath() );
    	assertEquals( empty.getPath().length(), 0 );
    	assertNotNull( empty.getQuery() );
    	assertEquals( empty.getQuery().size(), 0 );
    	assertNull( empty.getFragment() );
    	
    	// Constructor with empty URI. (Nonsense but has to work nevertheless)
    	SuperURL emptyToo = SuperURLBuilders.fromString().build( "" );
    	assertNull( emptyToo.getScheme() );
    	assertNull( emptyToo.getUserInfo() );
    	assertNull( emptyToo.getHost() );
    	//assertNull( emptyToo.getQuery() );
    	assertNull( emptyToo.getFragment() );
    	
    	String uriString = 
    			"https://myuser:mypass@" +
    			"www.domain.de:1234" +
    			"/dir1/dir2/myfile.myext/" +
    			"?key1=value1&key2=value2&key2=value22&key3" +
    			"#myFragment"
    	;
    	
    	SuperURL complete = SuperURLBuilders.fromString().build( uriString );
    	
    	assertEquals( complete.toStringEncode( false ), uriString );
    	
    	assertEquals( complete.getScheme(), "https" );
    	assertEquals( complete.getUserInfo().toString(), "myuser:mypass" );
    	assertEquals( complete.getHost().toString(), "www.domain.de" );
    	assertEquals( complete.getPath().toString(), "/dir1/dir2/myfile.myext/" );
    	assertEquals( complete.getQuery().toString(), "key1=value1&key2=value2&key2=value22&key3" );
    	assertEquals( complete.getFragment(), "myFragment" );
    	
    	// In this case encoded version equals unencoded
    	assertEquals( complete.toStringEncode( true ), uriString );
    }
    
    @Test( dependsOnMethods = "testSuperURL" )
    public void testPathOperations() throws Exception {
    	
    	String urlS = "http://www.axon-e.de";
    	String urlSlashS = "http://www.axon-e.de/";
    	String path = "test/test.txt";
    	
    	String refUrl = "http://www.axon-e.de/test/test.txt";
    	
    	SuperURL url = SuperURLBuilders.fromString().build( urlS );
    	SuperURL urlSlash = SuperURLBuilders.fromString().build( urlSlashS );
    	
    	assertTrue( url.hasHost() );
    	assertFalse( url.hasPath() );
    	assertFalse( url.getPath().isEndsWithSlash() );
    	
    	assertTrue( urlSlash.hasHost() );
    	assertTrue( urlSlash.hasPath() );
    	assertFalse( urlSlash.getPath().isEndsWithSlash() );
    	assertTrue( urlSlash.getPath().isStartsWithSlash() );
    	
    	assertFalse( urlSlash.getPath().isEndsWithSlash() );
    	assertTrue( urlSlash.getPath().isStartsWithSlash() );
    	
    	SuperURL pathUrl = SuperURLBuilders.fromString().build( path );
    	assertFalse( pathUrl.hasHost() );
    	
    	url.appendPath( path );
    	urlSlash.appendPath( path );
    	
    	assertEquals( url.toStringEncode( false ), refUrl );
    	assertEquals( urlSlash.toStringEncode( false ), refUrl );
    }
    
    public void createEmpty() {
    	
    	SuperURL empty = new SuperURL();
    	
    	assertTrue( empty.isIncludeScheme() );
    	assertTrue( empty.isIncludeUserInfo() );
    	assertTrue( empty.isIncludeHost() );
    	assertTrue( empty.isIncludePort() );
    	assertTrue( empty.isIncludePath() );
    	assertTrue( empty.isIncludeQuery() );
    	assertTrue( empty.isIncludeFragment() );
    	
    	assertNull( empty.getScheme() );
    	assertNull( empty.getUserInfo() );
    	assertNull( empty.getHost() );
    	assertNull( empty.getPort() );
    	assertEquals( empty.getPath(), new SuperURL.Path() );
    	assertEquals( empty.getQuery(), new SuperURL.Query() );
    	assertNull( empty.getFragment() );
    	
    	assertEquals( empty.toStringEncode( false ), "" ); // TODO: not nice
    	
    }
    
    @Test( dependsOnMethods = "testPathOperations" )
    public void testMorePathOperations() throws Exception {
    
    	String urlS = "http://www.axon-e.de";
    	String urlSlashS = "http://www.axon-e.de/";
    	String path = "test";
    	
    	String refUrl = "http://www.axon-e.de/test";
    
    	SuperURL url = SuperURLBuilders.fromString().build( urlS );
    	SuperURL urlSlash = SuperURLBuilders.fromString().build( urlSlashS );
    	
    	url.appendPath( path );
    	urlSlash.appendPath( path );
    	
    	assertEquals( url.toStringEncode( false ), refUrl );
    	assertEquals( urlSlash.toStringEncode( false ), refUrl );
    	
    	url = SuperURLBuilders.fromString().build( urlS );
    	urlSlash = SuperURLBuilders.fromString().build( urlSlashS );
    	
    	url.appendPath( path + "/" );
    	urlSlash.appendPath( path + "/" );
    	
    	assertEquals( url.toStringEncode( false ), refUrl + "/" );
    	assertEquals( urlSlash.toStringEncode( false ), refUrl + "/" );
    }
    
    public void testKnownProblemsWithSquareBracketsInPath() throws Exception {
    	
    	String urlS = "http://headshop-de.localhost:8080" +
    			"/tree/3015/5mm-Glas" +
    			"/article/00393/Weedstar-Mad-Professor-(3.Semester)-40cm-hoch.xhtml[/url]";
    	SuperURL url = SuperURLBuilders.fromString().build( urlS );
    	assertEquals( url.toStringEncode( false ), urlS );
    	assertEquals( url.toStringEncode( true ),"http://headshop-de.localhost:8080" +
    			"/tree/3015/5mm-Glas" +
    			"/article/00393/Weedstar-Mad-Professor-%283.Semester%29-40cm-hoch.xhtml%5B/url%5D" );
    }
    
    public void percentInPathShallBeEncoded() throws Exception {
    	
    	SuperURL url = SuperURLBuilders.fromString().build( "http://www.axon-e.de" );
    	url.getPath().addFirst( "I'm 100% yours" );
    	assertEquals( url.toStringEncode( false ), "http://www.axon-e.de/I'm 100% yours" );
    	assertEquals( url.toStringEncode( true ), "http://www.axon-e.de/I%27m+100%25+yours" );
    }
    
    public void testEncoding() throws Exception {
    	
    	SuperURL url = SuperURLBuilders.fromString().build( "http://www.axon-e.de/Bläh" );
    	assertEquals( url.toStringEncode( true ), "http://www.axon-e.de/Bl%C3%A4h" );
    	assertEquals( url.toStringEncode( false ), "http://www.axon-e.de/Bläh" );
    	
    	url = new SuperURL();
    	url.setScheme( "http" );
    	url.setHost( SuperURL.Host.parse( "www.äxon-e.de" ) );
    	url.setPath( Path.parse( "Bläh" ) );
    	assertEquals( url.toStringEncode( true ), "http://www.%C3%A4xon-e.de/Bl%C3%A4h" );
    	assertEquals( url.toStringEncode( false ), "http://www.äxon-e.de/Bläh" );
    	
    }
    
    public void testSlashesInParameters() throws Exception {
    	
    	SuperURL url = SuperURLBuilders.fromString().build( "http://www.axon-e.de" );
    	
    	url.getPath().addAll( "foo/bar" );
    	
    	assertEquals( url.toStringEncode( false ), "http://www.axon-e.de/foo/bar" );
    	assertEquals( url.toStringEncode( true ), "http://www.axon-e.de/foo%2Fbar" );
    	
    }
    
    public void testEmptyParameters() throws Exception {
    	
    	SuperURL url = SuperURLBuilders.fromString().build( "http://www.axon-e.de?q&a=b" );
    	
    	SuperURL.Query query = url.getQuery();
    	
    	assertEquals( query.size(), 2 );
    	assertEquals( query.getValue( "a" ), "b" );
    	assertNull( query.getValue( "q" ) );
    	
    	assertEquals( url.toStringEncode( false ), "http://www.axon-e.de?q&a=b" );
    	assertEquals( url.toStringEncode( true ), "http://www.axon-e.de?q&a=b" ); //Same
    	
    }
    
    public void testParsingOfEncodedURLs() throws Exception {
 
    	String unencoded = "http://www.axon-e.de/Bläh?füü=bär";
    	
    	SuperURL url = SuperURLBuilders.fromString().build( unencoded );
    	
    	assertEquals( url.getScheme(), "http" );
    	assertEquals( url.getHost().toString( false ), "www.axon-e.de" );
    	assertThat( url.getPath().toList() )
    			.contains( "Bläh", atIndex( 0 ) )
    			.hasSize( 1 )
    			;
    	assertThat( url.getQuery() )
    			.contains( new SuperURL.Query.QueryPart( "füü", "bär" ) )
    			.hasSize( 1 )
    			;
    	
    	String encoded = url.toStringEncode( true );
    	assertEquals( encoded, "http://www.axon-e.de/Bl%C3%A4h?f%C3%BC%C3%BC=b%C3%A4r" );
    	
    	SuperURL reread = SuperURLBuilders.fromString().build( encoded );
    	
    	assertThat( url ).isEqualTo( reread );
    	
    }
    
    public void testOnlyDomain(){
    	
    	String noSlash = "http://www.axon-e.de",
    	       oneSlash = noSlash + "/",
    	       twoSlash = oneSlash + "/"
    	       ;
    	
    	SuperURL url;
    	       
    	url = SuperURLBuilders.fromString().build( noSlash );
    	assertEquals( url.getPath().length(), 0 );
    	assertFalse( url.getPath().isEndsWithSlash() );
    	assertFalse( url.getPath().isStartsWithSlash() );
    	assertEquals( url.toStringEncode( false ), noSlash );
    	
    	url = SuperURLBuilders.fromString().build( oneSlash );
    	assertEquals( url.getPath().length(), 0 );
    	assertTrue( url.getPath().isStartsWithSlash() );
    	assertFalse( url.getPath().isEndsWithSlash() );
    	assertEquals( url.toStringEncode( false ), oneSlash );
    	
    	url = SuperURLBuilders.fromString().build( twoSlash );
    	assertEquals( url.getPath().length(), 1 );
    	assertTrue( url.getPath().isStartsWithSlash() );
    	assertTrue( url.getPath().isEndsWithSlash() );
    	assertEquals( url.toStringEncode( false ), twoSlash );
    }
}
