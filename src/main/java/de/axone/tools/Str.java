package de.axone.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import de.axone.refactor.NotTested;

public class Str {
	
	public static final Joiner<Object> TO_STRING_KOMMA =
			new Joiner<Object>() {
				@Override
				public String toString( Object object, int index ) {
					return object.toString();
				}
	};
	public static final Joiner<String> IS_STRING_KOMMA =
			new Joiner<String>() {
				@Override
				public String toString( String object, int index ) {
					return object;
				}
	};

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
	public static <T> String join( String joinWith, Stream<T> objects ){
		return joinB( joinWith, objects ).toString();
	}
	@SafeVarargs
	public static <T> String joinIgnoreEmpty( String joinWith, T ... objects ){
		return joinIgnoreEmptyB( joinWith, Arrays.asList( objects ) ).toString();
	}
	public static <T> String joinIgnoreEmpty( String joinWith, Iterable<T> objects ){
		return joinIgnoreEmptyB( joinWith, objects ).toString();
	}
	public static <T> String joinIgnoreEmpty( String joinWith, Stream<T> objects ){
		return joinIgnoreEmptyB( joinWith, objects ).toString();
	}
	@SafeVarargs
	public static <T, U extends T> String join( Joiner<T> joiner, U ... objects ){
		return joinB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T, U extends T> String join( Joiner<T> joiner, Iterable<U> objects ){
		return joinB( joiner, objects ).toString();
	}
	public static <T, U extends T> String join( StreamJoiner<T> joiner, Stream<U> objects ){
		return joinB( joiner, objects ).toString();
	}
	@SafeVarargs
	public static <T, U extends T> String joinIgnoreEmpty( Joiner<T> joiner, U ... objects ){
		return joinIgnoreEmptyB( joiner, Arrays.asList( objects ) ).toString();
	}
	public static <T, U extends T> String joinIgnoreEmpty( Joiner<T> joiner, Iterable<U> objects ){
		return joinIgnoreEmptyB( joiner, objects ).toString();
	}
	public static <T, U extends T> String joinIgnoreEmpty( StreamJoiner<T> joiner, Stream<U> objects ){
		return joinIgnoreEmptyB( joiner, objects ).toString();
	}
	
	// joinB 
	public static <T> StringBuilder joinB( String joinWith, Iterable<T> objects ){
		return joinBB( new StringBuilder(), joinWith, false, objects );
	}
	public static <T> StringBuilder joinB( String joinWith, Stream<T> objects ){
		return joinBB( new StringBuilder(), joinWith, false, objects );
	}
	public static <T, U extends T> StringBuilder joinB( Joiner<T> joiner, Iterable<U> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <T, U extends T> StringBuilder joinB( StreamJoiner<T> joiner, Stream<U> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <M,N> StringBuilder joinB( MapJoiner<M,N> joiner, Map<M,N> objects ){
		return joinBB( new StringBuilder(), joiner, false, objects );
	}
	public static <T, U extends T> StringBuilder joinIgnoreEmptyB( String joinWith, Iterable<U> objects ){
		return joinBB( new StringBuilder(), joinWith, true, objects );
	}
	public static <T, U extends T> StringBuilder joinIgnoreEmptyB( String joinWith, Stream<U> objects ){
		return joinBB( new StringBuilder(), joinWith, true, objects );
	}
	public static <T, U extends T> StringBuilder joinIgnoreEmptyB( Joiner<T> joiner, Iterable<U> objects ){
		return joinBB( new StringBuilder(), joiner, true, objects );
	}
	public static <T, U extends T> StringBuilder joinIgnoreEmptyB( StreamJoiner<T> joiner, Stream<U> objects ){
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
	public static <T, U extends T> StringBuilder joinBB( StringBuilder result, String joinWith, boolean ignoreEmpty, Stream<U> objects ){
		return joinBB( result, new SimpleStreamJoiner<T>( joinWith ), ignoreEmpty, objects );
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
	
	public static <T, U extends T> StringBuilder joinBB( StringBuilder result, StreamJoiner<T> joiner, boolean ignoreEmpty, Stream<U> objects ){
		
		return result.append( objects
				.collect( new StringCollector<T>( joiner ) )
				) ;
	}
	
	private static class StringCollector<T> implements Collector<T,StringJoiner,String>{
		
		private final StreamJoiner<T> joiner;
		
		StringCollector( StreamJoiner<T> joiner ){
			this.joiner = joiner;
		}

		@Override
		public Supplier<StringJoiner> supplier() {
			return () -> new StringJoiner( joiner.getSeparator(), "", "" );
		}

		@Override
		public BiConsumer<StringJoiner, T> accumulator() {
			return (sj, item) -> sj.add( joiner.toString( item ) );
		}

		@Override
		public BinaryOperator<StringJoiner> combiner() {
			return StringJoiner::merge;
		}

		@Override
		public Function<StringJoiner, String> finisher() {
			return StringJoiner::toString;
		}

		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			return Collections.emptySet();
		}
	};
	
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
	@FunctionalInterface
	public interface Joiner<T> {

		public String toString( T object, int index );
		
		public default String getSeparator(){ return ", "; }
	}
	
	@FunctionalInterface
	public interface StreamJoiner<T> {
		
		public String toString( T object ); //<- We can't use indexes here
		
		public default String getSeparator(){ return ", "; }
		
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
	
	public static class SimpleStreamJoiner<T> implements StreamJoiner<T> {

		private String separator;
		public SimpleStreamJoiner( String separator ){
			this.separator = separator;
		}
		@Override
		public String getSeparator(){
			return separator;
		}
		@Override
		public String toString( T object ){
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
	
	public static String[] splitAtWordBoundaryNear( String text, int len ) {
		
		assert text != null;
		assert len >= 0;
		
		if( len >= text.length() )
					return new String[]{ text, null };
		
		int i;
		for( i=len-1; i>0; i-- ){
			
			if( Character.isWhitespace( text.charAt( i ) ) ) break;
		}
		
		if( i > 0 ) {
			return new String[]{ text.substring( 0, i ), text.substring( i+1 ) };
		} else {
			return new String[]{ null, text };
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
		
		int n = count( s, fs ) + 1;
		
		if( n == 1 ) return new String[]{ s };
		
		return splitFastLimited( s, fs, n );
	}
	
	public static String [] splitFastAndTrim( String s, char fs ){
		
		String [] result = splitFast( s, fs );
		
		for( int i=0; i < result.length; i++ ){
			result [ i ] = result[ i ].trim();
		}
		
		return result;
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
	 * @return the splitted values in a string array
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
	
	public static List<String> splitFastToListAndProcess( String s, char split, Function<String,String> processor ){
		
		List<String> result = new ArrayList<>( 8 );
		
		final int len = s.length();
		
		int i, last=0;
		for( i=0; i<len; i++ ){
			
			if( split == s.charAt( i ) ){
				
				result.add( processor.apply( s.substring( last, i ) ) );
				
				last = i+1;
			};
		}
		
		if( len>=last ){
			result.add( processor.apply( s.substring( last ) ) );
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
	 * @return the split values in a string array
	 */
	public static String [] splitFastOnce( String s, char split ){
		
		int pos = s.indexOf( split );
		
		if( pos >= 0 ){
			return new String[]{ s.substring( 0, pos ), s.substring( pos+1 ) };
		} else {
			return new String[]{ s };
		}
	}
	
	/**
	 * Split s one time starting at index
	 * 
	 * @param s
	 * @param split
	 * @param startAt 
	 * @return the split values in a string array
	 */
	public static String [] splitFastOnce( String s, char split, int startAt ){
		
		int pos = s.indexOf( split, startAt );
		
		if( pos >= 0 ){
			return new String[]{ s.substring( 0, pos ), s.substring( pos+1 ) };
		} else {
			return new String[]{ s };
		}
	}
	
	/**
	 * Split s one time
	 * 
	 * @param s
	 * @param split
	 * @return the split values in a string array
	 */
	public static String [] splitFastOnce( String s, String split ){
		
		int pos = s.indexOf( split );
		
		if( pos >= 0 ){
			return new String[]{ s.substring( 0, pos ), s.substring( pos+split.length() ) };
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
			else result.add( value.trim() );
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
	
	public static int count( String text, char rs ) {
		
		int result = 0;
		for( int i=0; i<text.length(); i++ ){
			if( text.charAt( i ) == rs ) result++;
		}
		return result;
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
	public static String replaceFast( String value, String replace, String with ) {
		
		if( ! value.contains( replace ) ) return value;
		
		// TODO: Make fast
		return value.replace( replace, with );
	}
	
	@FunctionalInterface
	public interface ReplacementProvider {
		public String getReplacementFor( String key );
	}
	
	public static String replaceFast( final String text, final String prefix, final String suffix, ReplacementProvider provider ){
		
		int pLength = prefix.length(),
		    sLength = suffix.length();
		
		int idxStart = 0, idxEnd = -pLength;
		
		StringBuilder result = new StringBuilder();
		
		// Go through looking for "--("
		while( (idxStart = text.indexOf( prefix, idxEnd )) > -1 ){
			
			result.append( text.substring( idxEnd+sLength, idxStart ) );
			
			idxEnd = text.indexOf( suffix, idxStart );
			
			if( idxEnd < 0 ) break; // <-- Error is silently ignored here
			
			String val = text.substring( idxStart+pLength, idxEnd ).trim();
			if( val.length() > 0 ){
				val = val.toLowerCase();
				String txt = provider.getReplacementFor( val );
				if( txt == null ) txt = prefix + "missing: " + val + suffix;
				result.append( txt );
			} else {
				// Keep empty --()-- because they are processed later on and
				// mean "inherit text from parent/child"
				result.append( prefix + suffix );
			}
			
			idxStart = idxEnd;
		};
		
		if( idxEnd < 0 ){
			// Nothing to replace or error happend
			return text;
		} else{
			result.append( text.substring( idxEnd+pLength ) );
			return result.toString();
		}
		
	}
}
