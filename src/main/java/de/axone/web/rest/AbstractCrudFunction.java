package de.axone.web.rest;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.tools.E;
import de.axone.web.Method;
import de.axone.web.SuperURL;



public abstract class AbstractCrudFunction<OBJECT, DATA, REQUEST extends RestRequest>
extends AbstractRestFunction<DATA,REQUEST>
implements CrudFunction<OBJECT, DATA,REQUEST>
{
	
	private static final String P_ID = "id";

	public AbstractCrudFunction( String name,
			RestFunctionDescription description ) {
		
		super( name, description );
	}

	@Override
	public void doRun( DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {
		
		String id = parameters.get( P_ID );
		E.rr( id );
		
		/*
		ObjectResult<OBJECT> object = null;
		ListResult<OBJECT> list = null;
		*/
		
		switch( Method.from( req ) ){
		
		case GET:
			E.rr( id );
			if( "new".equals( id ) ){
				E.x( "new" );
				doCreate( data, method, out, req, resp );
			} else if( id != null ){
				E.x( "get" );
				doRead( data, method, Long.parseLong( id ), out, req, resp );
			} else {
				E.x( "list" );
				doList( data, method, parameters, out, req, resp );
			}
			break;
		case POST:
		case PUT:
			E.x( "post" );
			doUpdate( data, method, Long.parseLong( id ), out, req, resp );
			break;
		case DELETE:
			E.x( "delete" );
			doDelete( data, method, Long.parseLong( id ), out, req, resp );
			break;
		default:
			throw new IllegalArgumentException( "Unsupported Method: " + req.getMethod() );
		}
		
		/*
		if( object != null ){
			ResultWriter.writeValue( req.mapper(), out, object );
		} else if( list != null ){
			ResultWriter.writeValue( req.mapper(), out, list );
		} else {
			throw new IllegalStateException( "No result" );
		}
		*/
		
	}

}
