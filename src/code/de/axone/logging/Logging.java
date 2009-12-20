package de.axone.logging;

import java.util.Enumeration;

import org.apache.log4j.LogManager;

public abstract class Logging {
	
	private static enum LogSystem { LOG4J, CONSOLE; }
	private static LogSystem current = LogSystem.LOG4J;
	
	public static Log getLog( String name ){
		
		Log log;
		switch( current ){
		case LOG4J:
			log = new Log_Log4J( name );
			break;
		case CONSOLE:
    		log = new Log_Stdout( name );
    		log.setLevel( LogLevel.TRACE );
			break;
		default:
			log = null;
		}
		
		return log;
	}
	
	@SuppressWarnings("unchecked")
	public static Log getLog( Class clazz ){
		
		Log log;
		switch( current ){
		case LOG4J:
			log = new Log_Log4J( clazz );
			break;
		case CONSOLE:
    		log = new Log_Stdout( clazz );
    		log.setLevel( LogLevel.TRACE );
			break;
		default:
			log = null;
		}
		
		return log;
	}
	
	public static String info(){
		
		StringBuilder result = new StringBuilder();
		
		switch( current ){
		case LOG4J:
			
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
    		
			break;
		case CONSOLE:
			break;
		}
		
		return result.toString();
	}
	
}
