package de.axone.logging;

public class Log_Stdout implements Log {
	
	private static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.INFO;
	
	private LogLevel level = DEFAULT_LOG_LEVEL;
	private String fqcn;
	
	@SuppressWarnings("unchecked")
	Log_Stdout( Class clazz ){
		fqcn = clazz.getName();
	}
	Log_Stdout( String fqcn ){
		this.fqcn = fqcn;
	}
	
	public void log( LogLevel level, Object ... arguments ){
		
		System.err.print( level + " " + signature() + ": " );
		
		if( arguments.length == 0 ){
			
			System.err.println( "PING" );
		} else {
			
			// Text message. May include variables
			if( arguments[ 0 ] instanceof String ){
				
				System.err.println( LogHelper.parse( arguments ) );
				
			} else if( arguments[ 0 ] instanceof Throwable ) {
				
				Throwable e = (Throwable) arguments[ 0 ];
				e.printStackTrace( System.err );
				
				// Recurse remaining parameter
				if( arguments.length > 1 ){
    				Object[] args = new Object[ arguments.length -1 ];
    				
    				for( int i = 1; i < arguments.length; i++ ){
    					args[ i-1 ] = arguments[ i ];
    				}
    				log( level, args );
				}
				
			// Other. Output objects
			} else {
				
				boolean first = true;
				for( int i = 0; i < arguments.length; i++ ){
					
					if( first ) first = false;
					else System.err.print( ", " );
					
					System.err.print( arguments[ i ] );
				}
				System.err.println();
			}
		}
	}
	
	private String signature(){
		
		Throwable dummy = new Throwable();
		StackTraceElement[] trace = dummy.getStackTrace();
		
		StackTraceElement caller = trace[ 3 ];
		
		return caller.getClassName() + "." + caller.getMethodName() + "(" + caller.getLineNumber() + ")";
	}

	@Override
	public void trace( Object ... arguments ) {
		log( LogLevel.TRACE, arguments );
	}

	@Override
	public void debug( Object ... arguments ) {
		log( LogLevel.DEBUG, arguments );
	}

	@Override
	public void info( Object ... arguments ) {
		log( LogLevel.INFO, arguments );
	}

	@Override
	public void warn( Object ... arguments ) {
		log( LogLevel.WARN, arguments );
	}

	@Override
	public void error( Object ... arguments ) {
		log( LogLevel.ERROR, arguments );
	}

	@Override
	public void fatal( Object ... arguments ) {
		log( LogLevel.FATAL, arguments );
	}
	
	@Override
	public LogLevel getLevel(){
		return level;
	}
	
	@Override
	public void setLevel( LogLevel level ){
		this.level = level;
	}
	
	@Override
	public boolean isDebug() {
		
		if( getLevel().compareTo( LogLevel.DEBUG ) >= 0 )
			return true;
		else
    		return false;
	}
	@Override
	public boolean isError() {
		if( getLevel().compareTo( LogLevel.ERROR ) >= 0 )
			return true;
		else
    		return false;
	}
	@Override
	public boolean isFatal() {
		if( getLevel().compareTo( LogLevel.FATAL ) >= 0 )
			return true;
		else
    		return false;
	}
	@Override
	public boolean isInfo() {
		if( getLevel().compareTo( LogLevel.INFO ) >= 0 )
			return true;
		else
    		return false;
	}
	@Override
	public boolean isTrace() {
		if( getLevel().compareTo( LogLevel.TRACE ) >= 0 )
			return true;
		else
    		return false;
	}
	@Override
	public boolean isWarn() {
		if( getLevel().compareTo( LogLevel.WARN ) >= 0 )
			return true;
		else
    		return false;
	}
	
	@Override
	public String toString(){
		return "CL: " + fqcn + " (Lv: " + getLevel() + ")";
	}
}
