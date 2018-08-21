package de.axone.tools;

import java.util.Set;

public class WhereWhat<WHERE extends Enum<WHERE>,WHAT> {
	
	private WHERE where;
	private WHAT what;

	public WhereWhat( WHERE where, WHAT what ) {
		this.where = where;
		this.what = what;
	}
	public WHERE where() {
		return where;
	}
	public WHAT what() {
		return what;
	}
	
	// differs from Str.splitFastOnce in the null value
	private static String [] split( String s, char split ){
		
		int pos = s.indexOf( split );
		
		if( pos >= 0 ){
			return new String[]{ s.substring( 0, pos ), s.substring( pos+1 ) };
		} else {
			return new String[]{ s, null };
		}
	}
	
	public static <X extends Enum<X>> WhereWhat<X,String> from( Class<X> clazz, char fs, String value ) {
		
		if( value == null ) return null;
		
		
		String [] splitted = split( value, fs );
		
		return new WhereWhat<X,String>( Enum.valueOf( clazz, splitted[ 0 ].toUpperCase() ), splitted[ 1 ] );
	}
	
	public static <X extends Enum<X>> WhereWhat<X,String> from( Set<X> allowed, char fs, String value ) {
		
		if( value == null ) return null;
		if( allowed.size() == 0 ) return null;
		
		X first = allowed.iterator().next();
		@SuppressWarnings( "unchecked" )
		Class<X> clazz = (Class<X>)first.getClass();
		
		WhereWhat<X,String> result = from( clazz, fs, value );
		
		if( ! allowed.contains( result.where ) ) return null;
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return "[" + where + ": " + what + "]";
	}
}
