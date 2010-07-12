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
			builder.append( S._NULL_ );
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

	public static <T> StringBuilder joinB( Joiner<T> joiner, Iterable<T> list ){

		StringBuilder result = new StringBuilder();

		int index=0;
		if( list != null ) for( T object : list ){

			if( index > 0 ) result.append( joiner.getSeparator() );

			result.append( joiner.toString( object, index ) );

			index++;
		}
		return result;
	}

	public static <T> String join( Joiner<T> joiner, Iterable<T> list ){

		return joinB( joiner, list ).toString();
	}

	public interface Joiner<T> {

		public String getSeparator();
		public String toString( T object, int index );
	}
	public static class SimpleJoiner<T> implements Joiner<T> {

		private String separator;
		public SimpleJoiner( String separator ){
			this.separator = separator;
		}
		public String getSeparator(){
			return separator;
		}
		public String toString( T object, int index ){
			return index + ": " + object.toString();
		}
	}
}
