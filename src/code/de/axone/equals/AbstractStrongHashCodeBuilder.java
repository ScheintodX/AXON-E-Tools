package de.axone.equals;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractStrongHashCodeBuilder<T> implements StrongHashCodeBuilder<T> {
	
	private static final byte TRUE = 73;
	private static final byte FALSE = 117;

	@Override
	public StrongHashCodeBuilder<T> append( boolean v ) {
		return append( new byte[]{ v ? TRUE : FALSE } );
	}

	@Override
	public StrongHashCodeBuilder<T> append( byte v ) {
		return append( new byte[]{ v } );
	}

	@Override
	public StrongHashCodeBuilder<T> append( char v ) {
		return append( new byte[]{ (byte)(v>>8), (byte)(v&0xff) } );
	}

	@Override
	public StrongHashCodeBuilder<T> append( short v ) {
		return append( new byte[]{ (byte)((v&0xff00)>>8), (byte)(v&0xff) } );
		
	}

	@Override
	public StrongHashCodeBuilder<T> append( int v ) {
		
		return append( new byte[]{
				(byte)(((v&(0xff<<24))>>24)&0xff), (byte)((v&(0xff<<16))>>16), 
				(byte)((v&(0xff<<8))>>8), (byte)(v&0xff)
		} );
	}

	@Override
	public StrongHashCodeBuilder<T> append( long v ) {
		
		return append( new byte[]{
				(byte)(((v&(0xffL<<56))>>56)&0xff), (byte)((v&(0xffL<<48))>>48), 
				(byte)((v&(0xffL<<40))>>40), (byte)((v&(0xffL<<32))>>32), 
				(byte)((v&(0xffL<<24))>>24), (byte)((v&(0xffL<<16))>>16), 
				(byte)((v&(0xffL<<8))>>8), (byte)(v&0xffL)
		} );
	}
	
	@Override
	public StrongHashCodeBuilder<T> append( float v ) {
		return append( Float.floatToIntBits( v ) );
	}

	@Override
	public StrongHashCodeBuilder<T> append( double v ) {
		return append( Double.doubleToLongBits( v ) );
	}

	@Override
	public StrongHashCodeBuilder<T> append( boolean[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( boolean v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( char[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( char v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( short[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( short v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( int[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( int v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( long[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( long v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( float[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( float v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( double[] a ) {
		if( a == null ) appendNull();
		else if( a.length == 0 ) appendEmpty();
		else for( double v : a ) append( v );
		return this;
	}

	@Override
	public StrongHashCodeBuilder<T> append( String s ){
		return append( s.toCharArray() );
	}

	protected StrongHashCodeBuilder<T> append( StrongPair p ){
		append( p.a ); append( p.b );
		return this;
	}
	
	@Override
	public StrongHashCodeBuilder<T> append( Object o ) {
		
		if( o == null ) appendNull();
		else if( o instanceof Boolean ) append( (boolean)(Boolean)o );
		else if( o instanceof Byte ) append( (byte)(Byte)o );
		else if( o instanceof Character ) append( (char)(Character)o );
		else if( o instanceof Short ) append( (short)(Short)o );
		else if( o instanceof Integer ) append( (int)(Integer)o );
		else if( o instanceof Long ) append( (long)(Long)o );
		else if( o instanceof Float ) append( (float)(Float)o );
		else if( o instanceof Double ) append( (double)(Double)o );
		else if( o instanceof String ) append( (String)o );
		else if( o instanceof StrongPair ) append( (StrongPair)o );
		else if( o instanceof StrongHash ) append( ((StrongHash)o).strongHashCode() );
		else if( o.getClass().isArray() ) append( (Object[])o );
		else if( o instanceof Set ) append( (Set<?>)o );
		else if( o instanceof Collection ) append( (Collection<?>)o );
		else if( o instanceof Map ) append( (Map<?,?>)o );
		// Special treatment for currency which has no hashcode method
		// This leads to a somewhat changed behaviour since this is stable
		// over program runs and calling hashcode isn't.
		/* Moved to equals because is althoug needed in normal hashcode
		else if( o instanceof Currency ){
			//E.rr(  ((Currency)o).getCurrencyCode()  );
			append( ((Currency)o).getCurrencyCode() );
		}
		*/
		else{
			/*
			if( o instanceof Currency ){
				(new Throwable()).printStackTrace();
			}
			E.rr( o + ": (" + o.getClass().getSimpleName() + ")" + o.hashCode() );
			*/
			append( o.hashCode() ); // Fallback to normal hashcode if we can't do better
		}
		
		return this;
	}
	

	@Override
	public StrongHashCodeBuilder<T> append( Object[] a ) {
		if( a == null ) appendNull();
		else for( Object v : a ) append( v );
		return this;
	}
	
	// In Sets order doesn't matter
	public StrongHashCodeBuilder<T> append( Set<?> a ){
		//E.rr( a );
		if( a==null ) appendNull();
		else if( a.size() == 0 ) appendEmpty();
		else {
			T hash = empty();
			for( Object v : a ){
				StrongHashCodeBuilder<T> b = builder();
				b.append( v );
				hash = combineHash( hash, b.toHashCode() );
				//E.rr( v + "::==" + Base64.encodeBase64String( (byte[])hash ).trim() );
			}
			//E.rr( Base64.encodeBase64String( (byte[])hash ).trim() );
			appendParent( hash );
		}
		return this;
	}
	
	// Here order does matter
	public StrongHashCodeBuilder<T> append( Collection<?> a ){
		if( a==null ) appendNull();
		else if( a.size() == 0 ) appendEmpty();
		else for( Object v : a ){
			append( v );
		}
		return this;
	}
	
	// Treat map as unordered set of Pairs
	public StrongHashCodeBuilder<T> append( Map<?,?> a ){
		if( a==null ) appendNull();
		else if( a.size() == 0 ) appendEmpty();
		else {
			Set<StrongPair> set = new HashSet<StrongPair>( a.size() );
			for( Object key : a.keySet() ){
				set.add( new StrongPair( key, a.get( key ) ) );
			}
			append( set );
		}
		return this;
	}
	
	public abstract StrongHashCodeBuilder<T> appendNull();
	public abstract StrongHashCodeBuilder<T> appendEmpty();
	
	public abstract T combineHash( T hash1, T hash2 );
	public abstract T empty();
	public abstract StrongHashCodeBuilder<T> appendParent( T hash );
	
	abstract StrongHashCodeBuilder<T> builder();

	protected static final class StrongPair {
		final Object a, b;
		StrongPair( Object a, Object b ){
			this.a = a;
			this.b = b;
		}
	}
	
	@Override
	public int hashCode(){
		throw new RuntimeException( "You didn't mean to call that!" );
	}
}
