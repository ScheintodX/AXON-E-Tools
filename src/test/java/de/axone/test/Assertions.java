package de.axone.test;

import static org.testng.Assert.*;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.assertj.core.api.AbstractObjectAssert;
import org.testng.annotations.Test;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class Assertions {

	public void blah() {
		org.assertj.core.api.Assertions.assertThat( true );
	}
	
	
	public static <X> OptionalAssert<X> assertThat( Optional<X> optional ) {
		
		return new OptionalAssert<X>( optional );
	}
	
	public static PathAssert assertThat( Path optional ) {
		
		return new PathAssert( optional );
	}
	
	
	/* Optional */
	
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
			
			org.assertj.core.api.Assertions.assertThat( actual.isPresent() )
					.isTrue();
			
			return myself;
		}
		
		public X get() {
			
			return actual.get();
			
		}
	}
	
	/* Path */
	
	@Test
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
			
			if( ! Files.isReadable( actual ) )
					org.assertj.core.api.Assertions.fail( "Not a regular file" );
			
			return myself;
		}
		
		public S isDirectory( LinkOption ... options ) {
			
			if( ! Files.isDirectory( actual, options ) )
					org.assertj.core.api.Assertions.fail( "Not a regular file" );
			
			return myself;
		}
		
		public PathAssert resolve( Path p ) {
			
			return new PathAssert( actual.resolve( p ) );
		}
		
		public PathAssert resolve( String p ) {
			
			return new PathAssert( actual.resolve( p ) );
		}
		
		S isSameAs( Path other ) {
			
			throw new NotImplementedException();
			
			//return myself;
		}
		
	}
}
