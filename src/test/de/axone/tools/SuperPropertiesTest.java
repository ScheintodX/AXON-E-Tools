package de.axone.tools;

import static org.testng.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.Test;

@Test( groups="tools.superproperties" )
public class SuperPropertiesTest {

	public static void main( String [] args ){
		new SuperPropertiesTest().testAccess();
		new SuperPropertiesTest().testTypes();
		new SuperPropertiesTest().testFiles();
	}

	public void testAccess(){

		Properties backend = new Properties();

		backend.put( "A", "a--" );
		backend.put( "B.1", "b--1" );
		backend.put( "B.2", "b--2" );
		backend.put( "B.3", "b--3" );
		backend.put( "C.D", "cd-" );
		backend.put( "C.E.1", "ce-1" );
		backend.put( "C.E.2", "ce-2" );
		backend.put( "C.E.3", "ce-3" );
		backend.put( "C.F.G", "cfg" );
		backend.put( "C.F.H", "cfh" );

		SuperProperties p = new SuperProperties( backend );

		// Property access
		assertEquals( p.getProperty( "A" ), "a--" );

		// List access
		List<String> listB = p.getList( "B" );
		assertNotNull( listB );
		assertEquals( listB.size(), 3 );
		assertEquals( listB.get( 0 ), "b--1" );
		assertEquals( listB.get( 1 ), "b--2" );
		assertEquals( listB.get( 2 ), "b--3" );

		// Sub-Properties
		SuperProperties pC = p.subset( "C" );
		assertEquals( pC.getProperty( "D" ), "cd-" );

		List<String> listCE = pC.getList( "E" );
		assertNotNull( listCE );
		assertEquals( listCE.size(), 3 );
		assertEquals( listCE.get( 0 ), "ce-1" );
		assertEquals( listCE.get( 1 ), "ce-2" );
		assertEquals( listCE.get( 2 ), "ce-3" );

	}

	public void testTypes(){

		Properties backend = new Properties();

		backend.put( "string", "string" );
		backend.put( "int", "123" );
		backend.put( "bool", "true" );

		SuperProperties p = new SuperProperties( backend );

		assertEquals( p.getProperty( "string" ), "string" );
		assertEquals( (int)p.getInt( "int" ), 123 );
		assertEquals( (boolean)p.getBoolean( "bool" ), true );
		assertEquals( p.getProperty( "no", "default" ), "default" );
		assertEquals( p.getInt( "no", 321 ), 321 );
		assertEquals( p.getBoolean( "no", true ), true );

	}

	public void testFiles(){

		Properties backend = new Properties();

		File abs = new File( "abs.txt" ).getAbsoluteFile();

		backend.put( "base", "base" );
		backend.put( "rel", "rel.ext" );
		backend.put( "abs", abs.getPath() ); //<- allways the abs. path

		SuperProperties p = new SuperProperties( backend );

		assertEquals( p.getFile( "rel" ), new File( "rel.ext" ) );
		assertEquals( p.getFile( "abs" ), abs );
		assertFalse( p.getFile( "rel" ).isAbsolute() );
		assertTrue( p.getFile( "abs" ).isAbsolute() );

		assertEquals( p.getFile( new File( "bd" ), "rel" ), new File( "bd/rel.ext" ) );
		assertEquals( p.getFile( new File( "bd" ), "abs" ), abs );

		assertEquals( p.getFile( "base", "rel" ), new File( "base/rel.ext" ) );
		assertEquals( p.getFile( "base", "abs" ), abs );

		p.setRootDir( new File( "root" ) );

		assertEquals( p.getFile( "rel" ), new File( "root/rel.ext" ) );
		assertEquals( p.getFile( "abs" ), abs );

		assertEquals( p.getFile( new File( "bd" ), "rel" ), new File( "root/bd/rel.ext" ) );
		assertEquals( p.getFile( new File( "bd" ), "abs" ), abs );

		assertEquals( p.getFile( "base", "rel" ), new File( "root/base/rel.ext" ) );
		assertEquals( p.getFile( "base", "abs" ), abs );

	}

}
