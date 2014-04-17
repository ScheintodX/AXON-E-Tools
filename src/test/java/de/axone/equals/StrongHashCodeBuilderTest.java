package de.axone.equals;


import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import de.axone.tools.E;
import de.axone.tools.Mapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Test( groups="tools.equals" )
public class StrongHashCodeBuilderTest {
	
	public static void testStringHashesJenkins96() throws Exception {
		testStringHashes( new Jenkins96HashCodeBuilder() );
	}
	public static void testCollectionHashesJenkins96() throws Exception {
		testCollectionHashes( new Jenkins96HashCodeBuilder( false ) );
	}

	public static void testStringHashesSha1() throws Exception {
		testStringHashes( new CryptoHashCodeBuilder() );
	}
	public static void testCollectionHashesSha1() throws Exception {
		testCollectionHashes( new CryptoHashCodeBuilder() );
	}

	@SuppressFBWarnings( value="ES_COMPARING_STRINGS_WITH_EQ",
			justification="We exactly want to test if this is another object" )
	private static <T> void testStringHashes( AbstractStrongHashCodeBuilder<T> builder ) throws Exception {
		
		StrongHashCodeBuilder<T> j = builder.builder();
		assertEquals( j.toHashCode(), builder.empty() );
		
		int a = 123;
		String s1 = "Hello123";
		String s2 = "Hello"+a;
		
		assertEquals( s1, s2 );
		assertFalse( s1==s2 );
		assertEquals( s1.hashCode(), s2.hashCode() );
		
		T h1 = builder.builder()
				.append( s1 )
				.toHashCode();
		
		T h3 = builder.builder()
				.append( s2 )
				.toHashCode();
		
		assertEquals( h1, h3 );
		testHashesEquals( true, h1, h3 ); // Test test
	}
	
	private static <T> void testCollectionHashes( AbstractStrongHashCodeBuilder<T> builder ) throws Exception {
		
		int three=3;
		String s1 = "S1";
		String s2 = "S2";
		String s3 = "S3";
		String sThree = "S"+three;
		
		String [] a1 = new String[]{ s1, s2, s3 };
		String [] a1_ = new String[]{ s1, s2, sThree };
		String [] a1_x = new String[]{ s1, s3, s2 };
		String [] a2 = new String[]{ s2, s3 };
		String [] a3 = new String[]{ s1, s2 };
		String [] a4 = new String[]{ s1, sThree };
		
		testEquals( builder, true, null, null );
		
		// Arrays ----------------------------------------
		testEquals( builder, true, new String[0], new String[0] );
		testEquals( builder, false, new String[0], null );
		testEquals( builder, true, a1, a1 );
		testEquals( builder, true, a1, a1_ );
		testEquals( builder, false, a1, a1_x );
		testEquals( builder, false, a1, a2 );
		testEquals( builder, false, a1, a3 );
		testEquals( builder, false, a1, a4 );
		
		// Lists ----------------------------------------
		List<String> list1 = Arrays.asList( a1 );
		testEquals( builder, false, new LinkedList<String>(), null );
		testEquals( builder, true, list1, list1 );
		testEquals( builder, true, list1, Arrays.asList( a1_ ) );
		testEquals( builder, false, list1, Arrays.asList( a1_x ) );
		testEquals( builder, false, list1, Arrays.asList( a2 ) );
		testEquals( builder, false, list1, Arrays.asList( a3 ) );
		testEquals( builder, false, list1, Arrays.asList( a4 ) );
		
		// Sets ----------------------------------------
		Set<String> set1 = new HashSet<String>( Arrays.asList( a1 ) );
		Set<String> set1_ = new HashSet<String>( Arrays.asList( a1_ ) );
		Set<String> set_x = new HashSet<String>( Arrays.asList( a1_x ) );
		Set<String> set2 = new HashSet<String>( Arrays.asList( a2 ) );
		Set<String> set3 = new HashSet<String>( Arrays.asList( a3 ) );
		Set<String> set4 = new HashSet<String>( Arrays.asList( a4 ) );
		
		testEquals( builder, false, new HashSet<String>(), null );
		testEquals( builder, true, set1, set1 );
		testEquals( builder, true, set1, set1_ );
		testEquals( builder, true, set1, set_x );
		testEquals( builder, false, set1, set2 );
		testEquals( builder, false, set1, set3 );
		testEquals( builder, false, set1, set4 );
		
		// Maps ----------------------------------------
		Map<String,String> map1 = Mapper.hashMap( "a", s1, "b", s2, "c", s3 );
		Map<String,String> map1_ = Mapper.hashMap( "a", s1, "b", s2, "c", sThree );
		Map<String,String> map1_X = Mapper.hashMap( "b", s2, "c", s3, "a", s1 );
		Map<String,String> map1_X2 = Mapper.hashMap( "a", s2, "b", s1, "c", s3 );
		Map<String,String> map2 = Mapper.hashMap( "a", s1, "b", s2 );
		Map<String,String> map3 = Mapper.hashMap( "b", s2, "c", s3 );
		Map<String,String> map4 = Mapper.hashMap( "a", s1, "b", s2, "c", s3, "d", "s4" );
		
		testEquals( builder, true, map1, map1_ );
		testEquals( builder, true, map1, map1_X );
		testEquals( builder, false, map1, map1_X2 );
		testEquals( builder, false, map1, map2 );
		testEquals( builder, false, map1, map3 );
		testEquals( builder, false, map1, map4 );
		
	}
	
	private static <T> void testEquals( AbstractStrongHashCodeBuilder<T> builder,
			boolean equals, Object o1, Object o2 ){
		
		T h1 = builder.builder()
				.append( o1 )
				.toHashCode();
		
		T h2 = builder.builder()
				.append( o2 )
				.toHashCode();
		
		testHashesEquals( equals, h1, h2 );
	}

	private static void testHashesEquals( boolean equals, Object h1, Object h2 ){
		
		boolean ok=true;
		if( equals ){
			assertEquals( h1, h2 );
		} else {
			if( h1.equals( h2 ) ) E.rr( h1 );
			assertFalse( h1.equals( h2 ) );
		}
		if( !ok ){
			E.rr( h1 + " / " + h2 );
			assertTrue( false );
		}
	}
	
	
}
