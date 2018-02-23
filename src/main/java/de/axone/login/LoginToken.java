package de.axone.login;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import de.axone.data.Charsets;
import de.axone.tools.HEX;

/**
 * Interface representing on elogin Token
 * 
 * This serves the purpose that we don't have to keep any private
 * state in the token and don't accidentally expose it.
 * 
 * @author flo
 */
public interface LoginToken {
	
	public long getCreation();
	public long getId();
	
	public static class Builder {
		
		private static final String CIPHER = "AES/CBC/NoPadding";
	
		//                                  0123456789012345
		private static final long MAGIC = 0x600DF0075AFEC0DEL;
		
		private final String secret;
		
		public Builder( String secret ) {
			
			this.secret = secret;
		}
		
		public LoginToken build( long uid ) {
			
			long now = System.currentTimeMillis();
			
			LoginTokenImpl token = new LoginTokenImpl( MAGIC, new SecureRandom().nextLong(), now, uid );
			
			return token;
		}
		
		public LoginToken refresh( LoginToken oldOne ) {
			
			return new LoginTokenImpl( (LoginTokenImpl)oldOne, System.currentTimeMillis() );
		}
			
		public LoginToken decode( String encoded ) {
			
			byte [] rawEncoded = HEX.decode( encoded );
			
			try {
				
				Cipher cipher = Cipher.getInstance( CIPHER );
			
				byte secretBytes[] = secret.getBytes( Charsets.ASCII );
				
				SecretKeySpec key = new SecretKeySpec( secret.getBytes( Charsets.ASCII ), "AES" );
				IvParameterSpec iv = new IvParameterSpec( secretBytes );
				
				cipher.init( Cipher.DECRYPT_MODE, key, iv );
				
				byte[] decoded = cipher.doFinal( rawEncoded );
				
				return new LoginTokenImpl( MAGIC, decoded );
				
			} catch( GeneralSecurityException e ) {
				throw new Error( e );
			}
		}
		
		public String secure( LoginToken token ) {
			
			try {
				Cipher cipher = Cipher.getInstance( CIPHER );
				
				byte secretBytes[] = secret.getBytes( Charsets.ASCII );
				
				SecretKeySpec key = new SecretKeySpec( secretBytes, "AES" );
				IvParameterSpec iv = new IvParameterSpec( secretBytes );
				
				cipher.init( Cipher.ENCRYPT_MODE, key, iv );
				byte[] combined = ((LoginTokenImpl)token).combined();
				byte[] rawEncoded = cipher.doFinal( combined );
				
				return HEX.encode( rawEncoded );
				
			} catch( GeneralSecurityException e ) {
				throw new Error( e );
			}
		}
	}
	
	public static class LoginTokenImpl implements LoginToken, Serializable {
		
		public static final long serialVersionUID = 1L;
		
		private final long magic, random, creation, id;
		
		public LoginTokenImpl( long magic, long random, long creation, long id ) {
			this.magic = magic;
			this.random = random;
			this.creation = creation;
			this.id = id;
		}
		
		public LoginTokenImpl( LoginTokenImpl other, long time ) {
			this.magic = other.magic;
			this.random = other.random;
			this.creation = time;
			this.id = other.id;
		}
		
		public LoginTokenImpl( long magic, byte [] combined ) {
			
			if( combined.length != 32 )
					throw new IllegalArgumentException( "Error decoding token" );
			
			ByteBuffer buf = ByteBuffer.wrap( combined );
			
			this.random = buf.getLong();
			this.magic = buf.getLong();
			this.creation = buf.getLong();
			this.id = buf.getLong();
			
			if( magic != this.magic )
					throw new IllegalArgumentException( "Error decoding token" );
		}
		
		@Override
		public long getCreation() { return creation; }
		
		@Override
		public long getId() { return id; }
		
		public byte [] combined() {
			
			byte[] result = new byte[ 32 ];
			ByteBuffer buf = ByteBuffer.wrap( result );
			buf.putLong( random )
			   .putLong( magic )
			   .putLong( creation )
			   .putLong( id )
			   ;
			
			return result;
		}
		
		@Override
		public String toString() {
			
			return "" + creation + ':' + id;
		}
		
	}
}
