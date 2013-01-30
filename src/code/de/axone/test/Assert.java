package de.axone.test;
import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.Collection;

	
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
	
	public static <T> void assertContains( Collection<T> actual, T expected ){
		assertNotNull( actual );
		if( ! actual.contains( expected ) )
			fail( "Not contained: " + expected );
	}
	public static <T> void assertContains( T [] actual, T expected ){
		assertNotNull( actual );
		assertContains( Arrays.asList( actual ), expected );
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
	
	/*
	 * 	private static <T> void assertEqualsIgnoreOrder( T[] a1, T[] a2 ){
		assertEqualsIgnoreOrder( Arrays.asList( a1 ), Arrays.asList( a2 ) );
	}
	
	private static <T> void assertEqualsIgnoreOrder( Collection<T> c1, Collection<T> c2 ){
		
		E.rr( c1 );
		E.rr( c2 );
		
		ArrayList<T> l1 = new ArrayList<T>( c1 );
		ArrayList<T> l2 = new ArrayList<T>( c2 );
		
		@SuppressWarnings( "unchecked" )
		T NULL = (T)(new Object());
		
		M1: for( int i1=0; i1<l1.size(); i1++ ){
			
			for( int i2=0; i2<l2.size(); i2++ ){
				
				T o1 = l1.get( i1 );
				T o2 = l2.get( i2 );
				
				if( o1.equals( o2 ) ){
					l1.set( i1, NULL );
					l2.set( i1, NULL );
					continue M1;
				}
			}
		}

		for( int i=0; i<l1.size(); i++ ){
			if( l1.get( i ) == NULL ) fail( "A1[" + l1.get( i ) + "] has no match" );
		}
		for( int i=0; i<l2.size(); i++ ){
			if( l2.get( i ) == NULL ) fail( "A2[" + l2.get( i ) + "] has no match" );
		}
			
	}
	 */
}
