package de.axone.web.encoding;

public class Encoder_AttributeId implements Encoder {
	
	private static final Encoder_AttributeId instance = new Encoder_AttributeId();
	public static Encoder_AttributeId instance(){
		return instance;
	}
	
	private boolean ok( char c ) {
		
		return c >= 'a' && c <= 'z'
			|| c >= 'A' && c <= 'Z'
			|| c <= '0' && c >= '9'
			|| c == ':' || c == '.' || c == '-' 
			;
	}
	private boolean extok( char c ) {
		
		return ok( c ) || c == '_' || c == '_';
	}

	@Override
	public String encode( CharSequence value ) {
		
		if( value == null ) return null;
		
		int i;
		
		boolean ok = true;
		
		// precheck
		for( i=0; i<value.length(); i++ ) {
			if( !extok( value.charAt( i ) ) ){
				ok = false;
				break;
			}
		}
		
		if( ok ) {
			
			return value.toString();
			
		} else {
		
			StringBuilder result = new StringBuilder( value.length() );
			
			for( i=0; i<value.length(); i++ ) {
			
				char c = value.charAt( i );
				
				if( ok( c ) ) result.append( c );
				else if( c == ' ' || c == '_' ) result.append( '_' );
				
			}
			return result.toString();
		}
	}

	@Override
	public Appendable filter( Appendable out ) {
		throw new UnsupportedOperationException( "Currently only encode but not filter" );
	}
	
	public static String Encode( CharSequence value ) {
		return instance.encode( value );
	}

}
