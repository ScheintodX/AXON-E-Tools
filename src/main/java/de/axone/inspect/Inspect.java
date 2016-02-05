package de.axone.inspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import de.axone.tools.Str;

public abstract class Inspect {
	
	public static String pretty( Class<?> clz ) {
		
		StringBuilder result = new StringBuilder();
		
		String myName = clz.getCanonicalName();
		
		result.append( myName ).append( '\n' );
		
		for( Method m : clz.getDeclaredMethods() ) {
			
			result.append( '\t' )
					.append( pretty( m ) )
					.append( '\n' )
					;
		}
		
		return result.toString();
	}
	
	public static String pretty( Method m ) {
		
		Parameter [] ps = m.getParameters();
		
		Help help = findHelp( m );
		
		return ""
				+ m.getName()
				+ ( ps.length > 0 ? "( " + Str.join( (p,i) -> pretty( p ), ps ) + " )" : "()" )
				+ " : " + m.getReturnType().getSimpleName() 
				+ ( help != null ? "\n\t\t" + help.value() : "" )
				;
	}
	
	public static String pretty( Parameter p ) {
		
		return p.getType().getSimpleName();
	}
	
	private static final Help findHelp( Method m ) {
		
		Help help = m.getAnnotation( Help.class );
		
		// Search for help but only one level deep
		if( help == null ) {
			
			for( Class<?> iface: m.getDeclaringClass().getInterfaces() ) {
				
				try {
					Method m1 = iface.getMethod( m.getName(), m.getParameterTypes() );
					
					help = m1.getAnnotation( Help.class );
				
				} catch( NoSuchMethodException | SecurityException e ) { continue; }
				
				if( help != null ) break;
			}
		}
		
		return help;
	}
}
