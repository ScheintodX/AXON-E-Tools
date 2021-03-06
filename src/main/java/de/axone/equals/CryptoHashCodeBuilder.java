package de.axone.equals;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.axone.exception.Assert;

public class CryptoHashCodeBuilder extends AbstractStrongHashCodeBuilder<byte[]> {
	
	private static final String ALGO = "SHA1";
	
	private static final byte NULL_VALUE = 0;
	private static final int EMPTY_VALUE = Integer.MAX_VALUE-1; // "Some" rare(?) value
	
	private static final byte[] EMPTY_HASH;
	static{
		try {
			EMPTY_HASH = MessageDigest.getInstance( ALGO ).digest();
		} catch( NoSuchAlgorithmException e ) {
			throw new Error( e ); // <- this can happen
		}
	}
	
	private final MessageDigest digest;
	
	private final boolean treatEmptyAsNull;
	
	public CryptoHashCodeBuilder(){
		this( true );
	}
	public CryptoHashCodeBuilder( boolean treatEmptyAsNull ){
		this.treatEmptyAsNull = treatEmptyAsNull;
		try {
			this.digest = MessageDigest.getInstance( ALGO );
		} catch( NoSuchAlgorithmException e ) {
			throw new Error( e ); // <-- Can happen
		}
	}

	@Override
	public StrongHashCodeBuilder<byte[]> append( byte[] bytes ) {
		if( bytes == null ) appendNull();
		else if( bytes.length == 0 ) appendEmpty();
		else digest.update( bytes );
		return this;
	}

	@Override
	public byte[] toHashCode() {
		return digest.digest();
	}
	
	@Override
	public byte[] combineHash( byte[] hash1, byte[] hash2 ) {
		Assert.equal( hash1.length, "hash lenght", hash2.length );
		
		byte[] result = new byte[ hash1.length ];
		for( int i=0; i < hash1.length; i++ ){
			result[ i ] = (byte)( ( (hash1[i]&0xff) + (hash2[i]&0xff) )&0xff );
		}
		
		return result;
	}

	@Override
	public byte[] empty() {
		return EMPTY_HASH.clone();
	}

	@Override
	public StrongHashCodeBuilder<byte[]> appendNull() {
		return append( NULL_VALUE );
	}

	@Override
	public StrongHashCodeBuilder<byte[]> appendEmpty() {
		if( treatEmptyAsNull ) return appendNull();
		else return append( EMPTY_VALUE );
	}

	@Override
	StrongHashCodeBuilder<byte[]> builder() {
		return new CryptoHashCodeBuilder( treatEmptyAsNull );
	}
	@Override
	public StrongHashCodeBuilder<byte[]> appendParent( byte[] hash ) {
		return append( hash );
	}

}
