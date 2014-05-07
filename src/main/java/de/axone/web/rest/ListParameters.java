package de.axone.web.rest;

import javax.servlet.http.HttpServletRequest;

import de.axone.web.RequestUtil;

public class ListParameters extends RequestUtil {

	public ListParameters( HttpServletRequest request ) {
		super( request );
	}
	
	public String pFilter(){
		return get( "filter" );
	}
	
	public String [] pFilters(){
		String filter = pFilter();
		if( filter == null ) return null;
		filter = filter.trim();
		if( filter.length() == 0 ) return null;
		String [] result = filter.split( "\\s+" );
		for( int i=0; i<result.length; i++ ){
			result[i] = result[i].toLowerCase();
		}
		return result;
	}
	
	public int pStart(){
		return getInteger( "start", 0 );
	}
	
	public int pSize(){
		return getInteger( "size", 10 );
	}
	
	public String pSort(){
		return get( "sort" );
	}
	
	public int pAge(){
		return getInteger( "age", 1*60*60*24*7 );
	}
	
	public <T extends Enum<T> > T pSort( Class<T> clazz ){
		String sortStr = pSort();
		if( sortStr == null ) return null;
		return Enum.valueOf( clazz, sortStr );
	}
}
