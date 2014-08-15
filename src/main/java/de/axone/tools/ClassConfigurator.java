package de.axone.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is for creating classes which name and config values are given by
 * parameter.
 * 
 * Format of Parameter-String is: classpath[( parameter1=value1[, ...] )] where
 * values are strings.
 * 
 * Every class created in this way must conform to following conditions:
 * 
 * <ol>
 * <li>Class must have an public parameterless constructor</li>
 * <li>For every parameter parmN there must be a public setter Method with this
 * signature: public void setParamN( String value )</li>
 * 
 * @author flo
 * 
 */
public abstract class ClassConfigurator {

	private static final String WS = "\\s*";
	private static final String STRING = "[\\w\\.]+"; // word or dot
	private static final String NAME_ARG = "(" + STRING + ")";

	private static final String DEL = "'";
	private static final String P_NAME = "\\w+";
	private static final String P_VALUE = "[^']*";
	private static final String P_NV_ARG = 
			"(" + P_NAME + ")" +WS+ "=" +WS+ DEL + "(" + P_VALUE + ")" + DEL;

	private static final String COMPLETE = NAME_ARG + // classname
			WS+"(?:\\("+WS + // opt.
			P_NV_ARG + // first param
			"(?:"+WS+","+WS+"(" + P_NV_ARG + "))*"+WS+"\\))?";// otherparams

	private static final String PARAMETER = P_NV_ARG;

	private static final Pattern PATTERN_COMPLETE = Pattern.compile(COMPLETE);
	private static final Pattern PATTERN_PARAMETER = Pattern.compile(PARAMETER);
	
	/**
	 * Create a new Instance of the class specified in the description
	 * initialized by these parameters.
	 * 
	 * The constuctor to call is found via the given construcotorparameters
	 * which then are used to construct the object.
	 * 
	 * @param description
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @return the created object
	 */
	public static Object create(String description, Class<?> [] constructorClasses, Object [] constructorParameters ) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException {

		description = description.trim();
		
		Matcher matcherComplete = PATTERN_COMPLETE.matcher(description);

		String className = null;
		Map<String, String> parameters = new HashMap<String, String>();

		if (matcherComplete.matches()) {

			className = matcherComplete.group(1);

			Matcher matcherParameter = PATTERN_PARAMETER.matcher(description);
			
			while( matcherParameter.find() ){

				String name = matcherParameter.group(1);
				String value = matcherParameter.group(2);
				parameters.put(name, value);
			}
		} else {
			throw new IllegalArgumentException("Definition doesn't comply");
		}

		Class<?> clazz = Class.forName(className);

		Object instance = construct( clazz, constructorClasses, constructorParameters );
		
		configure( instance, parameters );
		
		return instance;
	}
	
	public static Object create(String description ) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			InvocationTargetException {
		
		return create( description, null, null );
	}
	
	public static <T> T construct( Class<T> clazz, Class<?> [] constructorClasses, Object [] constructorParameters )
			throws InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException{
		
		Constructor<T> constructor = clazz.getConstructor( constructorClasses );
		
		T instance = constructor.newInstance( constructorParameters );
		
		//T instance = clazz.newInstance();
		
		return instance;
	}
	
	public static void configure( Object object, Map<String,String> parameters )
	throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		for( Map.Entry<String,String> entry : parameters.entrySet() ){
			String parameter = entry.getKey();

			String methodName = methodize(parameter);

			Method method = object.getClass().getMethod( methodName, String.class );

			method.invoke( object, entry.getValue() );
		}
	}

	/*
	 * Make setXxx out of xxx
	 */
	private static String methodize(String name) {

		if (name.length() == 1) {
			return "set" + name.toUpperCase();
		} else {
			return "set" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
		}
	}
}
