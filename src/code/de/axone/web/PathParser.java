package de.axone.web;

import java.util.HashMap;

public class PathParser {
	
	private HashMap<String, String> values = new HashMap<String,String>();
	
	public PathParser( SuperURL url, String ... args ){
		
		SuperURL.Path path = url.getPath();
		
		for( int i = 0; i < args.length && i < path.length(); i++ ){
			
			values.put(
				args[ i ],
    			path.get( i )
			);
		}
		
	}
	
	public int size(){
		return values.size();
	}
	
	public boolean exists( String name ){
		
		return values.containsKey( name );
	}
	
	public String get( String name ){
		
		return values.get( name );
	}
	
	public Long getAsLong( String name ){
		
		String value = get( name );
		
		if( value == null ) return null;
		String trimmed = value.trim();
		if( trimmed.length() == 0 ) return null;
		
		return Long.parseLong( trimmed );
	}
	
}
