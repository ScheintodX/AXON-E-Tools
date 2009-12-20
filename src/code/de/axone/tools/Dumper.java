package de.axone.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class Dumper {
	
	private DumpFormatter formatter = new DumpFormatter();
	
	public String dump( Object o ){
		
		return dump( o, true );
	}
	
	@SuppressWarnings("unchecked")
	public String dump( Object o, boolean recurse ){
		
		StringBuilder result = new StringBuilder();
		
		if( o == null ){
			
			return "NULL";
			
		} else  if( o instanceof Class ){
			
			return "";
			
		} else if( o instanceof String ){
			
			return "\"" + o.toString() + "\"";
			
		} else if( o instanceof Number ){
			
			return o.toString();
		
		} else if( o instanceof Enumeration ){
			
			Enumeration enu = (Enumeration) o;
			
			result.append( '[' );
			
			boolean first = true;
			while( enu.hasMoreElements() ){
				
				if( first ) first=false; else result.append( ", " );
				
				result.append( dump( enu.nextElement(), true ) );
			}
			result.append( ']' );
			
			return result.toString();
			
		} else {
			
			if( ! recurse ) return "";
			
			result.append( o.getClass().getName() );
			result.append( "\n-----------------------\n" );
		
			Class c = o.getClass();
			
			result.append( "Methods\n" );
			
			for( Method m : c.getMethods() ){
				
				result.append( formatter.format( m ) );
				
				Class[] p = m.getParameterTypes();
				
				if( p.length == 0 && m.getName().matches( "^get.*" ) ){
					
					try {
						Object res = m.invoke( o, (Object[])null );
						result.append( ": " ).append( dump( res, false ) );
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				
				result.append( '\n' );
			}
			
			return result.toString();
		}
	}
}
