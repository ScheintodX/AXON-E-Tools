package de.axone.exception;

import java.util.Arrays;
import java.util.regex.Pattern;



public abstract class Ex {

	public static <T extends Throwable> T up( T e ){
		return up( e, 1 );
	}
	
	public static <T extends Throwable> T up( T e, int steps ){
		
		// This kills in combination with assert some debugging info.
		// Don't know why
		StackTraceElement [] trace = e.getStackTrace();
		
		e.setStackTrace( Arrays.copyOfRange( trace, steps, trace.length ) );
		
		return e;
	}
	
	public static String me(){
		
		return me( new Throwable(), 1 );
	}
	
	public static String me( int depth ){
		
		return me( new Throwable(), depth+1 );
		
	}
	
	public static String me( Throwable e, int depth ){
		
		StackTraceElement[] elm = e.getStackTrace();
		if( depth >= elm.length ) depth = elm.length-1;
		
		String clazz = simplifyClassName( elm[depth].getClassName() );
		clazz = removeNestedClasses( clazz );
		int line = elm[depth].getLineNumber();
		
		return clazz + ".java:" + line;
	}
	
	public static String me( Throwable e, int start, int depth ){
		
		StringBuilder result = new StringBuilder();
		
		for( int i=start; i<depth+start; i++ ){
			
			result.append( " < " )
				.append( '(' ).append( Ex.me( i ) ).append( ')' );
			
		}
		
		return result.toString();
	}
	
	/* === HELPER === */

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

}
