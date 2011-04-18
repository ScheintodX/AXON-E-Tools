package de.axone.tools;


public class StringAccessor {
	
	private StringAccessible a;

	public StringAccessor(){}
	public StringAccessor( StringAccessible accessible ){
		this.a = accessible;
	}
	public void setAccessible( StringAccessible accessible ){
		this.a = accessible;
	}
	
	// == String =====
	public String get( String name ){
		return SA.get( a.doGet( name ), name );
	}
	public String get( String name, String defaultValue ){
		return SA.get( a.doGet( name ), name, defaultValue );
	}
	public String getRequired( String name ){
		return SA.getRequired( a.doGet( name ), name );
	}
	public String getRequired( String name, String defaultValue ){
		return SA.getRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == Long =====
	public Long getLong( String name ){
		return SA.getLong( a.doGet( name ), name );
	}
	public long getLong( String name, long defaultValue ){
		return SA.getLong( a.doGet( name ), name, defaultValue );
	}
	public long getLongRequired( String name ){
		return SA.getLongRequired( a.doGet( name ), name );
	}
	public long getLongRequired( String name, Long defaultValue ){
		return SA.getLongRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == Enumeration =====
	public <T extends Enum<T>> T getEnum( Class<T> clazz, String name ){
		return SA.getEnum( a.doGet( name ), clazz, name );
	}
	public <T extends Enum<T>> T getEnum( Class<T> clazz, String name, T defaultValue ){
		return SA.getEnum( a.doGet( name ), clazz, name, defaultValue );
	}
	public <T extends Enum<T>> T getEnumRequired( Class<T> clazz, String name ){
		return SA.getEnumRequired( a.doGet( name ), clazz, name );
	}
	
	public interface StringAccessible {
		public String doGet( String name );
	}
}
