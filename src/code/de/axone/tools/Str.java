package de.axone.tools;

import java.util.Arrays;
import java.util.Map;

public class Str {

	// --- J o i n ------------------------------------------------------
	public static String join( String joinWith, boolean [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}
	public static String join( String joinWith, char [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}
	public static String join( String joinWith, short [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}
	public static String join( String joinWith, int [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}
	public static String join( String joinWith, long [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}
	public static String join( String joinWith, float [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}
	public static String join( String joinWith, double [] ints ){
		return join( joinWith, A.objects( ints ) ).toString();
	}

	@SafeVarargs
	public static <T> String join( String joinWith, T ... objects ){
		return joinB( joinWith, Arrays.asList( objects ) ).toString();
	}
	public static <T> String join( String joinWith, Iterable<T> objects ){
		return joinB( joinWith, objects ).toString();
	}
	@SafeVarargs
	public static <T> String joinIgnoreEmpty( String joinWith, T ... objects ){
		return joinIgnoreEmptyB( joinWith, Arrays.asList( objects ) ).toString();
	}
	public static <T> String joinIgnoreEmpty( String joinWith, Iterable<T> objects ){
		return joinIgnoreEmptyB( joinWith, objects ).toString();
	}
	@SafeVarargs
	public static <T> String join( Joiner<T> joiner, T ... objects ){
		return joinB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T> String join( Joiner<T> joiner, Iterable<T> objects ){
		return joinB( joiner, objects ).toString();
	}
	@SafeVarargs
	public static <T> String joinIgnoreEmpty( Joiner<T> joiner, T ... objects ){
		return joinIgnoreEmptyB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T> String joinIgnoreEmpty( Joiner<T> joiner, Iterable<T> objects ){
		return joinIgnoreEmptyB( joiner, objects ).toString();
	}
	
	// joinB 
	public static <T> StringBuilder joinB( String joinWith, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joinWith, false, objects );
	}
	public static <T> StringBuilder joinB( Joiner<T> joiner, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <M,N> StringBuilder joinB( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <T> StringBuilder joinIgnoreEmptyB( String joinWith, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joinWith, true, objects );
	}
	public static <T> StringBuilder joinIgnoreEmptyB( Joiner<T> joiner, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joiner, true, objects );
	}
	public static <M,N> StringBuilder joinIgnoreEmptyB( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinBB( new StringBuilder(), joiner, true, objects );
	}
	
	// joinBB
	public static <T> StringBuilder joinBB( StringBuilder result, String joinWith, boolean ignoreEmpty, Iterable<T> objects ){
		return joinBB( result, new SimpleJoiner<T>( joinWith ), ignoreEmpty, objects );
	}
	public static <T> StringBuilder joinBB( StringBuilder result, Joiner<T> joiner, boolean ignoreEmpty, Iterable<T> objects ){

		int index=0;
		if( objects != null ) for( T object : objects ){
			
			if( object == null && ignoreEmpty ) continue;

			if( index > 0 ) result.append( joiner.getSeparator() );

			result.append( joiner.toString( object, index ) );

			index++;
		}
		return result;
	}

	// Map
	public static <M,N> String join( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinB( joiner, objects ).toString();
	}
	public static <K,V> String join( String rs, String fs, Map<K,V> map ){
		return joinB( rs, fs, map ).toString();
	}
	public static <K,V> StringBuilder joinB( String rs, String fs, Map<K,V> map ){
		return joinBB( new StringBuilder(), rs, fs, false, map );
	}
	public static <M,N> String joinIgnoreEmpty( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinIgnoreEmptyB( joiner, objects ).toString();
	}
	public static <K,V> String joinIgnoreEmpty( String rs, String fs, Map<K,V> map ){
		return joinIgnoreEmptyB( rs, fs, map ).toString();
	}
	public static <K,V> StringBuilder joinIgnoreEmptyB( String rs, String fs, Map<K,V> map ){
		return joinBB( new StringBuilder(), rs, fs, true, map );
	}
	public static <K,V> StringBuilder joinBB( StringBuilder result, String rs, String fs, boolean ignoreEmpty, Map<K,V> map ){
		return joinBB( result, new SimpleMapJoiner<K,V>( rs, fs ), ignoreEmpty, map );
	}
	public static <K,V> StringBuilder joinBB( StringBuilder result, MapJoiner<K,V> joiner, boolean ignoreEmpty, Map<K,V> map ){
		
		int index = 0;
		if( map != null ) for( K o : map.keySet() ){
			
			V value = map.get( o );
			
			if( value == null && ! ignoreEmpty ) continue;

			if( index > 0 ) result.append( joiner.getRecordSeparator() );

			result
				.append( joiner.keyToString( o, index ) )
				.append( joiner.getFieldSeparator() )
				.append( joiner.valueToString( value, index ) )
			;
			
			index++;
			
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
		@Override
		public String getSeparator(){
			return separator;
		}
		@Override
		public String toString( T object, int index ){
			if( object == null ) return null;
			return object.toString();
		}
	}
	public static final Joiner<String> CSVJOINER = new Joiner<String>() {

		@Override
		public String getSeparator() {
			return ", ";
		}

		@Override
		public String toString( String object, int index ) {
			return '"' + object.replace( "\"", "\"\"" ) + '"';
		}

	};
	
	public interface MapJoiner<K,V> {
		public String getRecordSeparator();
		public String getFieldSeparator();
		public String keyToString( K nameField, int index );
		public String valueToString( V valueField, int index );
	}
	public static class SimpleMapJoiner<M,N> implements MapJoiner<M,N> {
		
		private String recordSeparator;
		private String fieldSeparator;
		
		SimpleMapJoiner( String recordSeparator, String fieldSeparator ){
			this.recordSeparator = recordSeparator;
			this.fieldSeparator = fieldSeparator;
		}

		@Override
		public String getRecordSeparator() {
			return recordSeparator;
		}

		@Override
		public String getFieldSeparator() {
			return fieldSeparator;
		}

		@Override
		public String keyToString( M nameField, int index ) {
			return nameField.toString();
		}

		@Override
		public String valueToString( N valueField, int index ) {
			return valueField != null ? valueField.toString() : "-null-";
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
