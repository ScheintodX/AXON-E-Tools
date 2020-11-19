package de.axone.tools;

import java.util.HashSet;
import java.util.Set;

public class Sets {

	public static <T> Set<T> union( Set<T> a, Set<T> b ){

		HashSet<T> result = new HashSet<>( a );
		result.addAll( b );

		return result;
	}

	public static <T> Set<T> intersection( Set<T> a, Set<T> b ){

		HashSet<T> result = new HashSet<>( a );
		result.retainAll( b );

		return result;
	}

	public static <T> Set<T> onlyInA( Set<T> a, Set<T> b ){

		HashSet<T> result = new HashSet<>( a );
		result.removeAll( b );

		return result;
	}

	public static <T> Set<T> onlyInB( Set<T> a, Set<T> b ){

		return onlyInA( b, a );
	}

}
