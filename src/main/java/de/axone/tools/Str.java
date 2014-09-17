package de.axone.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.axone.refactor.NotTested;

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
	public static <T, U extends T> String join( Joiner<T> joiner, U ... objects ){
		return joinB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T, U extends T> String join( Joiner<T> joiner, Iterable<U> objects ){
		return joinB( joiner, objects ).toString();
	}
	@SafeVarargs
	public static <T, U extends T> String joinIgnoreEmpty( Joiner<T> joiner, U ... objects ){
		return joinIgnoreEmptyB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T, U extends T> String joinIgnoreEmpty( Joiner<T> joiner, Iterable<U> objects ){
		return joinIgnoreEmptyB( joiner, objects ).toString();
	}
	
	// joinB 
	public static <T> StringBuilder joinB( String joinWith, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joinWith, false, objects );
	}
	public static <T, U extends T> StringBuilder joinB( Joiner<T> joiner, Iterable<U> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <M,N> StringBuilder joinB( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <T, U extends T> StringBuilder joinIgnoreEmptyB( String joinWith, Iterable<U> objects ){
		return joinBB( new StringBuilder(), joinWith, true, objects );
	}
	public static <T, U extends T> StringBuilder joinIgnoreEmptyB( Joiner<T> joiner, Iterable<U> objects ){
		return joinBB( new StringBuilder(), joiner, true, objects );
	}
	public static <M,N> StringBuilder joinIgnoreEmptyB( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinBB( new StringBuilder(), joiner, true, objects );
	}
	
	// joinBB
	@SuppressWarnings( "unchecked" )
	public static <T, U extends T> StringBuilder joinBB( StringBuilder result, String joinWith, boolean ignoreEmpty,  U ... objects ){
		return joinBB( result, new SimpleJoiner<T>( joinWith ), ignoreEmpty, Arrays.asList( objects ) );
	}
	public static <T, U extends T> StringBuilder joinBB( StringBuilder result, String joinWith, boolean ignoreEmpty, Iterable<U> objects ){
		return joinBB( result, new SimpleJoiner<T>( joinWith ), ignoreEmpty, objects );
	}
	public static <T, U extends T> StringBuilder joinBB( StringBuilder result, Joiner<T> joiner, boolean ignoreEmpty, Iterable<U> objects ){

		int index=0;
		if( objects != null ) for( T object : objects ){
			
			if( ignoreEmpty && ( object == null || "".equals( object ) ) ) continue;

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
		if( map != null ) for( Map.Entry<K,V> entry : map.entrySet() ){
			
			K key = entry.getKey();
			V value = entry.getValue();
			
			if( value == null && ! ignoreEmpty ) continue;

			if( index > 0 ) result.append( joiner.getRecordSeparator() );

			result
				.append( joiner.keyToString( key, index ) )
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
	
	public static String [] splitFast( String s, char fs ){
		
		int n=1;
		for( int i=0; i<s.length(); i++ ){
			if( s.charAt( i ) == fs ){
				n++;
			}
		}
		
		if( n == 1 ) return new String[]{ s };
		
		return splitFastLimited( s, fs, n );
	}
	
	
	/**
	 *  StipedDonwVersionOfSplit resticted to n results
	 *  
	 *  Note that the size of N has a significant impact on performance
	 *  even if the amount of found parts is smaller than n
	 *  
	 * @param s
	 * @param n
	 * @param split
	 * @return
	 */
	public static String [] splitFastLimited( String s, char split, int n ){
		
		final String [] result = new String[n];
		
		final int len = s.length();
		
		int num=0;
		int i, last=0;
		for( i=0; i<len; i++ ){
			
			if( split == s.charAt( i ) ){
				
				result[ num ] = s.substring( last, i );
				
				last = i+1;
				num++;
				if( num == n-1 ) break;
			};
		}
		
		if( len>=last ){
			result[ num ] = s.substring( last );
		}
		
		return Arrays.copyOf( result, num+1 );
	}
	
	public static List<String> splitFastToList( String s, char split ){
		
		List<String> result = new ArrayList<>( 8 );
		
		final int len = s.length();
		
		int i, last=0;
		for( i=0; i<len; i++ ){
			
			if( split == s.charAt( i ) ){
				
				result.add( s.substring( last, i ) );
				
				last = i+1;
			};
		}
		
		if( len>=last ){
			result.add( s.substring( last ) );
		}
		
		return result;
	}
	
	public static List<String> splitFastAtSpacesToList( String s ){
		
		List<String> result = new ArrayList<>( 8 );
		
		final int len = s.length();
		
		int i, last=0;
		for( i=0; i<len; i++ ){
			
			if( Character.isWhitespace( s.charAt( i ) ) ){
				
				if( last < i ){
					result.add( s.substring( last, i ) );
				}
				
				last = i+1;
			};
		}
		
		if( len>last ){
			result.add( s.substring( last ) );
		}
		
		return result;
	}
	
	
	/**
	 * Split s one time
	 * 
	 * @param s
	 * @param split
	 * @return
	 */
	public static String [] splitFastOnce( String s, char split ){
		
		int pos = s.indexOf( split );
		
		if( pos >= 0 ){
			
			return new String[]{ s.substring( 0, pos ), s.substring( pos+1 ) };
		} else {
			return new String[]{ s };
		}
	}
	
	// --- R e m o v e --------------------------------------------------
	
	public static String removeChars( String s, char ... chars ){
		
		Arrays.sort( chars );
		
		StringBuilder result = new StringBuilder();
		for( int i=0; i<s.length(); i++ ){
			
			char current = s.charAt( i );
			
			if( Arrays.binarySearch( chars, current ) < 0 ){
				result.append( current );
			}
		}
		
		return result.toString();
	}
	
	public static String collapseWhitespace( String s ) {
		
		StringBuilder result = new StringBuilder( s.length() );
		
		boolean startOfString = true;
		boolean lastWasWs = true;
		for( int i=0; i<s.length(); i++ ){
			
			char c = s.charAt( i );
			if( Character.isWhitespace( c ) ){
				if( lastWasWs ) continue;
				lastWasWs = true;
			} else {
				if( lastWasWs && ! startOfString ) result.append( ' ' );
				lastWasWs = false;
				startOfString = false;
				result.append( c );
			}
		}
		
		return result.toString();
	}
	
	@NotTested
	public static List<String> collapseEmpty( List<String> list ){
		
		List<String> result = new ArrayList<>( list.size() );
		
		for( String value : list ){
			if( value != null && value.length() > 0 ) result.add( value );
		}
		
		return result;
	}
	
	@NotTested
	public static List<String> collapseEmptyInPlace( List<String> list ){
		
		for( Iterator<String> it = list.iterator(); it.hasNext(); ){
			String value = it.next();
			if( value == null || value.length() == 0 ) it.remove();;
		}
		return list;
	}
	
	@NotTested
	public static List<String> trim( List<String> list ){
		
		List<String> result = new ArrayList<>( list.size() );
		for( String value : list ){
			if( value == null ) result.add( null );
			result.add( value.trim() );
		}
		return result;
	}
	
	@NotTested
	public static List<String> trimInPlace( List<String> list ){
		
		for( int i=0; i<list.size(); i++ ){
			
			String value = list.get( i );
			if( value == null ) continue;
			String trimmed = value.trim();
			if( value != trimmed ) list.set( i, trimmed );
		}
		return list;
	}
	
	@NotTested
	public static List<String> trimEmpty( List<String> list ){
		
		List<String> result = new ArrayList<>( list.size() );
		
		for( String value : list ){
			if( value != null && value.length() > 0 && value.trim().length() > 0 ) result.add( value );
		}
		
		return result;
	}
	
	@NotTested
	public static List<String> trimEmptyInPlace( List<String> list ){
		
		for( Iterator<String> it = list.iterator(); it.hasNext(); ){
			String value = it.next();
			if( value == null || value.length() == 0 || value.trim().length() == 0 ) it.remove();;
		}
		return list;
	}
	
	public static final String translate( String value, char from, String to ){
		
		if( value == null ) return null;
		
		StringBuilder result = new StringBuilder( value.length() + value.length()/10 );
		
		for( int i=0; i<value.length(); i++ ){
			
			char c = value.charAt( i );
			
			if( c == from ){
				result.append( to );
			} else {
				result.append( c );
			}
		}
		return result.toString();
	}
	
	public static final String translate( String value, char [] from, String [] to ){
		
		if( value == null ) return null;
		
		StringBuilder result = new StringBuilder( value.length() + value.length()/10 );
		
		for( int i=0; i<value.length(); i++ ){
			
			char c = value.charAt( i );
			
			boolean found = false;
			for( int j = 0; j<from.length; j++ ){
				
				if( c == from[ j ] ){
					result.append( to[ j ] );
					found = true;
				}
			}
			if( ! found ) result.append( c );
		}
		
		return result.toString();
	}
	
	public static final boolean contains( String value, char ch ){
		
		return value.indexOf( ch ) >= 0;
	}
	
	public static final boolean containsOneOf( String value, char [] chars ){
		
		value.indexOf( 'c' );
		
		for( int i=0; i<value.length(); i++ ){
			
			char c = value.charAt( i );
			
			for( char ct : chars ){
				
				if( c == ct ) return true;
			}
		}
		return false;
	}
	
}
