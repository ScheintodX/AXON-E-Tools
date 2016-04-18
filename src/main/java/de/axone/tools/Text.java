package de.axone.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.Formatter;
import java.util.Map;

public abstract class Text {

	/**
	 * Makes a one line Banner
	 *
	 * Like label(...) but prints the given width
	 *
	 * @param c char to use for banner border
	 * @param width of banner
	 * @param label label to print in banner
	 * @return the banner as string
	 */
	public static String banner( char c, int width, String label ){

		if( label == null ) label = "-null-";

		StringBuilder result = new StringBuilder();

		result.append( '\n' );

		lineNlBB( result, c, width );

		int labelLength = label.length() + 2;
		int leftLength = ( width - labelLength )/2;
		int rightLength = width - ( leftLength + labelLength );

		lineBB( result, c, leftLength );
		result.append( ' ' ).append( label ).append( ' ' );
		lineBB( result, c, rightLength );
		result.append( '\n' );

		lineNlBB( result, c, width );

		return result.toString();
	}

	public static String banner( String label ){
		return banner( '#', 79, label );
	}

	public static String banner( char c, String label ){
		return banner( c, 79, label );
	}

	/**
	 * Create a text label which will look like that:
	 *
	 * ##################
	 * ### label text ###
	 * ##################
	 *
	 * @param c char to use for border
	 * @param label to display in center of label
	 * @return the label as string
	 */
	public static String label( char c, String label ){

		return banner( c, label.length()+8, label );
	}

	public static String window( int width, int height, CharSequence output ){

		StringBuilder msg = new StringBuilder();

		String text = output.toString();

		int start, end=-1;
		int tHeight=0;
		while( true ){

			start = end+1;
			end = text.indexOf( '\n', start );

			String line;
			if( end > 0 ){
				line = text.substring( start, end );
			} else {
				line = text.substring( start );
			}

			tHeight++;

			int spaces = width - line.length() - 2;
			int left = spaces/2;
			int right = spaces-left;

			msg.append( '|' );
			lineBB( msg, ' ', left );
			msg.append( line );
			lineBB( msg, ' ', right );
			msg.append( '|' );
			msg.append( '\n' );

			if( end < 0 ) break;

		}

		int whiteLines = height-tHeight;
		int topSpaces = whiteLines/2;
		int bottomSpaces = whiteLines - topSpaces;

		StringBuilder result = new StringBuilder();
		result.append( '\n' );

		lineBB( result, '+', '-', width );
		result.append( '\n' );

		for( int i=0; i<topSpaces-1; i++ ){
			lineBB( result, '|', ' ', width );
			result.append( '\n' );
		}

		result.append( msg );

		for( int i=0; i<bottomSpaces-1; i++ ){
			lineBB( result, '|', ' ', width );
			result.append( '\n' );
		}

		lineBB( result, '+', '-', width );
		result.append( '\n' );

		return result.toString();
	}

	/**
	 * Makes a big multiline banner / posters
	 *
	 * The width is calculated by the widest line
	 *
	 * @param c char to use for border
	 * @param text to display in center of poster
	 * @return the poster as string
	 */
	public static String poster( char c, String text ){

		StringBuilder result = new StringBuilder();

		result.append( '\n' );

		if( text == null ) text = "";
		text = text.trim();

		try {
			int width = 0;
    		BufferedReader r = new BufferedReader( new StringReader( text ) );
    		String line;
			while( ( line = r.readLine() ) != null ){

				if( line.length() > width ) width = line.length();
			}

			width += 4; // borders

			lineNlBB( result, c, width );

			// Reset to 0. mart/reset seems strange
			r = new BufferedReader( new StringReader( text ) );
			while( ( line = r.readLine() ) != null ){
				Text.limitedFixedLineBB( result, c, width, line, false );
    			result.append( '\n' );
			}

			lineBB( result, c, width );

		} catch( IOException e ) {
			e.printStackTrace();
		}

		return result.toString();
	}
	public static String poster( String text ){
		return poster( '#', text );
	}
	public static String poster( char c, Iterable<?> collection ){
		return poster( c, Str.join( "\n", collection ) );
	}
	public static String poster( Iterable<?> collection ){
		return poster( '#', collection );
	}
	public static String poster( char c, Map<?,?> map ){
		return poster( c, Str.join( "\n", Mapper.mapToList( ": ", map ) ) );
	}
	public static String poster( Map<?,?> map ){
		return poster( '#', map );
	}

	public static StringBuilder lineNlBB( StringBuilder buffer, char outer, char inner, int width ){
		lineBB( buffer, outer, inner, width );
		buffer.append( '\n' );
		return buffer;
	}
	public static StringBuilder lineBB( StringBuilder buffer, char outer, char inner, int width ){
		buffer.append( outer );
		for( int i = 0; i < width-2; i++ ){
			buffer.append( inner );
		}
		buffer.append( outer );
		return buffer;
	}
	
