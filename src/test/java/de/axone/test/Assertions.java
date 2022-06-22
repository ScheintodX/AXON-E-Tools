package de.axone.test;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractPathAssert;


public class Assertions {

	public static MyPathAssert assertPath( Path path ) {

		return new MyPathAssert( path ).as( path.getFileName().toString() );
	}

	public static <X> StreamAssert<X> assertStream( Stream<X> stream ) {

		return new StreamAssert<>( stream );
	}

	/* ================= */
	/* = Path ========== */
	/* ================= */
	public static class MyPathAssert extends AbstractPathAssert<MyPathAssert> {

		protected MyPathAssert( Path actual ) {
			super( actual, MyPathAssert.class );
		}

		public MyPathAssert absolutelyStartsWith( Path other ) {

			if( ! actual.startsWith( other ) )
					failWithMessage( "expected '%s' to start with '%s', but didn't", actual, other );

			return myself;
		}

		public Path andReturn() {
			return actual;
		}

		public MyPathAssert resolve( Path p ) {

			return new MyPathAssert( actual.resolve( p ) );
		}

		public MyPathAssert resolve( String p ) {

			return new MyPathAssert( actual.resolve( p ) );
		}

		public MyPathAssert find( String pattern ) throws IOException {

			Optional<Path> maybeFound = Files.list( actual )
					.filter( p -> p.getFileName().toString().matches( pattern ) )
					.findFirst();

			 assertThat( maybeFound )
					.isPresent();

			 Path found = maybeFound.get();

			return assertPath( found );
		}
	}

	public static class StreamAssert<X> extends AbstractStreamAssert<X, StreamAssert<X>> {

		protected StreamAssert( Stream<X> actual ) {
			super( actual );
		}
	}

}
