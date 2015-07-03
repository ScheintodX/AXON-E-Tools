package de.axone.tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class Stack {

	public static String toString( StackTraceElement ste ) {
		
		String asString = ste.getClassName() + "(" + ste.getLineNumber() + ")";
		
		List<String> asArray = Str.splitFastToList( asString, '.' );
		
		asArray = asArray.subList( 2, asArray.size() );
		
		return Str.join( ".", asArray );
	}
	
	public static List<StackTraceElement> trace() {
		
		StackTraceElement [] trace = (new Throwable()).getStackTrace();
		
		// cut 1 for call of 'trace()'
		return Arrays.asList( trace ).subList( 1, trace.length );
	}
	
	public static <OUT extends Appendable> OUT print( OUT out, Throwable t ) throws IOException {
		
		return print( out, t, 1 );
	}
	
	public static <OUT extends Appendable> OUT print( OUT out, Throwable t, int stepback ) throws IOException {
		
		return print( out, t, stepback+1, 1000 );
	}
	
	public static <OUT extends Appendable> OUT print( OUT out, Throwable t, int stepback, int length ) throws IOException {
		
		stepback+=2;
		
		StackTraceElement [] stes = t.getStackTrace();
		
		stepback = Math.min( stepback, stes.length-1 );
		int end = Math.min( stepback + length, stes.length );
		
		for( int i=stepback; i<end; i++ ){
			
			if( i > stepback ) out.append( " / " );
			out.append( toString( stes[ i ] ) );
		}
		
		return out;
	}
	
	
	public static String print() {
		return print( 1 );
	}
	public static String print( int stepback ) {
		return print( stepback+1, 1000 );
	}
	public static String print( int stepback, int length ) {
		
		try {
			return print( new StringBuilder(), new Throwable(), stepback+1, length ).toString();
		} catch( IOException e ) {
			throw new Error( "Error writing to StringBuilder" );
		}
	}
	
	
	public static String print( Appendable out ) {
		return print( out, 1 );
	}
	public static String print( Appendable out, int stepback ) {
		return print( out, stepback+1, 1000 );
	}
	public static String print( Appendable out, int stepback, int length ) {
		
		try {
			return print( out, new Throwable(), stepback+1, length ).toString();
		} catch( IOException e ) {
			throw new Error( "Error writing to StringBuilder" );
		}
	}
	
	
	public static String print( Throwable t ) {
		return print( t, 1 );
	}
	public static String print( Throwable t, int stepback ) {
		return print( t, stepback+1, 1000 );
	}
	public static String print( Throwable t, int stepback, int length ) {
		
		try {
			return print( new StringBuilder(), t, stepback+1, length ).toString();
		} catch( IOException e ) {
			throw new Error( "Error writing to StringBuilder" );
		}
	}
	
}
