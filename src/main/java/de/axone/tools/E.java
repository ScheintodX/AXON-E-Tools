package de.axone.tools;

import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
	
	@SafeVarargs
	private static <T> T pick( T ... os ) {
		if( os == null || os.length == 0 ) return null;
		return os[ 0 ];
	}
	
	@SafeVarargs
	public static <T> T echo( PrintStream out, int depth, boolean lino, boolean nl, boolean threadName, T ... os ){
		
		if( threadName ) printThreadName( out );
		
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
		
		return pick( os );
	}
	
	@SafeVarargs
	private static <T> T printf( PrintStream out, boolean lino, boolean nl, String format, T ... args ){
		
		if( lino ) printPos( out, 2 );
		
		out.printf( format, args );
		if( nl ) out.println();
		
		return pick( args );
	}
	
	@SafeVarargs
	private static <T> T log( PrintStream out, boolean lino, boolean nl, String format, T ...args ){
		
		if( lino ) printPos( out, 2 );
		
		for( Object arg : args ){
			
			format = format.replaceFirst( "\\{\\}", F.ormat( arg ) );
		}
		out.print( format );
		
		if( nl ) out.println();
		
		return pick( args );
	}
	
	@SafeVarargs
	private static <T> T echo( PrintStream out, boolean lino, boolean nl, boolean threadName, T ... os ){
		return echo( out, 3, lino, nl, threadName, os );
	}
	@SafeVarargs
	private static <T> T echoup( int steps, PrintStream out, boolean lino, boolean nl, boolean threadName, T ... os ){
		return echo( out, 3+steps, lino, nl, threadName, os );
	}
	
	public static String banner( String text ){
		return echo( System.out, 2, true, true, false, Text.banner( text.charAt( 0 ), text ) );
	}
	
	public static String banner( char border, String text ){
		return echo( System.out, 2, true, true, false, Text.banner( border, text ) );
	}
	
	public static String  poster( String text ){
		return echo( System.out, 2, true, true, false, Text.poster( text.charAt( 0 ), text ) );
	}
	
	public static String poster( char border, String text ){
		return echo( System.out, 2, true, true, false, Text.poster( border, text ) );
	}
	
	/**
	 * Output file and line number and some text to STDERR
	 */
	public static void rr(){
		echo( System.err, true, true, false, "" );
	}
	
	public static byte[] rr( byte [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static char[] rr( char [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static short[] rr( short [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static int[] rr( int [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static long[] rr( long [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static float[] rr( float [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static double[] rr( double [] a ){
		echo( System.err, true, true, false, (Object[])A.objects( a ) );
		return a;
	}
	
	public static boolean[] rr( boolean [] a ){
		echo( System.err, true, true, false, a );
		return a;
	}
	@SafeVarargs
	public static <T> T rr( T ... os ){
		
		return echo( System.err, true, true, false, os );
	}
	@SafeVarargs
	public static <T> T rrup( int steps, T ... os ){
		
		return echoup( steps, System.err, true, true, false, os );
	}
	@SafeVarargs
	public static <T> T rr_( T ... os ){
		
		return echo( System.err, true, false, false, os );
	}
	@SafeVarargs
	public static <T> T rr( IS_DEBUG check, T ... os ) {
		String debug = check.isDebug();
		if( debug != null )
				echo( System.err, 2, true, true, false, A.unionO( debug, os ) );
		return pick( os );
	}
	/*
	public static <T> void rrm( Formatter<T> formatter, Object ... os ) {
		echo( System.err, true, false, false, formatter, os );
	}
	*/
	@SafeVarargs
	public static <T> T rrf( String format, T ... args ){
		return printf( System.err, true, true, format, args );
	}
	@SafeVarargs
	public static <T> T rrf_( String format, T ... args ){
		return printf( System.err, true, false, format, args );
	}
	@SafeVarargs
	public static <T> T _rrf( String format, T ... args ){
		return printf( System.err, false, true, format, args );
	}
	@SafeVarargs
	public static <T> T _rrf_( String format, T ... args ){
		return printf( System.err, false, false, format, args );
	}
	@SafeVarargs
	public static <T> T rrl( String format, T ... args ){
		return log( System.err, true, true, format, args );
	}
	@SafeVarargs
	public static <T> T _rrl( String format, T ... args ){
		return log( System.err, false, true, format, args );
	}
	@SafeVarargs
	public static <T> T rrl_( String format, T ... args ){
		return log( System.err, true, false, format, args );
	}
	@SafeVarargs
	public static <T> T _rrl_( String format, T ... args ){
		return log( System.err, false, false, format, args );
	}
	
	@SafeVarargs
	public synchronized static <T> T rrt( T ... os ){
		return echo( System.err, true, true, true, os );
	}
	@SafeVarargs
	public synchronized static <T> T _rrt( T ... os ){
		return echo( System.err, false, true, true, os );
	}
	/**
	 * Output file and line number and some text to STDOUT
	 */
	public static void cho(){
		
		echo( System.out, true, true, false, "" );
	}
	@SafeVarargs
	public static <T> T cho( T ... os ){
		return echo( System.out, true, true, false, os );
	}
	@SafeVarargs
	public static <T> T cho_( T ... os ){
		return echo( System.out, true, false, false, os );
	}
	@SafeVarargs
	public static <T> T _cho_( T ... os ){
		return echo( System.out, false, false, false, os );
	}
	@SafeVarargs
	public static <T> T _cho( T ... os ){
		return echo( System.out, false, true, false, os );
	}
	@SafeVarargs
	public static <T> T chof( String format, T ... args ){
		return printf( System.out, true, true, format, args );
	}
	@SafeVarargs
	public static <T> T chof_( String format, T ... args ){
		return printf( System.out, true, false, format, args );
	}
	@SafeVarargs
	public static <T> T _chof( String format, T ... args ){
		return printf( System.out, false, true, format, args );
	}
	@SafeVarargs
	public static <T> T _chof_( String format, T ... args ){
		return printf( System.out, false, false, format, args );
	}
	
	/* === Threads =========================================================== */
	synchronized public static String t( String message ) {
		
		return echo( System.err, true, true, true, message );
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
	public static <T> T x( T message ){
		return ex( System.err, new Throwable(), EX_UP, EX_DEFAULT_DEPTH, message );
	}
	public static void x( int depth ){
		ex( System.err, new Throwable(), EX_UP, depth, EX_MARK );
	}
	public static <T> T x( int depth, T message ){
		return ex( System.err, new Throwable(), EX_UP, depth, message );
	}
	/* for testing */
	static void _x( int depth, String message ){
		ex( System.out, new Throwable(), EX_UP, depth, message );
	}
	private static <T> T ex( PrintStream out, Throwable t, int start, int depth, T message ){
		
		StringBuilder result = new StringBuilder( depth*16 );
		
		result.append( "[" ).append( message.toString() ).append( "]" );
		
		result.append( Ex.me( t, start, depth ) );
		
		echo( out, start-1, true, true, false, result.toString() );
		
		return message;
	}
	public static Throwable x( int depth, Throwable t ){
		ex( System.err, t, EX_UP-1, depth, t.getMessage() );
		return t;
	}
	public static Throwable x( Throwable t ){
		ex( System.err, t, EX_UP-1, EX_DEFAULT_DEPTH, t.getMessage() );
		return t;
	}
	
	
	public static <T> Consumer<T> peeker( String name ){
		return what -> echo( System.err, 12, true, true, false, name, what );
	}
	
	public static <T> Runnable freelancer( String name, Supplier<T> whatToPrint ) {
		return () -> echo( System.err, 12, true, true, false, name, whatToPrint.get() );
	}
	
	
	//private static String 
	
	public static byte[] rrx( byte [] bytes ) {
		echo( System.err, true, true, false, HEX.toString( bytes ) );
		return bytes;
	}
	public static byte rrx( byte value ) {
		echo( System.err, true, true, false, HEX.toString( value ) );
		return value;
	}
	public static short rrx( short value ) {
		echo( System.err, true, true, false, HEX.toString( value ) );
		return value;
	}
	public static int rrx( int value ) {
		echo( System.err, true, true, false, HEX.toString( value ) );
		return value;
	}
	public static long rrx( long value ) {
		echo( System.err, true, true, false, HEX.toString( value ) );
		return value;
	}
	
	public static byte[] chox( byte [] bytes ) {
		echo( System.out, true, true, false, HEX.toString( bytes ) );
		return bytes;
	}
	public static byte chox( byte value ) {
		echo( System.out, true, true, false, HEX.toString( value ) );
		return value;
	}
	public static short chox( short value ) {
		echo( System.out, true, true, false, HEX.toString( value ) );
		return value;
	}
	public static int chox( int value ) {
		echo( System.out, true, true, false, HEX.toString( value ) );
		return value;
	}
	public static long chox( long value ) {
		echo( System.out, true, true, false, HEX.toString( value ) );
		return value;
	}
	
	@FunctionalInterface
	public interface IS_DEBUG {
		String isDebug();
	}
}
