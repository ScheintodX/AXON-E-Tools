package de.axone.logging;

public class Log_Stdout extends AbstractLog {

	private static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.INFO;

	private LogLevel level = LogLevel.INFO;

	@Override
	public LogLevel getLevel() {
		return level;
	}
	
	@Override
	public void setLevel( LogLevel level ) {
		this.level = level;
		
	}
	@Override
	public boolean isLevel( LogLevel level ){
		return getLevel().ordinal() >= level.ordinal();
	}

	private String fqcn;

	@SuppressWarnings( "unchecked" )
	Log_Stdout( Class clazz ) {
		this(clazz.getName());
	}

	Log_Stdout( String fqcn ) {
		this.fqcn = fqcn;
		setLevel(DEFAULT_LOG_LEVEL);
	}

	public void log( LogLevel level, Object ... arguments ) {
		
		if( Logging.isSuspended() ) return;

		System.err.print(level + " " + signature() + ": ");

		if( arguments.length == 0 ) {

			System.err.println("PING");
		} else {

			// Text message. May include variables
			if( arguments[ 0 ] instanceof String ) {

				System.err.println(LogHelper.parse(arguments));

			} else if( arguments[ 0 ] instanceof Throwable ) {

				Throwable e = (Throwable) arguments[ 0 ];
				e.printStackTrace(System.err);

				// Recurse remaining parameter
				if( arguments.length > 1 ) {
					Object[] args = new Object[arguments.length - 1];

					for( int i = 1; i < arguments.length; i++ ) {
						args[ i - 1 ] = arguments[ i ];
					}
					log(level, args);
				}

				// Other. Output objects
			} else {

				boolean first = true;
				for( int i = 0; i < arguments.length; i++ ) {

					if( first )
						first = false;
					else
						System.err.print(", ");

					System.err.print(arguments[ i ]);
				}
				System.err.println();
			}
		}
	}

	private String signature() {

		Throwable dummy = new Throwable();
		StackTraceElement[] trace = dummy.getStackTrace();

		StackTraceElement caller = trace[ 3 ];

		return caller.getClassName() + "." + caller.getMethodName() + "("
				+ caller.getLineNumber() + ")";
	}

	@Override
	public String toString() {
		return "CL: " + fqcn + " (Lv: " + getLevel() + ")";
	}

	public static class Factory implements LoggingFactory {

		@Override
		public Log makeLog( Class<?> clazz ) {
			return new Log_Stdout(clazz);
		}

		@Override
		public Log makeLog( String className ) {
			return new Log_Stdout(className);
		}

	}

}
