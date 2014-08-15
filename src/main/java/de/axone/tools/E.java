package de.axone.tools;

import java.io.PrintStream;

import de.axone.exception.Ex;

public abstract class E {
	
	protected static void printPos( PrintStream out, int depth ){
		
		String clazz = Ex.me( depth+1 );
		
		out.printf( ">>> (%s) ", clazz );
	}
	
	protected static void echo( PrintStream out, int depth, boolean lino, boolean nl, Object ... os ){
		
		if( lino ) printPos( out, depth );
		
		if( os == null ){
			out.print( F.ormat( null ) );
		} else if( os.length == 0 ){
			out.print( "- [] -" );
		} else {
			boolean first = true;
    		for( Object o : os ){
    			
    			if( first ) first = false;
    			else out.print( ", " );
    			
    			out.print( F.ormat( o ) );
    		}
		}
		
		if( nl ) out.println();
		out.flush();
	}
	
	protected static void printf( PrintStream out, boolean lino, boolean nl, String format, Object ... args ){
		
		if( lino ) printPos( out, 2 );
		
		out.printf( format, args );
		if( nl ) out.println();
	}
	
	protected static void log( PrintStream out, boolean lino, boolean nl, String format, Object ...args ){
		
		if( lino ) printPos( out, 2 );
		
		for( Object arg : args ){
			
			format = format.replaceFirst( "\\{\\}", F.ormat( arg ) );
		}
		out.print( format );
		
		if( nl ) out.println();
	}
	
	protected static void echo( PrintStream out, boolean lino, boolean nl, Object ... os ){
		echo( out, 3, lino, nl, os );
	}
	
	public static void banner( String text ){
		echo( System.out, 2, true, true, Text.banner( text.charAt( 0 ), text ) );
	}
	
	public static void banner( char border, String text ){
		echo( System.out, 2, true, true, Text.banner( border, text ) );
	}
	
	public static void poster( String text ){
		echo( System.out, 2, true, true, Text.poster( text.charAt( 0 ), text ) );
	}
	
	public static void poster( char border, String text ){
		echo( System.out, 2, true, true, Text.poster( border, text ) );
	}
	
	/**
	 * Output file and line number and some text to STDERR
	 */
	public static void rr(){
		echo( System.err, true, true, "" );
	}
	
	public static void rr( byte [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( char [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( short [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( int [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( long [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( float [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( double [] a ){
		echo( System.err, true, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( boolean [] os ){
		echo( System.err, true, true, os );
	}
	public static void rr( Object ... os ){
		
		echo( System.err, true, true, os );
	}
	public static void rr_( Object ... os ){
		
		echo( System.err, true, false, os );
	}
	public static void rrf( String format, Object ... args ){
		
		printf( System.err, true, true, format, args );
	}
	public static void rrf_( String format, Object ... args ){
		
		printf( System.err, true, false, format, args );
	}
	public static void _rrf( String format, Object ... args ){
		
		printf( System.err, false, true, format, args );
	}
	public static void _rrf_( String format, Object ... args ){
		
		printf( System.err, false, false, format, args );
	}
	public static void rrl( String format, Object ... args ){
		
		log( System.err, true, true, format, args );
	}
	public static void _rrl( String format, Object ... args ){
		
		log( System.err, false, true, format, args );
	}
	public static void rrl_( String format, Object ... args ){
		
		log( System.err, true, false, format, args );
	}
	public static void _rrl_( String format, Object ... args ){
		
		log( System.err, false, false, format, args );
	}
	
	/**
	 * Output file and line number and some text to STDOUT
	 */
	public static void cho(){
		
		echo( System.out, true, true, "" );
	}
	public static void cho( Object ... os ){
		
		echo( System.out, true, true, os );
	}
	public static void cho_( Object ... os ){
		
		echo( System.out, true, false, os );
	}
	public static void _cho_( Object ... os ){
		
		echo( System.out, false, false, os );
	}
	public static void _cho( Object ... os ){
		
		echo( System.out, false, true, os );
	}
	public static void chof( String format, Object ... args ){
		
		printf( System.out, true, true, format, args );
	}
	public static void chof_( String format, Object ... args ){
		
		printf( System.out, true, false, format, args );
	}
	public static void _chof( String format, Object ... args ){
		
		printf( System.out, false, true, format, args );
	}
	public static void _chof_( String format, Object ... args ){
		
		printf( System.out, false, false, format, args );
	}
	
	/* === EXIT =========================================================== */
	protected static final int EXIT_UP = 3;
	protected static void exit( String message, int code ){
		
		StringBuilder text = new StringBuilder();
		
		text.append( "EXIT: " );
		
		if( message != null ){
			text.append('"').append( message ).append( "\": " );
		}
		
		if( code == 0 ){
			text.append( "OK" );
    		echo( System.out, EXIT_UP, true, true, text.toString() );
		} else {
			text.append( code );
    		echo( System.err, EXIT_UP, true, true, text.toString() );
		}
		System.exit( code );
	}
	
	/**
	 * Exit system with a message
	 */
	public static void xit(){
		exit( null, -1 );
	}
	
	public static void xit( String message ){
		exit( message, -1 );
	}
	
	public static void xit( int code ){
		exit( null, code );
	}
	
	public static void xit( String message, int code ){
		exit( message, code );
	}
	
	public static void xit( Throwable t ){
		exit( t.getMessage(), -1 );
	}
	
	/* === EX =========================================================== */
	
	protected static final int EX_DEFAULT_DEPTH = 5;
	protected static final int EX_UP = 4;
	protected static final String EX_MARK = "MARK";
	
	/**
	 * Print a mark and a simplified stacktrace
	 */
	public static void x(){
		
		ex( System.err, new Throwable(), EX_UP, EX_DEFAULT_DEPTH, EX_MARK );
	}
	public static void x( String message ){
		ex( System.err, new Throwable(), EX_UP, EX_DEFAULT_DEPTH, message );
	}
	public static void x( int depth ){
		ex( System.err, new Throwable(), EX_UP, depth, EX_MARK );
	}
	public static void x( int depth, String message ){
		ex( System.err, new Throwable(), EX_UP, depth, message );
	}
	/* for testing */
	static void _x( int depth, String message ){
		ex( System.out, new Throwable(), EX_UP, depth, message );
	}
	protected static void ex( PrintStream out, Throwable t, int start, int depth, String message ){
		
		StringBuilder result = new StringBuilder( depth*16 );
		
		result.append( "[" ).append( message ).append( "]" );
		
		result.append( Ex.me( t, start, depth ) );
		
		echo( out, start-1, true, true, result.toString() );
	}
	public static void x( int depth, Throwable t ){
		ex( System.err, t, EX_UP-1, depth, t.getMessage() );
	}
	public static void x( Throwable t ){
		ex( System.err, t, EX_UP-1, EX_DEFAULT_DEPTH, t.getMessage() );
	}
	
}
