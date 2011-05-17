package de.axone.tools;

import java.util.Arrays;
import java.util.Map;

public class Str {

	// --- J o i n ------------------------------------------------------
	public static String join( String joinWith, boolean [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}
	public static String join( String joinWith, char [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}
	public static String join( String joinWith, short [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}
	public static String join( String joinWith, int [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}
	public static String join( String joinWith, long [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}
	public static String join( String joinWith, float [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}
	public static String join( String joinWith, double [] ints ){
		return join( joinWith, A.toObjects( ints ) ).toString();
	}

	public static <T> String join( String joinWith, T ... objects ){
		return joinB( joinWith, Arrays.asList( objects ) ).toString();
	}
	public static <T> String join( String joinWith, Iterable<T> objects ){
		return joinB( joinWith, objects ).toString();
	}
	public static <T> String join( Joiner<T> joiner, T ... objects ){
		return joinB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T> String join( Joiner<T> joiner, Iterable<T> objects ){
		return joinB( joiner, objects ).toString();
	}
	
	// joinB 
	public static <T> StringBuilder joinB( String joinWith, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joinWith, objects );
	}
	public static <T> StringBuilder joinB( Joiner<T> joiner, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joiner, objects );
	}
	
	// joinBB
	public static <T> StringBuilder joinBB( StringBuilder result, String joinWith, Iterable<T> objects ){
		return joinBB( result, new SimpleJoiner<T>( joinWith ), objects );
	}
	public static <T> StringBuilder joinBB( StringBuilder result, Joiner<T> joiner, Iterable<T> objects ){

		int index=0;
		if( objects != null ) for( T object : objects ){

			if( index > 0 ) result.append( joiner.getSeparator() );

			result.append( joiner.toString( object, index ) );

			index++;
		}
		return result;
	}

	// Map
	public static <K,V> String join( String rs, String fs, Map<K,V> map ){
		return joinB( rs, fs, map ).toString();
	}
	public static <K,V> StringBuilder joinB( String rs, String fs, Map<K,V> map ){
		return joinBB( new StringBuilder(), rs, fs, map );
	}
	public static <K,V> StringBuilder joinBB( StringBuilder result, String rs, String fs, Map<K,V> map ){

		boolean first = true;
		if( map != null ) for( K o : map.keySet() ){

			if( first ) first = false; else result.append( rs );

			result
				.append( o.toString() )
				.append( fs )
				.append( map.get( o ).toString() )
			;
		} else {
			result.append( "- null -" );
		}

		return result;
	}
	
	// --- J o i n e r --------------------------------------------------
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
			return object.toString();
		}
	}
	
	// --- T r i m ------------------------------------------------------
	public static String trimAtWordBoundary( String text, int len ){
		return trimAtWordBoundary( text, len, null );
	}
	public static String trimAtWordBoundary( String text, int len, String appendix ){
		
		assert text != null;
		assert len > 0;
		
		text = text.trim(); // Do some pretrimming
		
		if( len >= text.length() ) return text;
		
		int i;
		for( i=len-1; i>0; i-- ){
			
			if( Character.isWhitespace( text.charAt( i ) ) ) break;
		}
		
		if( i > 0 ){
			return appendix != null ? text.substring( 0, i ) + appendix : text.substring( 0, i );
		} else {
			return appendix != null ? text.substring( 0, len ) + appendix : text.substring( 0, len );
		}
	}
	
	// --- S p l i t ----------------------------------------------------
	public static String splitAt( int position, String text ){
		
		StringBuilder result = new StringBuilder( text.length() + text.length()/position +1 );
		
		for( int i=0; i < text.length(); i++ ){
			
			result.append( text.charAt( i ) );
			if( i%position ==0 && i < text.length()-1 ) result.append( '\n' );
		}
		
		return result.toString();
	}
	
}
