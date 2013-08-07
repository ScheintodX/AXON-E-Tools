package de.axone.tools;

import java.io.PrintStream;

public abstract class E {
	
	static void printPos( PrintStream out, int depth ){
		
		depth++;
		
		Exception e = new Exception();
		StackTraceElement[] elm = e.getStackTrace();
		
		String clazz = F.simplifyClassName( elm[depth].getClassName() );
		int line = elm[depth].getLineNumber();
		
		out.printf( ">>> (%s.java:%d) ", clazz, line );
	}
	
	static void echo( PrintStream out, int depth, boolean nl, Object ... os ){
		
		printPos( out, depth );
		
		if( os != null && os.length > 0 ){
				
			boolean first = true;
    		for( Object o : os ){
    			
    			if( first ) first = false;
    			else out.print( ", " );
    			
    			out.print( F.ormat( o ) );
    		}
    		if( nl ) out.println();
    		out.flush();
		} else {
			out.println( S._NULL_ );
		}
	}
	
	static void printf( PrintStream out, boolean nl, String format, Object ... args ){
		
		printPos( out, 2 );
		
		out.printf( format, args );
		if( nl ) out.println();
	}
	static void log( PrintStream out, boolean nl, String format, Object ...args ){
		
		printPos( out, 2 );
		
		for( Object arg : args ){
			
			format = format.replaceFirst( "\\{\\}", F.ormat( arg ) );
		}
		out.print( format );
		
		if( nl ) out.println();
	}
	
	static void echo( PrintStream out, boolean nl, Object ... os ){
		echo( out, 3, nl, os );
	}
	
	public static void banner( String text ){
		echo( System.out, 2, true, Text.banner( text.charAt( 0 ), text ) );
	}
	
	public static void banner( char border, String text ){
		echo( System.out, 2, true, Text.banner( border, text ) );
	}
	
	public static void poster( String text ){
		echo( System.out, 2, true, Text.poster( text.charAt( 0 ), text ) );
	}
	
	public static void poster( char border, String text ){
		echo( System.out, 2, true, Text.poster( border, text ) );
	}
	
	public static void rr(){
		echo( System.err, true, "" );
	}
	
	public static void rr( byte [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( char [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( short [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( int [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( long [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( float [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( double [] a ){
		echo( System.err, true, (Object[])A.objects( a ) );
	}
	
	public static void rr( boolean [] os ){
		echo( System.err, true, os );
	}
	public static void rr( Object ... os ){
		
		echo( System.err, true, os );
	}
	public static void rr_( Object ... os ){
		
		echo( System.err, false, os );
	}
	public static void rrf( String format, Object ... args ){
		
		printf( System.err, true, format, args );
	}
	public static void rrf_( String format, Object ... args ){
		
		printf( System.err, false, format, args );
	}
	public static void rrl( String format, Object ... args ){
		
		log( System.err, true, format, args );
	}
	
	public static void cho(){
		
		echo( System.out, true, "" );
	}
	public static void cho( Object ... os ){
		
		echo( System.out, true, os );
	}
	public static void cho_( Object ... os ){
		
		echo( System.out, false, os );
	}
	public static void chof( String format, Object ... args ){
		
		printf( System.out, true, format, args );
	}
	public static void chof_( String format, Object ... args ){
		
		printf( System.out, false, format, args );
	}
	
	/* === EXIT =========================================================== */
	public static void xit( String message, int code ){
		
		StringBuilder text = new StringBuilder();
		
		text.append( "EXIT: " );
		
		if( message != null ){
			text.append('"').append( message ).append( "\": " );
		}
		
		if( code == 0 ){
			text.append( "OK" );
    		echo( System.out, 3, true, text.toString() );
		} else {
			text.append( code );
    		echo( System.err, 3, true, text.toString() );
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
	
}
