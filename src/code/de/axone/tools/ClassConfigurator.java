package de.axone.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is for creating classes which name and config
 * values are given by parameter.
 * 
 * Format of Parameter-String is:
 * classpath[( parameter1=value1[, ...] )]
 * where values are strings.
 * 
 * Every in this way created class must conform to following
 * conditions:
 * 
 * <ol>
 * <li>Class must have an public parameterless constructor</li>
 * <li>For every parameter parmN there must be a public setter
 * Method with this signature: public void setParamN( String value )</li>
 * 
 * @author flo
 *
 */
public class ClassConfigurator {

	private static final Pattern patternComplete = Pattern.compile( 
				"\\s*([\\w\\.]+)\\s*" + 			// classname
				"(?:\\(\\s*" +						// opt.
					"\\w+=\\w*" +					// first param
					"(?:\\s*,\\s*(\\w+=\\w*))*" +	// other params
				"\\s*\\))?\\s*" );	
		
	private static final Pattern patternParameter = Pattern.compile(
				"(\\w+)=(\\w*)" );
		
	/**
	 * Create a new Instance of the class 
	 * spezified in the description initialized by these parameters
	 * 
	 * @param description
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	public Object create( String description )
	throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
	       SecurityException, NoSuchMethodException, IllegalArgumentException, 
	       InvocationTargetException {
		
		Matcher matcherComplete = patternComplete.matcher( description );
		
		String className = null;
		Map<String, String> parameters = new HashMap<String, String>();
		
		if( matcherComplete.matches() ){
			
			className = matcherComplete.group( 1 );
			
			Matcher matcherParameter = patternParameter.matcher( description );
			
			while( matcherParameter.find() ){
				
				String name =  matcherParameter.group( 1 );
				String value = matcherParameter.group( 2 );
				
				parameters.put( name, value );
			}
    	}
		
		Class clazz = Class.forName(className);
		
		Object instance = clazz.newInstance();
		
		for( String parameter : parameters.keySet() ){
			
			String methodName = methodize( parameter );
			
			Method method = clazz.getMethod(methodName, String.class);
			
			method.invoke( instance, parameters.get( parameter ) );
		}
		
		return instance;
	}
	
	/*
	 * Make setXxx out of xxx
	 */
	private String methodize( String name ){
		
		if( name.length() == 1 ){
			return "set" + name.toUpperCase();
		} else {
			return "set" + name.substring(0,1).toUpperCase() + name.substring(1);
		}
	}
}
