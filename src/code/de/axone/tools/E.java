package de.axone.tools;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

public abstract class E {
	
	private static String simplifyClassName( String className ){
		
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
	
	private static String f( String s ){
		return s;
	}
	private static String f( Object o ){
		if( o instanceof String ) return f( (String)o );
		else if( o instanceof Collection<?> ) return f( (Collection<?>)o );
		else if( o instanceof Map<?,?> ) return f( (Map<?,?>) o );
		else if( o.getClass().isArray() ) return f( (Object[]) o );
		else return o.toString();
	}
	private static String f( Collection<?> l ){
		StringBuilder r = new StringBuilder();
		r.append( "(" );
		boolean first = true;
		for( Object o : l ){
			if( first ) first = false;
			else r.append( ", " );
			r.append( "'" );
			r.append( f( o ) );
			r.append( "'" );
		}
		r.append( ")" );
		return r.toString();
	}
	private static String f( Map<?,?> m ){
		StringBuilder r = new StringBuilder();
		r.append( "{" );
		boolean first = true;
		for( Object key : m.keySet() ){
			if( first ) first = false;
			else r.append( ", " );
			r.append( f( key ) );
			r.append( "=>" );
			r.append( f( m.get( key ) ) );
		}
		r.append( "}" );
		return r.toString();
	}
	private static String f( Object [] a ){
		
		StringBuilder r = new StringBuilder();
		r.append( "[" );
		boolean first = true;
		for( Object o : a ){
			if( first ) first = false;
			else r.append( ", " );
			r.append( "'" );
			r.append( f( o ) );
			r.append( "'" );
		}
		r.append( "]" );
		return r.toString();
	}
	
	private static void echo( PrintStream out, boolean nl, Object ... os ){
		echo( out, 3, nl, os );
	}
	
	private static void echo( PrintStream out, int depth, boolean nl, Object ... os ){
		
		Exception e = new Exception();
		StackTraceElement[] elm = e.getStackTrace();
		
		String clazz = simplifyClassName( elm[depth].getClassName() );
		int line = elm[depth].getLineNumber();
		out.print( ">>> " + clazz + "(" + line + "): " );
		
		if( os != null && os.length > 0 ){
				
    		if( os.length > 1 ) out.println();
        		
    		for( Object o : os ){
    			
    			if( os.length > 1 ) out.print( '\t' );
    			out.print( o );
    			if( os.length > 1 ) out.println();
    		}
    		if( nl ) out.println();
    		out.flush();
		} else {
			out.println( S._NULL_ );
		}
	}
	
	private static void echo( PrintStream out, Map<?,?> map ){
		MapPair [] pairs = new MapPair[ map.size() ];
		int i = 0;
		for( Object key : map.keySet() ){
			pairs[ i++ ] = new MapPair( key, map.get( key ) );
		}
		echo( out, 3, true, (Object[])pairs );
	}
	
	public static void rr( Object ... os ){
		
		echo( System.err, true, os );
	}
	public static void rr( Map<?,?> map ){
		
		echo( System.err, map );
	}
	public static void rr_( Object ... os ){
		
		echo( System.err, false, os );
	}
	
	public static void cho( Object ... os ){
		
		echo( System.out, true, os );
	}
	public static void cho( Map<?,?> map ){
		
		echo( System.out, map );
	}
	public static void cho_( Object ... os ){
		
		echo( System.out, false, os );
	}
	
	public static void xit( String message, int code ){
		
		StringBuilder text = new StringBuilder();
		
		text.append( "EXIT: " );
		
		if( message != null ){
			text.append('"').append( message ).append( "\": " );
		}
		
		if( code == 0 ){
			text.append( "OK" );
    		echo( System.out, 4, true, text.toString() );
		} else {
			text.append( code );
    		echo( System.err, 4, true, text.toString() );
		}
		System.exit( code );
	}
	
	public static void xit(){
		xit( 0 );
	}
	
	public static void xit( String message ){
		xit( message, -1 );
	}
	
	public static void xit( int code ){
		xit( null, code );
	}
	
	public static void xit( Throwable t ){
		xit( t.getMessage() );
	}
	
	private static class MapPair{
		private Object key, value;
		MapPair( Object key, Object value ){
			this.key = key;
			this.value = value;
		}
		private String key(){
			if( key == null ) return "NULL";
			return "'" + key.toString() + "'";
		}
		private String value(){
			if( value == null ) return "NULL";
			return "\"" + f( value ) + "\"";
		}
		@Override
		public String toString(){
			return( key() + " => " + value() );
		}
	}
}
