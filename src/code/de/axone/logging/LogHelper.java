package de.axone.logging;

public abstract class LogHelper {
	
	public static String parse( Object ... arguments ){
		
		if( arguments.length == 1 )
			return arguments[ 0 ].toString();
		
		StringBuilder builder = new StringBuilder();
		
		int lastPos=0, pos=-1;
		
		String message = arguments[ 0 ].toString();
		
		while( ( pos = message.indexOf( '#', lastPos ) ) > 0 ){
			
			builder.append( message.substring( lastPos, pos ) );
			
			char c = message.charAt( pos+1 );
			if( (message.length() > pos) && c >='0' && c <= '9' ){
				
				int no = c-'0';
				
				if( arguments.length > no+1 ){
					builder.append( arguments[ no+1 ] );
				} else {
					builder.append( "NO ARGUMENT #" + no );
				}
				
				pos++;
			} else {
				builder.append( '#' );
			}
			
			lastPos = pos+1;
		}
		
		return builder.toString();
	}
}
