package de.axone.data;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import de.axone.tools.F;
import de.axone.tools.Str;
import de.axone.tools.StringValueAccessor;

public class AccessibleLine<T extends Enum<T>> implements StringValueAccessor<T> {
	
	private final String[] values;
	private long filePos;
	private int originalLength;
	
	@Override
	public String access( T key ) {
		return values[ key.ordinal() ];
	}
	
	public AccessibleLine( String [] values ) {
		this.values = values;
	}
	public AccessibleLine( String line, char fs ) {
		this( Str.splitFast( line, fs ) );
	}
	public AccessibleLine( List<String> values ) {
		this.values = values.toArray( new String[ values.size() ] );
	}
	
	public AccessibleLine<T> atPos( long pos ) {
		this.filePos = pos;
		return this;
	}
	public long filePos() {
		return filePos;
	}
	public AccessibleLine<T> withOriginalLength( int originalLength ) {
		this.originalLength = originalLength;
		return this;
	}
	public int originalLength() {
		return originalLength;
	}
	
	public boolean isEmpty() {
		return Str.isEmpty( values );
	}
	public <X extends Enum<X>> void ensureLength( Class<T> clazz ) {
		int size = clazz.getEnumConstants().length;
		if( values.length < size )
				throw new IllegalArgumentException( "Line length (" + values.length + ") too small for " + clazz.getSimpleName() + "(" + size + ")" );
	}
	
	public <X extends Enum<X>> AccessibleLine<T> set( T field, String value ) {
		return set( field.ordinal(), value );
	}
	public AccessibleLine<T> set( int i, String value ) {
		values[ i ] = value;
		return this;
	}
	
	public <X extends Enum<X>> String get( X field ) {
		return get( field.ordinal() );
	}
	public <X extends Enum<X>> String get( int i ) {
		return values[ i ];
	}
	
	public <X extends Enum<X>> String trimmed( X field ) {
		return trimmed( field.ordinal() );
	}
	public <X extends Enum<X>> String trimmed( int i ) {
		return values[ i ].trim();
	}
	
	public <X extends Enum<X>> String trimmedToUpper( X field ) {
		return toUpper( trimmed( field ) );
	}
	public <X extends Enum<X>> String trimmedToUpper( int i ) {
		return toUpper( trimmed( i ) );
	}
	public <X extends Enum<X>> String trimmedToLower( X field ) {
		return toLower( trimmed( field ) );
	}
	public <X extends Enum<X>> String trimmedToLower( int i ) {
		return toLower( trimmed( i ) );
	}
	
	@Nullable
	public <X extends Enum<X>> String trimmedToNull( X field ) {
		String result = trimmed( field );
		if( result.length() == 0 ) return null;
		return result;
	}
	@Nullable
	public <X extends Enum<X>> String trimmedToNull( int i ) {
		String result = trimmed( i );
		if( result.length() == 0 ) return null;
		return result;
	}
	
	@Nullable
	public <X extends Enum<X>> String trimmedToUpperNull( X field ) {
		return toUpper( trimmedToNull( field ) );
	}
	@Nullable
	public <X extends Enum<X>> String trimmedToUpperNull( int i ) {
		return toUpper( trimmedToNull( i ) );
	}
	@Nullable
	public <X extends Enum<X>> String trimmedToLowerNull( X field ) {
		return toLower( trimmedToNull( field ) );
	}
	@Nullable
	public <X extends Enum<X>> String trimmedToLowerNull( int i ) {
		return toLower( trimmedToNull( i ) );
	}
	
	
	private static String toUpper( String value ) {
		return value != null ? value.toUpperCase() : null;
	}
	private static String toLower( String value ) {
		return value != null ? value.toLowerCase() : null;
	}
	
	
	public static final <X extends Enum<X>> AccessibleLine<X> create( Class<X> clazz ) {
		
		int size = clazz.getEnumConstants().length;
		
		return new AccessibleLine<>( new String[ size ] );
	}
	
	@Override
	public String toString() {
		return F.ormat( (Object)values );
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + Arrays.hashCode( values );
		
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof AccessibleLine ) ) return false;
		
		AccessibleLine<?> other = (AccessibleLine<?>) obj;
		
		if( !Arrays.equals( values, other.values ) ) return false;
		
		return true;
	}
	
}