	public static StringBuilder lineNlBB( StringBuilder buffer, char c, int width ){
		lineBB( buffer, c, width );
		buffer.append( '\n' );
		return buffer;
	}

	public static StringBuilder lineBB( StringBuilder buffer, char c, int width ){
		for( int i = 0; i < width; i++ ){
			buffer.append( c );
		}
		return buffer;
	}
	
	public static StringBuilder lineB( char c, int width ){
		StringBuilder buffer = new StringBuilder( width );
		return lineBB( buffer, c, width );
	}
	public static String line( char c, int width ){
		return lineB( c, width).toString();
	}
	public static String line( int width ){
		return lineB( '-', width).toString();
	}
	
	public static StringBuilder lineBB( StringBuilder buffer, char c, int width, String text, boolean alignRight ){
		
		if( text.length() > width-4 ) text = text.substring( 0, width-4 );
		
		int	left = 1;
		int right = width - 3 - text.length();
		
		if( alignRight ){ // swap with xor.
			left ^= right;
			right ^= left;
			left ^= right;
		}
		
		lineBB( buffer, c, left );
		buffer.append( ' ' );
		buffer.append( text );
		buffer.append( ' ' );
		lineBB( buffer, c, right );
		
		return buffer;
	}
	public static StringBuilder lineB( char c, int width, String text, boolean alignRight ){
		return lineBB( new StringBuilder(), c, width, text, alignRight );
	}
	public static String line( char c, int width, String text, boolean alignRight ){
		return lineB( c, width, text, alignRight ).toString();
	}
	public static String line( char c, int width, String text ){
		return line( c, width, text, false );
	}
	
	public static StringBuilder xBB( StringBuilder buffer, String text, int repeat ){
		for( ; repeat>=0; repeat-- ) buffer.append( text );
		return buffer;
	}
	public static StringBuilder xB( String text, int repeat ){
		return xBB( new StringBuilder(), text, repeat );
	}
	public static String x( String text, int repeat ){
		return xB( text, repeat ).toString();
	}
	
	/**
	 * Returns the text surrounded by the given char.
	 *
	 * E.g.: <tt>limitText( '#', "Test" )</tt> will return "<tt># Test #</tt>"
	 *
	 * @param buffer to print into
	 * @param c char to use for border
	 * @param text to display at center
	 * @return the limited text as passed thru string builder
	 */
	public static StringBuilder limitedTextBB( StringBuilder buffer, char c, String text ){

		buffer
			.append( c )
			.append( ' ' )
			.append( text )
			.append( ' ' )
			.append( c );

		return buffer;
	}
	public static StringBuilder limitedTextB( char c, String text ){
		StringBuilder buffer = new StringBuilder();
		return limitedTextBB( buffer, c, text );
	}
	public static String limitedText( char c, String text ){
		return limitedTextB( c, text ).toString();
	}
	
	/**
	 * Returns a text with fixed width.
	 * Too short strings are filled with spaces.
	 * Too long strings are cut according to parameter <tt>limit</tt>
	 *
	 * @param builder to print into
	 * @param width of text returned
	 * @param text to print
	 * @param limit <tt>true</tt> will cut off the end of the text.
	 *
	 * @return the line with the passed thru string builder
	 */
	public static StringBuilder fixedWidthLineBB( StringBuilder builder, int width, String text, boolean limit ){

		int length = text.length();
		int dif = width - length;

		if( dif < 0 && limit ){
			text = text.substring( 0, width );
		}

		builder.append( text );

		if( dif > 0 ){
			lineBB( builder, ' ', dif );
		}

		return builder;
	}
	public static StringBuilder fixedWidthLineB( int width, String text, boolean limit ){
		StringBuilder builder = new StringBuilder();
		return fixedWidthLineBB( builder, width, text, limit );
	}
	public static String fixedWidthLine( int width, String text, boolean limit ){
		return fixedWidthLineB( width, text, limit ).toString();
	}

	/**
	 * Combination of the two named methods
	 *
	 * @param builder to print into
	 * @param c char to use for border
	 * @param width of text returned
	 * @param text to print
	 * @param limit <tt>true</tt> will cut off the end of the text.
	 * @return the line with the passed thru string builder
	 */
	public static StringBuilder limitedFixedLineBB( StringBuilder builder, char c, int width, String text, boolean limit ){

		return limitedTextBB( builder, c, fixedWidthLine( width-4, text, limit ) );
	}
	public static StringBuilder limitedFixedLineB( char c, int width, String text, boolean limit ){
		StringBuilder builder = new StringBuilder();
		return limitedFixedLineBB( builder, c, width, text, limit );
	}
	public static String limitedFixedLine( char c, int width, String text, boolean limit ){
		return limitedFixedLineB( c, width, text, limit ).toString();
	}

