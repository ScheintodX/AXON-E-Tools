package de.axone.web.rest;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.axone.web.rest.Validator.ValidatorError;
import de.axone.web.rest.Validator.ValidatorResult;
import de.axone.web.rest.Validator.ValidatorResult.Severity;
import de.axone.web.rest.Validator.ValidatorResultImpl;
import de.axone.web.rest.Validator.ValidatorResultList;

@Test( groups = "tools.rest" )
public class Validator2Test {

	@SuppressWarnings( "unchecked" )
	public void testValidatorResult() {
		
		ValidatorResultImpl res = new ValidatorResultImpl();
		
		res.error( "field", "error-code" );
		
		res.setInfo( "info" );
		res.warn( "field2", "warn-code" );
		
		ValidatorResult sub = res.descent( "sub" );
		
		sub.info( "sub-field", "info-code" );
		
		ValidatorResultList list = res.descentList( "list" );
		ValidatorResult sub1 = list.descent( 1 );
		sub1.info( "in-list1", "info" );
		ValidatorResult sub2 = list.descent( 2 );
		sub2.info( "in-list2", "info" );
		sub2.error( "in-list2-2", "error" );
		
		Map<String,Object> sim = new HashMap<String,Object>();
		sim.put( "field", "blah" );
		sim.put( "sub", new HashMap<String,Object>() );
		List<Object> theList = new LinkedList<>();
		theList.add( new HashMap<String,Object>() );
		theList.add( new HashMap<String,Object>() );
		sim.put( "list", theList );
		
		res.mergeInto( sim );
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable( SerializationFeature.INDENT_OUTPUT );
		
		//E.rr( mapper.writeValueAsString( sim ) );
		
		assertEquals( sim.get( "field" ), "blah" );
		Map<String,Object> errors = (Map<String,Object>) sim.get( Validator.ERROR_KEY );
		assertNotNull( errors );
		assertTrue( errors.containsKey( "field" ) );
		assertTrue( errors.containsKey( "field2" ) );
		
		Map<String,Object> simSub = (Map<String,Object>) sim.get( "sub" );
		assertNotNull( simSub );
		errors = (Map<String,Object>) simSub.get( Validator.ERROR_KEY );
		assertNotNull( errors );
		assertTrue( errors.containsKey( "sub-field" ) );
		
		List<Map<String,Object>> simList = (List<Map<String,Object>>) sim.get( "list" );
		assertNotNull( simList );
		assertEquals( simList.size(), 2 );
		Map<String,Object> simListItem = simList.get( 0 );
		assertFalse( simListItem.containsKey( Validator.ERROR_KEY ) );
		simListItem = simList.get( 1 );
		assertTrue( simListItem.containsKey( Validator.ERROR_KEY ) );
		assertError( simListItem.get( Validator.ERROR_KEY ), "in-list1", Severity.INFO, "info", null );
		
		List<Map<String,String>> asList = new LinkedList<>();
		
		res.writeInto( "", asList );
	}
	
	private static void assertError( Object o, String field, Severity severity, String code, String info ){
		
		assertThat( o ).isInstanceOf( Map.class );
		
		@SuppressWarnings( "unchecked" )
		Map<String,ValidatorError> err = (Map<String,ValidatorError>)o;
		
		assertThat( err ).containsKey( field );
		
		ValidatorError vErr = err.get( field );
		
		assertEquals( vErr.getSeverity(), severity );
		assertEquals( vErr.getCode(), code );
		assertEquals( vErr.getInfo(), info );
		
	}
}
