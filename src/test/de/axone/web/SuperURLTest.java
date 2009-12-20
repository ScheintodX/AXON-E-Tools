package de.axone.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test( groups="web.superurl" )
public class SuperURLTest {

	public void testHost() throws Exception {
		
		SuperURL.Host host;
		String hostStr;
		
		/* ----------- */
		hostStr = "www.axon-e.de";
		host = new SuperURL.Host( hostStr );
		
		assertEquals( host.getHost(), "www" );
		assertEquals( host.getNet().get( 0 ), "axon-e" );
		assertEquals( host.getNetAsString(), "axon-e" );
		assertEquals( host.getTld(), "de" );
		assertEquals( host.toString(), hostStr );
		
		host.getParts().removeFirst();
		assertEquals( host.toString(), "axon-e.de" );
		
		/* ----------- */
		hostStr = "test.webs.axon-e.de";
		host = new SuperURL.Host( hostStr );
		
		assertEquals( host.getHost(), "test" );
		assertEquals( host.getNet().size(), 2 );
		assertEquals( host.getNet().get( 0 ), "webs" );
		assertEquals( host.getNet().get( 1 ), "axon-e" );
		assertEquals( host.getNetAsString(), "webs.axon-e" );
		assertEquals( host.getTld(), "de" );
		assertEquals( host.toString(), hostStr );
		
	}
	
	public void testPath() throws Exception {
		
		SuperURL.Path path;
		String pathStr;
		
		pathStr = "/dir1/dir2/file.ext";
		path = new SuperURL.Path( pathStr );
		
		assertEquals( path.getLength(), 3 );
		assertEquals( path.getFirst(), "dir1" );
		assertEquals( path.getLast(), "file.ext" );
		assertEquals( path.getExtension(), "ext" );
		assertEquals( path.getLastWithoutExtension(), "file" );
		assertEquals( path.getPart( 0 ), "dir1" );
		assertEquals( path.getPart( 1 ), "dir2" );
		assertEquals( path.getPart( 2 ), "file.ext" );
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
	
	public void testQuery() throws Exception {
		
		SuperURL.Query query;
		String queryStr;
		
		queryStr = "key1=val1&key2=val2&key2=val22&key3";
		query = new SuperURL.Query( queryStr );
		
		assertEquals( query.size(), 4 );
		assertTrue( query.has( "key1" ) );
		assertEquals( query.getPart( "key1").getValue(), "val1" );
		assertEquals( query.getValue( "key1" ), "val1" );
		assertTrue( query.has( "key2" ) );
		assertEquals( query.getPart( "key2").getValue(), "val2" );
		assertEquals( query.getValue( "key2" ), "val2" );
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
		assertEquals( query.getValue( "key2" ), "newval2" );
		
	}
	
	public void testUserInfo() throws Exception {
		
		SuperURL.UserInfo userInfo;
		
		userInfo = new SuperURL.UserInfo( "myuser:mypass" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertEquals( userInfo.getPass(), "mypass" );
		assertEquals( userInfo.toString(), "myuser:mypass" );
		
		userInfo = new SuperURL.UserInfo( "myuser" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertNull( userInfo.getPass() );
		assertEquals( userInfo.toString(), "myuser" );
		
		userInfo = new SuperURL.UserInfo( "myuser:" );
		assertEquals( userInfo.getUser(), "myuser" );
		assertEquals( userInfo.getPass(), "" );
		assertEquals( userInfo.toString(), "myuser:" );
		
		userInfo = new SuperURL.UserInfo( ":mypass" );
		assertEquals( userInfo.getUser(), "" );
		assertEquals( userInfo.getPass(), "mypass" );
		assertEquals( userInfo.toString(), ":mypass" );
		
		userInfo = new SuperURL.UserInfo( ":" );
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
    	assertNull( empty.getPath() );
    	assertNull( empty.getQuery() );
    	assertNull( empty.getFragment() );
    	
    	// Constructor with empty URI. (Nonsense but has to work nevertheless)
    	SuperURL emptyToo = new SuperURL( "" );
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
    	
    	SuperURL complete = new SuperURL( uriString );
    	
    	assertEquals( complete.toString(), uriString );
    	
    	assertEquals( complete.getScheme(), "https" );
    	assertEquals( complete.getUserInfo().toString(), "myuser:mypass" );
    	assertEquals( complete.getHost().toString(), "www.domain.de" );
    	assertEquals( complete.getPath().toString(), "/dir1/dir2/myfile.myext/" );
    	assertEquals( complete.getQuery().toString(), "key1=value1&key2=value2&key2=value22&key3" );
    	assertEquals( complete.getFragment(), "myFragment" );
    	
    	// In this case encoded version equals unencoded
    	assertEquals( complete.toString( true ), uriString );
    }
	
}
