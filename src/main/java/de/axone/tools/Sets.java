package de.axone.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Sets {
	
	@SafeVarargs
	public static <T> HashSet<T> hashSetOf( T ... members ){
		return new HashSet<>( Arrays.asList( members ) );
	}
	
	@SafeVarargs
	public static <T> TreeSet<T> treeSetOf( T ... members ){
		return new TreeSet<>( Arrays.asList( members ) );
	}

	public static <T> Set<T> union( Set<T> a, Set<T> b ){
		
		HashSet<T> result = new HashSet<T>( a );
		result.addAll( b );
		
		return result;
	}
	
	public static <T> Set<T> intersection( Set<T> a, Set<T> b ){
		
		HashSet<T> result = new HashSet<T>( a );
		result.retainAll( b );
		
		return result;
	}
	
	public static <T> Set<T> onlyInA( Set<T> a, Set<T> b ){
		
		HashSet<T> result = new HashSet<T>( a );
		result.removeAll( b );
		
		return result;
	}
	
	public static <T> Set<T> onlyInB( Set<T> a, Set<T> b ){
		
		return onlyInA( b, a );
	}
	
}