	/**
	 * Prints values in format ....Name: Value with
	 * configurable indention
	 *
	 * @param builder to print into
	 * @param indent number of spaces to prepend
	 * @param label to print
	 * @param valueO value to print. objects are converted to string
	 * @return the value with the passed thru string builder
	 */
	public static StringBuilder valueBB( StringBuilder builder, int indent, String label, Object valueO ){
		
		String value = valueO != null ? valueO.toString() : S._NULL_;

		for( int i = 0; i < indent; i++ ) builder.append( ' ' );

		builder
			.append( label )
			.append( ": " )
			.append( value )
			.append( '\n' )
		;

		return builder;
	}

	public static StringBuilder valueB( int indent, String label, Object valueO ){
		String value = valueO != null ? valueO.toString() : S._NULL_;
		StringBuilder result = new StringBuilder( indent + label.length() + value.length() + 3 );
		return valueBB( result, indent, label, valueO );
	}

	public static String value( int indent, String label, Object valueO ){
		return valueB( indent, label, valueO ).toString();
	}

	/**
	 * Generate indention
	 *
	 * @param builder to print into
	 * @param indent number of spaces to prepend
	 * @return the indended line with the passed thru string builder
	 */
	public static StringBuilder indentBB( StringBuilder builder, int indent ){
		return indentBB( builder, indent, ' ' );
	}
	public static StringBuilder indentBB( StringBuilder builder, int indent, char c ){
		lineBB( builder, c, indent );
		return builder;
	}
	public static StringBuilder indentB( int indent ){
		return indentB( indent, ' ' );
	}
	public static StringBuilder indentB( int indent, char c ){
		StringBuilder result = new StringBuilder( indent );
		return indentBB( result, indent, c );
	}
	public static String indent( int indent ){
		return indent( indent, ' ' );
	}
	public static String indent( int indent, char c ){
		return indentB( indent, c ).toString();
	}

	public static String arrayHex( byte[] data ){
		return arrayHexB( data ).toString();
	}
	public static StringBuilder arrayHexB( byte[] data ){
		return arrayHexBB( new StringBuilder(), data );
	}
	public static StringBuilder arrayHexBB( StringBuilder builder, byte[] data ){
		return arrayHexBB( builder, data, 32, -1 );
	}
	public static String arrayHex( byte[] data, int widht ){
		return arrayHexB( data, widht ).toString();
	}
	public static StringBuilder arrayHexB( byte[] data, int widht ){
		return arrayHexBB( new StringBuilder(), data, widht );
	}
	public static StringBuilder arrayHexBB( StringBuilder builder, byte[] data, int width ){
		return arrayHexBB( builder, data, width, -1 );
	}
	public static String arrayHex( byte[] data, int widht, int indent ){
		return arrayHexB( data, widht, indent ).toString();
	}
	public static StringBuilder arrayHexB( byte[] data, int widht, int indent ){
		return arrayHexBB( new StringBuilder(), data, widht, indent );
	}
	public static StringBuilder arrayHexBB( StringBuilder builder, byte[] data, int width, int indent ){
		return arrayHexBB( builder, data, width, indent, 0, -1 );
	}
	public static StringBuilder arrayHexBB( StringBuilder builder, byte[] data, int width, int indent, int offset, int len ){

		@SuppressWarnings( "resource" )
		Formatter f = new Formatter( builder );

		if( indent > 0 ) indentBB( builder, indent );
		
		len = len > data.length ? data.length : len;

		for( int i=offset; i<data.length; i++ ){

			f.format( "%02x", data[i] );

			if( i < data.length-1 ){
    			if( i%width == width-1 ){
    				builder.append( '\n' );
    				if( indent > 0 ) indentBB( builder, indent );
    			} else {
    				builder.append( ' ' );
    			}
			}
		}

		return builder;
	}
	
