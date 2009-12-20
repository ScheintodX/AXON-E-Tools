package de.axone.tools;

import java.util.Arrays;
import java.util.Map;

public class Str {

	public static String join( String joinWith, String ... strings ){

		return joinB( joinWith, (Object[]) strings).toString();
	}

	public static String join( String joinWith, Object ... objects ){
		return joinB( joinWith, objects ).toString();
	}

	public static String join( String joinWith, Iterable<?> strings ){

		return joinB( joinWith, strings ).toString();
	}

	public static StringBuilder joinB( String joinWith, Object ... objects ){

		return joinB( joinWith, Arrays.asList( objects ) );
	}

	public static StringBuilder joinB( String joinWith, Iterable<?> strings ){

		StringBuilder builder = new StringBuilder();

		boolean first = true;
		if( strings != null ) for( Object o : strings ){

			if( first ) first = false; else builder.append( joinWith );

			builder.append( o.toString() );
		} else {
			builder.append( "- null -" );
		}

		return builder;
	}

	public static StringBuilder joinB( String joinWith, String separator, Map<?,?> map ){

		StringBuilder builder = new StringBuilder();

		boolean first = true;
		if( map != null ) for( Object o : map.keySet() ){

			if( first ) first = false; else builder.append( joinWith );

			builder
				.append( o.toString() )
				.append( separator )
				.append( map.get( o ).toString() )
			;
		} else {
			builder.append( "- null -" );
		}

		return builder;
	}

	public static String join( String joinWith, String separator, Map<?,?> map ){

		return joinB( joinWith, separator, map ).toString();
	}
}
