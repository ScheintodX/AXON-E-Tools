package de.axone.test;
import static de.axone.test.TestNGStub.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

	
// TODO: Mit FEST brauchen wir das eigentlich überhaupt nicht mehr.
public abstract class Assert {

	public static void assertIsInstance( Object object, Class<?> clazz ){
		assertTrue( clazz.isAssignableFrom( object.getClass() ) );
	}
	public static void assertIsInstance( Object object, Class<?> clazz, String message ){
		assertTrue( clazz.isAssignableFrom( object.getClass() ), message );
	}
	
	public static void assertEmpty( CharSequence string ){
		assertEquals( string, "" );
	}
	public static void assertEmpty( CharSequence string, String message ){
		assertEquals( string, "", message );
	}
	public static void assertEmpty( Collection<?> collection ){
		assertEquals( collection.size(), 0 );
	}
	public static void assertEmpty( Object [] arr ){
		assertEquals( arr.length, 0 );
	}
	
	public static void assertNotEmpty( CharSequence string ){
		assertNotEquals( string, "" );
	}
	public static void assertNotEmpty( CharSequence string, String message ){
		assertNotEquals( string, "", message );
	}
	public static void assertNotEmpty( Collection<?> collection ){
		assertNotEquals( collection.size(), 0 );
	}
	public static void assertNotEmpty( Object [] arr ){
		assertNotEquals( arr.length, 0 );
	}
	
	/*
	public static <T> void assertEqualsIgnoreOrder( T [] actual, T [] expected ){
		
		assertEquals( actual.length, expected.length, "length" );
		
		boolean [] matches = new boolean[ actual.length ];
		for( int i = 0; i < actual.length; i++ ){
			
			for( int j = 0; j < expected.length; j++ ){
				
				if( matches[ j ] == false && actual[ i ].equals( expected[ j ] ) ){
					matches[ j ] = true;
					break;
				}
			}
		}
		
		for( int i = 0; i < matches.length; i++ ){
			
			if( ! matches[ i ] )
				fail( "Expected: " + expected[ i ] + " has no match" );
		}
	}
	
	public static <T> void assertEqualsIgnoreOrder( Collection<T> actual, Collection<T> expected ){
		
		assertEqualsIgnoreOrder( actual.toArray(), expected.toArray() );
	}
	*/
	
	public static <T> void assertContains( Collection<T> actual, T expected ){
		assertNotNull( actual );
		if( ! actual.contains( expected ) )
			fail( "Not contained: " + expected );
	}
	public static <T> void assertContains( T [] actual, T expected ){
		assertNotNull( actual );
		assertContains( Arrays.asList( actual ), expected );
	}
	public static <T> void assertContainsKey( Map<T,?> actual, T expected ){
		assertNotNull( actual );
		if( ! actual.containsKey( expected ) )
			fail( "Not contained key: " + expected );
	}
	public static <T> void assertContainsValue( Map<T,?> actual, T expected ){
		assertNotNull( actual );
		if( ! actual.containsValue( expected ) )
			fail( "Not contained value: " + expected );
	}
	public static <T> void assertContainsAll( Collection<T> actual, Collection<T> expected ){
		assertNotNull( actual );
		for( T t : expected ){
			assertContains( actual, t );
		}
	}
	public static <T> void assertContainsAll( T [] actual, T [] expected ){
		assertNotNull( actual );
		assertContainsAll( Arrays.asList( actual ), Arrays.asList( expected ) );
	}
	
	public static void assertName( Named object, String expectedName ){
		assertNotNull( object );
		assertEquals( object.getName(), expectedName );
	}
	
	public static void assertContainsName( Collection<? extends Named> set, String expectedName ){
		for( Named x : set ){
			String name = x.getName();
			if( expectedName == null && name == null ) return;
			if( expectedName != null && expectedName.equals( name ) ) return;
		}
		fail( "Not contained: " + expectedName );
	}
	public static void assertNotContainsName( Collection<? extends Named> set, String notExpectedName ){
		for( Named x : set ){
			String name = x.getName();
			if( notExpectedName == null && name == null )
					fail( "contains null name" );
			if( notExpectedName != null && notExpectedName.equals( name ) )
					fail( "contains: " + notExpectedName );
		}
	}
	
	public static <T> void assertEqualsIgnoreOrder( T [] actual, T [] expected ){
		assertEqualsIgnoreOrder( Arrays.asList( actual ), Arrays.asList( expected ) );
	}
	public static <T> void assertEqualsIgnoreOrder( Collection<T> actual, Collection<T> expected ){
		
		assertEquals( actual.size(), expected.size(), "length" );
		assertContainsAll( actual, expected );
		assertContainsAll( expected, actual );
	}
	
	public static void assertEqualsIgnoreCase( CharSequence actual, CharSequence expexted ){
		
		String actualS = (""+actual).toLowerCase(),
		       expectedS = (""+expexted).toLowerCase();
		
		assertEquals( actualS, expectedS, "should equal ignoring case" );
	}
}
