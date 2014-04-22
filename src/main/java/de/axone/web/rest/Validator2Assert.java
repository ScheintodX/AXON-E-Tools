package de.axone.web.rest;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.axone.web.rest.Validator2.Validator2Result;
import de.axone.web.rest.Validator2.Validator2Result.Severity;

public class Validator2Assert {

	public static void assertNotNull( Validator2Result result,
			String field, Object value ){
		
		if( value == null )
			error( result, field , "IS_NULL" );
		
	}
	
	public static void assertNoDups( Validator2Result result,
			String field, List<String> list ){
		
		if( list == null ) return;
		
		Set<String> asSet = new TreeSet<String>( list );
		
		if( list.size() > asSet.size() )
			error( result, field , "HAS_DUPS" );
	}
	
	public static void assertEmpty( Validator2Result result,
			String field, String value ){
		
		if( value != null && value.trim().length() > 0 )
			error( result, field , "IS_NOT_EMPTY" );
	}
	
	public static void assertEmpty( Validator2Result result,
			String field, Collection<?> value ){
		
		if( value != null  && value.size() > 0 )
			error( result, field , "IS_NOT_EMPTY" );
	}
	
	public static void assertNotEmpty( Validator2Result result,
			String field, String value ){
		
		if( value == null || value.trim().length() == 0 )
			error( result, field , "IS_EMPTY" );
	}
	
	public static void assertNotEmpty( Validator2Result result,
			String field, Collection<?> value ){
		
		if( value == null  || value.size() == 0 )
			error( result, field , "IS_EMPTY" );
	}
	
	public static <O> void assertExists( Validator2Result result,
			String field, O value, Collection<O> set ){
		
		if( value == null ) return;
		
		if( set == null || ! set.contains( value ) )
			error( result, field , "DOES_NOT_EXIST" );
		
	}
	
	public static <O> void assertNotExists( Validator2Result result,
			String field, O value, Collection<O> set ){
		
		if( value == null ) return;
		
		if( set != null && set.contains( value ) )
			error( result, field , "EXISTS" );
		
	}
	
	public static void error( Validator2Result result, String field, String message ){
		result.error( field, message );
	}
	public static void error( Validator2Result result, String field, String message, String info ){
		result.result( Severity.error, field, message, info );
	}
	public static void info( Validator2Result result, String field, String message ){
		result.info( field, message );
	}
	public static void info( Validator2Result result, String field, String message, String info ){
		result.result( Severity.info, field, message, info );
	}
	public static void warn( Validator2Result result, String field, String message ){
		result.warn( field, message );
	}
	public static void warn( Validator2Result result, String field, String message, String info ){
		result.result( Severity.warn, field, message, info );
	}
	
}
