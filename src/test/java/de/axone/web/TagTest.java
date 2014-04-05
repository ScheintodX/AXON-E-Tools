package de.axone.web;

import static org.testng.Assert.*;

import java.util.LinkedList;

import org.testng.annotations.Test;

@Test( groups="web.tags" )
public class TagTest {

	public void testLink(){
		
		LinkedList<String> args = new LinkedList<String>();
		
		String target = "Target";
		String text = "Text";
		String clazz = "Class";
		
		assertEquals( Tag.link( target, text, null, (String[])null ),
				"<a href=\"Target\">Text</a>" );
		assertEquals( Tag.link( target, text, null, args.toArray( new String[0]) ),
				"<a href=\"Target\">Text</a>" );
		
		assertEquals( Tag.link( target, text, clazz, (String[])null ),
				"<a href=\"Target\" class=\"Class\">Text</a>" );
		assertEquals( Tag.link( target, text, clazz, args.toArray( new String[0]) ),
				"<a href=\"Target\" class=\"Class\">Text</a>" );
		
		args.add( "a" );
		args.add( "A" );
		assertEquals( Tag.link( target, text, clazz, args.toArray( new String[0]) ),
				"<a href=\"Target?a=A\" class=\"Class\">Text</a>" );
		
		args.add( "b" );
		args.add( "B" );
		assertEquals( Tag.link( target, text, clazz, args.toArray( new String[0]) ),
				"<a href=\"Target?a=A&amp;b=B\" class=\"Class\">Text</a>" );
		
	}
}
