package de.axone.web.rest;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

public abstract class Validator<T> {
	
	/**
	 * Validate the given object.
	 * 
	 * This method returns an
	 * 
	 * @param em
	 * @param object
	 * @return List of errors. Empty list if no errors.
	 */
	public List<FieldError> validate( EntityManager em, T object ){
		
		FieldErrorList result = new FieldErrorList();
		
		try {
			doValidate( result, em, object );
		} catch( ValidatorException e ){
			result.add( new FieldErrorWrapper( e ) );
		} catch( Exception e ){
			result.add( new FieldErrorImpl( "UNKNOWN", e.getMessage() ) );
		}
		
		return result;
	}
	
	public static <O> List<FieldError> validate( EntityManager em,
			Validator<O> validator, O object ) {
		
		return validator.validate( em, object );
	}
	
	public abstract void doValidate( FieldErrorList result, EntityManager em, T object ) throws ValidatorException;
	
	public void assertNotNull( FieldErrorList result,
			String field, Object value ){
		
		if( value == null )
			result.add( field, "IS_NULL" );
	}
	
	public void assertNoDups( FieldErrorList result,
			String field, List<String> list ){
		
		if( list == null ) return;
		
		Set<String> asSet = new TreeSet<String>( list );
		
		if( list.size() > asSet.size() )
			result.add( field, "HAS_DUPS" );
	}
	
	public void assertNotEmpty( FieldErrorList result,
			String field, String value ){
		
		if( value == null || value.trim().length() == 0 )
			result.add( field, "IS_EMPTY" );
	}
	
	public void assertNotEmpty( FieldErrorList result,
			String field, Collection<?> value ){
		
		if( value == null  || value.size() == 0 )
			result.add( field, "IS_EMPTY" );
	}
	
	public void assertNotExists( FieldErrorList result,
			String field, String value, Collection<String> set ){
		
		if( value == null ) return;
		
		if( set.contains( value ) )
			result.add( field, "EXISTS" );
		
	}
	
	/**
	 * Can be thrown from assert methods to indicate problems
	 * 
	 * largely compatible with
	 * 
	 * @author flo
	 */
	protected static class ValidatorException
	extends IllegalArgumentException
	implements FieldException {
		
		private final String field;
		
		public ValidatorException( String field, String message ) {
			super( message );
			this.field = field;
		}

		public ValidatorException( String field, Throwable cause ) {
			super( cause );
			this.field = field;
		}

		@Override
		public String getField() {
			return field;
		}
		
	}
	
	public interface FieldError {
		
		public static final String PREFIX = "ERROR.";
		
		public String getField();
		public String getMessage();
	}
	
	public static class FieldErrorWrapper implements FieldError {
		
		private final ValidatorException e;
		
		FieldErrorWrapper( ValidatorException e ){
			this.e = e;
		}
		@Override
		public String getField(){ return e.getField(); }
		@Override
		public String getMessage(){ return PREFIX + e.getMessage(); }
	}
	
	public static class FieldErrorImpl implements FieldError {
	
		private final String field;
		private final String message;
		
		public FieldErrorImpl( String field, String message ){
			this.field = field;
			this.message = message;
		}
		
		@Override
		public String getField() { return field; }
		@Override
		public String getMessage() { return PREFIX + message; }
	
	}
	
	public static final class FieldErrorList extends LinkedList<FieldError>{
		
		public void add( String field, String message ){
			add( new FieldErrorImpl( field, message ) );
		}
	}
}
