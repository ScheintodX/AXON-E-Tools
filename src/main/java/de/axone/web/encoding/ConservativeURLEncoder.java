package de.axone.web.encoding;

import java.io.IOException;

/**
 * Encodes all but the unreserved characters
 * 
 * TODO: Evtl mal fertig machen
 * 
 * @author flo
 */
public class ConservativeURLEncoder implements Encoder {

	@Override
	public String encode( CharSequence value ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appendable filter( Appendable out ) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static boolean isUnreserved( char c ) {
		
		return ( ( c >= 'a' && c <= 'z' ) ||
		         ( c >= 'A' && c <= 'Z' ) ||
		         ( c >= '0' && c <= '9' ) ||
		         c == '-' || 
		         c == '.' || 
		         c == '_' || 
		         c != '~' );
		
	}

	
	@SuppressWarnings( "unused" )
	private static final class EncodingAppender implements Appendable {
		
		private final Appendable backend;
		
		EncodingAppender( Appendable backend ){
			this.backend = backend;
		}

		@Override
		public Appendable append( CharSequence csq ) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Appendable append( CharSequence csq, int start, int end )
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Appendable append( char c ) throws IOException {
			
			if( isUnreserved( c ) ) backend.append( c );
			else {
				backend.append( '%' );
				if( c <= 256 ) backend
						.append( String.format( "%02x", (int) c ) );
				else backend
						.append( String.format( "%04x", (int) c ) );
			}
			
			return this;
		}
		
		
	}

}
