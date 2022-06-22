package de.axone.tools;

import static de.axone.tools.StringValueAccessorAssert.*;

import java.io.FileNotFoundException;

import org.testng.annotations.Test;

@Test
public class StringValueAccessorTest {

	static class TestStringValueAccessor implements StringValueAccessor<String,FileNotFoundException> {

		@Override
		public String access( String key ) throws FileNotFoundException {
			if( key == null || key.startsWith( "burp." ) )
					throw exception( key );
			return key;
		}

		@Override
		public String accessChecked( String key ) {
			return key;
		}

		@Override
		public FileNotFoundException exception( String key ) {
			return new FileNotFoundException( key );
		}

	}

	private enum Viking {
		ERIC, VIKI;
	}

	public void testStringAccess() throws Exception {

		assertThis( new TestStringValueAccessor() )
				.containsKey( "foo" ).containsKey( "bar" )
				.valueIs( "foo", "foo" )
				;

	}

	public void testOtherTypes() throws Exception {

		assertThis( new TestStringValueAccessor() )
				.valueAsIntegerIs( "123", 123 )
				.valueAsDoubleIs( "1.1", 1.1 )
				.valueAsEnumIs( "VIKI", Viking.VIKI )
				;

	}

}
