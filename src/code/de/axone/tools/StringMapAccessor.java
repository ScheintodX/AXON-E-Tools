package de.axone.tools;

import java.math.BigDecimal;

/**
 * Helper class for converting String values from Maps/Lists 
 * to other types.
 * 
 * There are accessor Methods available for different types and
 * different conditions: default value or not.
 * 
 * @author flo
 *
 * @param <K> Key type for Map/List-Access
 */
public abstract class StringMapAccessor<K> {
	
	/**
	 * Implement the direct to the Map/List via key
	 * 
	 * @param key
	 * @return
	 */
	protected abstract String doGet( K key );
	
	/**
	 * Create info. This is used for error messages
	 * when required value is not available.
	 * 
	 * @param key
	 * @return
	 */
	protected abstract String info( K key );

	// --String---
	public String getString( K key ) {
		return SA.get( doGet( key ), info( key ) );
	}

	public String getString( K key, String defaultValue ) {
		return SA.get( doGet( key ), info( key ), defaultValue );
	}

	public String getStringRequired( K key ) {
		return SA.getRequired( doGet( key ), info( key ) );
	}

	// --Integer---
	public Integer getInteger( K key ) {
		return SA.getInteger( doGet( key ), info( key ) );
	}

	public int getInteger( K key, int defaultValue ) {
		return SA.getInteger( doGet( key ), info( key ), defaultValue );
	}

	public int getIntegerRequired( K key ) {
		return SA.getIntegerRequired( doGet( key ), info( key ) );
	}

	// --Long---
	public Long getLong( K key ) {
		return SA.getLong( doGet( key ), info( key ) );
	}

	public long getLong( K key, long defaultValue ) {
		return SA.getLong( doGet( key ), info( key ), defaultValue );
	}

	public long getLongRequired( K key ) {
		return SA.getLong( doGet( key ), info( key ) );
	}

	// --Double---
	public Double getDouble( K key ) {
		return SA.getDouble( doGet( key ), info( key ) );
	}

	public double getDouble( K key, double defaultValue ) {
		return SA.getDouble( doGet( key ), info( key ), defaultValue );
	}

	public double getDoubleRequired( K key ) {
		return SA.getDouble( doGet( key ), info( key ) );
	}

	// --Boolean---
	public Boolean getBoolean( K key ) {
		return SA.getBoolean( doGet( key ), info( key ) );
	}

	public boolean getBoolean( K key, boolean defaultValue ) {
		return SA.getBoolean( doGet( key ), info( key ), defaultValue );
	}

	public boolean getBooleanRequired( K key ) {
		return SA.getBoolean( doGet( key ), info( key ) );
	}

	// --BigDecimal---
	public BigDecimal getBigDecimal( K key ) {
		return SA.getBigDecimal( doGet( key ), info( key ) );
	}

	public BigDecimal getBigDecimal( K key, BigDecimal defaultValue ) {
		return SA.getBigDecimal( doGet( key ), info( key ), defaultValue );
	}

	public BigDecimal getBigDecimalRequired( K key ) {
		return SA.getBigDecimal( doGet( key ), info( key ) );
	}

}
