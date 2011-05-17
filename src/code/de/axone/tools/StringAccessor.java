package de.axone.tools;

import java.math.BigDecimal;


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
		// == Double =====
	public  Double getDouble( String name ){
		return SA.getDouble( a.doGet( name ), name );
	}
	public  double getDouble( String name, double defaultValue ){
		return SA.getDouble( a.doGet( name ), name, defaultValue );
	}
	public  double getDoubleRequired( String name ){
		return SA.getDoubleRequired( a.doGet( name ), name );
	}
	public  double getDoubleRequired( String name, Double defaultValue ){
		return SA.getDoubleRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == Boolean =====
	public  Boolean getBoolean( String name ){
		return SA.getBoolean( a.doGet( name ), name );
	}
	public  boolean getBoolean( String name, boolean defaultValue ){
		return SA.getBoolean( a.doGet( name ), name, defaultValue );
	}
	public  boolean getBooleanRequired( String name ){
		return SA.getBooleanRequired( a.doGet( name ), name );
	}
	public  boolean getBooleanRequired( String name, boolean defaultValue ){
		return SA.getBooleanRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == BigDecimal =====
	public  BigDecimal getBigDecimal( String name ){
		return SA.getBigDecimal( a.doGet( name ), name );
	}
	public  BigDecimal getBigDecimal( String name, BigDecimal defaultValue ){
		return SA.getBigDecimal( a.doGet( name ), name, defaultValue );
	}
	public  BigDecimal getBigDecimalRequired( String name ){
		return SA.getBigDecimalRequired( a.doGet( name ), name );
	}
	public  BigDecimal getBigDecimalRequired( String name, BigDecimal defaultValue ){
		return SA.getBigDecimalRequired( a.doGet( name ), name, defaultValue );
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
