package de.axone.tools;

import java.io.PrintStream;

import de.axone.exception.Ex;

public abstract class E {
	
	private static void printPos( PrintStream out, int depth ){
		
		String clazz = Ex.me( depth+1 );
		
		out.append( ">>> (" + String.valueOf( clazz ) + ") " );
	}
	
	private static void printThreadName( PrintStream out ){
		out.append( '[' )
				.append( Thread.currentThread().getName() )
				.append( ']' );
	}
	
	private static void echo( PrintStream out, int depth, boolean lino, boolean nl, boolean tn, Object ... os ){
		
		if( tn ) printThreadName( out );
		
		if( lino ) printPos( out, depth );
		
		if( os == null ){
			F.ormatB( out, null );
			//out.print( F.ormat( null ) );
		} else if( os.length == 0 ){
			out.append( "- [] -" );
		} else {
			boolean first = true;
    		for( Object o : os ){
    			
    			if( first ) first = false;
    			else out.append( ", " );
    			
    			F.ormatB( out, o );
    		}
		}
		
		if( nl ) out.append( '\n' );
		out.flush();
	}
	
	private static void printf( PrintStream out, boolean lino, boolean nl, String format, Object ... args ){
		
		if( lino ) printPos( out, 2 );
		
		out.printf( format, args );
		if( nl ) out.println();
	}
	
	private static void log( PrintStream out, boolean lino, boolean nl, String format, Object ...args ){
		
		if( lino ) printPos( out, 2 );
		
		for( Object arg : args ){
			
			format = format.replaceFirst( "\\{\\}", F.ormat( arg ) );
		}
		out.print( format );
		
		if( nl ) out.println();
	}
	
	private static void echo( PrintStream out, boolean lino, boolean nl, boolean tn, Object ... os ){
		echo( out, 3, lino, nl, tn, os );
	}
	
	public static void banner( String text ){
		echo( System.out, 2, true, true, false, Text.banner( text.charAt( 0 ), text ) );
	}
	
	public static void banner( char border, String text ){
		echo( System.out, 2, true, true, false, Text.banner( border, text ) );
	}
	
	public static void poster( String text ){
		echo( System.out, 2, true, true, false, Text.poster( text.charAt( 0 ), text ) );
	}
	
	public static void poster( char border, String text ){
		echo( System.out, 2, true, true, false, Text.poster( border, text ) );
	}
	
	/**
	 * Output file and line number and some text to STDERR
	 */
	public static void rr(){
		echo( System.err, true, true, false, "" );
	}
	
	public static void rr( byte [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( char [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( short [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( int [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( long [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( float [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( double [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
	}
	
	public static void rr( boolean [] os ){
		echo( System.err, true, true, false, os );
	}
	public static void rr( Object ... os ){
		
		echo( System.err, true, true, false, os );
	}
	public static void rr_( Object ... os ){
		
		echo( System.err, true, false, false, os );
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
	
	public synchronized static void rrt( Object ... os ){
		
		echo( System.err, true, true, true, os );
	}
	
	public synchronized static void _rrt( Object ... os ){
		
		echo( System.err, false, true, true, os );
	}
	/**
	 * Output file and line number and some text to STDOUT
	 */
	public static void cho(){
		
		echo( System.out, true, true, false, "" );
	}
	public static void cho( Object ... os ){
		
		echo( System.out, true, true, false, os );
	}
	public static void cho_( Object ... os ){
		
		echo( System.out, true, false, false, os );
	}
	public static void _cho_( Object ... os ){
		
		echo( System.out, false, false, false, os );
	}
	public static void _cho( Object ... os ){
		
		echo( System.out, false, true, false, os );
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
	private static final int EXIT_UP = 3;
	private static void exit( String message, int code ){
		
		StringBuilder text = new StringBuilder();
		
		text.append( "EXIT: " );
		
		if( message != null ){
			text.append('"').append( message ).append( "\": " );
		}
		
		if( code == 0 ){
			text.append( "OK" );
    		echo( System.out, EXIT_UP, true, true, false, text.toString() );
		} else {
			text.append( code );
    		echo( System.err, EXIT_UP, true, true, false, text.toString() );
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
	
	private static final int EX_DEFAULT_DEPTH = 5;
	private static final int EX_UP = 4;
	private static final String EX_MARK = "MARK";
	
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
	private static void ex( PrintStream out, Throwable t, int start, int depth, String message ){
		
		StringBuilder result = new StringBuilder( depth*16 );
		
		result.append( "[" ).append( message ).append( "]" );
		
		result.append( Ex.me( t, start, depth ) );
		
		echo( out, start-1, true, true, false, result.toString() );
	}
	public static void x( int depth, Throwable t ){
		ex( System.err, t, EX_UP-1, depth, t.getMessage() );
	}
	public static void x( Throwable t ){
		ex( System.err, t, EX_UP-1, EX_DEFAULT_DEPTH, t.getMessage() );
	}
	
}
