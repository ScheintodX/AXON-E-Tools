package de.axone.logging;

import java.util.Enumeration;

import org.apache.log4j.LogManager;

public abstract class Logging {
	
	private static boolean suspended=false;
	
	private static LoggingFactory factory = new Log_Log4J.Factory();
	//private static LoggingFactory factory = new Log_Stdout.Factory();
	
	public static void setFactory( LoggingFactory factory ){
		
		Logging.factory = factory;
	}
	
	public static Log getLog( String name ){
		
		return factory.makeLog( name );
	}
	
	public static Log getLog( Class<?> clazz ){
		
		return factory.makeLog( clazz );
	}
	
	public static String info(){
		
		StringBuilder result = new StringBuilder();
		
		if( factory instanceof Log_Log4J.Factory ){
			
    		Enumeration<?> cur = LogManager.getCurrentLoggers();
    		while( cur.hasMoreElements() ){
    			org.apache.log4j.Logger logger = (org.apache.log4j.Logger) cur.nextElement() ;
    			result
            			.append( '\n' )
            			.append( "Log4J Logger: " )
            			.append( logger.getName() )
            			.append( " has Level " )
            			.append( logger.getEffectiveLevel() )
    			;
    		}
    		
		} else {
			result
				.append( "Factory: " )
				.append( factory.getClass() )
			;
		}
		
		return result.toString();
	}
	
	public static void shutup(){
		suspended=true;
	}
	public static void resume(){
		suspended=false;
	}
	public static boolean isSuspended(){
		return suspended;
	}
}
