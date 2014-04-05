package de.axone.tools;

import org.testng.annotations.Test;

import de.axone.web.RequestUtil;
import de.axone.web.TestHttpServletRequest;

import static org.testng.Assert.*;

@Test( groups="tools.requestutil" )
public class TestRequestUtil {

	public void testGetNameParameter() throws Exception {
		
		String test = "blah-666";
		String testResult = RequestUtil.extract( test, "blah" );
		assertEquals( testResult, "666" );
		
		TestHttpServletRequest request = new TestHttpServletRequest();
		request.setParameter( "item1-123", "test123" );
		request.setParameter( "item2-", "test456" );
		
		assertTrue( RequestUtil.hasNameParameter( request, "item1" ) );
		String item1 = RequestUtil.getNameParameter( request, "item1" );
		assertEquals( item1, "123" );
		
		assertTrue( RequestUtil.hasNameParameter( request, "item2" ) );
		String item2 = RequestUtil.getNameParameter( request, "item2" );
		assertEquals( item2, "" );
		
		assertFalse( RequestUtil.hasNameParameter( request, "wrong" ) );
		String wrong = RequestUtil.getNameParameter( request, "wrong" );
		assertNull( wrong );
	}
}
