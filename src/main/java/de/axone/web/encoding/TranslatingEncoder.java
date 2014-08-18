package de.axone.web.encoding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.axone.data.Tripple;
import de.axone.exception.Assert;
import de.axone.tools.S;

public class TranslatingEncoder implements Encoder {

	private final String [] tt;
	private final char min, max;

	public TranslatingEncoder( char [] from, String [] to ) {
		
		Tripple<String[], Character, Character> t = buildTranslationTable( from, to );
		this.tt = t.getA();
		this.min = t.getB();
		this.max = t.getC();
	}
	
	protected Tripple<String[], Character, Character> buildTranslationTable( char[] from, String[] to ) {
		
		Map<Character,String> map = new HashMap<>();
		
		char min = Character.MAX_VALUE,
		     max = Character.MIN_VALUE;
		
		for( int i=0; i<from.length; i++ ){
			
			char c = from[ i ];
			
			if( c < min ) min = c;
			if( c > max ) max = c;
			
			map.put( c, to[ i ] );
		}
		
		int size = max-min+1;
		
		if( size > 256 ) //Some arbitrary size. This is to prevent major memory leak.
				throw new IllegalArgumentException( "Build to big translation table" );
		
		String [] result = new String[ size ];
		
		for( Map.Entry<Character,String> entry : map.entrySet() ){
			
			result[ entry.getKey() - min ] = entry.getValue();
		}
		
		return new Tripple<>( result, min, max );
	}

	
	@Override
	public String encode( CharSequence csq ) {
		
		if( csq == null ) return null;
		
		StringBuilder result = new StringBuilder();
		
		try {
			append( result, csq, 0, csq.length() );
		} catch( IOException e ) {
			throw new RuntimeException( "IO on StringBuffer should not happend",e );
		}
		
		return result.toString();
	}
	
	
	private void append( Appendable ap, CharSequence csq, int start, int end ) throws IOException{
		
		int subStart=0;
		
		// iterate over string until there is something to encode
		int i;
		for( i=start; i<end; i++ ){
			
			char c = csq.charAt( i );
			
			if( c >= min && c <= max ){
				
				String value = tt[ c-min ];
				
				if( value != null ){
					
					// Append chunk
					if( i-subStart > 0 )
							ap.append( csq.subSequence( subStart, i ) );
					
					// append encoded
					ap.append( value );
					
					// remember new start
					subStart = i+1;
				}
			}
		}
		
		// Nothing encoded. Append complete string.
		if( subStart == 0 ) {
			ap.append( csq );
			
		// Append rest of string
		} else if( i-subStart > 0 ) {
			ap.append( csq.subSequence( subStart, i ) );
		}
		
	}
	
	private void append( Appendable ap, char c ) throws IOException{
		
		if( c >= min && c <= max ){
			String value = tt[ c-min ];
			if( value != null ){
				ap.append( value );
				return;
			}
		}
		ap.append( c );
	}
	
	
	@Override
	public Appendable filter( Appendable sub ) {
		
		return new EncodingAppender( sub );
	}

	private class EncodingAppender implements Appendable {
		
		private final Appendable sub;
	
		protected EncodingAppender( Appendable out ) {
			this.sub = out;
		}
		
		@Override
		public Appendable append( CharSequence csq ) throws IOException {
			
			if( csq == null ) csq = S._NULL_;
			
			return append( csq, 0, csq.length() );
		}
	
		@Override
		public Appendable append( CharSequence csq, int start, int end )
				throws IOException {
			
			Assert.notNull( csq, "csq" );
			Assert.gte( start, "start", 0 );
			Assert.lte( start, "start", csq.length() );
			Assert.gte( end, "end", start );
			Assert.lte( end, "end", csq.length() );
			
			TranslatingEncoder.this.append( sub, csq, start, end );
			
			return this;
		}
	
		@Override
		public Appendable append( char c ) throws IOException {
			
			TranslatingEncoder.this.append( sub, c );
			
			return this;
		}
		
	}

}
