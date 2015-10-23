package de.axone.test;

import static org.testng.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ObjectAssert;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class Assertions {

	public static <X> OptionalAssert<X> assertOptional( Optional<X> optional ) {
		
		return new OptionalAssert<X>( optional );
	}
	
	public static PathAssert assertPath( Path path ) {
		
		return new PathAssert( path ).as( path.getFileName().toString() );
	}
	
	public static <X> StreamAssert<X> assertStream( Stream<X> stream ) {
		
		return new StreamAssert<>( stream );
	}
	
	
	/* ================= */
	/* = Optional ====== */
	/* ================= */
	public static class OptionalAssert<X> extends AbstractOptionalAssert<X, OptionalAssert<X>> {

		protected OptionalAssert( Optional<X> actual ) {
			super( actual, OptionalAssert.class );
		}
		
	}

	public static abstract class AbstractOptionalAssert<
			X,
			S extends AbstractObjectAssert<S, Optional<X>>
	
	> extends AbstractObjectAssert<S,Optional<X>> {

		protected AbstractOptionalAssert( Optional<X> actual, Class<?> selfType ) {
			super( actual, selfType );
		}
		
		public S isPresent() {
			
			if( ! actual.isPresent() )
					fail( "Expected " + this.descriptionText() + " to be present" );
			
			return myself;
		}
		
		public S isNotPresent() {
			
			if( actual.isPresent() )
					fail( "Expected " + this.descriptionText() + " to be NOT present" );
			
			return myself;
		}
		
		public X get() {
			
			return actual.get();
		}
		
		public ObjectAssert<X> lookingAt() {
			
			return org.assertj.core.api.Assertions.assertThat( actual.get() );
		}
		
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
			
			Path found = assertOptional( maybeFound )
					.isPresent()
					.get();
			
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
	
	
	public static abstract class AbstractStreamAssert<
			X,
			S extends AbstractObjectAssert<S, Stream<X>>
	
	> extends AbstractObjectAssert<S,Stream<X>> {

		protected AbstractStreamAssert( Stream<X> actual ) {
			super( actual, AbstractStreamAssert.class );
		}
		
		public S hasSize( int size ) {
			
			org.assertj.core.api.Assertions.assertThat( actual.count() )
					.as( descriptionText() )
					.isEqualTo( size );
			
			return myself;
		}
		
		public ListAssert<X> asList() {
			
			return org.assertj.core.api.Assertions.assertThat(
					actual.collect( Collectors.toList() ) );
		}
		
		public List<X> theList() {
			
			return actual.collect( Collectors.toList() );
		}
		
	}
	
}
