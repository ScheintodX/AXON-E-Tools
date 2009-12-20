package de.axone.tools;

import java.io.PrintStream;

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
	
	private static void echo( PrintStream out, boolean nl, Object ... os ){
		echo( out, 3, nl, os );
	}
	
	private static void echo( PrintStream out, int depth, boolean nl, Object ... os ){
		
		Exception e = new Exception();
		StackTraceElement[] elm = e.getStackTrace();
		
		if( os.length > 0 ){
    		for( Object o : os ){
    			String clazz = simplifyClassName( elm[depth].getClassName() );
    			int line = elm[depth].getLineNumber();
        		out.print( ">>> " + clazz + "(" + line + "): " + o );
        		if( nl ) out.println();
        		out.flush();
    		}
		} else {
			out.println();
		}
	}
	
	public static void rr( Object ... os ){
		
		echo( System.err, true, os );
	}
	
	public static void rr_( Object ... os ){
		
		echo( System.err, false, os );
	}
	
	public static void cho( Object ... os ){
		
		echo( System.out, true, os );
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
}
