package de.axone.tools;


public abstract class CamelCase {

	public static String toCamelCase( String underscored, boolean firstUpperCase ){
		
		if( underscored == null || underscored.length() == 0 ) return underscored;
		
		StringBuilder result = new StringBuilder();
		
		boolean makeUpperCase = firstUpperCase;
		for( char c : underscored.toCharArray() ){
			
			if( c == '_' ){
				makeUpperCase = true;
			} else {
				if( makeUpperCase ){
					c = Character.toUpperCase( c );
				}
				result.append( c );
				makeUpperCase = false;
			}
		}
		
		return result.toString();
	}
	
	public static String toUnderscored( String camelCase ){
		
		if( camelCase == null || camelCase.length() == 0 ) return camelCase;
		
		StringBuilder result = new StringBuilder( camelCase.length() * 2 );
		
		char last = ' '; // not in field names. neither upper nor lower case. not a digit.
		int len = camelCase.length();
		for( int i = 0; i < len; i++ ){
			
			char c = camelCase.charAt( i );
			char next;
			if( i < len-2 ) next = camelCase.charAt( i+1 );
			else next = ' ';
			
			boolean is_ = false;
			
			if( i > 0 ){
				if( i < len-2
						&& Character.isUpperCase( last ) 
						&& Character.isUpperCase( c )
						&& Character.isLowerCase( next )
				) is_ = true;
				
				else if( Character.isLowerCase( last ) && Character.isUpperCase( c ) ) is_ = true;
				else if( Character.isDigit( last ) && ! Character.isDigit( c ) ) is_ = true;
				else if( (! Character.isDigit( last )) && Character.isDigit( c ) ) is_ = true;
			}
			
			if( is_ ) result.append( '_' );
			
			result.append( Character.toLowerCase( c ) );
			
			last = c;
		}
		
		return result.toString();
	}
}
