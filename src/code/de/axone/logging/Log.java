package de.axone.logging;


public interface Log {

	public void trace( Object ... o );
	public void debug( Object ... o );
	public void info( Object ... o );
	public void warn( Object ... o );
	public void error( Object ... o );
	public void fatal( Object ... o );
	
	public void log( LogLevel level, Object ... o );
	
	public LogLevel getLevel();
	public void setLevel( LogLevel level );
	public boolean isLevel( LogLevel level );
	
	public boolean isTrace();
	public boolean isDebug();
	public boolean isInfo();
	public boolean isWarn();
	public boolean isError();
	public boolean isFatal();
}
