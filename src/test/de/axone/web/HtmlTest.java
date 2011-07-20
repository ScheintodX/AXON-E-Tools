package de.axone.web;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="web.html" )
public class HtmlTest {

	public void testRemoveHtml(){
		
		assertEquals( Html.removeTags( "<b>test</b>" ), "test" );
		assertEquals( Html.removeTags( "-<a>a<b>b<c>c<d>d</b>-" ), "-abcd-" );
	}
}
