package de.axone.tools;

import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

public class F {

	public static String ormat( Object o ){
		StringBuilder r = new StringBuilder();
		ormatB( r, o );
		return r.toString();
	}

	public static void ormatB( Appendable r, Object o ){
		try {
			_ormatB( r, o );
		} catch( IOException e ){
			throw new IOError( e );
		}
	}
	public static void _ormatB( Appendable r, Object o ) throws IOException {

		if( o == null ) r.append( S._NULL_ );
		else if( o instanceof CharSequence ) formatCharSequence( r, (CharSequence)o );
		else if( o instanceof Byte ) formatByte( r, (Byte)o );
		else if( hasToString( o ) ) formatToString( r, o );
		else if( QueryFormatter.canFormat( o ) ) QueryFormatter.format( r, o );
		else if( o instanceof Path ) formatPath( r, (Path)o );
		else if( o instanceof Iterable<?> ) formatIterable( r, (Iterable<?>)o );
		else if( o instanceof Map<?,?> ) formatMap( r, (Map<?,?>) o );
		else if( o instanceof Enumeration<?> ) formatEnumeration( r, (Enumeration<?>) o );
		else if( o instanceof Map.Entry<?,?> ) formatEntry( r, (Map.Entry<?,?>) o );
		else if( o.getClass().isArray() ) formatArray( r, o );
		else r.append( o.toString() );
	}

	private static boolean hasToString( Object o ) {

		if( o == null ) return false;

		Method [] methods = o.getClass().getDeclaredMethods();

		for( Method method : methods )
			if( "toString".equals( method.getName() ) )
					return true;
		return false;
	}

	private static void formatToString( Appendable r, Object o ) throws IOException {

		r
		 .append( o.toString() )

		 ;
	}

	private static void formatByte( Appendable r, Byte b ) throws IOException {

		r
	     .append( Character.forDigit( (b >> 4) & 0xF, 16))
	     .append( Character.forDigit( (b & 0xF), 16))

	     ;
	}

	private static void formatIterable( Appendable r, Iterable<?> l ) throws IOException {

		r.append( "( " );
		boolean first = true;
		for( Object o : l ){
			if( first ) first = false;
			else r.append( ", " );

			formatItem( r, o );
		}
		r.append( " )" );
	}

	private static void formatEnumeration( Appendable r, Enumeration<?> l ) throws IOException{

		r.append( "( " );
		boolean first = true;

		while( l.hasMoreElements() ){

			Object o = l.nextElement();
			if( first ) first = false;
			else r.append( ", " );

			formatItem( r, o );
		}
		r.append( " )" );
	}

	private static void formatEntry( Appendable r, Map.Entry<?,?> l ) throws IOException {

		r.append( '(' );
		_ormatB( r, l.getKey() );
		r.append( "=\"" );
		_ormatB( r, l.getValue() );
		r.append( "\")" );
	}

	private static void formatMap( Appendable r, Map<?,?> m ) throws IOException {

		r.append( "{ " );
		boolean first = true;
		for( Map.Entry<?,?> entry : m.entrySet() ){

			Object key = entry.getKey();

			if( first ) first = false;
			else r.append( ", " );

			formatItem( r, key );
			r.append( "=>" );
			formatItem( r, entry.getValue() );
		}
		r.append( " }" );
	}

	private static void formatArray( Appendable r, Object a ) throws IOException{

		Class<?> component = a.getClass().getComponentType();

		r.append( "[ " );
		boolean first = true;
		if( component == boolean.class )
			for( boolean o : (boolean[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == byte.class )
			for( byte o : (byte[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == char.class )
			for( char o : (char[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == short.class )
			for( short o : (byte[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == int.class )
			for( int o : (int[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == long.class )
			for( long o : (long[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == float.class )
			for( float o : (float[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component == double.class )
			for( double o : (double[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		else if( component.isArray() ) {

			r.append( "\n" );
			for( Object o : (Object[])a ){
				formatArray( r, o );
				r.append( '\n' );
				first = false;
			}

		} else
			for( Object o : (Object[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		r.append( " ]" );
	}

	private static void _formatArrayItem( Appendable r, boolean first, Object o ) throws IOException {
		if( ! first ) r.append( ", " );
		formatItem( r, o );
	}

	private static void formatCharSequence( Appendable r, CharSequence s ) throws IOException {
		r.append( s );
	}

	// We need this because Path is recursive and Iterable
	// TODO: General recusion handling ...
	private static void formatPath( Appendable r, Path p ) throws IOException {
		r.append( p.toFile().getAbsolutePath() );
	}

	private static void formatItem( Appendable r, Object o ) throws IOException {

		boolean dontUseQuotes =
				o == null ||
				o instanceof Number ||
				o instanceof Collection<?> ||
				o instanceof Map<?,?> ||
				o.getClass().isArray()
		;

		if( !dontUseQuotes ) r.append( '\'' );
		ormatB( r, o );
		if( !dontUseQuotes ) r.append( '\'' );
	}

}
