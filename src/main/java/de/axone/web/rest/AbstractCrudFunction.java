package de.axone.web.rest;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.web.Method;
import de.axone.web.SuperURL;

public abstract class AbstractCrudFunction<ID, DATA, REQUEST extends RestRequest>
extends AbstractRestFunction<DATA,REQUEST>
implements CrudFunction<ID, DATA,REQUEST> {
	
	private static final String A_CONFIG = "__CONFIG__";
	private static final String A_NEW = "new";
	protected static final String P_ID = "id";
	
	public enum ACTION{ CREATE, READ, UPDATE, DELETE, LIST, OTHER, CONFIG };
	
	private final RestFunctionDescription description;
	
	//private final String name;

	public AbstractCrudFunction( String name, boolean hasConfig ) {
		
		//this.name = name;
		super( name );
		this.description = makeDescription( name, hasConfig );
	}
	
	/*
	@Override
	public String name(){ return name; }
	*/

	@Override
	public RestFunctionDescription description() {
		return description;
	}
	
	private static RestFunctionDescription makeDescription( String name, boolean hasConfig ) {
		
		RestFunctionDescription description = new RestFunctionDescription( name, "crud functions" );
		
		description.addMethod( "GET /" + name , "Return a list of " + name )
				.addParameter( "start", "start index" )
				.addParameter( "count", "amount of objects to return" )
				.addParameter( "sort", "name of sorter/field to sort by" )
				.addParameter( "filter", "filter value" )
				.setReturnValue( "list", "list of " + name )
		;
		
		description.addMethod( "GET /" + name + "/:id", "return one " + name + " identified by id" )
				.setReturnValue( "object", "the found instance of " + name );
		
		description.addMethod( "GET /" + name + "/new", "return one new " + name + " object" )
				.setReturnValue( "object", "a new instance of " + name + " initialized by default values" );
		
		if( hasConfig ){
			description.addMethod( "GET /" + name + "/__CONFIG__", "return the configuration and default values" )
					.setReturnValue( "object", "a map of key/value config values" );
		}
		
		description.addMethod( "POST /" + name + "/:id", "save one " + name + " identified by id" )
				.setReturnValue( "object", "the saved instance of " + name + " or the posted object if fatal errors occur" );
				
		description.addMethod( "DELETE /" + name + "/:id", "delete one " + name + " identified by id" )
				.setReturnValue( "object", "the deleted instance of " + name );
		
		return description;
	}
	
	@Override
	public void registerMe( RestFunctionRegistry<DATA, REQUEST> registry ) {
		
		registry.register( new RestFunctionRoute.Simple( "/" + name() + "/:id.*" ), this );
		registry.register( new RestFunctionRoute.Simple( "/" + name() + ".*" ), this );
	}
	
	@Override
	public void doRun( DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {
		
		String idStr = parameters.get( P_ID );
		
		ACTION action = ACTION( data, method, idStr, parameters, url, out, req, resp );
		
		doAction( data, method, action, idStr, parameters, url, out, req, resp );
		
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
	
	protected ACTION ACTION( DATA data, Method method, String idStr,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {
		
		ACTION action = ACTION.OTHER;
		
		
		switch( method ){
		
			case GET:
				if( A_NEW.equals( url.getPath().getLast() ) ){
					action = ACTION.CREATE;
				} else if( A_CONFIG.equals( url.getPath().getLast() ) ){
					action = ACTION.CONFIG;
				} else if( url.getPath().length() == 1 && idStr != null ){
					action = ACTION.READ;
				} else if( url.getPath().length() == 0 ) {
					action = ACTION.LIST;
				}
				break;
				
			case POST:
			case PUT:
				action = ACTION.UPDATE; break;
				
			case DELETE:
				action = ACTION.DELETE; break;
				
			default:
				throw new IllegalArgumentException( "Unsupported Method: " + method );
		}
		
		return action;
	}
	
	protected void doAction( DATA data, Method method, ACTION action, String idStr,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {
		
		ID id;
		
		switch( action ){
			case CREATE:
				doCreate( data, method, parameters, out, req, resp ); break;
			case READ:
				id = parseId( data, method, idStr, parameters, req );
				doRead( data, method, id, parameters, out, req, resp ); break;
			case UPDATE:
				id = parseId( data, method, idStr, parameters, req );
				doUpdate( data, method, id, parameters, out, req, resp ); break;
			case DELETE:
				id = parseId( data, method, idStr, parameters, req );
				doDelete( data, method, id, parameters, out, req, resp ); break;
			case LIST:
				doList( data, method, parameters, out, req, resp ); break;
			case CONFIG:
				doConfig( data, method, parameters, out, req, resp ); break;
			case OTHER:
				doOther( data, method, parameters, url, out, req, resp ); break;
			default:
				throw new IllegalArgumentException( "Unknown action: " + action );
		}
	}

	@Override
	public void doConfig( DATA data, Method method,
			Map<String, String> parameters, PrintWriter out, REQUEST req,
			HttpServletResponse resp ) throws Exception {
		
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void doOther( DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out, REQUEST req,
			HttpServletResponse resp ) throws Exception {
		
		throw new UnsupportedOperationException();
	}

	
	protected interface M<DATA,REQUEST> {
		public void run(  DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out, REQUEST req,
			HttpServletResponse resp ) throws Exception;
	}

	
	/*
	protected static class RestFunctionToMethod<ID, DATA, REQUEST> implements RestFunction<DATA,REQUEST> {
		
		final java.lang.reflect.Method call;
		final Object self;

		@Override
		public void run( DATA data, Method method,
				Map<String, String> parameters, SuperURL url, PrintWriter out,
				REQUEST req, HttpServletResponse resp ) throws Exception {
			
			call.invoke( self, data, method, parameters, url, out, req, resp );
		}

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RestFunctionDescription description() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	*/

}
