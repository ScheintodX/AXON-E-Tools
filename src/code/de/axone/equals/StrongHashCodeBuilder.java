package de.axone.equals;


public interface StrongHashCodeBuilder<T> {
	
	public StrongHashCodeBuilder<T> append( boolean v );
	public StrongHashCodeBuilder<T> append( byte v );
	public StrongHashCodeBuilder<T> append( char v );
	public StrongHashCodeBuilder<T> append( short v );
	public StrongHashCodeBuilder<T> append( int v );
	public StrongHashCodeBuilder<T> append( long v );
	public StrongHashCodeBuilder<T> append( float v );
	public StrongHashCodeBuilder<T> append( double v );
	
	public StrongHashCodeBuilder<T> append( boolean [] a );
	public StrongHashCodeBuilder<T> append( char [] a );
	public StrongHashCodeBuilder<T> append( short [] a );
	public StrongHashCodeBuilder<T> append( int [] a );
	public StrongHashCodeBuilder<T> append( long [] a );
	public StrongHashCodeBuilder<T> append( float [] a );
	public StrongHashCodeBuilder<T> append( double [] a );
	
	public StrongHashCodeBuilder<T> append( String s );
	
	public StrongHashCodeBuilder<T> append( Object o );
	public StrongHashCodeBuilder<T> append( Object [] a );
	
	public StrongHashCodeBuilder<T> append( byte [] bytes );
	
	public T toHashCode();

}
