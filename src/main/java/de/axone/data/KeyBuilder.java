package de.axone.data;


public class KeyBuilder {
	
	private static final char SEP = '~', NUL = 'Ø';
	
	private final StringBuilder parts = new StringBuilder();
	
	private boolean first = true;
	
	private KeyBuilder(){}
	
	public static KeyBuilder begin() {
		return new KeyBuilder();
	}
	
	public KeyBuilder add( String value ) {
		
		if( first ) {
			first = false;
		} else {
			parts.append( SEP );
		}
		
		parts.append( value );
		
		return this;
	}
	public KeyBuilder addNull() {
		return add( NUL );
	}
	
	public KeyBuilder add( boolean value ) {
		return add( value ? "1" : "0" );
	}
	public KeyBuilder add( short value ) {
		return add( Integer.toHexString( value ) ); // should be faster than toString
	}
	public KeyBuilder add( int value ) {
		return add( Integer.toHexString( value ) );
	}
	public KeyBuilder add( long value ) {
		return add( Long.toHexString( value ) );
	}
	public KeyBuilder add( Boolean value ) {
		return value != null ? add( (boolean)value ) : addNull();
	}
	public KeyBuilder add( Short value ) {
		return value != null ? add( (short)value ) : addNull();
	}
	public KeyBuilder add( Integer value ) {
		return value != null ? add( (int)value ) : addNull();
	}
	public KeyBuilder add( Long value ) {
		return value != null ? add( (long)value ) : addNull();
	}
	
	public String key(){
		return parts.toString();
	}
	
}
