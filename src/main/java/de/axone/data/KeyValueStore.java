package de.axone.data;


public interface KeyValueStore<K,V,EX extends Exception> {

	/**
	 * @return value for key
	 *
	 * May throw exception if not available
	 *
	 * @param key
	 * @throws EX
	 */
	public V access( K key ) throws EX;

	/**
	 * @return value for key
	 *
	 * Does never throw an exception
	 *
	 * @param key
	 */
	public V accessChecked( K key );


	/**
	 * @return an exception for this key which was not found
	 *
	 * @param key
	 */
	public EX exception( K key );

	/**
	 * @param key
	 *
	 * @return true if key is available
	 */
	default public boolean checkAccess( K key ) {
		return accessChecked( key ) != null;
	}

}
