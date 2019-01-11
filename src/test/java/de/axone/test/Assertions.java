package de.axone.test;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractObjectAssert;

import de.axone.exception.NotImplementedException;


public class Assertions {

	public static PathAssert assertPath( Path path ) {
		
		return new PathAssert( path ).as( path.getFileName().toString() );
	}
	
	public static <X> StreamAssert<X> assertStream( Stream<X> stream ) {
		
		return new StreamAssert<>( stream );
	}
	
	/* ================= */
	/* = Path ========== */
	/* ================= */
	public static class PathAssert extends AbstractPathAssert<PathAssert, Path> {

		protected PathAssert( Path actual ) {
			super( actual, PathAssert.class );
		}
	}

	public static abstract class AbstractPathAssert<
			S extends AbstractPathAssert<S, A>,
			A extends Path
	> extends AbstractObjectAssert<S, A> {

		protected AbstractPathAssert( A actual, Class<?> selfType ) {
			super( actual, selfType );
		}
		
		public S isEqualTo( Path other ) {
			
			/*
			if( ! actual.equals( other ) )
					org.assertj.core.api.Assertions.fail( "Expected: '" + other + "' actual: '" + actual + "' differ" );
			*/
			assertEquals( actual, other );
			
			return myself;
		}
		
		public S isEqualTo( String other ) {
			
			return isEqualTo( Paths.get( other ) );
		}
		
		public S isFile() {
			
			if( ! Files.exists( actual ) )
					failWithMessage( "Does not exist: '%s'", actual.toAbsolutePath() );
			
			if( ! Files.isReadable( actual ) )
					failWithMessage( "Not a regular file: '%s'", actual.toAbsolutePath() );
			
			return myself;
		}
		
		public S isDirectory( LinkOption ... options ) {
			
			if( ! Files.isDirectory( actual, options ) )
					failWithMessage( "Not a directory: '%s'", actual.toAbsolutePath() );
			
			return myself;
		}
		
		public S startsWith( Path other ) {
			
			if( actual.startsWith( other ) )
					failWithMessage( "expected '%s' to start with '%s', but didn't", actual, other );
			
			return myself;
		}
		
		public PathAssert resolve( Path p ) {
			
			return new PathAssert( actual.resolve( p ) );
		}
		
		public PathAssert resolve( String p ) {
			
			return new PathAssert( actual.resolve( p ) );
		}
		
		public PathAssert find( String pattern ) throws IOException {
			
			Optional<Path> maybeFound = Files.list( actual )
					.filter( p -> p.getFileName().toString().matches( pattern ) )
					.findFirst();
			
			 assertThat( maybeFound )
					.isPresent();
			 
			 Path found = maybeFound.get();
			
			return assertPath( found );
		}
		
		S isSameAs( Path other ) {
			
			throw new NotImplementedException();
		}
		
		public A andReturn() {
			return actual;
		}
	}
	
	public static class StreamAssert<X> extends AbstractStreamAssert<X, StreamAssert<X>> {

		protected StreamAssert( Stream<X> actual ) {
			super( actual );
		}
	}
	
}
