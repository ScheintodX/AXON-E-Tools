package de.axone.web.rest;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import de.axone.exception.FieldException;

public abstract class Validator<T> {
	
	public List<FieldError> validate( EntityManager em, T object ){
		
		List<FieldError> result = new LinkedList<>();
		
		doValidate( result, em, object );
		
		return result;
	}
	
	public abstract void doValidate( List<FieldError> result, EntityManager em, T object );
	
	public void assertNotNull( List<FieldError> result,
			String field, Object value ){
		
		assertAssertion( result, NotNullA, field, value, null );
	}
	
	public void assertNoDups( List<FieldError> result,
			String field, List<String> value ){
		
		assertAssertion( result, NoDupsA, field, value, null );
	}
	
	public void assertNotEmpty( List<FieldError> result,
			String field, String value ){
		
		assertAssertion( result, NotEmptyA, field, value, null );
	}
	
	public void assertNotEmpty( List<FieldError> result,
			String field, Collection<?> value ){
		
		assertAssertion( result, NotEmptyColA, field, value, null );
	}
	
	public void assertNotExists( List<FieldError> result,
			String field, String value, Collection<String> set ){
		
		assertAssertion( result, NotExistsA, field, value, set );
	}
	
	public <A,E> void assertAssertion( List<FieldError> result,
			Assertion<A,E> assertion, String field, A value, E extended ){
		
		try {
			assertion.test( field, value, extended );
		} catch( IllegalArgumentException e ){
			
			if( e instanceof FieldException ){
				
				result.add( new FieldError( ((FieldException)e).getField(), e.getMessage() ) );
			} else {
				result.add( new FieldError( field, e.getMessage() ) );
			}
		}
	}
	
	public static <O> List<FieldError> validate( EntityManager em,
			Validator<O> validator, O object ) {
		
		return validator.validate( em, object );
	}
	
	protected interface Assertion<V,E> {
		
		public void test( String field, V value, E extended );
	}
	
	protected static Assertion<String,Object> NotEmptyA = new Assertion<String,Object>() {

		@Override
		public void test( String field, String value, Object extended ) {
			if( value == null || value.trim().length() == 0 )
					throw new ValidatorException( field, "IS_EMPTY" );
		}
		
	};
	
	protected static Assertion<Collection<String>,Object> NoDupsA = new Assertion<Collection<String>,Object>() {

		@Override
		public void test( String field, Collection<String> list, Object extended ) {
			if( list == null ) return;
			
			Set<String> asSet = new TreeSet<String>( list );
			
			if( list.size() > asSet.size() )
					throw new ValidatorException( field, "HAS_DUPS" );
		}
		
	};
	
	protected static Assertion<Object,Object> NotNullA = new Assertion<Object,Object>() {

		@Override
		public void test( String field, Object value, Object extended ) {
			if( value == null )
					throw new ValidatorException( field, "IS_NULL" );
		}
		
	};
	
	protected static Assertion<String,Collection<String>> NotExistsA = new Assertion<String,Collection<String>>() {

		@Override
		public void test( String field, String value, Collection<String> extended ) {
			if( value == null ) return;
			if( extended.contains( value ) )
					throw new ValidatorException( field, "EXISTS" );
		}
		
	};
	
	protected static Assertion<Collection<?>,?> NotEmptyColA = new Assertion<Collection<?>, Object>() {

		@Override
		public void test( String field, Collection<?> value, Object unused ) {
			if( value == null  || value.size() == 0 )
					throw new ValidatorException( field, "IS_EMPTY" );
		}
		
	};
	
	/* Idee wie man persistence exceptions handeln könnte.
	 * Ist aber wohl besser das auf servlet-ebene zu machen und das dann
	 * sinnvoll auszuwerten.
	 *
	public static <T> void persist( List<FieldError> result, EntityManager em, T object ){
		try {
			em.persist( object );
		} catch( PersistenceException e ){
			result.add( new FieldError( "PERSIST", e.getMessage() ) );
			throw e;
		}
	}
	public static <T> T merge( List<FieldError> result, EntityManager em, T object ){
		return em.merge( object );
	}
	*/
	
	
	/**
	 * Can be thrown from assert methods to indicate problems
	 * 
	 * largely compatible with
	 * 
	 * @author flo
	 */
	protected static class ValidatorException extends IllegalArgumentException implements FieldException {
		
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
	
	public static class FieldError {
	
		private static final String PREFIX = "ERROR.";
		
		private final String field;
		private final String message;
		
		public FieldError( String field, String message ){
			this.field = field;
			this.message = message;
		}
		
		public String getField() { return field; }
		public String getMessage() { return PREFIX + message; }
	
	}
}
