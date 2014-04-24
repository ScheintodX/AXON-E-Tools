package de.axone.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.exception.IllegalNamedArgumentException;
import de.axone.web.Method;
import de.axone.web.SuperURL;

public class RestFunctionRegistry<DATA, REQUEST extends RestRequest> {

	private static Logger log = LoggerFactory.getLogger( RestFunctionRegistry.class );

	private List<RestFunctionRoute> routes = new LinkedList<>();
	private List<RestFunction<DATA, REQUEST>> functions = new LinkedList<>();
	
	private final int stepBack;
	
	public RestFunctionRegistry(){
		this( 1 );
	}
	public RestFunctionRegistry( int stepBack ){
		this.stepBack = stepBack;
	}

	public void run( DATA data, REQUEST req,
			HttpServletResponse resp ) throws Exception {

		SuperURL url = new SuperURL( req );
		
		if( url.getPath() == null || url.getPath().length() < stepBack ) {

			renderHelp( req, resp );
			
		} else {

			for( int i=0; i<stepBack; i++ ){
				url.getPath().removeFirst();
			}
			
			RestFunction<DATA, REQUEST> f = null;
			Map<String,String> parameters = null;
			for( int i=0; i<routes.size(); i++ ){
				
				RestFunctionRoute route = routes.get( i );
				
				url.getPath().setEndsWithSlash( false );
				
				String path = url.getPath().toString();
				
				parameters = route.match(
						req.getRestMethod(), path );
				
				if( parameters != null ){
					
					f = functions.get( i );
					break;
				}
				
			}

			if( f == null ) {
				
				log.warn( "No function: " + req.getRequestURI() );
				renderHelp( req, resp );
			} else {
				
				log.info( "Client requested {}", f.name() );
				
				url.getPath().removeFirst();
				try {
						
					log.debug( "Running: {}", f.name() );
					
					f.run( data, req.getRestMethod(), parameters, url,
							resp.getWriter(), req, resp );
					
				} catch( Throwable e ){
					
					handleException( f, e, req, resp );
					
				}
			}
		}
	}
	
	protected void handleException( RestFunction<?,?> f,
			Throwable e, REQUEST req, HttpServletResponse resp ) throws IOException{
		
		int status;
		
		if( e instanceof RestFunctionException ){
			status = ((RestFunctionException) e).code();
		} else if( e instanceof LoginException ){
			status = 403;
		} else if( e instanceof IllegalNamedArgumentException ){
			status = 420; // Policy Not Fulfilled
		} else {
			status = 500;
		}
		
		if( !resp.isCommitted() ){
			resp.setStatus( status );
		}
		PrintWriter out = resp.getWriter();
		
		JsonResponse response;
		if( e instanceof IllegalNamedArgumentException ){
			response = JsonResponseImpl.INVALID( (IllegalNamedArgumentException) e );
		} else {
			response = JsonResponseImpl.ERROR( status, e );
		}
		
		req.mapper().writeValue( out, response );
		
		log.error( "Exception while running '" + f.name() + "'", e );
	}
	
	private void renderHelp( RestRequest req, HttpServletResponse resp )
	throws Exception {

		resp.setContentType( "text/html" );
		resp.setCharacterEncoding( "utf-8" );

		PrintWriter out = resp.getWriter();
					
		out.println( "<!DOCTYPE html>" );
		out.println( "<html><head><title>Functions</title>" );
		out.println( "<script src=\"//ajax.googleapis.com/ajax/libs/mootools/1.4.5/mootools-yui-compressed.js\"></script>" );
		out.println( "<script src=\"/static/admin/js/ajax_help.js?yui=false\"></script>" );
		out.println( "</head><body>\n" );
		
		for( RestFunction<DATA, REQUEST> function : functions ){

			function.renderHelp( out, null, false );
		}
		
		out.println( "\n</body></html>" );
	}

	public void register( RestFunction<DATA, REQUEST> function ) {

		register( new RestFunctionRoute.Simple( "/" + function.name() ), function );
	}
	
	public void register( String route, RestFunction<DATA, REQUEST> function ){
		register( new RestFunctionRoute.Simple( route ), function );
	}
	
	public void register( String route, Method methods, RestFunction<DATA, REQUEST> function ){
		register( new RestFunctionRoute.Simple( route, methods ), function );
	}
	
	public void register( String route, EnumSet<Method> methods, RestFunction<DATA, REQUEST> function ){
		register( new RestFunctionRoute.Simple( route, methods ), function );
	}
	
	public void register( RestFunctionRoute route, RestFunction<DATA, REQUEST> function ){
		
		function.setRoute( route );
		
		routes.add( route );
		functions.add( function );
	}
	
}
