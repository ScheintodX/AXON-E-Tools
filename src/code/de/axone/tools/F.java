package de.axone.tools;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Pattern;

public class F {
	
	private static final Pattern NESTED_CLASS = Pattern.compile( "\\$[\\w_]+" );
	static String removeNestedClasses( String className ){
		
		return NESTED_CLASS.matcher( className ).replaceAll( "" );
	}

	static String simplifyClassName( String className ){
		
		int oldIndex = className.indexOf( '.' );
		int index=oldIndex;
		
		if( oldIndex > 0 ){
			while( ( index = className.indexOf( '.', index+1 ) ) > 0 ){
				
				oldIndex = index;
			}
			return className.substring( oldIndex+1 );
		} else {
			return className;
		}
	}

	public static String ormat( Object o ){
		StringBuilder r = new StringBuilder();
		format( r, o );
		return r.toString();
	}

	private static void formatIterable( StringBuilder r, Iterable<?> l ){
		
		r.append( "(" );
		boolean first = true;
		for( Object o : l ){
			if( first ) first = false;
			else r.append( ", " );
			
			formatItem( r, o );
		}
		r.append( ")" );
	}

	private static void formatEnumeration( StringBuilder r, Enumeration<?> l ){
		
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

	private static void formatMap( StringBuilder r, Map<?,?> m ){
		
		r.append( "{" );
		boolean first = true;
		for( Object key : m.keySet() ){
			if( first ) first = false;
			else r.append( ", " );
			
			formatItem( r, key );
			r.append( "=>" );
			formatItem( r, m.get( key ) );
		}
		r.append( "}" );
	}

	private static void formatArray( StringBuilder r, Object a ){
		
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
	
	private static void _formatArrayItem( StringBuilder r, boolean first, Object o ){
		if( ! first ) r.append( ", " );
		formatItem( r, o );
	}
	
	private static void format( StringBuilder r, Object o ){
		
		if( o == null ) r.append( S._NULL_ );
		else {
			// TODO: Explicit vorhandenes toString checken
			if( o instanceof String ) formatString( r, (String)o );
			else if( o instanceof Path ) formatPath( r, (Path)o );
			else if( o instanceof Iterable<?> ) formatIterable( r, (Iterable<?>)o );
			else if( o instanceof Map<?,?> ) formatMap( r, (Map<?,?>) o );
			else if( o instanceof Enumeration<?> ) formatEnumeration( r, (Enumeration<?>) o );
			else if( o.getClass().isArray() ) formatArray( r, o );
			else r.append( o );
		}
	}
	
	private static void formatString( StringBuilder r, String s ){
		r.append( s );
	}
	
	// We need this because Path is recursive and Iterable
	// TODO: General recusion handling ...
	private static void formatPath( StringBuilder r, Path p ){
		r.append( p.toFile().getAbsolutePath() );
	}
	
	private static void formatItem( StringBuilder r, Object o ){
		
		boolean isSpecial = 
				o == null ||
				o instanceof Collection<?> ||
				o instanceof Map<?,?> ||
				o.getClass().isArray()
		;
		
		if( !isSpecial ) r.append( '\'' );
		format( r, o );
		if( !isSpecial ) r.append( '\'' );
	}

}