	public static String arrayHex( ByteBuffer data, int widht ){
		return arrayHex( data, widht, 0 );
	}
	public static String arrayHex( ByteBuffer data, int widht, int indent ){
		return arrayHexB( data, widht, indent ).toString();
	}
	public static StringBuilder arrayHexB( ByteBuffer data, int widht, int indent ){
		return arrayHexBB( new StringBuilder(), data, widht, indent );
	}
	public static StringBuilder arrayHexBB( StringBuilder builder, ByteBuffer data, int width, int indent ){

		@SuppressWarnings( "resource" )
		Formatter f = new Formatter( builder );

		if( indent > 0 ) indentBB( builder, indent );

		for( int i=data.position(); i<data.limit(); i++ ){

			f.format( "%02x", data.get(i) );

			if( i < data.limit()-1 ){
    			if( i%width == width-1 ){
    				builder.append( '\n' );
    				if( indent > 0 ) indentBB( builder, indent );
    			} else {
    				builder.append( ' ' );
    			}
			}
		}

		return builder;
	}
	
	
	/**
	 * Trim text to a certain length.
	 * Append timmedMark if text is longer than expected.
	 * 
	 * If a trimmedMark is supplied this method makes sure that the trimmed text with
	 * mark appended is not longer than 'width'.
	 * 
	 * @param text to trim
	 * @param width to trim to
	 * @param trimmedMark mark to output when trimmed
	 * @return the trimmed value
	 */
	
	public static String trimTo( String text, int width, String trimmedMark ){
		
		if( text.length() <= width ) return text;
		if( trimmedMark != null ) width = width - trimmedMark.length();
		if( width < 0 ) throw new IllegalArgumentException( "width is less than trimmedMark length" );
		return text.substring( 0, width ) + trimmedMark;
	}
	
	public enum Align { LEFT, RIGHT; }
	
	public static String left( String text, int width ){
		if( text != null && text.length() > 0 ) text = text + " ";
		return align( Align.LEFT, text, width, '.' );
	}
	
	public static String right( String text, int width ){
		if( text != null && text.length() > 0 ) text = " " + text;
		return align( Align.RIGHT, text, width, '.' );
	}
	
	public static String align( Align align, String text, int width, char fillChar ){
		return alignB( align, text, width, fillChar ).toString();
	}
	
	public static StringBuilder alignB( Align align, String text, int width, char fillChar ){
		return alignBB( new StringBuilder(), align, text, width, fillChar );
	}
	
	public static StringBuilder alignBB( StringBuilder builder, Align align, String text, int width, char fillChar ){
		
		if( text == null ) text = "";
		if( text.length() >= width ) return builder.append( text );
		
		if( align == Align.LEFT ){
			
			builder.append( text );
			lineBB( builder, fillChar, width-text.length() );
			return builder;
			
		} else if( align == Align.RIGHT ){
			
			lineBB( builder, fillChar, width-text.length() );
			builder.append( text );
			return builder;
			
		} else {
			throw new IllegalArgumentException( "Not supported Alignment: " + align );
		}
		
	}
	
	public static String diff( String s1, String s2 ){
		return diff( s1, s2, -1 );
	}
	public static String diff( String s1, String s2, int truncate ){
		
		if( s1 == s2 ) return "[SAME]";
		if( s1 == null ) return "[s1 IS NULL]";
		if( s2 == null ) return "[s2 IS NULL]";
		if( s1.equals( s2 ) ) return "[EQUAL]";
		
		String min;
		if( s1.length() < s2.length() ){
			min = s1; //max = s2;
		} else {
			min = s2; //max = s1;
		}
		
		StringBuilder leftB = new StringBuilder();
		int i;
		for( i=0; i<min.length(); i++ ){
			
			char c1 = s1.charAt( i );
			char c2 = s2.charAt( i );
			
			if( c1!=c2 ){
				leftB.append( "--->[:" );
				break;
			}
			leftB.append( c1 );
		}
		
		StringBuilder rightB = new StringBuilder();
		int j;
		for( j=0; j<min.length(); j++ ){
			
			char c1 = s1.charAt( s1.length()-1 -j );
			char c2 = s2.charAt( s2.length()-1 -j );
			
			if( c1!=c2 ){
				rightB.insert( 0, ":]<---" );
				break;
			}
			rightB.insert( 0, c1 );
		}
		
		String left = leftB.toString();
		String right = rightB.toString();
		
		StringBuilder result = new StringBuilder();
		
		if( truncate >= 0 ){
			
			truncate += 6; // --->[ / ]<---
			
			if( left.length() > truncate ){
				left = left.substring( left.length() - truncate );
			}
			if( right.length() > truncate ){
				right = right.substring( 0, truncate );
			}
		}
		
		result.append( left );
		result.append( s1.subSequence( i, s1.length() -j ) );
		result.append( ":|:" );
		result.append( s2.subSequence( i, s2.length() -j ) );
		result.append( right );
		
		return result.toString();
	}
	
	public static StringBuilder reverseB( CharSequence s ){
		
		StringBuilder result = new StringBuilder();
		for( int i=0; i<s.length(); i++ ){
			result.append( s.charAt( s.length()-1-i ) );
		}
		return result;
	}
	
	// Various toString methods for conversion of 'any object' to string
}