package de.axone.web.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.axone.exception.Assert;
import de.axone.tools.Mapper;
import de.axone.web.rest.Validator.ValidatorResult.Severity;

public abstract class Validator<T> {
	
	public static final String ERROR_KEY = "__errors__";
		
	private final String key;
	
	public Validator( String key ){
		this.key = key;
	}

	public ValidatorResult validate( EntityManager em, T object ){
		
		ValidatorResult result = new ValidatorResultImpl();
		validate( result, em, object );
		return result;
	}
	
	public void validate( ValidatorResult result, EntityManager em, T object ){
		try {
			doValidate( result, em, object );
		} catch( ValidatorException e ){
			result.error( e );
		}
	}
	
	public void cascade( ValidatorResult result, EntityManager em, T object ){
		result = result.descent( key );
		validate( result, em, object );
	}
	
	public void iterate( ValidatorResult result, EntityManager em, Iterable<T> objects ){
		
		if( objects == null ) return;
		
		ValidatorResultList list = result.descentList( key );
		
		int i=0;
		for( T o : objects ){
			
			doValidate( list.descent( i++ ), em, o );
		}
	}
	
	protected void doValidate(  ValidatorResult result, EntityManager em, T object ){
		// Override me
	}
	
	public interface ValidatorResult {
		
		public enum Severity {
			FATAL, ERROR, WARN, INFO;
			public boolean is( Severity min ){
				return min.ordinal() >= ordinal();
			}
		}
		
		public void result( Severity severity, String field, String code );
		public void result( Severity severity, String field, String code, String info );
		public void fatal( String field, String code );
		public void fatal( ValidatorException e );
		public void error( String field, String code );
		public void error( ValidatorException e );
		public void warn( String field, String code );
		public void info( String field, String code );
		
		public ValidatorResult descent( String key );
		public ValidatorResultList descentList( String key );
		
		public void setInfo( String info );
		public void clearInfo();
		
		public void mergeInto( Map<String,Object> data );
		public void writeInto( String prefix, List<Map<String,String>> list );
		public List<Map<String,String>> asList( String prefix );
		
		public boolean hasError();
		public boolean isFatal();
	}
	
	public interface ValidatorResultList {
		
		public ValidatorResult descent( int index );
		public boolean hasError();
		public boolean isFatal();
		public void mergeInto( Iterable<Map<String,Object>> it );
		public void writeInto( String prefix, List<Map<String,String>> list );
	}
	
	public interface ValidatorError {
		
		public Severity getSeverity();
		public String getField();
		public String getCode();
		public String getInfo();
		public void setInfo( String info );
	}
	
	protected static class ValidatorErrorImpl implements ValidatorError {
		
		private final String field, code;
		private String info;
		private final ValidatorResult.Severity severity;
		
		public ValidatorErrorImpl( Severity severity, String field, String code ) {
			this( severity, field, code, null );
		}
			
		public ValidatorErrorImpl( Severity severity, String field, String code, String info ) {
			this.severity = severity;
			this.field = field;
			this.code = code;
			this.info = info;
		}

		@Override
		public Severity getSeverity() { return severity; }

		@Override
		@JsonIgnore
		public String getField() { return field; }

		@Override
		public String getCode() { return code; }

		@Override
		public String getInfo() { return info; }

		@Override
		public void setInfo( String info ) {
			this.info = info;
		}

		@Override
		public String toString() {
			return "["
					+ ( field != null ? "field=" + field + ", " : "" )
					+ ( code != null ? "code=" + code + ", " : "" )
					+ ( info != null ? "info=" + info + ", " : "" )
					+ ( severity != null ? "severity=" + severity : "" ) + "]";
		}
	}
	
