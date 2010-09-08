package de.axone.tools;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

public class SetsTest {

	@Test
	public void testSets() throws Exception {
		
		Set<Character> a = new HashSet<Character>( Arrays.asList( 'a', 'b',      'd'      ) );
		Set<Character> b = new HashSet<Character>( Arrays.asList(      'b',      'd', 'e' ) );
		
		Set<Character> AnB = new HashSet<Character>( Arrays.asList( 'b', 'd' ) );
		Set<Character> AvB = new HashSet<Character>( Arrays.asList( 'a', 'b', 'd', 'e' ) );
		
		Set<Character> An_B = new HashSet<Character>( Arrays.asList( 'a' ) );
		Set<Character> Bn_A = new HashSet<Character>( Arrays.asList( 'e' ) );
		
		assertEquals( Sets.intersection( a, b ), AnB );
		assertEquals( Sets.union( a, b ), AvB );
		
		assertEquals( Sets.onlyInA( a, b ), An_B );
		assertEquals( Sets.onlyInA( b, a ), Bn_A );
	}
}
