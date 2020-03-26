package de.axone.web;

import static de.axone.test.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.axone.data.tupple.Pair;
import de.axone.file.Crusher;

@Test( groups="tools.picturebuilder" )
public class PictureBuilderTest {

	// ../Tools because cwd is different depending on how the test is run
	Path home = Paths.get( "src/test/resources/cache" );

	public void buildPictures() throws Exception {

		PictureBuilderBuilder b = PictureBuilderBuilder.instance( home )
				.watermark( "watermark/test.png" )
				;

		PictureBuilderNG builder = b.builder( "test001", 0 );

		Optional<Pair<Path,Boolean>> o = builder.get( 100 );
		assertThat( o )
				.as( "pic 100" )
				.isNotNull()
				.isPresent()

				;
		assertPath( o.get().getLeft() )
				.startsWith( home.resolve( "testpng/t/test001/100/test001.jpg" ) )
				;

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

	@BeforeClass
	public void checkDependencies() {

		// check everything is there as needed now
		assertPath( home.resolve( "main/t/test001" ) ).isDirectory()
				.resolve( "test001.jpg" ).isFile()
				;
		assertPath( home.resolve( "watermark" ) )
				.isDirectory()
				.resolve( "test.png" )
				.isFile()
				;

	}

	@BeforeClass
	@AfterClass
	public void cleanup() throws Exception {

		// remove files from old run
		Crusher.crushDirIfExists( home.resolve( "plain" ) );
		Crusher.crushDirIfExists( home.resolve( "testpng" ) );

	}

	public void realRun() throws Exception {

		PictureBuilderBuilder pbb = PictureBuilderBuilder.instance( home );

		PictureBuilderNG builder = pbb.builder( "test001", 0 );
		assertTrue( builder.exists() );
		assertEquals( builder.fileCount(), 20 );
		Optional<Pair<Path,Boolean>> scaledImage = builder.get( 100 );

		assertThat( scaledImage ).isPresent();
		assertPath( scaledImage.get().getLeft() )
				.startsWith( home.resolve( "plain/t/test001/100/test001.jpg" ) )
				;

		assertPath( home )
				.resolve( "plain" ).isDirectory()
				.resolve( "t" ).isDirectory()
				.resolve( "test001" ).isDirectory()
				.resolve( "100" ).isDirectory()
				.find( "test001\\.jpg.*" ).isFile()
				;

		assertThat( PictureBuilderBuilder.DEFAULT_PRESCALE_SIZE )
				.isEqualTo( 2000 ); // May change

		assertPath( home )
				.resolve( "plain/t/test001/" + PictureBuilderBuilder.DEFAULT_PRESCALE_SIZE ).isDirectory()
				.find( "test001\\.jpg.*" ).isFile()
				;

		// Now with watermark:
		pbb = pbb.watermark( "watermark/test.png" );

		builder = pbb.builder( "test001", 0 );
		scaledImage = builder.get( 100 );

		Path test001_testpng100 = assertPath( home.resolve( "testpng/t/test001/100" ) )
				.find( "test001\\.jpg.*" )
				.andReturn()
				;

		assertThat( scaledImage ).isPresent();
		assertPath( scaledImage.get().getLeft() )
				.isEqualTo( test001_testpng100 )
				;
	}

	private Path builderGet( String file, int index, int size ) throws IOException {

		PictureBuilderBuilder pbb = PictureBuilderBuilder.instance( home );

		PictureBuilderNG builder = pbb.builder( file, index );

		Optional<Pair<Path,Boolean>> x = builder.get( size );
		assertThat( x )
				.isNotNull()
				.isPresent();

		assertThat( x.get() )
				.isNotNull()
				;

		Path p = x.get().getLeft();

		assertThat( p )
				.isNotNull()
				;

		return x.get().getLeft();
	}

	public void testSortedIndex() throws Exception {

		PictureBuilderBuilder pbb = PictureBuilderBuilder.instance( home );

		PictureBuilderNG builder = pbb.builder( "test002", 0 );

		assertEquals( builder.fileCount(), 3 ); //test001.jpg, test002.png, test002.jpg

		assertThat( builderGet( "test002", 0, 100 ).getFileName().toString() )
				.startsWith( "test002.jpg" )
				;

		assertThat( builderGet( "test002", 1, 100 ).getFileName().toString() )
				.startsWith( "test002.png" )
				;

		assertThat( builderGet( "test002", 2, 100 ).getFileName().toString() )
				.startsWith( "test001.jpg" )
				;

	}

	private List<Path> path( String ... strings ){
		return Arrays.asList( strings )
				.stream()
				.map( Paths::get )
				.collect( Collectors.toList() )
				;
	}

	public void testComponents() {

		List<Path> files = path( "test3.jpg", "test2.jpg", "test0.jpg", "test1.jpg" ),
				expected = path( "test2.jpg", "test0.jpg", "test1.jpg", "test3.jpg" );

		files.sort( new PictureBuilderBuilderImpl.SameNameFirst( "test2" ) );

		assertThat( files ).isEqualTo( expected );

	}
}
