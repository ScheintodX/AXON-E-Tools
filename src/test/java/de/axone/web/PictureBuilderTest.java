package de.axone.web;

import static de.axone.test.Assertions.*;
import static org.testng.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.testng.annotations.Test;

import de.axone.file.Crusher;
import de.axone.tools.E;

@Test( groups="tools.picturebuilder" )
public class PictureBuilderTest {

	public void buildPictures() throws Exception {
		
		PictureBuilderBuilder b = PictureBuilderBuilder.instance( Paths.get( "cache" ) )
				.watermark( "watermark" )
				;
		
		PictureBuilderNG builder = b.builder( "BLAH", 0 );
		
		E.rr( builder.exists() );
		E.rr( builder.get( 100 ) );
		
		assertEquals( builder.get( 100 ), Paths.get( "cache/watermark/B/100/BLAH.jpg" ) );
	}
	
	public void hashNames() {
		
		assertEquals( PictureBuilderBuilderImpl.hashName( "12345", 1 ), "1" );
		assertEquals( PictureBuilderBuilderImpl.hashName( "02345", 1 ), "2" );
		assertEquals( PictureBuilderBuilderImpl.hashName( "00005", 1 ), "5" );
		assertEquals( PictureBuilderBuilderImpl.hashName( "00000", 1 ), "0" );
		
		assertEquals( PictureBuilderBuilderImpl.hashName( "12345", 2 ), "12" );
		assertEquals( PictureBuilderBuilderImpl.hashName( "02345", 2 ), "23" );
		assertEquals( PictureBuilderBuilderImpl.hashName( "00005", 2 ), "05" );
		assertEquals( PictureBuilderBuilderImpl.hashName( "00000", 2 ), "00" );
		
		assertEquals( PictureBuilderBuilderImpl.hashName( "12345", 5 ), "12345" );
		
		assertEquals( PictureBuilderBuilderImpl.hashName( "12345", 6 ), "12345" );
	}
	
	public void mainFile() throws Exception {
		
		Path tmp = Files.createTempDirectory( "emt" );
		
		Path maindir = tmp.resolve( "main" );
		
		Path hashdir = maindir.resolve( "1/12345" );
		
		Path jpg12345 = hashdir.resolve( "12345.jpg" );
		Path jpg12345_1 = hashdir.resolve( "12345_1.jpg" );
		
		while( !hashdir.toFile().mkdirs() ) Thread.sleep( 10 );
		
		while( !jpg12345.toFile().createNewFile() ) Thread.sleep( 10 );
		while( !jpg12345_1.toFile().createNewFile() ) Thread.sleep( 10 );
		
		
		E.rr( jpg12345.toFile().getAbsolutePath() );
		
		Optional<Path> p;
		
		p = PictureBuilderBuilderImpl.findFile( maindir, "1", "12345", 0 );
		assertTrue( p.isPresent() );
		assertEquals( p.get(), jpg12345 );
		
		p = PictureBuilderBuilderImpl.findFile( maindir, "1", "12345", 1 );
		assertTrue( p.isPresent() );
		assertEquals( p.get(), jpg12345_1 );
		
		p = PictureBuilderBuilderImpl.findFile( maindir, "1", "12345", 2 );
		assertFalse( p.isPresent() );
		
		Crusher.crushDir( tmp );
	}
	
	public void realRun() throws Exception {
		
		Path home = Paths.get( "src/test/resources/cache" );
		
		// check everything is there as needed now
		assertTrue( Files.isDirectory( home.resolve( "main/t/test001" ) ) );
		assertTrue( Files.isReadable( home.resolve( "main/t/test001/test001.jpg" ) ) );
		
		assertTrue( Files.isDirectory( home.resolve( "watermark" ) ) );
		assertTrue( Files.isReadable( home.resolve( "watermark/test.png" ) ) );
		
		// remove files from old run
		Crusher.crushDirIfExists( home.resolve( "plain" ) );
		Crusher.crushDirIfExists( home.resolve( "testpng" ) );
		
		PictureBuilderBuilder pbb = PictureBuilderBuilder.instance( home );
		
		PictureBuilderNG builder = pbb.builder( "test001", 0 );
		assertTrue( builder.exists() );
		assertEquals( builder.fileCount(), 1 );
		Optional<Path> scaledImage = builder.get( 100 );
		
		assertThat( scaledImage ).isPresent();
		assertThat( scaledImage.get() )
				.isEqualTo( home.resolve( "plain/t/test001/100/test001.jpg" ) )
				;
		
		assertThat( home )
				.resolve( "plain" ).isDirectory()
				.resolve( "t" ).isDirectory()
				.resolve( "test001" ).isDirectory()
				.resolve( "100" ).isDirectory()
				.resolve( "test001.jpg" ).isFile()
				;
		assertThat( home )
				.resolve( "plain/t/test001/1000" ).isDirectory()
				.resolve( "test001.jpg" ).isFile()
				;
		
		
		// Now with watermark:
		pbb = pbb.watermark( "test.png" );
		
		builder = pbb.builder( "test001", 0 );
		scaledImage = builder.get( 100 );
		
		assertThat( scaledImage ).isPresent();
		assertThat( scaledImage.get() )
				.isEqualTo( home.resolve( "testpng/t/test001/100/test001.jpg" ) )
				;
	}
}
