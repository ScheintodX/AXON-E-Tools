package de.axone.tools;

import static de.axone.tools.StringValueAccessorAssert.*;
import static org.testng.Assert.*;

import java.io.File;
import java.util.List;

import org.testng.annotations.Test;

@Test( groups="tools.superproperties" )
public class SuperPropertiesTest {

	public void testAccess(){

		SuperProperties p = new SuperProperties();

		p.set( "A", "a--" );
		p.set( "B.1", "b--1" );
		p.set( "B.2", "b--2" );
		p.set( "B.3", "b--3" );
		p.set( "C.D", "cd-" );
		p.set( "C.E.1", "ce-1" );
		p.set( "C.E.2", "ce-2" );
		p.set( "C.E.3", "ce-3" );
		p.set( "C.F.G", "cfg" );
		p.set( "C.F.H", "cfh" );

		// Property access
		assertEquals( p.get( "A" ), "a--" );

		assertThis( p )
				.valueIs( "A", "a--" )
				;


		// List access
		List<String> listB = p.getList( "B" );
		assertNotNull( listB );
		assertEquals( listB.size(), 3 );
		assertEquals( listB.get( 0 ), "b--1" );
		assertEquals( listB.get( 1 ), "b--2" );
		assertEquals( listB.get( 2 ), "b--3" );

		// Sub-Properties
		SuperProperties pC = p.subset( "C" );
		assertEquals( pC.get( "D" ), "cd-" );
		assertThis( pC )
				.valueIs( "D", "cd-" );

		List<String> listCE = pC.getList( "E" );
		assertNotNull( listCE );
		assertEquals( listCE.size(), 3 );
		assertEquals( listCE.get( 0 ), "ce-1" );
		assertEquals( listCE.get( 1 ), "ce-2" );
		assertEquals( listCE.get( 2 ), "ce-3" );

	}

	public void testTypes(){

		SuperProperties p = new SuperProperties();
		p.set( "string", "string" );
		p.set( "int", "123" );
		p.set( "bool", "true" );

		assertEquals( p.get( "string" ), "string" );
		assertEquals( (int)p.getInteger( "int" ), 123 );
		assertEquals( (boolean)p.getBoolean( "bool" ), true );
		assertEquals( p.get( "no", "default" ), "default" );
		assertEquals( p.getInteger( "no", 321 ), 321 );
		assertEquals( p.getBoolean( "no", true ), true );

		assertThis( p )
				.valueIs( "string", "string" )
				.valueAsIntegerIs( "int", 123 )
				.valueAsBooleanIs( "bool", true )
				;

	}

	public void testFiles() throws Exception {

		File abs = new File( "abs.txt" ).getAbsoluteFile();

		SuperProperties p = new SuperProperties();
		p.set( "base", "base" );
		p.set( "rel", "rel.ext" );
		p.set( "abs", abs.getPath() ); //<- allways the abs. path

		assertEquals( p.getFile( "rel" ), new File( "rel.ext" ) );
		assertEquals( p.getFile( "abs" ), abs );
		assertFalse( p.getFile( "rel" ).isAbsolute() );
		assertTrue( p.getFile( "abs" ).isAbsolute() );

		assertEquals( p.getFile( new File( "bd" ), "rel" ), new File( "bd/rel.ext" ) );
		assertEquals( p.getFile( new File( "bd" ), "abs" ), abs );

		assertEquals( p.getFile( "base", "rel" ), new File( "base/rel.ext" ) );
		assertEquals( p.getFile( "base", "abs" ), abs );

		assertEquals( p.getFile( "no", "rel" ), new File( "rel.ext" ) );
		assertEquals( p.getFile( "no", "abs" ), abs );

		p.setRootDir( new File( "root" ) );

		assertEquals( p.getFile( "rel" ), new File( "root/rel.ext" ) );
		assertEquals( p.getFile( "abs" ), abs );

		assertEquals( p.getFile( new File( "bd" ), "rel" ), new File( "root/bd/rel.ext" ) );
		assertEquals( p.getFile( new File( "bd" ), "abs" ), abs );

		assertEquals( p.getFile( "base", "rel" ), new File( "root/base/rel.ext" ) );
		assertEquals( p.getFile( "base", "abs" ), abs );

		assertEquals( p.getFile( "no", "rel" ), new File( "root/rel.ext" ) );
		assertEquals( p.getFile( "no", "abs" ), abs );

	}

	public void testFilesRequired() throws Exception {

		File abs = new File( "abs.txt" ).getAbsoluteFile();

		SuperProperties p = new SuperProperties();
		p.set( "base", "base" );
		p.set( "rel", "rel.ext" );
		p.set( "abs", abs.getPath() ); //<- allways the abs. path

		assertEquals( p.getFileRequired( "rel" ), new File( "rel.ext" ) );
		assertEquals( p.getFileRequired( "abs" ), abs );
		assertFalse( p.getFileRequired( "rel" ).isAbsolute() );
		assertTrue( p.getFileRequired( "abs" ).isAbsolute() );

		assertEquals( p.getFileRequired( new File( "bd" ), "rel" ), new File( "bd/rel.ext" ) );
		assertEquals( p.getFileRequired( new File( "bd" ), "abs" ), abs );

		assertEquals( p.getFileRequired( "base", "rel" ), new File( "base/rel.ext" ) );
		assertEquals( p.getFileRequired( "base", "abs" ), abs );

		p.setRootDir( new File( "root" ) );

		assertEquals( p.getFileRequired( "rel" ), new File( "root/rel.ext" ) );
		assertEquals( p.getFileRequired( "abs" ), abs );

		assertEquals( p.getFileRequired( new File( "bd" ), "rel" ), new File( "root/bd/rel.ext" ) );
		assertEquals( p.getFileRequired( new File( "bd" ), "abs" ), abs );

		assertEquals( p.getFileRequired( "base", "rel" ), new File( "root/base/rel.ext" ) );
		assertEquals( p.getFileRequired( "base", "abs" ), abs );

	}

}
