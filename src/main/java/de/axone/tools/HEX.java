package de.axone.tools;

public class HEX {

	private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	public static String toString( byte[] bytes ) {
		
		if( bytes == null ) return "";
		
		int len = bytes.length;
		int outsize = (int)(len*2.5) + (int)(len/16) +2;
		
		StringBuilder hexBuilder = new StringBuilder( outsize );
		
		if( bytes.length > 16 ) hexBuilder.append( '\n' );
		
	    for ( int i = 0; i < bytes.length; i++ ) {
	    	
			if( i%16 != 0 ){
				hexBuilder.append( ' ' );
			} else if( i != 0 ) {
				hexBuilder.append( '\n' );
			}
			
	        int v = bytes[i] & 0xFF;
	        hexBuilder.append( HEX_ARRAY[v >>> 4] );
	        hexBuilder.append( HEX_ARRAY[v & 0x0F] );
	    }
	    
	    return hexBuilder.toString();
	    
	}
	
	public static String toString( Integer value ) {
		
		if( value == null ) return "";
		
		return new String( new char[]{
				                        HEX_ARRAY[ (value >>> 28)&0xf ], HEX_ARRAY[ (value >>> 24)&0xf ],
				                        HEX_ARRAY[ (value >>> 20)&0xf ], HEX_ARRAY[ (value >>> 16)&0xf ],
				                        HEX_ARRAY[ (value >>> 12)&0xf ], HEX_ARRAY[ (value >>> 8)&0xf ],
				                        HEX_ARRAY[ (value >>> 4)&0xf ], HEX_ARRAY[ value &0xf ] } );
	}
	
	public static String toString( Short value ) {
		
		if( value == null ) return "";
		
		return new String( new char[]{ HEX_ARRAY[ (value >>> 12)&0xf ], HEX_ARRAY[ (value >>> 8)&0xf ],
				                        HEX_ARRAY[ (value >>> 4)&0xf ], HEX_ARRAY[ value &0xf ] } );
	}
	
	public static String toString( Byte value ) {
		
		if( value == null ) return "";
		
		return new String( new char[]{ HEX_ARRAY[ value >>> 4 ], HEX_ARRAY[ value &0xf ] } );
	}
	
	public static String toString( Long value ) {
		
		if( value == null ) return "";
		
		return toString( (int)(value >> 32) ) + "." + toString( (int)(value&0xffffffff) );
	}
}
