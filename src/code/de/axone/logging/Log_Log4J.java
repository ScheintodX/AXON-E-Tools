package de.axone.logging;

import org.apache.log4j.Level;

public class Log_Log4J extends AbstractLog {

	private org.apache.log4j.Logger log;
	private static final String FQCN = Log_Log4J.class.getName();

	@SuppressWarnings( "unchecked" )
	Log_Log4J( Class clazz ) {
		log = org.apache.log4j.Logger.getLogger(clazz);
	}

	Log_Log4J( String fqcn ) {
		log = org.apache.log4j.Logger.getLogger(fqcn);
	}

	private void log( Level level, Object ... arguments ) {

		Throwable t = null;
		for( Object o : arguments ) {
			if( o instanceof Throwable ) {
				t = (Throwable) o;
				break;
			}
		}

		log.log(FQCN, level, LogHelper.parse(arguments), t);
	}

	@Override
	public void log( LogLevel level, Object ... o ) {

		switch( level ) {
		case DEBUG:
			log(Level.DEBUG, o);
			break;
		case ERROR:
			log(Level.ERROR, o);
			break;
		case FATAL:
			log(Level.FATAL, o);
			break;
		case INFO:
			log(Level.INFO, o);
			break;
		case TRACE:
			log(Level.TRACE, o);
			break;
		case WARN:
			log(Level.WARN, o);
			break;
		}
	}

	@Override
	public LogLevel getLevel() {

		Level level = log.getEffectiveLevel();

		if( level == Level.DEBUG )
			return LogLevel.DEBUG;
		else if( level == Level.ERROR )
			return LogLevel.ERROR;
		else if( level == Level.FATAL )
			return LogLevel.FATAL;
		else if( level == Level.INFO )
			return LogLevel.INFO;
		else if( level == Level.TRACE )
			return LogLevel.TRACE;
		else if( level == Level.WARN )
			return LogLevel.WARN;

		return null;
	}

	@Override
	public void setLevel( LogLevel level ) {

		switch( level ) {
		case DEBUG:
			log.setLevel(Level.DEBUG);
			break;
		case ERROR:
			log.setLevel(Level.ERROR);
			break;
		case FATAL:
			log.setLevel(Level.FATAL);
			break;
		case INFO:
			log.setLevel(Level.INFO);
			break;
		case TRACE:
			log.setLevel(Level.TRACE);
			break;
		case WARN:
			log.setLevel(Level.WARN);
			break;
		}
	}

	@Override
	public String toString() {

		StringBuilder result = new StringBuilder();

		result.append("Log4J: ").append(log.getName()).append("(Lv:").append(
				log.getLevel()).append(")");

		/*
		 * Enumeration<?> appends = log.getAllAppenders(); while(
		 * appends.hasMoreElements() ){
		 * 
		 * Appender appender = (Appender) appends.nextElement();
		 * 
		 * result .append( ' ' ) .append( appender.getName() ) ; }
		 */

		return result.toString();
	}

	public static class Factory implements LoggingFactory {

		@Override
		public Log makeLog( Class<?> clazz ) {
			return new Log_Log4J(clazz);
		}

		@Override
		public Log makeLog( String className ) {
			return new Log_Log4J(className);
		}

	}

}
