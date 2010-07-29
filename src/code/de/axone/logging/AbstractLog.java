package de.axone.logging;

public abstract class AbstractLog implements Log {
	
	@Override
	public void trace( Object ... o ) {
		log( LogLevel.TRACE, o );
	}
	@Override
	public void debug( Object ... o ) {
		log( LogLevel.DEBUG, o );
	}
	@Override
	public void info( Object ... o ) {
		log( LogLevel.INFO, o );
	}
	@Override
	public void warn( Object ... o ) {
		log( LogLevel.WARN, o );
	}
	@Override
	public void error( Object ... o ) {
		log( LogLevel.ERROR, o );
	}
	@Override
	public void fatal( Object ... o ) {
		log( LogLevel.FATAL, o );
	}
	
	@Override
	public boolean isTrace() {
		return isLevel( LogLevel.TRACE );
	}
	@Override
	public boolean isDebug() {
		return isLevel( LogLevel.DEBUG );
	}
	@Override
	public boolean isInfo() {
		return isLevel( LogLevel.INFO );
	}
	@Override
	public boolean isWarn() {
		return isLevel( LogLevel.WARN );
	}
	@Override
	public boolean isError() {
		return isLevel( LogLevel.ERROR );
	}
	@Override
	public boolean isFatal() {
		return isLevel( LogLevel.FATAL );
	}

}
