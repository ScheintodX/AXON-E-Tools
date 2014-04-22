package de.axone.data;

import static org.testng.Assert.*;

import java.io.File;

import org.testng.annotations.Test;

import de.axone.data.Mime.MimeType;

@Test( groups = "tools.mime" )
public class MimeTest {

	public void testMimeTypes(){
		
		MimeType type = Mime.MimeTypes.forExtension( "jpg" );
		
		assertNotNull( type );
		assertEquals( type.getGroup(), "image" );
		assertEquals( type.getType(), "jpeg" );
		
		type = Mime.MimeTypes.forExtension( "psd" );
		assertNull( type );
		
	}
	
	public void testLoading() throws Exception {
		
		Mime mime = new Mime();
		mime.load( new File( "/etc/mime.types" ) );
		
		MimeType type = mime.forExtension( "psd" );
		assertNotNull( type );
		assertEquals( type.getGroup(), "image" );
		assertEquals( type.getType(), "x-photoshop" );
	}
}
