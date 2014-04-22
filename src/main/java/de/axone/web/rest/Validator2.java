package de.axone.web.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.axone.tools.Mapper;
import de.axone.web.rest.Validator2.Validator2Result.Severity;

public abstract class Validator2<T> {
	
	public static final String ERROR_KEY = "__errors__";
		
	private final String key;
	
	public Validator2( String key ){
		this.key = key;
	}

	public Validator2Result validate( EntityManager em, T object ){
		
		Validator2Result result = new Validator2ResultImpl();
		validate( result, em, object );
		return result;
	}
	public void validate( Validator2Result result, EntityManager em, T object ){
		doValidate( result, em, object );
	}
	
	public void cascade( Validator2Result result, EntityManager em, T object ){
		result = result.descent( key );
		doValidate( result, em, object );
	}
	
	public void iterate( Validator2Result result, EntityManager em, Iterable<T> objects ){
		
		Validator2ResultList list = result.descentList( key );
		
		int i=0;
		for( T o : objects ){
			
			doValidate( list.descent( i++ ), em, o );
		}
	}
	
	protected void doValidate(  Validator2Result result, EntityManager em, T object ){
		
	}
	
	public interface Validator2Result {
		
		public enum Severity { error, warn, info; }
		
		public void result( Severity severity, String field, String code );
		public void result( Severity severity, String field, String code, String info );
		public void error( String field, String code );
		public void warn( String field, String code );
		public void info( String field, String code );
		
		public Validator2Result descent( String key );
		public Validator2ResultList descentList( String key );
		
		public void setInfo( String info );
		public void clearInfo();
		
		public void mergeInto( Map<String,Object> data );
		public void writeInto( String prefix, List<Map<String,String>> list );
		
		public boolean hasError();
	}
	
	public interface Validator2ResultList {
		
		public Validator2Result descent( int index );
		public boolean hasError();
		public void mergeInto( Iterable<Map<String,Object>> it );
		public void writeInto( String prefix, List<Map<String,String>> list );
	}
	
	public interface Validator2Error {
		
		public Severity getSeverity();
		public String getField();
		public String getCode();
		public String getInfo();
		public void setInfo( String info );
	}
	
	protected static class Validator2ErrorImpl implements Validator2Error {
		
		private final String field, code;
		private String info;
		private final Validator2Result.Severity severity;
		
		public Validator2ErrorImpl( Severity severity, String field, String code ) {
			this( severity, field, code, null );
		}
			
		public Validator2ErrorImpl( Severity severity, String field, String code, String info ) {
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
	
	public static final class Validator2ResultImpl
	extends LinkedHashMap<String,Object>
	implements Validator2Result
	{
		
		private String currentInfo;
		
		@Override
		public void error( String field, String code ) {
			result( Severity.error, field, code );
		}

		@Override
		public void warn( String field, String code ) {
			result( Severity.warn, field, code );
		}

		@Override
		public void info( String field, String code ) {
			result( Severity.info, field, code );
		}
		
		@Override
		public void result( Severity severity, String field, String code ) {
			result( severity, field, code, currentInfo() );
		}
		@Override
		public void result( Severity severity, String field, String code, String info ) {
			result( new Validator2ErrorImpl( severity, field, code, info ) );
		}
		
		public void result( Validator2Error error ){
			error.setInfo( currentInfo() );
			put( error.getField(), error );
		}

		@Override
		public Validator2Result descent( String key ) {
			
			Validator2ResultImpl newResult = new Validator2ResultImpl();
			
			put( key, newResult );
			
			return newResult;
		}
		
		@Override
		public Validator2ResultList descentList( String key ){
			
			Validator2ResultList newList = new Validator2ResultListImpl();
			
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
				if( value instanceof Validator2Error ){
					Validator2Error error = (Validator2Error) value;
					Map<String,Validator2Error> errorsField = (Map<String,Validator2Error>)data.get( ERROR_KEY );
					if( errorsField == null ){
						errorsField = new HashMap<>();
						data.put( ERROR_KEY, errorsField );
					}
					errorsField.put( key, error );
				}
				if( value instanceof Validator2Result ){
					
					Object dataValue = data.get( key );
					
					if( dataValue == null ) continue;
					
					if( !(  dataValue instanceof Map ) )
						throw new RuntimeException( "Value is of wrong format: " + dataValue.getClass() );
					
					((Validator2Result)value).mergeInto( (Map<String,Object>)dataValue );
					
				}
				if( value instanceof Validator2ResultList ){
					
					Object dataValue = data.get( key );
					
					if( dataValue == null ) continue;
					
					if( !(  dataValue instanceof Iterable ) )
						throw new RuntimeException( "Value is of wrong format: " + dataValue.getClass() );
					
					((Validator2ResultList)value).mergeInto( (Iterable<Map<String,Object>>)dataValue );
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
				if( value instanceof Validator2Error ) return true;
				if( value instanceof Validator2ResultList )
					if( ((Validator2ResultList)value).hasError() ) return true;
			}
			return false;
		}

		@Override
		public void writeInto( String prefix, List<Map<String, String>> list ) {
			
			for( Map.Entry<String,Object> entry : entrySet() ){
				
				String key = entry.getKey();
				Object value = entry.getValue();
				
				if( value instanceof Validator2Error ){
					Validator2Error error = (Validator2Error) value;
					list.add( Mapper.hashMap(
							"severity", "" + error.getSeverity(),
							"field", prefix + error.getField(),
							"info", error.getInfo(),
							"message", error.getSeverity() + "." + error.getCode()
					) );
					
				} else if( value instanceof Validator2Result ){
					((Validator2Result)value).writeInto( prefix + key + ".", list );
					
				} else if( value instanceof Validator2ResultList ){
					((Validator2ResultList)value).writeInto( prefix + key + ".", list );
				}
			}
			
		}
		
	}
	
	public static final class Validator2ResultListImpl
	extends LinkedHashMap<Integer,Validator2Result>
	implements Validator2ResultList
	{
		
		@Override
		public Validator2Result descent( int index ) {
			
			Validator2ResultImpl newResult = new Validator2ResultImpl();
			
			put( index, newResult );
			
			return newResult;
		}

		@Override
		public boolean hasError() {
			for( Validator2Result result : this.values() ){
				if( result.hasError() ) return true;
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
			
			for( Map.Entry<Integer, Validator2Result> entry : entrySet() ){
				
				entry.getValue().writeInto( prefix + entry.getKey() + ".", list );
			}
			
		}
	}
	
}
