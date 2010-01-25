package de.axone.test;
import static org.testng.Assert.*;

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
	
	public static void assertEqualsIgnoreOrder( Object [] actual, Object [] expected ){
		
		assertEquals( actual.length, expected.length );
		
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
	public static void assertEqualsIgnoreOrder( Collection<?> actual, Collection<?> expected ){
		
		assertEqualsIgnoreOrder( actual.toArray(), expected.toArray() );
	}
	
}
