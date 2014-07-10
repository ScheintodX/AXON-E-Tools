package de.axone.equals;

import java.io.ByteArrayOutputStream;

public class Jenkins96HashCodeBuilder extends AbstractStrongHashCodeBuilder<Integer> {
	
	private static final byte NULL_VALUE = 0;
	private static final int EMPTY_VALUE = Integer.MAX_VALUE-1; // "Some" rare(?) value
	
	public static final int EMPTY_HASH = (int)((new Jenkins96Backend()).hash( new byte[]{} ));
	private Jenkins96Backend backend = new Jenkins96Backend();
	
	//private final List<Byte> bytes = new ArrayList<Byte>();
	//private final ByteBuffer bytes = new ByteBuffer();
	private final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	
	private final boolean treatEmptyAsNull;
	
	public Jenkins96HashCodeBuilder(){
		this( true );
	}
	public Jenkins96HashCodeBuilder( boolean treatEmptyAsNull ){
		this.treatEmptyAsNull = treatEmptyAsNull;
	}

	@Override
	public StrongHashCodeBuilder<Integer> append( byte[] bytes ) {
		if( bytes == null ) return appendNull();
		if( bytes.length == 0 ) return appendEmpty();
		for( byte b : bytes ){
			this.bytes.write( b );
		}
		return this;
	}

	@Override
	public StrongHashCodeBuilder<Integer> appendNull() {
		return append( NULL_VALUE );
	}

	@Override
	public StrongHashCodeBuilder<Integer> appendEmpty() {
		if( treatEmptyAsNull ) return appendNull();
		else return append( EMPTY_VALUE );
	}
	@Override
	public StrongHashCodeBuilder<Integer> appendParent( Integer hash ) {
		return append( hash );
	}

	@Override
	public Integer combineHash( Integer hash1, Integer hash2 ) {
		return hash1+hash2;
	}

	@Override
	protected StrongHashCodeBuilder<Integer> builder() {
		return new Jenkins96HashCodeBuilder( treatEmptyAsNull );
	}

	@Override
	public Integer empty() {
		return EMPTY_HASH;
	}

	@Override
	public Integer toHashCode() {
		
		byte[] bytes = this.bytes.toByteArray();
		
		return (int)backend.hash( bytes );
	}

}
