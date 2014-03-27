package de.axone.data;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.testng.annotations.Test;

@Test( groups="tools.singleimmutableset" )
public class SingleImmutableSetTest {

	public void testSet(){
		
		SingleImmutableSet<String> 
				s1 = new SingleImmutableSet<>( "foo" ),
				s2 = new SingleImmutableSet<>( "f"+'o'+"o" ),
				s3 = new SingleImmutableSet<>( "bar" )
		;
				
		Iterator<String> it = s1.iterator();
		assertTrue( it.hasNext() );
		assertEquals( it.next(), "foo" );
		assertFalse( it.hasNext() );
		assertEquals( it.next(), null );
				
		int c=0;
		for( String s : s1 ){
			assertEquals( s, "foo" );
			c++;
		}
		assertEquals( c, 1 );
				
		assertTrue( s1.contains( "foo" ) );
		assertTrue( s2.contains( "foo" ) );
		assertFalse( s3.contains( "foo" ) );
		assertFalse( s1.contains( "var" ) );
		
		assertTrue( s1.equals( s2 ) );
		assertEquals( s1.hashCode(), s2.hashCode() );
		
		assertFalse( s1.equals( s3 ) );
		// This has a probability of failing if String's hashCode changes. (Which it won't)
		assertNotEquals( s1.hashCode(), s3.hashCode() ); 
		
		assertTrue( s1.containsAll( s2 ) );
		assertTrue( s2.containsAll( s1 ) );
		assertFalse( s1.containsAll( s3 ) );
				
		HashSet<String>
				hs1 = new HashSet<>(),
				hs2 = new HashSet<>( Arrays.asList( "foo" ) ),
				hs3 = new HashSet<>( Arrays.asList( "foo", "bar" ) )
		;
				
		assertTrue( s1.containsAll( hs1 ) );
		assertTrue( s1.containsAll( hs2 ) );
		assertFalse( s1.containsAll( hs3 ) );
		
		assertFalse( hs1.containsAll( s1 ) );
		assertTrue( hs2.containsAll( s1 ) );
		assertTrue( hs3.containsAll( s1 ) );
	}
}
