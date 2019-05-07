package de.axone.web.rest;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.web.Method;
import de.axone.web.SuperURL;

public abstract class AbstractCrudFunction<ID, DATA, REQUEST extends RestRequest>
extends AbstractRestFunction<DATA,REQUEST> {

	private static final String A_CONFIG = "__CONFIG__";
	private static final String A_NEW = "new";
	private static final String A_HELP = "help";
	protected static final String P_ID = "id";

	public enum Actions{ CREATE, READ, UPDATE, DELETE, LIST, OTHER, CONFIG, HELP };

	public AbstractCrudFunction( String name, boolean hasConfig ) {

		super( makeDescription( name, hasConfig ) );
	}

	abstract public ID parseId( DATA data, Method method, String id, Map<String,String> parameters, REQUEST req ) throws Exception;

	public void doCreateP( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception {

		// Protection left out because this is WebTools and not Micro

		doCreate( data, method, parameters, out, req, resp );
	}

	public void doReadP( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception {

		doRead( data, method, id, parameters, out, req, resp );
	}

	public void doUpdateP( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception {

		doUpdate( data, method, id, parameters, out, req, resp );
	}

	public void doDeleteP( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception {

		doDelete( data, method, id, parameters, out, req, resp );
	}

	public void doListP( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception {

		doList( data, method, parameters, out, req, resp );
	}


	abstract public void doCreate( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;

	abstract public void doRead( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;

	abstract public void doUpdate( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;

	abstract public void doDelete( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;

	abstract public void doList( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;



	private static RestFunctionDescription makeDescription( String name, boolean hasConfig ) {

		RestFunctionDescription description = new RestFunctionDescription( name, "crud functions" );

		description.addMethod( "GET /" + name , "Return a list of " + name )
				.par( "start", "start index" )
				.par( "count", "amount of objects to return" )
				.par( "sort", "name of sorter/field to sort by" )
				.par( "filter", "filter value" )
				.returns( "list", "list of " + name )
		;

		description.addMethod( "GET /" + name + "/:id", "return one " + name + " identified by id" )
				.returns( "object", "the found instance of " + name );

		description.addMethod( "GET /" + name + "/new", "return one new " + name + " object" )
				.returns( "object", "a new instance of " + name + " initialized by default values" );

		if( hasConfig ){
			description.addMethod( "GET /" + name + "/__CONFIG__", "return the configuration and default values" )
					.returns( "object", "a map of key/value config values" );
		}

		description.addMethod( "POST /" + name + "/:id", "save one " + name + " identified by id" )
				.returns( "object", "the saved instance of " + name + " or the posted object if fatal errors occur" );

		description.addMethod( "DELETE /" + name + "/:id", "delete one " + name + " identified by id" )
				.returns( "object", "the deleted instance of " + name );

		return description;
	}

	public void registerMe( RestFunctionRegistry<DATA, REQUEST> registry ) {

		registry.register( new RestFunctionRoute.Simple( "/" + name() + "/:id.*" ), this );
		registry.register( new RestFunctionRoute.Simple( "/" + name() + ".*" ), this );
	}

	@Override
	public void run( DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {

		String idStr = parameters.get( P_ID );

		Actions action = ACTION( data, method, idStr, parameters, url, out, req, resp );

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

	protected Actions ACTION( DATA data, Method method, String idStr,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {

		Actions action = Actions.OTHER;

		switch( method ){

			case GET:
				if( A_NEW.equals( url.getPath().getLast() ) ){
					action = Actions.CREATE;
				} else if( A_HELP.equals( url.getPath().getLast() ) ){
					action = Actions.HELP;
				} else if( A_CONFIG.equals( url.getPath().getLast() ) ){
					action = Actions.CONFIG;
				} else if( url.getPath().length() == 1 && idStr != null ){
					action = Actions.READ;
				} else if( url.getPath().length() == 0 ) {
					action = Actions.LIST;
				}
				break;

			case POST:
			case PUT:
				action = Actions.UPDATE; break;

			case DELETE:
				action = Actions.DELETE; break;

			default:
				throw new IllegalArgumentException( "Unsupported Method: " + method );
		}

		//E.rr( action );

		return action;
	}

	protected void doAction( DATA data, Method method, Actions action, String idStr,
			Map<String, String> parameters, SuperURL url, PrintWriter out,
			REQUEST req, HttpServletResponse resp ) throws Exception {

		ID id;

		switch( action ){
			case CREATE:
				doCreateP( data, method, parameters, out, req, resp ); break;
			case READ:
				id = parseId( data, method, idStr, parameters, req );
				doReadP( data, method, id, parameters, out, req, resp ); break;
			case UPDATE:
				id = parseId( data, method, idStr, parameters, req );
				doUpdateP( data, method, id, parameters, out, req, resp ); break;
			case DELETE:
				id = parseId( data, method, idStr, parameters, req );
				doDeleteP( data, method, id, parameters, out, req, resp ); break;
			case LIST:
				doListP( data, method, parameters, out, req, resp ); break;
			case CONFIG:
				doConfig( data, method, parameters, out, req, resp ); break;
			case OTHER:
				doOther( data, method, parameters, url, out, req, resp ); break;
			case HELP:
				doHelp( data, method, parameters, url, out, req, resp ); break;
			default:
				throw new IllegalArgumentException( "Unknown action: " + action );
		}
	}

	public void doConfig( DATA data, Method method,
			Map<String, String> parameters, PrintWriter out, REQUEST req,
			HttpServletResponse resp ) throws Exception {

		throw new UnsupportedOperationException();
	}

	public void doOther( DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out, REQUEST req,
			HttpServletResponse resp ) throws Exception {

		throw new UnsupportedOperationException();
	}

	public void doHelp( DATA data, Method method,
			Map<String, String> parameters, SuperURL url, PrintWriter out, REQUEST req,
			HttpServletResponse resp ) throws Exception {

		resp.setContentType( "text/html" );

		out.println( description().toHtml( true ) );
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
			return null;
		}

		@Override
		public RestFunctionDescription description() {
			return null;
		}

	}
	*/

}
