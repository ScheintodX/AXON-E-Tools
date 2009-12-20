package de.axone.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DumpFormatter {
	
	public StringBuilder format( Method m ){
		
		StringBuilder result = new StringBuilder();
		
		result.append( m.getName() );
		
		return result;
	}
	
	public StringBuilder format( Field f ){
		
		StringBuilder result = new StringBuilder();
		
		result.append( f.toString() );
		
		return result;
	}
}
