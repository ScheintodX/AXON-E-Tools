package de.axone.tools;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class ObjectAccessor {
	
	private ObjectAccessible a;

	public ObjectAccessor(){}
	public ObjectAccessor( ObjectAccessible accessible ){
		this.a = accessible;
	}
	public void setAccessible( ObjectAccessible accessible ){
		this.a = accessible;
	}
	
	public boolean has( String name ){
		return a.doGet( name ) != null;
	}
	
	// == Object =====
	public Object get( String name ){
		return OA.get( a.doGet( name ), name );
	}
	public Object get( String name, String defaultValue ){
		return OA.get( a.doGet( name ), name, defaultValue );
	}
	public Object getRequired( String name ){
		return OA.getRequired( a.doGet( name ), name );
	}
	public Object getRequired( String name, String defaultValue ){
		return OA.getRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == String =====
	public String getString( String name ){
		return OA.getString( a.doGet( name ), name );
	}
	public long getString( String name, long defaultValue ){
		return OA.getLong( a.doGet( name ), name, defaultValue );
	}
	public long getStringRequired( String name ){
		return OA.getLongRequired( a.doGet( name ), name );
	}
	public long getStringRequired( String name, Long defaultValue ){
		return OA.getLongRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == Integer =====
	public Integer getInteger( String name ){
		return OA.getInteger( a.doGet( name ), name );
	}
	public int getInteger( String name, int defaultValue ){
		return OA.getInteger( a.doGet( name ), name, defaultValue );
	}
	public int getIntegerRequired( String name ){
		return OA.getIntegerRequired( a.doGet( name ), name );
	}
	public int getIntegerRequired( String name, Integer defaultValue ){
		return OA.getIntegerRequired( a.doGet( name ), name, defaultValue );
	}
		// == Long =====
	public Long getLong( String name ){
		return OA.getLong( a.doGet( name ), name );
	}
	public long getLong( String name, long defaultValue ){
		return OA.getLong( a.doGet( name ), name, defaultValue );
	}
	public long getLongRequired( String name ){
		return OA.getLongRequired( a.doGet( name ), name );
	}
	public long getLongRequired( String name, Long defaultValue ){
		return OA.getLongRequired( a.doGet( name ), name, defaultValue );
	}
		// == Double =====
	public  Double getDouble( String name ){
		return OA.getDouble( a.doGet( name ), name );
	}
	public  double getDouble( String name, double defaultValue ){
		return OA.getDouble( a.doGet( name ), name, defaultValue );
	}
	public  double getDoubleRequired( String name ){
		return OA.getDoubleRequired( a.doGet( name ), name );
	}
	public  double getDoubleRequired( String name, Double defaultValue ){
		return OA.getDoubleRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == Boolean =====
	public  Boolean getBoolean( String name ){
		return OA.getBoolean( a.doGet( name ), name );
	}
	public  boolean getBoolean( String name, boolean defaultValue ){
		return OA.getBoolean( a.doGet( name ), name, defaultValue );
	}
	public  boolean getBooleanRequired( String name ){
		return OA.getBooleanRequired( a.doGet( name ), name );
	}
	public  boolean getBooleanRequired( String name, boolean defaultValue ){
		return OA.getBooleanRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == BigDecimal =====
	public  BigDecimal getBigDecimal( String name ){
		return OA.getBigDecimal( a.doGet( name ), name );
	}
	public  BigDecimal getBigDecimal( String name, BigDecimal defaultValue ){
		return OA.getBigDecimal( a.doGet( name ), name, defaultValue );
	}
	public  BigDecimal getBigDecimalRequired( String name ){
		return OA.getBigDecimalRequired( a.doGet( name ), name );
	}
	public  BigDecimal getBigDecimalRequired( String name, BigDecimal defaultValue ){
		return OA.getBigDecimalRequired( a.doGet( name ), name, defaultValue );
	}
	
	// == Enumeration =====
	public <T extends Enum<T>> T getEnum( Class<T> clazz, String name ){
		return OA.getEnum( a.doGet( name ), clazz, name );
	}
	public <T extends Enum<T>> T getEnum( Class<T> clazz, String name, T defaultValue ){
		return OA.getEnum( a.doGet( name ), clazz, name, defaultValue );
	}
	public <T extends Enum<T>> T getEnumRequired( Class<T> clazz, String name ){
		return OA.getEnumRequired( a.doGet( name ), clazz, name );
	}
	
	// == List =====
	public List<?> getList( String name ){
		return OA.getList( a.doGet( name ), name );
	}
	public List<?> getList( String name, List<?> defaultValue ){
		return OA.getList( a.doGet( name ), name, defaultValue );
	}
	public List<?> getListRequired( String name, List<?> defaultValue ){
		return OA.getListRequired( a.doGet( name ), name, defaultValue );
	}
	public List<?> getListRequired( String name ){
		return OA.getListRequired( a.doGet( name ), name );
	}
	
	// == Map =====
	public Map<String,?> getMap( String name ){
		return OA.getMap( a.doGet( name ), name );
	}
	public Map<String,?> getMap( String name, Map<String,?> defaultValue ){
		return OA.getMap( a.doGet( name ), name, defaultValue );
	}
	public Map<String,?> getMapRequired( String name, Map<String,?> defaultValue ){
		return OA.getMapRequired( a.doGet( name ), name, defaultValue );
	}
	public Map<String,?> getMapRequired( String name ){
		return OA.getMapRequired( a.doGet( name ), name );
	}
	
	public interface ObjectAccessible {
		public Object doGet( String name );
	}
	
}