	public static final class ValidatorResultImpl
	extends LinkedHashMap<String,Object>
	implements ValidatorResult
	{
		
		private String currentInfo;
		
		@Override
		public void fatal( String field, String code ) {
			result( Severity.FATAL, field, code );
		}

		@Override
		public void fatal( ValidatorException e ) {
			result( Severity.FATAL, e.getField(), e.getMessage() );
		}

		@Override
		public void error( String field, String code ) {
			result( Severity.ERROR, field, code );
		}

		@Override
		public void error( ValidatorException e ) {
			result( Severity.ERROR, e.getField(), e.getMessage() );
		}

		@Override
		public void warn( String field, String code ) {
			result( Severity.WARN, field, code );
		}

		@Override
		public void info( String field, String code ) {
			result( Severity.INFO, field, code );
		}
		
		@Override
		public void result( Severity severity, String field, String code ) {
			result( severity, field, code, currentInfo() );
		}
		@Override
		public void result( Severity severity, String field, String code, String info ) {
			Assert.notNull( field, "field" );
			result( new ValidatorErrorImpl( severity, field, code, info ) );
		}
		
		public void result( ValidatorError error ){
			error.setInfo( currentInfo() );
			put( error.getField(), error );
		}

		@Override
		public ValidatorResult descent( String key ) {
			
			ValidatorResultImpl newResult = new ValidatorResultImpl();
			
			put( key, newResult );
			
			return newResult;
		}
		
		@Override
		public ValidatorResultList descentList( String key ){
			
			ValidatorResultList newList = new ValidatorResultListImpl();
			
			put( key, newList );
			
			return newList;
		}

		@Override
		public void setInfo( String info ) {
			this.currentInfo = info;
		}

		@Override
		public void clearInfo() {
			this.currentInfo = null;
		}
		
		protected String currentInfo(){
			return currentInfo;
		}

		@SuppressWarnings( "unchecked" )
		@Override
		public void mergeInto( Map<String, Object> data ) {
			
			for( Map.Entry<String,Object> entry : this.entrySet() ){
				String key = entry.getKey();
				Object value = entry.getValue();
				if( value instanceof ValidatorError ){
					ValidatorError error = (ValidatorError) value;
					Map<String,ValidatorError> errorsField = (Map<String,ValidatorError>)data.get( ERROR_KEY );
					if( errorsField == null ){
						errorsField = new HashMap<>();
						data.put( ERROR_KEY, errorsField );
					}
					errorsField.put( key, error );
				}
				if( value instanceof ValidatorResult ){
					
					Object dataValue = data.get( key );
					
					if( dataValue == null ) continue;
					
					if( !(  dataValue instanceof Map ) )
						throw new RuntimeException( "Value is of wrong format: " + dataValue.getClass() );
					
					((ValidatorResult)value).mergeInto( (Map<String,Object>)dataValue );
					
				}
				if( value instanceof ValidatorResultList ){
					
					Object dataValue = data.get( key );
					
					if( dataValue == null ) continue;
					
					if( !(  dataValue instanceof Iterable ) )
						throw new RuntimeException( "Value is of wrong format: " + dataValue.getClass() );
					
					((ValidatorResultList)value).mergeInto( (Iterable<Map<String,Object>>)dataValue );
				}
			}
		}
		
		@Override
		public String toString(){
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion( Include.NON_EMPTY );
			mapper.enable( SerializationFeature.INDENT_OUTPUT );
			
			try {
				return mapper.writeValueAsString(  this  );
			} catch( JsonProcessingException e ) {
				throw new RuntimeException( e );
			}
		}

		@Override
		public boolean hasError() {
			if( size() == 0 ) return false;
			for( Object value : values() ){
				if( value instanceof ValidatorError &&
						((ValidatorError)value).getSeverity().is( Severity.ERROR ) ) return true;
				if( value instanceof ValidatorResultList )
					if( ((ValidatorResultList)value).hasError() ) return true;
			}
			return false;
		}
		
		@Override
		public boolean isFatal() {
			if( size() == 0 ) return false;
			for( Object value : values() ){
				if( value instanceof ValidatorError &&
						((ValidatorError)value).getSeverity().is( Severity.FATAL ) ) return true;
				if( value instanceof ValidatorResultList )
					if( ((ValidatorResultList)value).isFatal() ) return true;
			}
			return false;
		}


		@Override
		public void writeInto( String prefix, List<Map<String, String>> list ) {
			
			for( Map.Entry<String,Object> entry : entrySet() ){
				
				String key = entry.getKey();
				Object value = entry.getValue();
				
				if( value instanceof ValidatorError ){
					ValidatorError error = (ValidatorError) value;
					list.add( Mapper.hashMap(
							"severity", "" + error.getSeverity(),
							"field", prefix + error.getField(),
							"info", error.getInfo(),
							"code", "ERROR." + error.getCode()
					) );
					
				} else if( value instanceof ValidatorResult ){
					((ValidatorResult)value).writeInto( prefix + key + ".", list );
					
				} else if( value instanceof ValidatorResultList ){
					((ValidatorResultList)value).writeInto( prefix + key + ".", list );
				}
			}
			
		}

		@Override
		public List<Map<String, String>> asList( String prefix ) {
			List<Map<String,String>> result = new LinkedList<>();
			writeInto( prefix, result );
			return result;
		}
		
	}
	
