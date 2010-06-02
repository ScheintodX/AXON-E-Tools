package de.axone.logging;

public abstract class AbstractLog implements Log {
	
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
	public boolean isDebug() {
		return isLevel( LogLevel.DEBUG );
	}
	@Override
	public boolean isError() {
		return isLevel( LogLevel.ERROR );
	}
	@Override
	public boolean isFatal() {
		return isLevel( LogLevel.FATAL );
	}
	@Override
	public boolean isInfo() {
		return isLevel( LogLevel.INFO );
	}
	@Override
	public boolean isTrace() {
		return isLevel( LogLevel.TRACE );
	}
	@Override
	public boolean isWarn() {
		return isLevel( LogLevel.WARN );
	}

}
