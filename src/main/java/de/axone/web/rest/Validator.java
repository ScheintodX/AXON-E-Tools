package de.axone.web.rest;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import de.axone.tools.Str;

public abstract class Validator<T> {
	
	private final String name;
	
	public Validator( String name ) {
		this.name = name;
	}

	/**
	 * Validate the given object.
	 * 
	 * This method returns an
	 * 
	 * TODO: Umstellen auf map: "field.name" -> list validators
	 * Damit ließen sich dann validatoren auch direkt anspringen für live validation
	 * HM: Geht das überhaupt? Für einzel-validierungen hat man ja kein object auf dem das läuft ...
	 * 
	 * @param em
	 * @param object
	 * @return List of errors. Empty list if no errors.
	 */
	public FieldErrorList validate( EntityManager em, T object ){
		
		FieldErrorList result = new FieldErrorList();
		
		result = cascade( em, result, this.name, object );
		
		return result;
	}
	
	public FieldErrorList cascade( EntityManager em, FieldErrorList result, T object ){
		
		return cascade( em, result, this.name, object );
	}
	
	public FieldErrorList cascade( EntityManager em, FieldErrorList result, String name, T object ){
		
		try {
			result.descent( name );
			
			doValidate( result, em, object );
			
		} finally {
			result.ascent( name );
		}
		
		return result;
	}
	
	public static <O> FieldErrorList validate( EntityManager em,
			Validator<O> validator, O object ) {
		
		return validator.validate( em, object );
	}
	
	public static <O> FieldErrorList cascade( EntityManager em,
			FieldErrorList result, Validator<O> validator, String name, O object ) {
		
		return validator.cascade( em, result, name, object );
	}
	
	public abstract void doValidate( FieldErrorList result, EntityManager em, T object ) throws ValidatorException;
	
	protected void error( FieldErrorList result, String field, String message ){
		result.add( field, message );
	}
	protected void error( FieldErrorList result, String field, String message, String info ){
		result.add( field, message, info );
	}
	
	public void assertNotNull( FieldErrorList result,
			String field, Object value ){
		
		if( value == null )
			error( result, field , "IS_NULL" );
		
	}
	
	public void assertNoDups( FieldErrorList result,
			String field, List<String> list ){
		
		if( list == null ) return;
		
		Set<String> asSet = new TreeSet<String>( list );
		
		if( list.size() > asSet.size() )
			error( result, field , "HAS_DUPS" );
	}
	
	public void assertEmpty( FieldErrorList result,
			String field, String value ){
		
		if( value != null && value.trim().length() > 0 )
			error( result, field , "IS_NOT_EMPTY" );
	}
	
	public void assertEmpty( FieldErrorList result,
			String field, Collection<?> value ){
		
		if( value != null  && value.size() > 0 )
			error( result, field , "IS_NOT_EMPTY" );
	}
	
	public void assertNotEmpty( FieldErrorList result,
			String field, String value ){
		
		if( value == null || value.trim().length() == 0 )
			error( result, field , "IS_EMPTY" );
	}
	
	public void assertNotEmpty( FieldErrorList result,
			String field, Collection<?> value ){
		
		if( value == null  || value.size() == 0 )
			error( result, field , "IS_EMPTY" );
	}
	
	public <O> void assertExists( FieldErrorList result,
			String field, O value, Collection<O> set ){
		
		if( value == null ) return;
		
		if( set == null || ! set.contains( value ) )
			error( result, field , "DOES_NOT_EXIST" );
		
	}
	
	public <O> void assertNotExists( FieldErrorList result,
			String field, O value, Collection<O> set ){
		
		if( value == null ) return;
		
		if( set != null && set.contains( value ) )
			error( result, field , "EXISTS" );
		
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
		public String getInfo();
		public void setInfo( String info );
		public void setBase( String base );
	}
	public static abstract class AbstractFieldError implements FieldError {
		private String info;
		private String base;
		@Override
		public String getInfo(){ return info; }
		@Override
		public void setInfo( String info ){ this.info = info; };
		@Override
		public void setBase( String base ){
			this.base = base;
		}
		protected String base( String field ){
			if( base == null ) return field;
			return base + "." + field;
		}
		@Override
		public String toString(){
			return getField() + ": " + getMessage() + ( getInfo() != null ? " " + getInfo() : "" );
		}
	}
	
	public static class ThrowableWrapper extends AbstractFieldError {
		
		private final Throwable t;
		
		ThrowableWrapper( Throwable t ){
			this.t = t;
		}
		ThrowableWrapper( Throwable t, String info ){
			this.t = t;
			setInfo( info );
		}
		
		@Override
		public String getField(){ return "UNKNOWN"; }
		@Override
		public String getMessage(){ return PREFIX + t.getMessage(); }
		
	}
	
	public static class FieldErrorWrapper extends AbstractFieldError {
		
		private final ValidatorException e;
		
		FieldErrorWrapper( ValidatorException e ){
			this.e = e;
		}
		FieldErrorWrapper( ValidatorException e, String info ){
			this.e = e;
			setInfo( info );
		}
		
		@Override
		public String getField(){ return base( e.getField() ); }
		@Override
		public String getMessage(){ return PREFIX + e.getMessage(); }
	}
	
	public static class FieldErrorImpl extends AbstractFieldError {
	
		private final String field;
		private final String message;
		
		public FieldErrorImpl( String field, String message ){
			this.field = field;
			this.message = message;
		}
		public FieldErrorImpl( String field, String message, String info ){
			this.field = field;
			this.message = message;
			setInfo( info );
		}
		
		@Override
		public String getField() { return base( field ); }
		@Override
		public String getMessage() { return PREFIX + message; }
		
	}
	
	public static final class FieldErrorList extends LinkedList<FieldError> {
		
		private LinkedList<String> infos = new LinkedList<>();
		private LinkedList<String> bases = new LinkedList<>();
		
		public void add( String field, String message ){
			add( new FieldErrorImpl( field, message ) );
		}
		public void add( String field, String message, String info ){
			add( new FieldErrorImpl( field, message, info ) );
		}

		@Override
		public boolean add( FieldError e ) {
			if( e == null ) return false;
			if( e.getInfo() == null ) e.setInfo( getCurrentInfo() );
			e.setBase( currentBase() );
			return super.add( e );
		}
		
		public boolean hasError(){
			return size() > 0;
		}

		public String getCurrentInfo() {
			if( infos.size() > 0 )
				return infos.getLast();
			else
				return null;
		}

		public void pushInfo( String info ) {
			this.infos.addLast( info );
		}
		public void popInfo(){
			this.infos.removeLast();
		}
		
		private String currentBaseBuffer;
		public void descent( String base ){
			if( base != null ){
				this.currentBaseBuffer = null;
				this.bases.addLast( base );
			}
		}
		public void ascent( String base ){
			if( base != null ){
				this.currentBaseBuffer = null;
				this.bases.removeLast();
			}
		}
		public String currentBase(){
			if( currentBaseBuffer == null ){
				currentBaseBuffer = Str.join( ".", bases );
			}
			return currentBaseBuffer;
		}
		
	}
}
