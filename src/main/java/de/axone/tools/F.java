package de.axone.tools;

import java.io.IOError;
import java.io.IOException;
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
		else {
			// TODO: Explicit vorhandenes toString checken
			if( o instanceof String ) formatString( r, (String)o );
			else if( o instanceof Path ) formatPath( r, (Path)o );
			else if( o instanceof Iterable<?> ) formatIterable( r, (Iterable<?>)o );
			else if( o instanceof Map<?,?> ) formatMap( r, (Map<?,?>) o );
			else if( o instanceof Enumeration<?> ) formatEnumeration( r, (Enumeration<?>) o );
			else if( o.getClass().isArray() ) formatArray( r, o );
			else r.append( String.valueOf( o ) );
		}
	}
	
	private static void formatIterable( Appendable r, Iterable<?> l ) throws IOException{
		
		r.append( "(" );
		boolean first = true;
		for( Object o : l ){
			if( first ) first = false;
			else r.append( ", " );
			
			formatItem( r, o );
		}
		r.append( ")" );
	}

	private static void formatEnumeration( Appendable r, Enumeration<?> l ) throws IOException{
		
		r.append( "(" );
		boolean first = true;
		
		while( l.hasMoreElements() ){
			
			Object o = l.nextElement();
			if( first ) first = false;
			else r.append( ", " );
			
			formatItem( r, o );
		}
		r.append( ")" );
	}

	private static void formatMap( Appendable r, Map<?,?> m ) throws IOException {
		
		r.append( "{" );
		boolean first = true;
		for( Map.Entry<?,?> entry : m.entrySet() ){
			
			Object key = entry.getKey();
			
			if( first ) first = false;
			else r.append( ", " );
			
			formatItem( r, key );
			r.append( "=>" );
			formatItem( r, entry.getValue() );
		}
		r.append( "}" );
	}

	private static void formatArray( Appendable r, Object a ) throws IOException{
		
		Class<?> component = a.getClass().getComponentType();
		
		r.append( "[" );
		boolean first = true;
		if( component == boolean.class ){
			for( boolean o : (boolean[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == byte.class ){
			for( byte o : (byte[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == char.class ){
			for( char o : (char[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == short.class ){
			for( short o : (byte[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == int.class ){
			for( int o : (int[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == long.class ){
			for( long o : (long[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == float.class ){
			for( float o : (float[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else if( component == double.class ){
			for( double o : (double[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		} else {
			// This handles arrays of arrays, too
			for( Object o : (Object[])a ){
				_formatArrayItem( r, first, o );
				first = false;
			}
		}
		r.append( "]" );
	}
	
	private static void _formatArrayItem( Appendable r, boolean first, Object o ) throws IOException {
		if( ! first ) r.append( ", " );
		formatItem( r, o );
	}
	
	private static void formatString( Appendable r, String s ) throws IOException {
		r.append( s );
	}
	
	// We need this because Path is recursive and Iterable
	// TODO: General recusion handling ...
	private static void formatPath( Appendable r, Path p ) throws IOException {
		r.append( p.toFile().getAbsolutePath() );
	}
	
	private static void formatItem( Appendable r, Object o ) throws IOException {
		
		boolean isSpecial = 
				o == null ||
				o instanceof Collection<?> ||
				o instanceof Map<?,?> ||
				o.getClass().isArray()
		;
		
		if( !isSpecial ) r.append( '\'' );
		ormatB( r, o );
		if( !isSpecial ) r.append( '\'' );
	}

}