	public static final class ValidatorResultListImpl
	extends LinkedHashMap<Integer,ValidatorResult>
	implements ValidatorResultList
	{
		
		@Override
		public ValidatorResult descent( int index ) {
			
			ValidatorResultImpl newResult = new ValidatorResultImpl();
			
			put( index, newResult );
			
			return newResult;
		}

		@Override
		public boolean hasError() {
			for( ValidatorResult result : this.values() ){
				if( result.hasError() ) return true;
			}
			return false;
		}

		@Override
		public boolean isFatal() {
			for( ValidatorResult result : this.values() ){
				if( result.isFatal() ) return true;
			}
			return false;
		}

		@Override
		public void mergeInto( Iterable<Map<String,Object>> it ){
			int i=0;
			for( Map<String,Object> o : it ){
				
				if( containsKey( i ) ){
					get( i ).mergeInto( o );
				}
				
				i++;
			}
		}

		@Override
		public void writeInto( String prefix, List<Map<String, String>> list ) {
			
			for( Map.Entry<Integer, ValidatorResult> entry : entrySet() ){
				
				entry.getValue().writeInto( prefix + entry.getKey() + ".", list );
			}
			
		}
	}
	
	public static class ValidatorException
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
	
	/* ================================================ */
	/* ==================== ASSERT ==================== */
	/* ================================================ */
	
	public static void assertNotNull( ValidatorResult result,
			String field, Object value ){
		
		if( value == null )
			error( result, field , "IS_NULL" );
		
	}
	
	public static void assertNoDups( ValidatorResult result,
			String field, List<String> list ){
		
		if( list == null ) return;
		
		Set<String> asSet = new TreeSet<String>( list );
		
		if( list.size() > asSet.size() )
			error( result, field , "HAS_DUPS" );
	}
	
	public static void assertEmpty( ValidatorResult result,
			String field, String value ){
		
		if( value != null && value.trim().length() > 0 )
			error( result, field , "IS_NOT_EMPTY" );
	}
	
	public static void assertEmpty( ValidatorResult result,
			String field, Collection<?> value ){
		
		if( value != null  && value.size() > 0 )
			error( result, field , "IS_NOT_EMPTY" );
	}
	
	public static void assertNotEmpty( ValidatorResult result,
			String field, Object value ){
		
		if( value == null || value.toString().trim().length() == 0 )
			error( result, field , "IS_EMPTY" );
	}
	
	public static void assertNotEmpty(
			String field, Object value ){
		
		if( value == null || value.toString().trim().length() == 0 )
			except( field , "IS_EMPTY" );
	}
	
	
	public static void assertNotEmpty( ValidatorResult result,
			String field, Collection<?> value ){
		
		if( value == null  || value.size() == 0 )
			error( result, field , "IS_EMPTY" );
	}
	
	public static <O> void assertExists( ValidatorResult result,
			String field, O value, Collection<O> set ){
		
		if( value == null ) return;
		
		if( set == null || ! set.contains( value ) )
			error( result, field , "DOES_NOT_EXIST" );
		
	}
	
	public static <O> void assertNotExists( ValidatorResult result,
			String field, O value, Collection<O> set ){
		
		if( value == null ) return;
		
		if( set != null && set.contains( value ) )
			error( result, field , "EXISTS" );
		
	}
	
	public static void error( ValidatorResult result, String field, String message ){
		result.error( field, message );
	}
	public static void error( ValidatorResult result, String field, String message, String info ){
		result.result( Severity.ERROR, field, message, info );
	}
	public static void info( ValidatorResult result, String field, String message ){
		result.info( field, message );
	}
	public static void info( ValidatorResult result, String field, String message, String info ){
		result.result( Severity.INFO, field, message, info );
	}
	public static void warn( ValidatorResult result, String field, String message ){
		result.warn( field, message );
	}
	public static void warn( ValidatorResult result, String field, String message, String info ){
		result.result( Severity.WARN, field, message, info );
	}
	
	public static void except( String field, String message ){
		throw new ValidatorException( field, message );
	}
	public static void except( String field ){
		throw new ValidatorException( field, "ILLEGAL_ARGUMENT" );
	}
	
}
